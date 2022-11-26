package net.shibboleth.utilities.java.support.component;

public class DestroyedComponentException extends RuntimeException {
   private static final long serialVersionUID = -2254557697221898493L;

   public DestroyedComponentException() {
   }

   public DestroyedComponentException(Object destroyedComponent) {
      super(destroyedComponent.toString() + " has been destroyed");
   }

   public DestroyedComponentException(String message) {
      super(message);
   }

   public DestroyedComponentException(Exception wrappedException) {
      super(wrappedException);
   }

   public DestroyedComponentException(String message, Exception wrappedException) {
      super(message, wrappedException);
   }
}
