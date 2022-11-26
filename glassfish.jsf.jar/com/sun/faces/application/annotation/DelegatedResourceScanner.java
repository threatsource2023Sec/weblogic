package com.sun.faces.application.annotation;

public class DelegatedResourceScanner implements Scanner {
   private Scanner delegate;

   public DelegatedResourceScanner() {
      try {
         this.delegate = new ResourceScanner();
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
