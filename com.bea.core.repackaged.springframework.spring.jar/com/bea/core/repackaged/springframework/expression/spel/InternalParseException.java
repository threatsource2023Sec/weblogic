package com.bea.core.repackaged.springframework.expression.spel;

public class InternalParseException extends RuntimeException {
   public InternalParseException(SpelParseException cause) {
      super(cause);
   }

   public SpelParseException getCause() {
      return (SpelParseException)super.getCause();
   }
}
