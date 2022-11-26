package weblogic.deploy.container;

import weblogic.management.DeploymentException;

public class NonFatalDeploymentException extends DeploymentException {
   private static final long serialVersionUID = 321088334582734288L;

   public NonFatalDeploymentException(String message, Throwable t) {
      super(message, t);
   }

   public NonFatalDeploymentException(Throwable t) {
      super(t);
   }

   public NonFatalDeploymentException(String message) {
      super(message);
   }
}
