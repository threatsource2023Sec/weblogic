package org.antlr.runtime.debug;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.misc.DoubleKeyMap;

public class Profiler extends BlankDebugEventListener {
   public static final String DATA_SEP = "\t";
   public static final String newline = System.getProperty("line.separator");
   static boolean dump = false;
   public static final String Version = "3";
   public static final String RUNTIME_STATS_FILENAME = "runtime.stats";
   public DebugParser parser = null;
   protected int ruleLevel = 0;
   protected Token lastRealTokenTouchedInDecision;
   protected Set uniqueRules = new HashSet();
   protected Stack currentGrammarFileName = new Stack();
   protected Stack currentRuleName = new Stack();
   protected Stack currentLine = new Stack();
   protected Stack currentPos = new Stack();
   protected DoubleKeyMap decisions = new DoubleKeyMap();
   protected List decisionEvents = new ArrayList();
   protected Stack decisionStack = new Stack();
   protected int backtrackDepth;
   ProfileStats stats = new ProfileStats();

   public Profiler() {
   }

   public Profiler(DebugParser parser) {
      this.parser = parser;
   }

   public void enterRule(String grammarFileName, String ruleName) {
      ++this.ruleLevel;
      ++this.stats.numRuleInvocations;
      this.uniqueRules.add(grammarFileName + ":" + ruleName);
      this.stats.maxRuleInvocationDepth = Math.max(this.stats.maxRuleInvocationDepth, this.ruleLevel);
      this.currentGrammarFileName.push(grammarFileName);
      this.currentRuleName.push(ruleName);
   }

   public void exitRule(String grammarFileName, String ruleName) {
      --this.ruleLevel;
      this.currentGrammarFileName.pop();
      this.currentRuleName.pop();
   }

   public void examineRuleMemoization(IntStream input, int ruleIndex, int stopIndex, String ruleName) {
      if (dump) {
         System.out.println("examine memo " + ruleName + " at " + input.index() + ": " + stopIndex);
      }

      if (stopIndex == -1) {
         ++this.stats.numMemoizationCacheMisses;
         ++this.stats.numGuessingRuleInvocations;
         ++this.currentDecision().numMemoizationCacheMisses;
      } else {
         ++this.stats.numMemoizationCacheHits;
         ++this.currentDecision().numMemoizationCacheHits;
      }

   }

   public void memoize(IntStream input, int ruleIndex, int ruleStartIndex, String ruleName) {
      if (dump) {
         System.out.println("memoize " + ruleName);
      }

      ++this.stats.numMemoizationCacheEntries;
   }

   public void location(int line, int pos) {
      this.currentLine.push(line);
      this.currentPos.push(pos);
   }

   public void enterDecision(int decisionNumber, boolean couldBacktrack) {
      this.lastRealTokenTouchedInDecision = null;
      ++this.stats.numDecisionEvents;
      int startingLookaheadIndex = this.parser.getTokenStream().index();
      TokenStream input = this.parser.getTokenStream();
      if (dump) {
         System.out.println("enterDecision canBacktrack=" + couldBacktrack + " " + decisionNumber + " backtrack depth " + this.backtrackDepth + " @ " + input.get(input.index()) + " rule " + this.locationDescription());
      }

      String g = (String)this.currentGrammarFileName.peek();
      DecisionDescriptor descriptor = (DecisionDescriptor)this.decisions.get(g, decisionNumber);
      if (descriptor == null) {
         descriptor = new DecisionDescriptor();
         this.decisions.put(g, decisionNumber, descriptor);
         descriptor.decision = decisionNumber;
         descriptor.fileName = (String)this.currentGrammarFileName.peek();
         descriptor.ruleName = (String)this.currentRuleName.peek();
         descriptor.line = (Integer)this.currentLine.peek();
         descriptor.pos = (Integer)this.currentPos.peek();
         descriptor.couldBacktrack = couldBacktrack;
      }

      ++descriptor.n;
      DecisionEvent d = new DecisionEvent();
      this.decisionStack.push(d);
      d.decision = descriptor;
      d.startTime = System.currentTimeMillis();
      d.startIndex = startingLookaheadIndex;
   }

   public void exitDecision(int decisionNumber) {
      DecisionEvent d = (DecisionEvent)this.decisionStack.pop();
      d.stopTime = System.currentTimeMillis();
      int lastTokenIndex = this.lastRealTokenTouchedInDecision.getTokenIndex();
      int numHidden = this.getNumberOfHiddenTokens(d.startIndex, lastTokenIndex);
      int depth = lastTokenIndex - d.startIndex - numHidden + 1;
      d.k = depth;
      d.decision.maxk = Math.max(d.decision.maxk, depth);
      if (dump) {
         System.out.println("exitDecision " + decisionNumber + " in " + d.decision.ruleName + " lookahead " + d.k + " max token " + this.lastRealTokenTouchedInDecision);
      }

      this.decisionEvents.add(d);
   }

