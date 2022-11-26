package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.bayeux.BayeuxException;

public class BayeuxParseException extends BayeuxException {
   private static final long serialVersionUID = -2234294213978194240L;

   public BayeuxParseException(String message) {
      super(message);
   }

   public BayeuxParseException(String message, Throwable cause) {
      super(message, cause);
   }

   public BayeuxParseException(Throwable cause) {
      super(cause);
   }
}
