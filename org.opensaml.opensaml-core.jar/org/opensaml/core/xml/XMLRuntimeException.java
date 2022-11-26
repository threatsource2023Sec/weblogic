package org.opensaml.core.xml;

import javax.annotation.Nullable;

public class XMLRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 1299468635977382060L;

   public XMLRuntimeException() {
   }

   public XMLRuntimeException(@Nullable String message) {
      super(message);
   }

   public XMLRuntimeException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public XMLRuntimeException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
