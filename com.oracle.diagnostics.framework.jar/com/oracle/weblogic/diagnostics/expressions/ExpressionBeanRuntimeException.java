package com.oracle.weblogic.diagnostics.expressions;

public class ExpressionBeanRuntimeException extends ExpressionExtensionRuntimeException {
   public ExpressionBeanRuntimeException() {
   }

   public ExpressionBeanRuntimeException(String message) {
      super(message);
   }

   public ExpressionBeanRuntimeException(Throwable cause) {
      super(cause);
   }

   public ExpressionBeanRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }

   public ExpressionBeanRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
