package com.bea.httppubsub.json;

public class JSONException extends RuntimeException {
   private static final long serialVersionUID = -6507864846923641603L;
   private Throwable cause;

   public JSONException(String message) {
      super(message);
   }

   public JSONException(Throwable t) {
      super(t.getMessage());
      this.cause = t;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
