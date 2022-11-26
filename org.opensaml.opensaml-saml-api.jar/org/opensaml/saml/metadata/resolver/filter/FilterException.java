package org.opensaml.saml.metadata.resolver.filter;

import javax.annotation.Nullable;

public class FilterException extends Exception {
   private static final long serialVersionUID = 6214474330141026496L;

   public FilterException() {
   }

   public FilterException(@Nullable String message) {
      super(message);
   }

   public FilterException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public FilterException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
