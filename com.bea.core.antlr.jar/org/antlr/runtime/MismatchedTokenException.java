package org.antlr.runtime;

public class MismatchedTokenException extends RecognitionException {
   public int expecting = 0;

   public MismatchedTokenException() {
   }

   public MismatchedTokenException(int expecting, IntStream input) {
      super(input);
      this.expecting = expecting;
   }

   public String toString() {
      return "MismatchedTokenException(" + this.getUnexpectedType() + "!=" + this.expecting + ")";
   }
}
