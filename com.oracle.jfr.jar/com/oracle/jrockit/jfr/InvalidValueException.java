package com.oracle.jrockit.jfr;

public class InvalidValueException extends Exception {
   private static final long serialVersionUID = 1890935164600518666L;

   public InvalidValueException() {
   }

   public InvalidValueException(String message) {
      super(message);
   }

   public InvalidValueException(Throwable cause) {
      super(cause);
   }

   public InvalidValueException(String message, Throwable cause) {
      super(message, cause);
   }
}
