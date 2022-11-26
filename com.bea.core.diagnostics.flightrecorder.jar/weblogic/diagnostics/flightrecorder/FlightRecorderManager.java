package weblogic.diagnostics.flightrecorder;

import java.io.File;
import weblogic.diagnostics.flightrecorder.event.DebugEventContributor;
import weblogic.diagnostics.flightrecorder.impl.FlightRecorderManagerBaseImpl;
import weblogic.diagnostics.flightrecorder.impl.FlightRecorderManagerV1Impl;
import weblogic.diagnostics.flightrecorder.impl.FlightRecorderManagerV2Impl;

public interface FlightRecorderManager {
   void enableJVMEventsInImageRecording();

   void disableJVMEventsInImageRecording();

   void setDefaultSettings(int var1, boolean var2);

   void setDebugEventContributor(DebugEventContributor var1);

   boolean isRecordingPossible();

   boolean isECIDEnabled();

   boolean areJVMEventsExpensive();

   boolean canGenerateDebug();

   Object beginDebugTimedEvent(String var1, String var2, Object var3);

   void commitDebugTimedEvent(Object var1, Throwable var2, Object var3);

   void generateDebugEvent(String var1, String var2, Throwable var3, Object var4);

   void debugRecorderDetails();

   String getInstrumentedEventPackagePrefix();

   void startImageFlightRecording(String var1, File var2, boolean var3) throws Exception;

   boolean isImageRecordingActive();

   boolean copyImageRecordingToFile(File var1) throws Exception;

   boolean isJFR2();

   void enableImageRecordingClientEvents(String var1, int var2, boolean var3);

   void addEvent(Object var1, Class var2);

   public static final class Factory {
      private static FlightRecorderManager SINGLETON = null;

      public static synchronized FlightRecorderManager getInstance() {
         if (SINGLETON == null) {
            SINGLETON = getVersionAppropriateManager();
         }

         return SINGLETON;
      }

      private static FlightRecorderManager getVersionAppropriateManager() {
         FlightRecorderManager manager = instantiateV2ManagerOrNull();
         if (manager != null) {
            return manager;
         } else {
            return (FlightRecorderManager)(FlightRecorderManagerV1Impl.detectActiveV1() ? new FlightRecorderManagerV1Impl() : new FlightRecorderManagerBaseImpl());
         }
      }

      private static FlightRecorderManager instantiateV2ManagerOrNull() {
         try {
            Class flightRecorderClass = Class.forName("jdk.jfr.FlightRecorder");
            return flightRecorderClass != null && FlightRecorderManagerV2Impl.detectV2() ? new FlightRecorderManagerV2Impl() : null;
         } catch (ClassNotFoundException var1) {
            return null;
         }
      }
   }
}
