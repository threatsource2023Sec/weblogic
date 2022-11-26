package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nullable;

public class UnmodifiableComponentException extends RuntimeException {
   private static final long serialVersionUID = 4652326903008490376L;

   public UnmodifiableComponentException() {
   }

   public UnmodifiableComponentException(@Nullable String message) {
      super(message);
   }

   public UnmodifiableComponentException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public UnmodifiableComponentException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
