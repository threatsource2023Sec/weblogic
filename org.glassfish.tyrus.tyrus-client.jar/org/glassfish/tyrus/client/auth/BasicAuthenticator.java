package org.glassfish.tyrus.client.auth;

import java.net.URI;
import java.util.Base64;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

final class BasicAuthenticator extends Authenticator {
   public String generateAuthorizationHeader(URI uri, String wwwAuthenticateHeader, Credentials credentials) throws AuthenticationException {
      return this.generateAuthorizationHeader(credentials);
   }

   private String generateAuthorizationHeader(Credentials credentials) throws AuthenticationException {
      if (credentials == null) {
         throw new AuthenticationException(LocalizationMessages.AUTHENTICATION_CREDENTIALS_MISSING());
      } else {
         String username = credentials.getUsername();
         byte[] password = credentials.getPassword();
         byte[] prefix = (username + ":").getBytes(AuthConfig.CHARACTER_SET);
         byte[] usernamePassword = new byte[prefix.length + password.length];
         System.arraycopy(prefix, 0, usernamePassword, 0, prefix.length);
         System.arraycopy(password, 0, usernamePassword, prefix.length, password.length);
         return "Basic " + Base64.getEncoder().encodeToString(usernamePassword);
      }
   }
}
