package org.glassfish.grizzly.http;

public class ContentEncodingException extends IllegalStateException {
   public ContentEncodingException() {
   }

   public ContentEncodingException(String s) {
      super(s);
   }

   public ContentEncodingException(Throwable cause) {
      super(cause);
   }

   public ContentEncodingException(String message, Throwable cause) {
      super(message, cause);
   }
}
