package org.glassfish.tyrus.client;

import org.glassfish.tyrus.core.HandshakeException;

public class RetryAfterException extends HandshakeException {
   private final Long delay;

   public RetryAfterException(String message, Long delay) {
      super(503, message);
      this.delay = delay;
   }

   public Long getDelay() {
      return this.delay;
   }
}
