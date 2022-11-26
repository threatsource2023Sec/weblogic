package com.bea.core.repackaged.springframework.expression;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class ParseException extends ExpressionException {
   public ParseException(@Nullable String expressionString, int position, String message) {
      super(expressionString, position, message);
   }

   public ParseException(int position, String message, Throwable cause) {
      super(position, message, cause);
   }

   public ParseException(int position, String message) {
      super(position, message);
   }
}
