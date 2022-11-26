package com.bea.common.engine;

public class InvalidParameterException extends SecurityServiceRuntimeException {
   private static final long serialVersionUID = -8100082465205505077L;

   public InvalidParameterException() {
   }

   public InvalidParameterException(String msg) {
      super(msg);
   }

   public InvalidParameterException(Throwable nested) {
      super(nested);
   }

   public InvalidParameterException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
