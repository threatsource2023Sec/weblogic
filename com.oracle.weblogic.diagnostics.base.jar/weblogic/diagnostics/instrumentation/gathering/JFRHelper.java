package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.FlightRecorderManager;

public interface JFRHelper {
   void initialize(int var1);

   void handlePropertyChange(int var1);

   public static final class Factory {
      private static JFRHelper SINGLETON = null;

      public static synchronized JFRHelper getInstance() {
         if (SINGLETON == null) {
            SINGLETON = getVersionAppropriateHelper();
         }

         return SINGLETON;
      }

      private static JFRHelper getVersionAppropriateHelper() {
         FlightRecorderManager fMgr = weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory.getInstance();
         return (JFRHelper)(fMgr.isJFR2() ? new JFRHelperV2Impl() : new JFRHelperV1Impl());
      }
   }
}
