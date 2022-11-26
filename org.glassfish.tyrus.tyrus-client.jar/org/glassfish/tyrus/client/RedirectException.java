package org.glassfish.tyrus.client;

import org.glassfish.tyrus.core.HandshakeException;

public class RedirectException extends HandshakeException {
   private static final long serialVersionUID = 4357724300486801294L;

   public RedirectException(int httpStatusCode, String message) {
      super(httpStatusCode, message);
   }
}
