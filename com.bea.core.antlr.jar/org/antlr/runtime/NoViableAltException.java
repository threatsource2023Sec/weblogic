package org.antlr.runtime;

public class NoViableAltException extends RecognitionException {
   public String grammarDecisionDescription;
   public int decisionNumber;
   public int stateNumber;

   public NoViableAltException() {
   }

   public NoViableAltException(String grammarDecisionDescription, int decisionNumber, int stateNumber, IntStream input) {
      super(input);
      this.grammarDecisionDescription = grammarDecisionDescription;
      this.decisionNumber = decisionNumber;
      this.stateNumber = stateNumber;
   }

   public String toString() {
      return this.input instanceof CharStream ? "NoViableAltException('" + (char)this.getUnexpectedType() + "'@[" + this.grammarDecisionDescription + "])" : "NoViableAltException(" + this.getUnexpectedType() + "@[" + this.grammarDecisionDescription + "])";
   }
}
