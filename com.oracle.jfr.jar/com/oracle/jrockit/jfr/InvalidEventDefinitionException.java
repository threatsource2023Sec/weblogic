package com.oracle.jrockit.jfr;

public class InvalidEventDefinitionException extends Exception {
   private static final long serialVersionUID = 2320799086444730596L;

   public InvalidEventDefinitionException() {
   }

   public InvalidEventDefinitionException(String message) {
      super(message);
   }

   public InvalidEventDefinitionException(Throwable cause) {
      super(cause);
   }

   public InvalidEventDefinitionException(String message, Throwable cause) {
      super(message, cause);
   }
}
