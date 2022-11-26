package org.opensaml.saml.common;

import javax.annotation.Nullable;

public class SAMLException extends Exception {
   private static final long serialVersionUID = 6308450535247361691L;

   public SAMLException() {
   }

   public SAMLException(@Nullable String message) {
      super(message);
   }

   public SAMLException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public SAMLException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
