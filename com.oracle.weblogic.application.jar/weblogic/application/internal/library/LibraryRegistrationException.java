package weblogic.application.internal.library;

import weblogic.management.DeploymentException;

public class LibraryRegistrationException extends DeploymentException {
   public LibraryRegistrationException(String message, Throwable t) {
      super(message, t);
   }

   public LibraryRegistrationException(Throwable t) {
      this("", t);
   }

   public LibraryRegistrationException(String message) {
      this(message, (Throwable)null);
   }
}
