package com.bea.common.classloader.service;

public class ClassLoaderInitializationException extends RuntimeException {
   private static final long serialVersionUID = 3988196121034594619L;

   public ClassLoaderInitializationException() {
   }

   public ClassLoaderInitializationException(String msg) {
      super(msg);
   }

   public ClassLoaderInitializationException(Throwable nested) {
      super(nested);
   }

   public ClassLoaderInitializationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
