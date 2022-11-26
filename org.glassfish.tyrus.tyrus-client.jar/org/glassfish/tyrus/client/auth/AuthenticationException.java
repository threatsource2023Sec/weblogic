package org.glassfish.tyrus.client.auth;

import org.glassfish.tyrus.core.Beta;
import org.glassfish.tyrus.core.HandshakeException;

@Beta
public class AuthenticationException extends HandshakeException {
   public AuthenticationException(String message) {
      super(401, message);
   }
}
