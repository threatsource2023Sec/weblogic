package org.stringtemplate.v4.misc;

public class AmbiguousMatchException extends RuntimeException {
   public AmbiguousMatchException() {
   }

   public AmbiguousMatchException(String message) {
      super(message);
   }

   public AmbiguousMatchException(Throwable cause) {
      super(cause);
   }

   public AmbiguousMatchException(String message, Throwable cause) {
      super(message, cause);
   }
}
