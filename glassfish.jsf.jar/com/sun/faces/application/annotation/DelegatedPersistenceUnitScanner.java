package com.sun.faces.application.annotation;

public class DelegatedPersistenceUnitScanner implements Scanner {
   private Scanner delegate;

   public DelegatedPersistenceUnitScanner() {
      try {
         this.delegate = new PersistenceUnitScanner();
      } catch (Throwable var2) {
         var2.printStackTrace(System.err);
      }

   }

   public Class getAnnotation() {
      return this.delegate != null ? this.delegate.getAnnotation() : null;
   }

   public RuntimeAnnotationHandler scan(Class clazz) {
      return this.delegate != null ? this.delegate.scan(clazz) : null;
   }
}
