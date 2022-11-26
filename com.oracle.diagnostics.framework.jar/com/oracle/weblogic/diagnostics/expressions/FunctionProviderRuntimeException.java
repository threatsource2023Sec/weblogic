package com.oracle.weblogic.diagnostics.expressions;

public class FunctionProviderRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public FunctionProviderRuntimeException() {
   }

   public FunctionProviderRuntimeException(String message) {
      super(message);
   }

   public FunctionProviderRuntimeException(Throwable cause) {
      super(cause);
   }

   public FunctionProviderRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }

   public FunctionProviderRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
