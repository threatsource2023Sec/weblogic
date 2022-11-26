package org.python.antlr.runtime.debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.python.antlr.runtime.CommonToken;
import org.python.antlr.runtime.IntStream;
import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.Token;
import org.python.antlr.runtime.TokenStream;
import org.python.antlr.runtime.misc.Stats;

public class Profiler extends BlankDebugEventListener {
   public static final String Version = "2";
   public static final String RUNTIME_STATS_FILENAME = "runtime.stats";
   public static final int NUM_RUNTIME_STATS = 29;
   public DebugParser parser = null;
   protected int ruleLevel = 0;
   protected int decisionLevel = 0;
   protected int maxLookaheadInCurrentDecision = 0;
   protected CommonToken lastTokenConsumed = null;
   protected List lookaheadStack = new ArrayList();
   public int numRuleInvocations = 0;
   public int numGuessingRuleInvocations = 0;
   public int maxRuleInvocationDepth = 0;
   public int numFixedDecisions = 0;
   public int numCyclicDecisions = 0;
   public int numBacktrackDecisions = 0;
   public int[] decisionMaxFixedLookaheads = new int[200];
   public int[] decisionMaxCyclicLookaheads = new int[200];
   public List decisionMaxSynPredLookaheads = new ArrayList();
   public int numHiddenTokens = 0;
   public int numCharsMatched = 0;
   public int numHiddenCharsMatched = 0;
   public int numSemanticPredicates = 0;
   public int numSyntacticPredicates = 0;
   protected int numberReportedErrors = 0;
   public int numMemoizationCacheMisses = 0;
   public int numMemoizationCacheHits = 0;
   public int numMemoizationCacheEntries = 0;

   public Profiler() {
   }

   public Profiler(DebugParser parser) {
      this.parser = parser;
   }

   public void enterRule(String grammarFileName, String ruleName) {
      ++this.ruleLevel;
      ++this.numRuleInvocations;
      if (this.ruleLevel > this.maxRuleInvocationDepth) {
         this.maxRuleInvocationDepth = this.ruleLevel;
      }

   }

   public void examineRuleMemoization(IntStream input, int ruleIndex, String ruleName) {
      int stopIndex = this.parser.getRuleMemoization(ruleIndex, input.index());
      if (stopIndex == -1) {
         ++this.numMemoizationCacheMisses;
         ++this.numGuessingRuleInvocations;
      } else {
         ++this.numMemoizationCacheHits;
      }

   }

   public void memoize(IntStream input, int ruleIndex, int ruleStartIndex, String ruleName) {
      ++this.numMemoizationCacheEntries;
   }

   public void exitRule(String grammarFileName, String ruleName) {
      --this.ruleLevel;
   }

   public void enterDecision(int decisionNumber) {
      ++this.decisionLevel;
      int startingLookaheadIndex = this.parser.getTokenStream().index();
      this.lookaheadStack.add(new Integer(startingLookaheadIndex));
   }

   public void exitDecision(int decisionNumber) {
      if (this.parser.isCyclicDecision) {
         ++this.numCyclicDecisions;
      } else {
         ++this.numFixedDecisions;
      }

      this.lookaheadStack.remove(this.lookaheadStack.size() - 1);
      --this.decisionLevel;
      int[] bigger;
      if (this.parser.isCyclicDecision) {
         if (this.numCyclicDecisions >= this.decisionMaxCyclicLookaheads.length) {
            bigger = new int[this.decisionMaxCyclicLookaheads.length * 2];
            System.arraycopy(this.decisionMaxCyclicLookaheads, 0, bigger, 0, this.decisionMaxCyclicLookaheads.length);
            this.decisionMaxCyclicLookaheads = bigger;
         }

         this.decisionMaxCyclicLookaheads[this.numCyclicDecisions - 1] = this.maxLookaheadInCurrentDecision;
      } else {
         if (this.numFixedDecisions >= this.decisionMaxFixedLookaheads.length) {
            bigger = new int[this.decisionMaxFixedLookaheads.length * 2];
            System.arraycopy(this.decisionMaxFixedLookaheads, 0, bigger, 0, this.decisionMaxFixedLookaheads.length);
            this.decisionMaxFixedLookaheads = bigger;
         }

         this.decisionMaxFixedLookaheads[this.numFixedDecisions - 1] = this.maxLookaheadInCurrentDecision;
      }

      this.parser.isCyclicDecision = false;
      this.maxLookaheadInCurrentDecision = 0;
   }

