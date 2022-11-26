package weblogic.diagnostics.image;

import weblogic.diagnostics.type.DiagnosticException;

public class InvalidLockoutTimeException extends DiagnosticException {
   public InvalidLockoutTimeException() {
   }

   public InvalidLockoutTimeException(String msg) {
      super(msg);
   }

   public InvalidLockoutTimeException(Throwable t) {
      super(t);
   }

   public InvalidLockoutTimeException(String msg, Throwable t) {
      super(msg, t);
   }
}
