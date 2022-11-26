package org.antlr.analysis;

public class AnalysisRecursionOverflowException extends RuntimeException {
   public DFAState ovfState;
   public NFAConfiguration proposedNFAConfiguration;

   public AnalysisRecursionOverflowException(DFAState ovfState, NFAConfiguration proposedNFAConfiguration) {
      this.ovfState = ovfState;
      this.proposedNFAConfiguration = proposedNFAConfiguration;
   }
}
