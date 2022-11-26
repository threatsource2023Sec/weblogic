package com.bea.common.logger.service;

public class LoggerInitializationException extends RuntimeException {
   private static final long serialVersionUID = -3437046993101671050L;

   public LoggerInitializationException() {
   }

   public LoggerInitializationException(String msg) {
      super(msg);
   }

   public LoggerInitializationException(Throwable nested) {
      super(nested);
   }

   public LoggerInitializationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