   public void consumeToken(Token token) {
      if (dump) {
         System.out.println("consume token " + token);
      }

      if (!this.inDecision()) {
         ++this.stats.numTokens;
      } else {
         if (this.lastRealTokenTouchedInDecision == null || this.lastRealTokenTouchedInDecision.getTokenIndex() < token.getTokenIndex()) {
            this.lastRealTokenTouchedInDecision = token;
         }

         DecisionEvent d = this.currentDecision();
         int thisRefIndex = token.getTokenIndex();
         int numHidden = this.getNumberOfHiddenTokens(d.startIndex, thisRefIndex);
         int depth = thisRefIndex - d.startIndex - numHidden + 1;
         if (dump) {
            System.out.println("consume " + thisRefIndex + " " + depth + " tokens ahead in " + d.decision.ruleName + "-" + d.decision.decision + " start index " + d.startIndex);
         }

      }
   }

   public boolean inDecision() {
      return this.decisionStack.size() > 0;
   }

   public void consumeHiddenToken(Token token) {
      if (!this.inDecision()) {
         ++this.stats.numHiddenTokens;
      }

   }

   public void LT(int i, Token t) {
      if (this.inDecision() && i > 0) {
         DecisionEvent d = this.currentDecision();
         if (dump) {
            System.out.println("LT(" + i + ")=" + t + " index " + t.getTokenIndex() + " relative to " + d.decision.ruleName + "-" + d.decision.decision + " start index " + d.startIndex);
         }

         if (this.lastRealTokenTouchedInDecision == null || this.lastRealTokenTouchedInDecision.getTokenIndex() < t.getTokenIndex()) {
            this.lastRealTokenTouchedInDecision = t;
            if (dump) {
               System.out.println("set last token " + this.lastRealTokenTouchedInDecision);
            }
         }
      }

   }

   public void beginBacktrack(int level) {
      if (dump) {
         System.out.println("enter backtrack " + level);
      }

      ++this.backtrackDepth;
      DecisionEvent e = this.currentDecision();
      if (e.decision.couldBacktrack) {
         ++this.stats.numBacktrackOccurrences;
         ++e.decision.numBacktrackOccurrences;
         e.backtracks = true;
      }

   }

   public void endBacktrack(int level, boolean successful) {
      if (dump) {
         System.out.println("exit backtrack " + level + ": " + successful);
      }

      --this.backtrackDepth;
   }

   public void mark(int i) {
      if (dump) {
         System.out.println("mark " + i);
      }

   }

   public void rewind(int i) {
      if (dump) {
         System.out.println("rewind " + i);
      }

   }

   public void rewind() {
      if (dump) {
         System.out.println("rewind");
      }

   }

   protected DecisionEvent currentDecision() {
      return (DecisionEvent)this.decisionStack.peek();
   }

   public void recognitionException(RecognitionException e) {
      ++this.stats.numReportedErrors;
   }

   public void semanticPredicate(boolean result, String predicate) {
      ++this.stats.numSemanticPredicates;
      if (this.inDecision()) {
         DecisionEvent d = this.currentDecision();
         d.evalSemPred = true;
         ++d.decision.numSemPredEvals;
         if (dump) {
            System.out.println("eval " + predicate + " in " + d.decision.ruleName + "-" + d.decision.decision);
         }
      }

   }

   public void terminate() {
      Iterator i$ = this.decisionEvents.iterator();

      ProfileStats var5;
      while(i$.hasNext()) {
         DecisionEvent e = (DecisionEvent)i$.next();
         DecisionDescriptor var10000 = e.decision;
         var10000.avgk += (float)e.k;
         var5 = this.stats;
         var5.avgkPerDecisionEvent += (float)e.k;
         if (e.backtracks) {
            var5 = this.stats;
            var5.avgkPerBacktrackingDecisionEvent += (float)e.k;
         }
      }

      this.stats.averageDecisionPercentBacktracks = 0.0F;
      i$ = this.decisions.values().iterator();

      while(i$.hasNext()) {
         DecisionDescriptor d = (DecisionDescriptor)i$.next();
         ++this.stats.numDecisionsCovered;
         d.avgk = (float)((double)d.avgk / (double)d.n);
         if (d.couldBacktrack) {
            ++this.stats.numDecisionsThatPotentiallyBacktrack;
            float percentBacktracks = (float)d.numBacktrackOccurrences / (float)d.n;
            var5 = this.stats;
            var5.averageDecisionPercentBacktracks += percentBacktracks;
         }

         if (d.numBacktrackOccurrences > 0) {
            ++this.stats.numDecisionsThatDoBacktrack;
         }
      }

      var5 = this.stats;
      var5.averageDecisionPercentBacktracks /= (float)this.stats.numDecisionsThatPotentiallyBacktrack;
      var5 = this.stats;
      var5.averageDecisionPercentBacktracks *= 100.0F;
      var5 = this.stats;
      var5.avgkPerDecisionEvent /= (float)this.stats.numDecisionEvents;
      var5 = this.stats;
      var5.avgkPerBacktrackingDecisionEvent = (float)((double)var5.avgkPerBacktrackingDecisionEvent / (double)this.stats.numBacktrackOccurrences);
      System.err.println(this.toString());
      System.err.println(this.getDecisionStatsDump());
   }

