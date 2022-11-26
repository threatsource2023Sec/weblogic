package com.bea.common.engine;

public class ServiceConfigurationException extends ServiceInitializationException {
   private static final long serialVersionUID = 333152577772689885L;

   public ServiceConfigurationException() {
   }

   public ServiceConfigurationException(String msg) {
      super(msg);
   }

   public ServiceConfigurationException(Throwable nested) {
      super(nested);
   }

   public ServiceConfigurationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
