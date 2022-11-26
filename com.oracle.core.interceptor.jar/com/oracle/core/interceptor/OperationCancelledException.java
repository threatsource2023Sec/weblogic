package com.oracle.core.interceptor;

public class OperationCancelledException extends RuntimeException {
   public OperationCancelledException() {
   }

   public OperationCancelledException(String message) {
      super(message);
   }

   public OperationCancelledException(String message, Throwable cause) {
      super(message, cause);
   }

   public OperationCancelledException(Throwable cause) {
      super(cause);
   }

   public OperationCancelledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
