package weblogic.diagnostics.type;

public class FeatureNotAvailableException extends DiagnosticException {
   public FeatureNotAvailableException() {
   }

   public FeatureNotAvailableException(String msg) {
      super(msg);
   }

   public FeatureNotAvailableException(Throwable t) {
      super(t);
   }

   public FeatureNotAvailableException(String msg, Throwable t) {
      super(msg, t);
   }
}
