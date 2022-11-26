package weblogic.diagnostics.type;

public class DiagnosticRuntimeException extends RuntimeException {
   public DiagnosticRuntimeException() {
   }

   public DiagnosticRuntimeException(String msg) {
      super(msg);
   }

   public DiagnosticRuntimeException(Throwable t) {
      super(t);
   }

   public DiagnosticRuntimeException(String msg, Throwable t) {
      super(msg, t);
   }
}
