package com.bea.core.repackaged.aspectj.lang;

public class NoAspectBoundException extends RuntimeException {
   Throwable cause;

   public NoAspectBoundException(String aspectName, Throwable inner) {
      super(inner == null ? aspectName : "Exception while initializing " + aspectName + ": " + inner);
      this.cause = inner;
   }

   public NoAspectBoundException() {
   }

   public Throwable getCause() {
      return this.cause;
   }
}
