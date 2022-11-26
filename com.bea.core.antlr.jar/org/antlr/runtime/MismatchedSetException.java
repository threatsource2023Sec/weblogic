package org.antlr.runtime;

public class MismatchedSetException extends RecognitionException {
   public BitSet expecting;

   public MismatchedSetException() {
   }

   public MismatchedSetException(BitSet expecting, IntStream input) {
      super(input);
      this.expecting = expecting;
   }

   public String toString() {
      return "MismatchedSetException(" + this.getUnexpectedType() + "!=" + this.expecting + ")";
   }
}
