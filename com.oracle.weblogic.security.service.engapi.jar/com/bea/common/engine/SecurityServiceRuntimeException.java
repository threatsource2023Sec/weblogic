package com.bea.common.engine;

public class SecurityServiceRuntimeException extends RuntimeException {
   private static final long serialVersionUID = -2224395578871761163L;

   public SecurityServiceRuntimeException() {
   }

   public SecurityServiceRuntimeException(String msg) {
      super(msg);
   }

   public SecurityServiceRuntimeException(Throwable nested) {
      super(nested);
   }

   public SecurityServiceRuntimeException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
