package com.oracle.cmm.lowertier.jfr;

import com.oracle.cmm.lowertier.jfr.impl.FlightRecorderManagerBaseImpl;
import com.oracle.cmm.lowertier.jfr.impl.FlightRecorderManagerV1Impl;
import com.oracle.cmm.lowertier.jfr.impl.FlightRecorderManagerV2Impl;

public interface FlightRecorderManager {
   int OFF_VOLUME = 0;
   int LOW_VOLUME = 1;
   int MEDIUM_VOLUME = 2;
   int HIGH_VOLUME = 3;

   boolean isEventGenerationEnabled(int var1);

   void addEvent(Class var1);

   public static final class Factory {
      private static FlightRecorderManager SINGLETON = null;

      public static synchronized FlightRecorderManager getInstance() {
         if (SINGLETON == null) {
            SINGLETON = getVersionAppropriateManager();
         }

         return SINGLETON;
      }

      private static FlightRecorderManager getVersionAppropriateManager() {
         if (FlightRecorderManagerV2Impl.detectV2()) {
            return new FlightRecorderManagerV2Impl();
         } else {
            return (FlightRecorderManager)(FlightRecorderManagerV1Impl.detectActiveV1() ? new FlightRecorderManagerV1Impl() : new FlightRecorderManagerBaseImpl());
         }
      }
   }
}
