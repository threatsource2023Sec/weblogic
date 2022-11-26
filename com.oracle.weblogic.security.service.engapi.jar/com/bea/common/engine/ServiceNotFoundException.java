package com.bea.common.engine;

public class ServiceNotFoundException extends ServiceInitializationException {
   private static final long serialVersionUID = 2359915333722944845L;

   public ServiceNotFoundException() {
   }

   public ServiceNotFoundException(String msg) {
      super(msg);
   }

   public ServiceNotFoundException(Throwable nested) {
      super(nested);
   }

   public ServiceNotFoundException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
