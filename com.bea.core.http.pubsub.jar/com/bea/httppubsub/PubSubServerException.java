package com.bea.httppubsub;

public class PubSubServerException extends Exception {
   private static final long serialVersionUID = 1068834620601960861L;

   public PubSubServerException() {
   }

   public PubSubServerException(String message) {
      super(message);
   }

   public PubSubServerException(String message, Throwable cause) {
      super(message, cause);
   }

   public PubSubServerException(Throwable cause) {
      super(cause);
   }
}
