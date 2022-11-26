package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;

public class JFRHelperV2Impl implements JFRHelper {
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   private static FlightRecorderManager flightRecorderMgr = weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory.getInstance();

   public void initialize(int diagnosticVolume) {
      this.handlePropertyChange(diagnosticVolume);
   }

   public void handlePropertyChange(int newVolume) {
      if (newVolume != 2 && newVolume != 3) {
         flightRecorderMgr.disableJVMEventsInImageRecording();
      } else {
         flightRecorderMgr.enableJVMEventsInImageRecording();
      }

   }
}
