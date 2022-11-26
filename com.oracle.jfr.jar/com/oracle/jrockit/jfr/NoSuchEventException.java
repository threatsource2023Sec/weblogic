package com.oracle.jrockit.jfr;

public class NoSuchEventException extends Exception {
   private static final long serialVersionUID = 2362960483503059802L;

   public NoSuchEventException(int id) {
      this(String.valueOf(id));
   }

   public NoSuchEventException() {
   }

   public NoSuchEventException(String message) {
      super(message);
   }

   public NoSuchEventException(Throwable cause) {
      super(cause);
   }

   public NoSuchEventException(String message, Throwable cause) {
      super(message, cause);
   }
}
