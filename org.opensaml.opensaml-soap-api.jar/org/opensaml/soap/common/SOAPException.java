package org.opensaml.soap.common;

import javax.annotation.Nullable;

public class SOAPException extends Exception {
   private static final long serialVersionUID = 1374150092262909937L;

   public SOAPException() {
   }

   public SOAPException(@Nullable String message) {
      super(message);
   }

   public SOAPException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public SOAPException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
