package org.opensaml.xmlsec.encryption.support;

import javax.annotation.Nullable;

public class EncryptionException extends Exception {
   private static final long serialVersionUID = -3196546983060216532L;

   public EncryptionException() {
   }

   public EncryptionException(@Nullable String message) {
      super(message);
   }

   public EncryptionException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public EncryptionException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
