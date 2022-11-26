package weblogic.diagnostics.flightrecorder.impl;

import java.io.File;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.event.DebugEventContributor;
import weblogic.utils.PropertyHelper;

public class FlightRecorderManagerBaseImpl implements FlightRecorderManager {
   protected DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   protected final boolean pureJavaAllowed = PropertyHelper.getBoolean("weblogic.diagnostics.flightrecorder.UsePureJavaJFR");
   protected final long threshold = PropertyHelper.getLong("weblogic.diagnostics.flightrecorder.CommitThreshold", -1L);
   protected final boolean ECIDEnabled = !PropertyHelper.getBoolean("weblogic.diagnostics.flightrecorder.DisableECID");
   protected final boolean internalStackTracesEnabled = PropertyHelper.getBoolean("weblogic.diagnostics.flightrecorder.EnableInternalStackTraces");
   protected final boolean disableWLDFJFRUsage = PropertyHelper.getBoolean("weblogic.diagnostics.flightrecorder.DisableWLDFJFRUsage");
   protected final boolean disableJVMEventExpenseCheck = PropertyHelper.getBoolean("weblogic.diagnostics.flightrecorder.DisableJVMEventExpenseCheck");
   protected boolean JVMEventsExpensive = true;
   protected boolean clientCheckPerformed = false;
   protected final long MINIMUM_RECORDING_SIZE = 5000000L;
   protected int defaultMaxSizeMultiple = 3;
   protected boolean defaultEnableStackTraces = false;
   protected boolean jfrChecksCached = false;
   protected boolean isNativeJFR = false;
   protected boolean isJFRActive = false;
   protected boolean debugRecordingStarted = false;
   protected boolean debugRecordingStartupFailed = false;
   protected final String DEBUG_RECORDING_NAME = "__WLDF_debug_events";
   protected final String DEBUG_PRODUCER_INFO = "WLDF Debug Event Producer";
   protected final String DEBUG_PRODUCER_URI = "http://www.oracle.com/wls/flightrecorder/debug";
   protected volatile DebugEventContributor contributor = null;

   public boolean isRecordingPossible() {
      return false;
   }

   public void enableJVMEventsInImageRecording() {
   }

   public void disableJVMEventsInImageRecording() {
   }

   public void setDefaultSettings(int multiple, boolean enableStackTraces) {
   }

   public void setDebugEventContributor(DebugEventContributor theContributor) {
   }

   public boolean isECIDEnabled() {
      return this.ECIDEnabled;
   }

   public boolean areJVMEventsExpensive() {
      return this.disableJVMEventExpenseCheck ? false : this.JVMEventsExpensive;
   }

   public void startImageFlightRecording(String recordingName, File destinationFile, boolean jvmEventsDisabled) throws Exception {
   }

   public boolean isImageRecordingActive() {
      return false;
   }

   public boolean copyImageRecordingToFile(File tempFile) throws Exception {
      return false;
   }

   public String getInstrumentedEventPackagePrefix() {
      return "";
   }

   public void addEvent(Object tokenObj, Class eventClass) {
   }

   public boolean canGenerateDebug() {
      return false;
   }

   public Object beginDebugTimedEvent(String component, String message, Object contributorOverride) {
      return null;
   }

   public void commitDebugTimedEvent(Object event, Throwable debugTh, Object contributorOverride) {
   }

   public void generateDebugEvent(String component, String message, Throwable debugTh, Object contributorOverride) {
   }

   public void debugRecorderDetails() {
      if (this.debugLog.isDebugEnabled()) {
         this.debugLog.debug("debugRecorderDetails() flight recorder is not possible or is not enabled");
      }

   }

   public boolean isJFR2() {
      return false;
   }

   public void enableImageRecordingClientEvents(String producerPrefix, int maxSizeMultiple, boolean enableEventStackTraces) {
   }
}
