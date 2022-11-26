package org.jboss.weld.exceptions;

public class InconsistentSpecializationException extends DeploymentException {
   private static final long serialVersionUID = 4359656880524913555L;

   public InconsistentSpecializationException(Throwable throwable) {
      super(throwable);
   }

   public InconsistentSpecializationException(String message) {
      super(message);
   }
}
