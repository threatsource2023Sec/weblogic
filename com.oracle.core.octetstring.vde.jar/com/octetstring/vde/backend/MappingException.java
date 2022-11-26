package com.octetstring.vde.backend;

public class MappingException extends Exception {
   private String message = null;

   public MappingException(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }
}
