package com.bea.httppubsub.internal;

import com.bea.httppubsub.bayeux.messages.BayeuxParseException;

public class InvalidChannelException extends BayeuxParseException {
   private static final long serialVersionUID = 3475609180852979371L;

   public InvalidChannelException(String message) {
      super(message);
   }

   public InvalidChannelException(String message, Throwable cause) {
      super(message, cause);
   }

   public InvalidChannelException(Throwable cause) {
      super(cause);
   }
}