   public void setParser(DebugParser parser) {
      this.parser = parser;
   }

   public String toNotifyString() {
      StringBuilder buf = new StringBuilder();
      buf.append("3");
      buf.append('\t');
      buf.append(this.parser.getClass().getName());
      return buf.toString();
   }

   public String toString() {
      return toString(this.getReport());
   }

   public ProfileStats getReport() {
      this.stats.Version = "3";
      this.stats.name = this.parser.getClass().getName();
      this.stats.numUniqueRulesInvoked = this.uniqueRules.size();
      return this.stats;
   }

   public DoubleKeyMap getDecisionStats() {
      return this.decisions;
   }

   public List getDecisionEvents() {
      return this.decisionEvents;
   }

   public static String toString(ProfileStats stats) {
      StringBuilder buf = new StringBuilder();
      buf.append("ANTLR Runtime Report; Profile Version ");
      buf.append(stats.Version);
      buf.append(newline);
      buf.append("parser name ");
      buf.append(stats.name);
      buf.append(newline);
      buf.append("Number of rule invocations ");
      buf.append(stats.numRuleInvocations);
      buf.append(newline);
      buf.append("Number of unique rules visited ");
      buf.append(stats.numUniqueRulesInvoked);
      buf.append(newline);
      buf.append("Number of decision events ");
      buf.append(stats.numDecisionEvents);
      buf.append(newline);
      buf.append("Overall average k per decision event ");
      buf.append(stats.avgkPerDecisionEvent);
      buf.append(newline);
      buf.append("Number of backtracking occurrences (can be multiple per decision) ");
      buf.append(stats.numBacktrackOccurrences);
      buf.append(newline);
      buf.append("Overall average k per decision event that backtracks ");
      buf.append(stats.avgkPerBacktrackingDecisionEvent);
      buf.append(newline);
      buf.append("Number of rule invocations while backtracking ");
      buf.append(stats.numGuessingRuleInvocations);
      buf.append(newline);
      buf.append("num decisions that potentially backtrack ");
      buf.append(stats.numDecisionsThatPotentiallyBacktrack);
      buf.append(newline);
      buf.append("num decisions that do backtrack ");
      buf.append(stats.numDecisionsThatDoBacktrack);
      buf.append(newline);
      buf.append("num decisions that potentially backtrack but don't ");
      buf.append(stats.numDecisionsThatPotentiallyBacktrack - stats.numDecisionsThatDoBacktrack);
      buf.append(newline);
      buf.append("average % of time a potentially backtracking decision backtracks ");
      buf.append(stats.averageDecisionPercentBacktracks);
      buf.append(newline);
      buf.append("num unique decisions covered ");
      buf.append(stats.numDecisionsCovered);
      buf.append(newline);
      buf.append("max rule invocation nesting depth ");
      buf.append(stats.maxRuleInvocationDepth);
      buf.append(newline);
      buf.append("rule memoization cache size ");
      buf.append(stats.numMemoizationCacheEntries);
      buf.append(newline);
      buf.append("number of rule memoization cache hits ");
      buf.append(stats.numMemoizationCacheHits);
      buf.append(newline);
      buf.append("number of rule memoization cache misses ");
      buf.append(stats.numMemoizationCacheMisses);
      buf.append(newline);
      buf.append("number of tokens ");
      buf.append(stats.numTokens);
      buf.append(newline);
      buf.append("number of hidden tokens ");
      buf.append(stats.numHiddenTokens);
      buf.append(newline);
      buf.append("number of char ");
      buf.append(stats.numCharsMatched);
      buf.append(newline);
      buf.append("number of hidden char ");
      buf.append(stats.numHiddenCharsMatched);
      buf.append(newline);
      buf.append("number of syntax errors ");
      buf.append(stats.numReportedErrors);
      buf.append(newline);
      return buf.toString();
   }

