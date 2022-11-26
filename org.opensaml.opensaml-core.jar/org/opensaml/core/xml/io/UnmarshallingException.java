package org.opensaml.core.xml.io;

import javax.annotation.Nullable;

public class UnmarshallingException extends Exception {
   private static final long serialVersionUID = 7512624219806550152L;

   public UnmarshallingException() {
   }

   public UnmarshallingException(@Nullable String message) {
      super(message);
   }

   public UnmarshallingException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public UnmarshallingException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