   public void consumeToken(Token token) {
      this.lastTokenConsumed = (CommonToken)token;
   }

   public boolean inDecision() {
      return this.decisionLevel > 0;
   }

   public void consumeHiddenToken(Token token) {
      this.lastTokenConsumed = (CommonToken)token;
   }

   public void LT(int i, Token t) {
      if (this.inDecision()) {
         int stackTop = this.lookaheadStack.size() - 1;
         Integer startingIndex = (Integer)this.lookaheadStack.get(stackTop);
         int thisRefIndex = this.parser.getTokenStream().index();
         int numHidden = this.getNumberOfHiddenTokens(startingIndex, thisRefIndex);
         int depth = i + thisRefIndex - startingIndex - numHidden;
         if (depth > this.maxLookaheadInCurrentDecision) {
            this.maxLookaheadInCurrentDecision = depth;
         }
      }

   }

   public void beginBacktrack(int level) {
      ++this.numBacktrackDecisions;
   }

   public void endBacktrack(int level, boolean successful) {
      this.decisionMaxSynPredLookaheads.add(new Integer(this.maxLookaheadInCurrentDecision));
   }

   public void recognitionException(RecognitionException e) {
      ++this.numberReportedErrors;
   }

   public void semanticPredicate(boolean result, String predicate) {
      if (this.inDecision()) {
         ++this.numSemanticPredicates;
      }

   }

   public void terminate() {
      String stats = this.toNotifyString();

      try {
         Stats.writeReport("runtime.stats", stats);
      } catch (IOException var3) {
         System.err.println(var3);
         var3.printStackTrace(System.err);
      }

      System.out.println(toString(stats));
   }

   public void setParser(DebugParser parser) {
      this.parser = parser;
   }

   public String toNotifyString() {
      TokenStream input = this.parser.getTokenStream();

      for(int i = 0; i < input.size() && this.lastTokenConsumed != null && i <= this.lastTokenConsumed.getTokenIndex(); ++i) {
         Token t = input.get(i);
         if (t.getChannel() != 0) {
            ++this.numHiddenTokens;
            this.numHiddenCharsMatched += t.getText().length();
         }
      }

      this.numCharsMatched = this.lastTokenConsumed.getStopIndex() + 1;
      this.decisionMaxFixedLookaheads = this.trim(this.decisionMaxFixedLookaheads, this.numFixedDecisions);
      this.decisionMaxCyclicLookaheads = this.trim(this.decisionMaxCyclicLookaheads, this.numCyclicDecisions);
      StringBuffer buf = new StringBuffer();
      buf.append("2");
      buf.append('\t');
      buf.append(this.parser.getClass().getName());
      buf.append('\t');
      buf.append(this.numRuleInvocations);
      buf.append('\t');
      buf.append(this.maxRuleInvocationDepth);
      buf.append('\t');
      buf.append(this.numFixedDecisions);
      buf.append('\t');
      buf.append(Stats.min(this.decisionMaxFixedLookaheads));
      buf.append('\t');
      buf.append(Stats.max(this.decisionMaxFixedLookaheads));
      buf.append('\t');
      buf.append(Stats.avg(this.decisionMaxFixedLookaheads));
      buf.append('\t');
      buf.append(Stats.stddev(this.decisionMaxFixedLookaheads));
      buf.append('\t');
      buf.append(this.numCyclicDecisions);
      buf.append('\t');
      buf.append(Stats.min(this.decisionMaxCyclicLookaheads));
      buf.append('\t');
      buf.append(Stats.max(this.decisionMaxCyclicLookaheads));
      buf.append('\t');
      buf.append(Stats.avg(this.decisionMaxCyclicLookaheads));
      buf.append('\t');
      buf.append(Stats.stddev(this.decisionMaxCyclicLookaheads));
      buf.append('\t');
      buf.append(this.numBacktrackDecisions);
      buf.append('\t');
      buf.append(Stats.min(this.toArray(this.decisionMaxSynPredLookaheads)));
      buf.append('\t');
      buf.append(Stats.max(this.toArray(this.decisionMaxSynPredLookaheads)));
      buf.append('\t');
      buf.append(Stats.avg(this.toArray(this.decisionMaxSynPredLookaheads)));
      buf.append('\t');
      buf.append(Stats.stddev(this.toArray(this.decisionMaxSynPredLookaheads)));
      buf.append('\t');
      buf.append(this.numSemanticPredicates);
      buf.append('\t');
      buf.append(this.parser.getTokenStream().size());
      buf.append('\t');
      buf.append(this.numHiddenTokens);
      buf.append('\t');
      buf.append(this.numCharsMatched);
      buf.append('\t');
      buf.append(this.numHiddenCharsMatched);
      buf.append('\t');
      buf.append(this.numberReportedErrors);
      buf.append('\t');
      buf.append(this.numMemoizationCacheHits);
      buf.append('\t');
      buf.append(this.numMemoizationCacheMisses);
      buf.append('\t');
      buf.append(this.numGuessingRuleInvocations);
      buf.append('\t');
      buf.append(this.numMemoizationCacheEntries);
      return buf.toString();
   }

