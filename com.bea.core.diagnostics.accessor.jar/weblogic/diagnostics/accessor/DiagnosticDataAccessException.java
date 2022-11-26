package weblogic.diagnostics.accessor;

public class DiagnosticDataAccessException extends Exception {
   public DiagnosticDataAccessException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public DiagnosticDataAccessException(String msg) {
      super(msg);
   }

   public DiagnosticDataAccessException(Throwable cause) {
      super(cause);
   }
}
