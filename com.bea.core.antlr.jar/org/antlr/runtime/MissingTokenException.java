package org.antlr.runtime;

public class MissingTokenException extends MismatchedTokenException {
   public Object inserted;

   public MissingTokenException() {
   }

   public MissingTokenException(int expecting, IntStream input, Object inserted) {
      super(expecting, input);
      this.inserted = inserted;
   }

   public int getMissingType() {
      return this.expecting;
   }

   public String toString() {
      if (this.inserted != null && this.token != null) {
         return "MissingTokenException(inserted " + this.inserted + " at " + this.token.getText() + ")";
      } else {
         return this.token != null ? "MissingTokenException(at " + this.token.getText() + ")" : "MissingTokenException";
      }
   }
}
