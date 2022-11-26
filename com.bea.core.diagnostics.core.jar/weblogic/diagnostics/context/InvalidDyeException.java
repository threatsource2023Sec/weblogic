package weblogic.diagnostics.context;

import weblogic.diagnostics.type.DiagnosticException;

public class InvalidDyeException extends DiagnosticException {
   public InvalidDyeException() {
   }

   public InvalidDyeException(String msg) {
      super(msg);
   }

   public InvalidDyeException(Throwable t) {
      super(t);
   }

   public InvalidDyeException(String msg, Throwable t) {
      super(msg, t);
   }
}
