package com.bea.security.providers.xacml;

public class ConflictException extends Exception {
   public ConflictException(String msg) {
      super(msg);
   }

   public ConflictException(Throwable cause) {
      super(cause);
   }

   public ConflictException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
