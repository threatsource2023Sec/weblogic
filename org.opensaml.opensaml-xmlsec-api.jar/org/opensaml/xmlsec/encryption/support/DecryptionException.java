package org.opensaml.xmlsec.encryption.support;

import javax.annotation.Nullable;

public class DecryptionException extends Exception {
   private static final long serialVersionUID = 7785660781162212790L;

   public DecryptionException() {
   }

   public DecryptionException(@Nullable String message) {
      super(message);
   }

   public DecryptionException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public DecryptionException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
