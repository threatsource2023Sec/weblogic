package com.bea.httppubsub.bayeux.messages;

public class InvalidChannelUrlFoundException extends Exception {
   private static final long serialVersionUID = -1311679211975291924L;

   public InvalidChannelUrlFoundException() {
   }

   public InvalidChannelUrlFoundException(String message) {
      super(message);
   }

   public InvalidChannelUrlFoundException(String message, Throwable cause) {
      super(message, cause);
   }

   public InvalidChannelUrlFoundException(Throwable cause) {
      super(cause);
   }
}
