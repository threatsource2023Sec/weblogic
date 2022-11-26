package com.bea.common.classloader.service;

public class ClassLoaderNotFoundException extends ClassLoaderInitializationException {
   private static final long serialVersionUID = -5096590607498472137L;

   public ClassLoaderNotFoundException() {
   }

   public ClassLoaderNotFoundException(String msg) {
      super(msg);
   }

   public ClassLoaderNotFoundException(Throwable nested) {
      super(nested);
   }

   public ClassLoaderNotFoundException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
