package org.opensaml.security;

import javax.annotation.Nullable;

public class SecurityException extends Exception {
   private static final long serialVersionUID = 485895728446891757L;

   public SecurityException() {
   }

   public SecurityException(@Nullable String message) {
      super(message);
   }

   public SecurityException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public SecurityException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
