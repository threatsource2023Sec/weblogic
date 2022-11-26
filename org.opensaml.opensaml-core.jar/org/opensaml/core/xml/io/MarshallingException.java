package org.opensaml.core.xml.io;

import javax.annotation.Nullable;

public class MarshallingException extends Exception {
   private static final long serialVersionUID = 7381813529926476459L;

   public MarshallingException() {
   }

   public MarshallingException(@Nullable String message) {
      super(message);
   }

   public MarshallingException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public MarshallingException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
