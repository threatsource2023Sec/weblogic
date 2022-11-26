package org.glassfish.soteria.identitystores;

public class IdentityStoreRuntimeException extends IdentityStoreException {
   public IdentityStoreRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }

   public IdentityStoreRuntimeException(Throwable cause) {
      super(cause);
   }

   public IdentityStoreRuntimeException(String message) {
      super(message);
   }
}
