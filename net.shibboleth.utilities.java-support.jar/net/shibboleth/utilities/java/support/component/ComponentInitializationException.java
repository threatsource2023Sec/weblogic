package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nullable;

public class ComponentInitializationException extends Exception {
   private static final long serialVersionUID = -470551134594606458L;

   public ComponentInitializationException() {
   }

   public ComponentInitializationException(@Nullable String message) {
      super(message);
   }

   public ComponentInitializationException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public ComponentInitializationException(@Nullable String message, @Nullable Exception wrapped) {
      super(message, wrapped);
   }
}
