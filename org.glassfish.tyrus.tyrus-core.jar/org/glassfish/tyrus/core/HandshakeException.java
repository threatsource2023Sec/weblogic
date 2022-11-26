package org.glassfish.tyrus.core;

public class HandshakeException extends Exception {
   private final int httpStatusCode;

   public HandshakeException(String message) {
      this(500, message);
   }

   public HandshakeException(int httpStatusCode, String message) {
      super(message);
      this.httpStatusCode = httpStatusCode;
   }

   public int getHttpStatusCode() {
      return this.httpStatusCode;
   }
}
