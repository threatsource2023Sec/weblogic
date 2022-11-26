package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nullable;

public class SignatureException extends Exception {
   private static final long serialVersionUID = 7879866991794922684L;

   public SignatureException() {
   }

   public SignatureException(@Nullable String message) {
      super(message);
   }

   public SignatureException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public SignatureException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
