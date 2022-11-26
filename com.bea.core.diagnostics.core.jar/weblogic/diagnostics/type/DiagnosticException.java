package weblogic.diagnostics.type;

public class DiagnosticException extends Exception {
   public DiagnosticException() {
   }

   public DiagnosticException(String msg) {
      super(msg);
   }

   public DiagnosticException(Throwable t) {
      super(t);
   }

   public DiagnosticException(String msg, Throwable t) {
      super(msg, t);
   }
}
