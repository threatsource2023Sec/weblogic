package com.bea.common.store.service;

public class StoreNotFoundException extends StoreInitializationException {
   private static final long serialVersionUID = -8811140117396699173L;

   public StoreNotFoundException() {
   }

   public StoreNotFoundException(String msg) {
      super(msg);
   }

   public StoreNotFoundException(Throwable nested) {
      super(nested);
   }

   public StoreNotFoundException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
