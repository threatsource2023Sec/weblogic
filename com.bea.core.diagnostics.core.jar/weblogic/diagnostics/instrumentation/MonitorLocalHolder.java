package weblogic.diagnostics.instrumentation;

public final class MonitorLocalHolder {
   public DiagnosticMonitor monitor = null;
   public DynamicJoinPoint djp = null;
   public DiagnosticAction[] actions = null;
   public DiagnosticActionState[] states = null;
   public boolean captureArgs = false;
   public Object monitorState = null;

   public Object getMonitorState() {
      return this.monitorState;
   }

   public void setMonitorState(Object obj) {
      this.monitorState = obj;
   }
}