   public String toString() {
      return toString(this.toNotifyString());
   }

   protected static String[] decodeReportData(String data) {
      String[] fields = new String[29];
      StringTokenizer st = new StringTokenizer(data, "\t");

      int i;
      for(i = 0; st.hasMoreTokens(); ++i) {
         fields[i] = st.nextToken();
      }

      return i != 29 ? null : fields;
   }

   public static String toString(String notifyDataLine) {
      String[] fields = decodeReportData(notifyDataLine);
      if (fields == null) {
         return null;
      } else {
         StringBuffer buf = new StringBuffer();
         buf.append("ANTLR Runtime Report; Profile Version ");
         buf.append(fields[0]);
         buf.append('\n');
         buf.append("parser name ");
         buf.append(fields[1]);
         buf.append('\n');
         buf.append("Number of rule invocations ");
         buf.append(fields[2]);
         buf.append('\n');
         buf.append("Number of rule invocations in \"guessing\" mode ");
         buf.append(fields[27]);
         buf.append('\n');
         buf.append("max rule invocation nesting depth ");
         buf.append(fields[3]);
         buf.append('\n');
         buf.append("number of fixed lookahead decisions ");
         buf.append(fields[4]);
         buf.append('\n');
         buf.append("min lookahead used in a fixed lookahead decision ");
         buf.append(fields[5]);
         buf.append('\n');
         buf.append("max lookahead used in a fixed lookahead decision ");
         buf.append(fields[6]);
         buf.append('\n');
         buf.append("average lookahead depth used in fixed lookahead decisions ");
         buf.append(fields[7]);
         buf.append('\n');
         buf.append("standard deviation of depth used in fixed lookahead decisions ");
         buf.append(fields[8]);
         buf.append('\n');
         buf.append("number of arbitrary lookahead decisions ");
         buf.append(fields[9]);
         buf.append('\n');
         buf.append("min lookahead used in an arbitrary lookahead decision ");
         buf.append(fields[10]);
         buf.append('\n');
         buf.append("max lookahead used in an arbitrary lookahead decision ");
         buf.append(fields[11]);
         buf.append('\n');
         buf.append("average lookahead depth used in arbitrary lookahead decisions ");
         buf.append(fields[12]);
         buf.append('\n');
         buf.append("standard deviation of depth used in arbitrary lookahead decisions ");
         buf.append(fields[13]);
         buf.append('\n');
         buf.append("number of evaluated syntactic predicates ");
         buf.append(fields[14]);
         buf.append('\n');
         buf.append("min lookahead used in a syntactic predicate ");
         buf.append(fields[15]);
         buf.append('\n');
         buf.append("max lookahead used in a syntactic predicate ");
         buf.append(fields[16]);
         buf.append('\n');
         buf.append("average lookahead depth used in syntactic predicates ");
         buf.append(fields[17]);
         buf.append('\n');
         buf.append("standard deviation of depth used in syntactic predicates ");
         buf.append(fields[18]);
         buf.append('\n');
         buf.append("rule memoization cache size ");
         buf.append(fields[28]);
         buf.append('\n');
         buf.append("number of rule memoization cache hits ");
         buf.append(fields[25]);
         buf.append('\n');
         buf.append("number of rule memoization cache misses ");
         buf.append(fields[26]);
         buf.append('\n');
         buf.append("number of evaluated semantic predicates ");
         buf.append(fields[19]);
         buf.append('\n');
         buf.append("number of tokens ");
         buf.append(fields[20]);
         buf.append('\n');
         buf.append("number of hidden tokens ");
         buf.append(fields[21]);
         buf.append('\n');
         buf.append("number of char ");
         buf.append(fields[22]);
         buf.append('\n');
         buf.append("number of hidden char ");
         buf.append(fields[23]);
         buf.append('\n');
         buf.append("number of syntax errors ");
         buf.append(fields[24]);
         buf.append('\n');
         return buf.toString();
      }
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
}
