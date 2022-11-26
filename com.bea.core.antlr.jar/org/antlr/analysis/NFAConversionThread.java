package org.antlr.analysis;

import org.antlr.misc.Barrier;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;

public class NFAConversionThread implements Runnable {
   Grammar grammar;
   int i;
   int j;
   Barrier barrier;

   public NFAConversionThread(Grammar grammar, Barrier barrier, int i, int j) {
      this.grammar = grammar;
      this.barrier = barrier;
      this.i = i;
      this.j = j;
   }

   public void run() {
      for(int decision = this.i; decision <= this.j; ++decision) {
         NFAState decisionStartState = this.grammar.getDecisionNFAStartState(decision);
         if (decisionStartState.getNumberOfTransitions() > 1) {
            this.grammar.createLookaheadDFA(decision, true);
         }
      }

      try {
         this.barrier.waitForRelease();
      } catch (InterruptedException var3) {
         ErrorManager.internalError("what the hell? DFA interruptus", var3);
      }

   }
}
