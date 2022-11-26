package weblogic.diagnostics.accessor;

import weblogic.diagnostics.type.DiagnosticException;

public class AccessorException extends DiagnosticException {
   public AccessorException(String msg) {
      super(msg);
   }

   public AccessorException(String msg, Throwable t) {
      super(msg, t);
   }
}
