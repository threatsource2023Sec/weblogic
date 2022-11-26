package org.antlr.runtime;

public class EarlyExitException extends RecognitionException {
   public int decisionNumber;

   public EarlyExitException() {
   }

   public EarlyExitException(int decisionNumber, IntStream input) {
      super(input);
      this.decisionNumber = decisionNumber;
   }
}
