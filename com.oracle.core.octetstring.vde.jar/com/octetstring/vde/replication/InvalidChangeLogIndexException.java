package com.octetstring.vde.replication;

public class InvalidChangeLogIndexException extends Exception {
   private String message = null;

   public InvalidChangeLogIndexException(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }
}
