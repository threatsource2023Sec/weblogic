package org.opensaml.soap.client;

import javax.annotation.Nullable;
import org.opensaml.soap.common.SOAPException;

public class SOAPClientException extends SOAPException {
   private static final long serialVersionUID = 6203715340959992457L;

   public SOAPClientException() {
   }

   public SOAPClientException(@Nullable String message) {
      super(message);
   }

   public SOAPClientException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public SOAPClientException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
