package com.bea.security.utils.authentication;

public class PasswordHashException extends RuntimeException {
   public PasswordHashException() {
   }

   public PasswordHashException(String msg) {
      super(msg);
   }

   public PasswordHashException(Throwable nested) {
      super(nested);
   }

   public PasswordHashException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
