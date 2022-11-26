package weblogic.diagnostics.instrumentation;

import weblogic.diagnostics.type.DiagnosticException;

public class InstrumentationException extends DiagnosticException {
   public InstrumentationException() {
   }

   public InstrumentationException(String msg) {
      super(msg);
   }

   public InstrumentationException(Throwable t) {
      super(t);
   }

   public InstrumentationException(String msg, Throwable t) {
      super(msg, t);
   }
}
