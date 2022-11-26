package com.bea.httppubsub;

public class PubSubSecurityException extends PubSubServerException {
   private static final long serialVersionUID = 6780906306372233378L;

   public PubSubSecurityException(Throwable cause) {
      super(cause);
   }

   public PubSubSecurityException(String message, Throwable cause) {
      super(message, cause);
   }

   public PubSubSecurityException(String message) {
      super(message);
   }

   public PubSubSecurityException() {
   }
}
