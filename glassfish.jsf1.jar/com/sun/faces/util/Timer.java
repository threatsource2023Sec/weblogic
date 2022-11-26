package com.sun.faces.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Timer {
   private static final Logger LOGGER;
   long start;
   long stop;

   private Timer() {
   }

   public static Timer getInstance() {
      return LOGGER.isLoggable(Level.FINE) ? new Timer() : null;
   }

   public void startTiming() {
      this.start = System.currentTimeMillis();
   }

   public void stopTiming() {
      this.stop = System.currentTimeMillis();
   }

   public void logResult(String taskInfo) {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, " [TIMING] - [" + this.getTimingResult() + "ms] : " + taskInfo);
      }

   }

   private long getTimingResult() {
      return this.stop - this.start;
   }

   static {
      LOGGER = FacesLogger.TIMING.getLogger();
   }
}
