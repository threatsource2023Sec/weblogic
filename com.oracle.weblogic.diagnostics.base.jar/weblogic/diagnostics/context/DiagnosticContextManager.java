package weblogic.diagnostics.context;

public final class DiagnosticContextManager {
   private static final DiagnosticContextManager singleton = new DiagnosticContextManager();
   private CorrelationManager correlationManager = CorrelationManager.getCorrelationManagerInternal();

   private DiagnosticContextManager() {
   }

   public static DiagnosticContextManager getDiagnosticContextManager() {
      return singleton;
   }

   public boolean isEnabled() {
      return this.correlationManager.isEnabled();
   }

   public void setEnabled(boolean enable) {
      this.correlationManager.setEnabled(enable);
   }
}
