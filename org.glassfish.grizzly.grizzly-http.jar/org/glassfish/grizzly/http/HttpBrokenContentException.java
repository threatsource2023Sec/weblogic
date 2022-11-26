package org.glassfish.grizzly.http;

public final class HttpBrokenContentException extends RuntimeException {
   public HttpBrokenContentException() {
   }

   public HttpBrokenContentException(String message) {
      super(message);
   }

   public HttpBrokenContentException(Throwable cause) {
      this(cause.getMessage(), cause);
   }

   public HttpBrokenContentException(String message, Throwable cause) {
      super(message, cause);
   }
}
