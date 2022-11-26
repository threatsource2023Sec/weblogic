package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.flightrecorder.FlightRecorderBaseEvent;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory;
import weblogic.diagnostics.instrumentation.AroundDiagnosticAction;
import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.gathering.DataGatheringManager;
import weblogic.diagnostics.instrumentation.gathering.FlightRecorderEventHelper;

public class FlightRecorderAroundAction implements AroundDiagnosticAction {
   private static final FlightRecorderManager flightRecorderMgr = Factory.getInstance();
   private static final long serialVersionUID = 1L;
   private DiagnosticMonitor monitor;
   private String type;
   private static final FlightRecorderAroundActionState disabledActionState = new FlightRecorderAroundActionState(false);

   public FlightRecorderAroundAction() {
      this.setType("FlightRecorderAroundAction");
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

   public DiagnosticActionState createState() {
      return DataGatheringManager.getDiagnosticVolume() != 0 && flightRecorderMgr.isRecordingPossible() ? new FlightRecorderAroundActionState(true) : disabledActionState;
   }

   public void preProcess(JoinPoint jp, DiagnosticActionState actionState) {
      if (actionState != null) {
         FlightRecorderAroundActionState frState = (FlightRecorderAroundActionState)actionState;
         frState.begin(this.monitor, jp);
      }
   }

   public void postProcess(JoinPoint jp, DiagnosticActionState actionState) {
      if (actionState != null) {
         FlightRecorderAroundActionState frState = (FlightRecorderAroundActionState)actionState;
         frState.finishAndRecord(this.monitor, jp);
      }
   }

   private static class FlightRecorderAroundActionState implements DiagnosticActionState {
      private boolean enabled;
      private FlightRecorderBaseEvent aroundEvent;

      private FlightRecorderAroundActionState(boolean enabled) {
         this.enabled = false;
         this.aroundEvent = null;
         this.enabled = enabled;
      }

      private void begin(DiagnosticMonitor monitor, JoinPoint jp) {
         if (this.enabled) {
            this.aroundEvent = FlightRecorderEventHelper.getInstance().getTimedEvent(monitor, jp);
            if (this.aroundEvent != null) {
               this.aroundEvent.callBegin();
            }

         }
      }

      private void finishAndRecord(DiagnosticMonitor monitor, JoinPoint jp) {
         if (this.enabled && this.aroundEvent != null) {
            this.aroundEvent.callEnd();
            FlightRecorderEventHelper.getInstance().recordTimedEvent(monitor, jp, this.aroundEvent);
         }
      }

      // $FF: synthetic method
      FlightRecorderAroundActionState(boolean x0, Object x1) {
         this(x0);
      }
   }
}
