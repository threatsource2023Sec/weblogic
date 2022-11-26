package com.bea.security.providers.xacml.store.ldap;

public class EntlConversionException extends Exception {
   public EntlConversionException() {
   }

   public EntlConversionException(String msg) {
      super(msg);
   }

   public EntlConversionException(Throwable nested) {
      super(nested);
   }

   public EntlConversionException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
