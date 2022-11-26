package com.bea.common.security.internal.utils.negotiate;

public final class NegotiateTokenException extends RuntimeException {
   public NegotiateTokenException() {
   }

   public NegotiateTokenException(String msg) {
      super(msg);
   }

   public NegotiateTokenException(Throwable nested) {
      super(nested);
   }

   public NegotiateTokenException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
