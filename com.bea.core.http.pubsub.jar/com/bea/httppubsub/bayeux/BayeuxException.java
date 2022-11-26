package com.bea.httppubsub.bayeux;

public class BayeuxException extends RuntimeException {
   private static final long serialVersionUID = 8880903817159517534L;

   public BayeuxException(String message) {
      super(message);
   }

   public BayeuxException(String message, Throwable cause) {
      super(message, cause);
   }

   public BayeuxException(Throwable cause) {
      super(cause);
   }
}
