package org.glassfish.soteria.identitystores;

public class IdentityStoreException extends RuntimeException {
   public IdentityStoreException(String message, Throwable cause) {
      super(message, cause);
   }

   public IdentityStoreException(Throwable cause) {
      super(cause);
   }

   public IdentityStoreException(String message) {
      super(message);
   }
}
