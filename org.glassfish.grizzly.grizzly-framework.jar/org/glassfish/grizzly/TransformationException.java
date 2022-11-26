package org.glassfish.grizzly;

public class TransformationException extends RuntimeException {
   public TransformationException() {
   }

   public TransformationException(String message) {
      super(message);
   }

   public TransformationException(Throwable cause) {
      super(cause);
   }

   public TransformationException(String message, Throwable cause) {
      super(message, cause);
   }
}
