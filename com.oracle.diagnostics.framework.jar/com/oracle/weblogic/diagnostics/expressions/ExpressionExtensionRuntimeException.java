package com.oracle.weblogic.diagnostics.expressions;

public class ExpressionExtensionRuntimeException extends RuntimeException {
   public ExpressionExtensionRuntimeException() {
   }

   public ExpressionExtensionRuntimeException(String message) {
      super(message);
   }

   public ExpressionExtensionRuntimeException(Throwable cause) {
      super(cause);
   }

   public ExpressionExtensionRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }

   public ExpressionExtensionRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
