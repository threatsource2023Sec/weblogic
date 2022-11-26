package org.antlr.runtime;

public class UnwantedTokenException extends MismatchedTokenException {
   public UnwantedTokenException() {
   }

   public UnwantedTokenException(int expecting, IntStream input) {
      super(expecting, input);
   }

   public Token getUnexpectedToken() {
      return this.token;
   }

   public String toString() {
      String exp = ", expected " + this.expecting;
      if (this.expecting == 0) {
         exp = "";
      }

      return this.token == null ? "UnwantedTokenException(found=" + null + exp + ")" : "UnwantedTokenException(found=" + this.token.getText() + exp + ")";
   }
}
