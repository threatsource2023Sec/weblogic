package com.oracle.weblogic.diagnostics.expressions;

public class NotEnoughDataException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public NotEnoughDataException() {
   }

   public NotEnoughDataException(String s) {
      super(s);
   }

   public NotEnoughDataException(String s, Throwable throwable) {
      super(s, throwable);
   }

   public NotEnoughDataException(Throwable throwable) {
      super(throwable);
   }
}
