package org.glassfish.soteria.identitystores;

public class IdentityStoreConfigurationException extends IdentityStoreException {
   public IdentityStoreConfigurationException(String message, Throwable cause) {
      super(message, cause);
   }

   public IdentityStoreConfigurationException(Throwable cause) {
      super(cause);
   }

   public IdentityStoreConfigurationException(String message) {
      super(message);
   }
}