   public String getDecisionStatsDump() {
      StringBuilder buf = new StringBuilder();
      buf.append("location");
      buf.append("\t");
      buf.append("n");
      buf.append("\t");
      buf.append("avgk");
      buf.append("\t");
      buf.append("maxk");
      buf.append("\t");
      buf.append("synpred");
      buf.append("\t");
      buf.append("sempred");
      buf.append("\t");
      buf.append("canbacktrack");
      buf.append("\n");
      Iterator i$ = this.decisions.keySet().iterator();

      while(i$.hasNext()) {
         String fileName = (String)i$.next();
         Iterator i$ = this.decisions.keySet(fileName).iterator();

         while(i$.hasNext()) {
            int d = (Integer)i$.next();
            DecisionDescriptor s = (DecisionDescriptor)this.decisions.get(fileName, d);
            buf.append(s.decision);
            buf.append("@");
            buf.append(this.locationDescription(s.fileName, s.ruleName, s.line, s.pos));
            buf.append("\t");
            buf.append(s.n);
            buf.append("\t");
            buf.append(String.format("%.2f", s.avgk));
            buf.append("\t");
            buf.append(s.maxk);
            buf.append("\t");
            buf.append(s.numBacktrackOccurrences);
            buf.append("\t");
            buf.append(s.numSemPredEvals);
            buf.append("\t");
            buf.append(s.couldBacktrack ? "1" : "0");
            buf.append(newline);
         }
      }

      return buf.toString();
   }

   protected int[] trim(int[] X, int n) {
      if (n < X.length) {
         int[] trimmed = new int[n];
         System.arraycopy(X, 0, trimmed, 0, n);
         X = trimmed;
      }

      return X;
   }

   protected int[] toArray(List a) {
      int[] x = new int[a.size()];

      for(int i = 0; i < a.size(); ++i) {
         Integer I = (Integer)a.get(i);
         x[i] = I;
      }

      return x;
   }

   public int getNumberOfHiddenTokens(int i, int j) {
      int n = 0;
      TokenStream input = this.parser.getTokenStream();

      for(int ti = i; ti < input.size() && ti <= j; ++ti) {
         Token t = input.get(ti);
         if (t.getChannel() != 0) {
            ++n;
         }
      }

      return n;
   }

   protected String locationDescription() {
      return this.locationDescription((String)this.currentGrammarFileName.peek(), (String)this.currentRuleName.peek(), (Integer)this.currentLine.peek(), (Integer)this.currentPos.peek());
   }

   protected String locationDescription(String file, String rule, int line, int pos) {
      return file + ":" + line + ":" + pos + "(" + rule + ")";
   }

   public static class DecisionEvent {
      public DecisionDescriptor decision;
      public int startIndex;
      public int k;
      public boolean backtracks;
      public boolean evalSemPred;
      public long startTime;
      public long stopTime;
      public int numMemoizationCacheHits;
      public int numMemoizationCacheMisses;
   }

   public static class DecisionDescriptor {
      public int decision;
      public String fileName;
      public String ruleName;
      public int line;
      public int pos;
      public boolean couldBacktrack;
      public int n;
      public float avgk;
      public int maxk;
      public int numBacktrackOccurrences;
      public int numSemPredEvals;
   }

   public static class ProfileStats {
      public String Version;
      public String name;
      public int numRuleInvocations;
      public int numUniqueRulesInvoked;
      public int numDecisionEvents;
      public int numDecisionsCovered;
      public int numDecisionsThatPotentiallyBacktrack;
      public int numDecisionsThatDoBacktrack;
      public int maxRuleInvocationDepth;
      public float avgkPerDecisionEvent;
      public float avgkPerBacktrackingDecisionEvent;
      public float averageDecisionPercentBacktracks;
      public int numBacktrackOccurrences;
      public int numFixedDecisions;
      public int minDecisionMaxFixedLookaheads;
      public int maxDecisionMaxFixedLookaheads;
      public int avgDecisionMaxFixedLookaheads;
      public int stddevDecisionMaxFixedLookaheads;
      public int numCyclicDecisions;
      public int minDecisionMaxCyclicLookaheads;
      public int maxDecisionMaxCyclicLookaheads;
      public int avgDecisionMaxCyclicLookaheads;
      public int stddevDecisionMaxCyclicLookaheads;
      public int numSemanticPredicates;
      public int numTokens;
      public int numHiddenTokens;
      public int numCharsMatched;
      public int numHiddenCharsMatched;
      public int numReportedErrors;
      public int numMemoizationCacheHits;
      public int numMemoizationCacheMisses;
      public int numGuessingRuleInvocations;
      public int numMemoizationCacheEntries;
   }
}
