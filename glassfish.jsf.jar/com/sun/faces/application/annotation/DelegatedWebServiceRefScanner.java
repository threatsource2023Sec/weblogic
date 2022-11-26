package com.sun.faces.application.annotation;

class DelegatedWebServiceRefScanner implements Scanner {
   private Scanner delegate;

   public DelegatedWebServiceRefScanner() {
      try {
         this.delegate = new WebServiceRefScanner();
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
