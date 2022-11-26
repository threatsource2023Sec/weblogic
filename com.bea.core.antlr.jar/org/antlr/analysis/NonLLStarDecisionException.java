package org.antlr.analysis;

public class NonLLStarDecisionException extends RuntimeException {
   public DFA abortedDFA;

   public NonLLStarDecisionException(DFA abortedDFA) {
      this.abortedDFA = abortedDFA;
   }
}
