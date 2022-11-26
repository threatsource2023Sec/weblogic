package com.bea.common.store.service;

public class StoreServiceRuntimeException extends RuntimeException {
   private static final long serialVersionUID = -3348834287802707888L;

   public StoreServiceRuntimeException() {
   }

   public StoreServiceRuntimeException(String message) {
      super(message);
   }

   public StoreServiceRuntimeException(Throwable cause) {
      super(cause);
   }

   public StoreServiceRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }
}
