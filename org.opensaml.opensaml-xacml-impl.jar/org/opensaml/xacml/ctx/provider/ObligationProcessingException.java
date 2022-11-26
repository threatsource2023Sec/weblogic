package org.opensaml.xacml.ctx.provider;

public class ObligationProcessingException extends Exception {
   private static final long serialVersionUID = -8978474052544318919L;

   public ObligationProcessingException() {
   }

   public ObligationProcessingException(String message) {
      super(message);
   }

   public ObligationProcessingException(Exception wrappedException) {
      super(wrappedException);
   }

   public ObligationProcessingException(String message, Exception wrappedException) {
      super(message, wrappedException);
   }
}
