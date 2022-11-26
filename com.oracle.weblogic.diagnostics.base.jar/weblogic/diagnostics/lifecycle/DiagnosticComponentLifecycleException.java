package weblogic.diagnostics.lifecycle;

import weblogic.diagnostics.type.DiagnosticException;

public class DiagnosticComponentLifecycleException extends DiagnosticException {
   public DiagnosticComponentLifecycleException() {
   }

   public DiagnosticComponentLifecycleException(String msg) {
      super(msg);
   }

   public DiagnosticComponentLifecycleException(Throwable t) {
      super(t);
   }

   public DiagnosticComponentLifecycleException(String msg, Throwable t) {
      super(msg, t);
   }
}
