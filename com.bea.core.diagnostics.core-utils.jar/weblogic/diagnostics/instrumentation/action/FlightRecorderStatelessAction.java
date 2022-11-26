package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;
import weblogic.diagnostics.instrumentation.gathering.FlightRecorderEventHelper;

public class FlightRecorderStatelessAction implements StatelessDiagnosticAction {
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   private static final FlightRecorderManager flightRecorderMgr = Factory.getInstance();
   private static final long serialVersionUID = 1L;
   private DiagnosticMonitor monitor;
   private String type;

   public FlightRecorderStatelessAction() {
      this.setType("FlightRecorderStatelessAction");
   }

   public boolean requiresArgumentsCapture() {
      return true;
   }

   public void setDiagnosticMonitor(DiagnosticMonitor monitor) {
      this.monitor = monitor;
   }

   public DiagnosticMonitor getDiagnosticMonitor() {
      return this.monitor;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getType() {
      return this.type;
   }

   public void process(JoinPoint jp) {
      if (flightRecorderMgr.isRecordingPossible()) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("FlightRecorderStatelessAction.process()");
         }

         FlightRecorderEventHelper.getInstance().recordStatelessEvent(this.monitor, jp);
      }
   }
}
