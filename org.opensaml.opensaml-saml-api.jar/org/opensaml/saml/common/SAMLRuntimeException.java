package org.opensaml.saml.common;

import javax.annotation.Nullable;

public class SAMLRuntimeException extends RuntimeException {
   private static final long serialVersionUID = -593201582585161250L;

   public SAMLRuntimeException() {
   }

   public SAMLRuntimeException(@Nullable String message) {
      super(message);
   }

   public SAMLRuntimeException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public SAMLRuntimeException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
