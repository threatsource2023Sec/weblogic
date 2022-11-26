package org.antlr.tool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import org.antlr.analysis.DFA;
import org.antlr.misc.Utils;
import org.antlr.runtime.misc.Stats;

public class GrammarReport {
   public static final String Version = "5";
   public static final String GRAMMAR_STATS_FILENAME = "grammar.stats";
   public static final String newline = System.getProperty("line.separator");
   public Grammar grammar;

   public GrammarReport(Grammar grammar) {
      this.grammar = grammar;
   }

   public static ReportData getReportData(Grammar g) {
      ReportData data = new ReportData();
      data.version = "5";
      data.gname = g.name;
      data.gtype = g.getGrammarTypeString();
      data.language = (String)g.getOption("language");
      data.output = (String)g.getOption("output");
      if (data.output == null) {
         data.output = "none";
      }

      String k = (String)g.getOption("k");
      if (k == null) {
         k = "none";
      }

      data.grammarLevelk = k;
      String backtrack = (String)g.getOption("backtrack");
      if (backtrack == null) {
         backtrack = "false";
      }

      data.grammarLevelBacktrack = backtrack;
      int totalNonSynPredProductions = 0;
      int totalNonSynPredRules = 0;
      Collection rules = g.getRules();
      Iterator i$ = rules.iterator();

      while(i$.hasNext()) {
         Rule r = (Rule)i$.next();
         if (!r.name.toUpperCase().startsWith("synpred".toUpperCase())) {
            totalNonSynPredProductions += r.numberOfAlts;
            ++totalNonSynPredRules;
         }
      }

      data.numRules = totalNonSynPredRules;
      data.numOuterProductions = totalNonSynPredProductions;
      int numACyclicDecisions = g.getNumberOfDecisions() - g.getNumberOfCyclicDecisions();
      List depths = new ArrayList();
      int[] acyclicDFAStates = new int[numACyclicDecisions];
      int[] cyclicDFAStates = new int[g.getNumberOfCyclicDecisions()];
      int acyclicIndex = 0;
      int cyclicIndex = 0;
      int numLL1 = 0;
      int blocksWithSynPreds = 0;
      int dfaWithSynPred = 0;
      int numDecisions = 0;
      int numCyclicDecisions = 0;

      for(int i = 1; i <= g.getNumberOfDecisions(); ++i) {
         Grammar.Decision d = g.getDecision(i);
         if (d.dfa != null) {
            Rule r = d.dfa.decisionNFAStartState.enclosingRule;
            if (!r.name.toUpperCase().startsWith("synpred".toUpperCase())) {
               ++numDecisions;
               if (blockHasSynPred(d.blockAST)) {
                  ++blocksWithSynPreds;
               }

               if (d.dfa.hasSynPred()) {
                  ++dfaWithSynPred;
               }

               if (!d.dfa.isCyclic()) {
                  if (d.dfa.isClassicDFA()) {
                     int maxk = d.dfa.getMaxLookaheadDepth();
                     if (maxk == 1) {
                        ++numLL1;
                     }

                     depths.add(maxk);
                  } else {
                     acyclicDFAStates[acyclicIndex] = d.dfa.getNumberOfStates();
                     ++acyclicIndex;
                  }
               } else {
                  ++numCyclicDecisions;
                  cyclicDFAStates[cyclicIndex] = d.dfa.getNumberOfStates();
                  ++cyclicIndex;
               }
            }
         }
      }

      data.numLL1 = numLL1;
      data.numberOfFixedKDecisions = depths.size();
      data.mink = Stats.min((List)depths);
      data.maxk = Stats.max((List)depths);
      data.avgk = Stats.avg((List)depths);
      data.numberOfDecisionsInRealRules = numDecisions;
      data.numberOfDecisions = g.getNumberOfDecisions();
      data.numberOfCyclicDecisions = numCyclicDecisions;
      data.blocksWithSynPreds = blocksWithSynPreds;
      data.decisionsWhoseDFAsUsesSynPreds = dfaWithSynPred;
      data.numTokens = g.getTokenTypes().size();
      data.DFACreationWallClockTimeInMS = g.DFACreationWallClockTimeInMS;
      data.numberOfSemanticPredicates = g.numberOfSemanticPredicates;
      data.numberOfManualLookaheadOptions = g.numberOfManualLookaheadOptions;
      data.numNonLLStarDecisions = g.numNonLLStar;
      data.numNondeterministicDecisions = g.setOfNondeterministicDecisionNumbers.size();
      data.numNondeterministicDecisionNumbersResolvedWithPredicates = g.setOfNondeterministicDecisionNumbersResolvedWithPredicates.size();
      data.errors = ErrorManager.getErrorState().errors;
      data.warnings = ErrorManager.getErrorState().warnings;
      data.infos = ErrorManager.getErrorState().infos;
      data.blocksWithSemPreds = g.blocksWithSemPreds.size();
      data.decisionsWhoseDFAsUsesSemPreds = g.decisionsWhoseDFAsUsesSemPreds.size();
      return data;
   }

