package org.antlr.runtime;

public class MismatchedNotSetException extends MismatchedSetException {
   public MismatchedNotSetException() {
   }

   public MismatchedNotSetException(BitSet expecting, IntStream input) {
      super(expecting, input);
   }

   public String toString() {
      return "MismatchedNotSetException(" + this.getUnexpectedType() + "!=" + this.expecting + ")";
   }
}
