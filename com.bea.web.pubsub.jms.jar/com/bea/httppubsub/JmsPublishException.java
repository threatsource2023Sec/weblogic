package com.bea.httppubsub;

public class JmsPublishException extends RuntimeException {
   private static final long serialVersionUID = 5609834274479284370L;

   public JmsPublishException() {
   }

   public JmsPublishException(String message) {
      super(message);
   }

   public JmsPublishException(String message, Throwable cause) {
      super(message, cause);
   }

   public JmsPublishException(Throwable cause) {
      super(cause);
   }
}
