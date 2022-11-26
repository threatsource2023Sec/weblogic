package com.bea.common.security.service;

public class NamedSQLConnectionNotFoundException extends RuntimeException {
   private static final long serialVersionUID = 23599153337244845L;

   public NamedSQLConnectionNotFoundException() {
   }

   public NamedSQLConnectionNotFoundException(String msg) {
      super(msg);
   }

   public NamedSQLConnectionNotFoundException(Throwable nested) {
      super(nested);
   }

   public NamedSQLConnectionNotFoundException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
