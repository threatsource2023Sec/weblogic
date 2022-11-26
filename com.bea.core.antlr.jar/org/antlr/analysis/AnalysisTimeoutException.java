package org.antlr.analysis;

public class AnalysisTimeoutException extends RuntimeException {
   public DFA abortedDFA;

   public AnalysisTimeoutException(DFA abortedDFA) {
      this.abortedDFA = abortedDFA;
   }
}
