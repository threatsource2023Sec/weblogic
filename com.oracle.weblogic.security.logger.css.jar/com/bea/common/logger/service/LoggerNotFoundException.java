package com.bea.common.logger.service;

public class LoggerNotFoundException extends LoggerInitializationException {
   private static final long serialVersionUID = -7037390260691853291L;

   public LoggerNotFoundException() {
   }

   public LoggerNotFoundException(String msg) {
      super(msg);
   }

   public LoggerNotFoundException(Throwable nested) {
      super(nested);
   }

   public LoggerNotFoundException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
