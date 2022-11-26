package com.bea.httppubsub.internal;

import com.bea.httppubsub.PubSubServerException;

public class SchemaValidationException extends PubSubServerException {
   private static final long serialVersionUID = 6655664907526669755L;

   public SchemaValidationException() {
   }

   public SchemaValidationException(String message, Throwable cause) {
      super(message, cause);
   }

   public SchemaValidationException(String message) {
      super(message);
   }

   public SchemaValidationException(Throwable cause) {
      super(cause);
   }
}
