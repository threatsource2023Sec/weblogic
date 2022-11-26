package org.cryptacular;

public class EncodingException extends RuntimeException {
   public EncodingException(String message) {
      super(message);
   }

   public EncodingException(String message, Throwable cause) {
      super(message, cause);
   }
}
