package net.shibboleth.utilities.java.support.resolver;

import javax.annotation.Nullable;

public class ResolverException extends Exception {
   private static final long serialVersionUID = 485895728446891757L;

   public ResolverException() {
   }

   public ResolverException(@Nullable String message) {
      super(message);
   }

   public ResolverException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public ResolverException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
