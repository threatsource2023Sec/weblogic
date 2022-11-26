package com.bea.common.engine;

public class ServiceInitializationException extends Exception {
   private static final long serialVersionUID = -2074208936871884664L;

   public ServiceInitializationException() {
   }

   public ServiceInitializationException(String msg) {
      super(msg);
   }

   public ServiceInitializationException(Throwable nested) {
      super(nested);
   }

   public ServiceInitializationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
