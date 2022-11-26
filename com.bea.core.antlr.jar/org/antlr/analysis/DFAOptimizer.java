package org.antlr.analysis;

import java.util.HashSet;
import java.util.Set;
import org.antlr.misc.Utils;
import org.antlr.tool.Grammar;

public class DFAOptimizer {
   public static boolean PRUNE_EBNF_EXIT_BRANCHES = true;
   public static boolean PRUNE_TOKENS_RULE_SUPERFLUOUS_EOT_EDGES = true;
   public static boolean COLLAPSE_ALL_PARALLEL_EDGES = true;
   public static boolean MERGE_STOP_STATES = true;
   protected Set visited = new HashSet();
   protected Grammar grammar;

   public DFAOptimizer(Grammar grammar) {
      this.grammar = grammar;
   }

   public void optimize() {
      for(int decisionNumber = 1; decisionNumber <= this.grammar.getNumberOfDecisions(); ++decisionNumber) {
         DFA dfa = this.grammar.getLookaheadDFA(decisionNumber);
         this.optimize(dfa);
      }

   }

   protected void optimize(DFA dfa) {
      if (dfa != null) {
         if (PRUNE_EBNF_EXIT_BRANCHES && dfa.canInlineDecision()) {
            this.visited.clear();
            int decisionType = dfa.getNFADecisionStartState().decisionStateType;
            if (dfa.isGreedy() && (decisionType == 3 || decisionType == 1)) {
               this.optimizeExitBranches(dfa.startState);
            }
         }

         if (PRUNE_TOKENS_RULE_SUPERFLUOUS_EOT_EDGES && dfa.isTokensRuleDecision() && dfa.probe.stateToSyntacticallyAmbiguousTokensRuleAltsMap.size() > 0) {
            this.visited.clear();
            this.optimizeEOTBranches(dfa.startState);
         }

      }
   }

   protected void optimizeExitBranches(DFAState d) {
      Integer sI = Utils.integer(d.stateNumber);
      if (!this.visited.contains(sI)) {
         this.visited.add(sI);
         int nAlts = d.dfa.getNumberOfAlts();

         for(int i = 0; i < d.getNumberOfTransitions(); ++i) {
            Transition edge = d.transition(i);
            DFAState edgeTarget = (DFAState)edge.target;
            if (edgeTarget.isAcceptState() && edgeTarget.getUniquelyPredictedAlt() == nAlts) {
               d.removeTransition(i);
               --i;
            }

            this.optimizeExitBranches(edgeTarget);
         }

      }
   }

   protected void optimizeEOTBranches(DFAState d) {
      Integer sI = Utils.integer(d.stateNumber);
      if (!this.visited.contains(sI)) {
         this.visited.add(sI);

         for(int i = 0; i < d.getNumberOfTransitions(); ++i) {
            Transition edge = d.transition(i);
            DFAState edgeTarget = (DFAState)edge.target;
            if (PRUNE_TOKENS_RULE_SUPERFLUOUS_EOT_EDGES && edgeTarget.isAcceptState() && d.getNumberOfTransitions() == 1 && edge.label.isAtom() && edge.label.getAtom() == -2) {
               d.removeTransition(i);
               d.setAcceptState(true);
               d.cachedUniquelyPredicatedAlt = edgeTarget.getUniquelyPredictedAlt();
               --i;
            }

            this.optimizeEOTBranches(edgeTarget);
         }

      }
   }
}
