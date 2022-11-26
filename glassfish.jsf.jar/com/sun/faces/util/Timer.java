package com.sun.faces.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Timer {
   private static final Logger LOGGER;
   private final Level logLevel;
   private long start;
   private long stop;

   private Timer(Level logLevel) {
      this.logLevel = logLevel;
   }

   public static Timer getInstance() {
      return getInstance(Level.FINE);
   }

   public static Timer getInstance(Level logLevel) {
      return LOGGER.isLoggable(logLevel) ? new Timer(logLevel) : null;
   }

   public void startTiming() {
      this.start = System.currentTimeMillis();
   }

   public void stopTiming() {
      this.stop = System.currentTimeMillis();
   }

   public void logResult(String taskInfo) {
      if (LOGGER.isLoggable(this.logLevel)) {
         LOGGER.log(this.logLevel, " [TIMING] - [" + this.getTimingResult() + "ms] : " + taskInfo);
      }

   }

   private long getTimingResult() {
      return this.stop - this.start;
   }

   static {
      LOGGER = FacesLogger.TIMING.getLogger();
   }
}
