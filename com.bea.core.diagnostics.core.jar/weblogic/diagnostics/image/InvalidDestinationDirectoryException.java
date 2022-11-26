package weblogic.diagnostics.image;

import weblogic.diagnostics.type.DiagnosticException;

public class InvalidDestinationDirectoryException extends DiagnosticException {
   public InvalidDestinationDirectoryException() {
   }

   public InvalidDestinationDirectoryException(String msg) {
      super(msg);
   }

   public InvalidDestinationDirectoryException(Throwable t) {
      super(t);
   }

   public InvalidDestinationDirectoryException(String msg, Throwable t) {
      super(msg, t);
   }
}
