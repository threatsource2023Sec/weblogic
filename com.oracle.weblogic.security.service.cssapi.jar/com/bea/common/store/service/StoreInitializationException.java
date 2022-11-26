package com.bea.common.store.service;

public class StoreInitializationException extends StoreServiceRuntimeException {
   private static final long serialVersionUID = -5964918936391718494L;

   public StoreInitializationException() {
   }

   public StoreInitializationException(String msg) {
      super(msg);
   }

   public StoreInitializationException(Throwable nested) {
      super(nested);
   }

   public StoreInitializationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
