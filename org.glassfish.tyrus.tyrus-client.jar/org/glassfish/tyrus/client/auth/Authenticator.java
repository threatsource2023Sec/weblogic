package org.glassfish.tyrus.client.auth;

import java.net.URI;
import org.glassfish.tyrus.core.Beta;

@Beta
public abstract class Authenticator {
   public abstract String generateAuthorizationHeader(URI var1, String var2, Credentials var3) throws AuthenticationException;
}
