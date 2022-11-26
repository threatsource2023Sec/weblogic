package org.jboss.weld.exceptions;

public class UnserializableDependencyException extends DeploymentException {
   private static final long serialVersionUID = -6287506607413810688L;

   public UnserializableDependencyException(Throwable throwable) {
      super(throwable);
   }

   public UnserializableDependencyException(String message) {
      super(message);
   }
}
