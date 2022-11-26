package com.oracle.cmm.agent;

public class CMMException extends RuntimeException {
   private static final long serialVersionUID = 3139400615414944757L;

   public CMMException() {
   }

   public CMMException(String message) {
      super(message);
   }

   public CMMException(Throwable cause) {
      super(cause);
   }

   public CMMException(String message, Throwable cause) {
      super(message, cause);
   }

   public CMMException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
