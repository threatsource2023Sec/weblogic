package org.glassfish.tyrus.client.auth;

import org.glassfish.tyrus.core.Beta;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

@Beta
public final class Credentials {
   private final String username;
   private final byte[] password;

   public Credentials(String username, byte[] password) {
      if (username == null) {
         throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("username"));
      } else if (password == null) {
         throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("password"));
      } else {
         this.username = username;
         this.password = password;
      }
   }

   public Credentials(String username, String password) {
      if (username == null) {
         throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("username"));
      } else if (password == null) {
         throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("password"));
      } else {
         this.username = username;
         this.password = password.getBytes(AuthConfig.CHARACTER_SET);
      }
   }

   public String getUsername() {
      return this.username;
   }

   public byte[] getPassword() {
      return this.password;
   }

   public String toString() {
      return "Credentials{username: " + this.username + ", password: *****}";
   }
}