   public String toNotifyString() {
      StringBuilder buf = new StringBuilder();
      ReportData data = getReportData(this.grammar);
      Field[] fields = ReportData.class.getDeclaredFields();
      int i = 0;
      Field[] arr$ = fields;
      int len$ = fields.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Field f = arr$[i$];

         try {
            Object v = f.get(data);
            String s = v != null ? v.toString() : "null";
            if (i > 0) {
               buf.append('\t');
            }

            buf.append(s);
         } catch (Exception var11) {
            ErrorManager.internalError("Can't get data", var11);
         }

         ++i;
      }

      return buf.toString();
   }

   public String getBacktrackingReport() {
      StringBuilder buf = new StringBuilder();
      buf.append("Backtracking report:");
      buf.append(newline);
      buf.append("Number of decisions that backtrack: ");
      buf.append(this.grammar.decisionsWhoseDFAsUsesSynPreds.size());
      buf.append(newline);
      buf.append(this.getDFALocations(this.grammar.decisionsWhoseDFAsUsesSynPreds));
      return buf.toString();
   }

   protected String getDFALocations(Set dfas) {
      Set decisions = new HashSet();
      StringBuilder buf = new StringBuilder();
      Iterator i$ = dfas.iterator();

      while(i$.hasNext()) {
         DFA dfa = (DFA)i$.next();
         if (!decisions.contains(Utils.integer(dfa.decisionNumber))) {
            decisions.add(Utils.integer(dfa.decisionNumber));
            buf.append("Rule ");
            buf.append(dfa.decisionNFAStartState.enclosingRule.name);
            buf.append(" decision ");
            buf.append(dfa.decisionNumber);
            buf.append(" location ");
            GrammarAST decisionAST = dfa.decisionNFAStartState.associatedASTNode;
            buf.append(decisionAST.getLine());
            buf.append(":");
            buf.append(decisionAST.getCharPositionInLine());
            buf.append(newline);
         }
      }

      return buf.toString();
   }

   public String toString() {
      return toString(this.toNotifyString());
   }

   protected static ReportData decodeReportData(String dataS) {
      ReportData data = new ReportData();
      StringTokenizer st = new StringTokenizer(dataS, "\t");
      Field[] fields = ReportData.class.getDeclaredFields();
      Field[] arr$ = fields;
      int len$ = fields.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Field f = arr$[i$];
         String v = st.nextToken();

         try {
            if (f.getType() == String.class) {
               f.set(data, v);
            } else if (f.getType() == Double.TYPE) {
               f.set(data, Double.valueOf(v));
            } else {
               f.set(data, Integer.valueOf(v));
            }
         } catch (Exception var10) {
            ErrorManager.internalError("Can't get data", var10);
         }
      }

      return data;
   }

   public static String toString(String notifyDataLine) {
      ReportData data = decodeReportData(notifyDataLine);
      if (data == null) {
         return null;
      } else {
         StringBuilder buf = new StringBuilder();
         buf.append("ANTLR Grammar Report; Stats Version ");
         buf.append(data.version);
         buf.append('\n');
         buf.append("Grammar: ");
         buf.append(data.gname);
         buf.append('\n');
         buf.append("Type: ");
         buf.append(data.gtype);
         buf.append('\n');
         buf.append("Target language: ");
         buf.append(data.language);
         buf.append('\n');
         buf.append("Output: ");
         buf.append(data.output);
         buf.append('\n');
         buf.append("Grammar option k: ");
         buf.append(data.grammarLevelk);
         buf.append('\n');
         buf.append("Grammar option backtrack: ");
         buf.append(data.grammarLevelBacktrack);
         buf.append('\n');
         buf.append("Rules: ");
         buf.append(data.numRules);
         buf.append('\n');
         buf.append("Outer productions: ");
         buf.append(data.numOuterProductions);
         buf.append('\n');
         buf.append("Decisions: ");
         buf.append(data.numberOfDecisions);
         buf.append('\n');
         buf.append("Decisions (ignoring decisions in synpreds): ");
         buf.append(data.numberOfDecisionsInRealRules);
         buf.append('\n');
         buf.append("Fixed k DFA decisions: ");
         buf.append(data.numberOfFixedKDecisions);
         buf.append('\n');
         buf.append("Cyclic DFA decisions: ");
         buf.append(data.numberOfCyclicDecisions);
         buf.append('\n');
         buf.append("LL(1) decisions: ");
         buf.append(data.numLL1);
         buf.append('\n');
         buf.append("Min fixed k: ");
         buf.append(data.mink);
         buf.append('\n');
         buf.append("Max fixed k: ");
         buf.append(data.maxk);
         buf.append('\n');
         buf.append("Average fixed k: ");
         buf.append(data.avgk);
         buf.append('\n');
         buf.append("DFA creation time in ms: ");
         buf.append(data.DFACreationWallClockTimeInMS);
         buf.append('\n');
         buf.append("Decisions with available syntactic predicates (ignoring synpred rules): ");
         buf.append(data.blocksWithSynPreds);
         buf.append('\n');
         buf.append("Decision DFAs using syntactic predicates (ignoring synpred rules): ");
         buf.append(data.decisionsWhoseDFAsUsesSynPreds);
         buf.append('\n');
         buf.append("Number of semantic predicates found: ");
         buf.append(data.numberOfSemanticPredicates);
         buf.append('\n');
         buf.append("Decisions with semantic predicates: ");
         buf.append(data.blocksWithSemPreds);
         buf.append('\n');
         buf.append("Decision DFAs using semantic predicates: ");
         buf.append(data.decisionsWhoseDFAsUsesSemPreds);
         buf.append('\n');
         buf.append("Number of (likely) non-LL(*) decisions: ");
         buf.append(data.numNonLLStarDecisions);
         buf.append('\n');
         buf.append("Number of nondeterministic decisions: ");
         buf.append(data.numNondeterministicDecisions);
         buf.append('\n');
         buf.append("Number of nondeterministic decisions resolved with predicates: ");
         buf.append(data.numNondeterministicDecisionNumbersResolvedWithPredicates);
         buf.append('\n');
         buf.append("Number of manual or forced fixed lookahead k=value options: ");
         buf.append(data.numberOfManualLookaheadOptions);
         buf.append('\n');
         buf.append("Vocabulary size: ");
         buf.append(data.numTokens);
         buf.append('\n');
         buf.append("Number of errors: ");
         buf.append(data.errors);
         buf.append('\n');
         buf.append("Number of warnings: ");
         buf.append(data.warnings);
         buf.append('\n');
         buf.append("Number of infos: ");
         buf.append(data.infos);
         buf.append('\n');
         return buf.toString();
      }
   }

   public static boolean blockHasSynPred(GrammarAST blockAST) {
      GrammarAST c1 = blockAST.findFirstType(90);
      GrammarAST c2 = blockAST.findFirstType(14);
      return c1 != null || c2 != null;
   }

   public static class ReportData {
      String version;
      String gname;
      String gtype;
      String language;
      int numRules;
      int numOuterProductions;
      int numberOfDecisionsInRealRules;
      int numberOfDecisions;
      int numberOfCyclicDecisions;
      int numberOfFixedKDecisions;
      int numLL1;
      int mink;
      int maxk;
      double avgk;
      int numTokens;
      long DFACreationWallClockTimeInMS;
      int numberOfSemanticPredicates;
      int numberOfManualLookaheadOptions;
      int numNonLLStarDecisions;
      int numNondeterministicDecisions;
      int numNondeterministicDecisionNumbersResolvedWithPredicates;
      int errors;
      int warnings;
      int infos;
      int blocksWithSynPreds;
      int decisionsWhoseDFAsUsesSynPreds;
      int blocksWithSemPreds;
      int decisionsWhoseDFAsUsesSemPreds;
      String output;
      String grammarLevelk;
      String grammarLevelBacktrack;
   }
}
