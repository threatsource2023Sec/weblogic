package weblogic.application.library;

import weblogic.management.DeploymentException;

public class LibraryDeploymentException extends DeploymentException {
   public LibraryDeploymentException(String s) {
      super(s);
   }

   public LibraryDeploymentException(Throwable th) {
      super(th);
   }
}
