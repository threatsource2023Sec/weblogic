package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nullable;

public class UninitializedComponentException extends RuntimeException {
   private static final long serialVersionUID = -3451363632449131551L;

   public UninitializedComponentException() {
   }

   public UninitializedComponentException(Object uninitializedComponent) {
      super(uninitializedComponent.toString() + " has not been initialized");
   }

   public UninitializedComponentException(@Nullable String message) {
      super(message);
   }

   public UninitializedComponentException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public UninitializedComponentException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
