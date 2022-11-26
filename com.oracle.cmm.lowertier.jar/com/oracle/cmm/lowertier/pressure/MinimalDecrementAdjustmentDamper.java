package com.oracle.cmm.lowertier.pressure;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinimalDecrementAdjustmentDamper implements ResourcePressureAdjustmentDamper {
   private static final Logger LOGGER = Logger.getLogger(MinimalDecrementAdjustmentDamper.class.getPackage().getName());
   private long lastPressureAdjustmentTime = 0L;
   private int adjustedPressureSetting = 0;
   private static final String MINIMAL_DECREMENT = "MinimalDecrementTime";
   private static final long DEFAULT_MINIMAL_DECREMENT_TIME = 300000L;
   private long minimalDecrementTime = 300000L;

   public MinimalDecrementAdjustmentDamper() {
      this.lastPressureAdjustmentTime = System.currentTimeMillis();
      this.adjustedPressureSetting = 0;
   }

   public void initialize(String initialValue) {
      Properties props = new Properties();
      ParseUtils.parseCommaSeparatedNamedValues(initialValue, props);
      this.minimalDecrementTime = ParseUtils.getLongValueOrDefault(props, "MinimalDecrementTime", 300000L);
      if (this.minimalDecrementTime < 1L) {
         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("Invalid MinimalDecrementTime specified, defaulting to 5 minutes");
         }

         this.minimalDecrementTime = 300000L;
      }

   }

   public int adjustMemoryPressure(int currentPressure) {
      if (LOGGER.isLoggable(Level.FINER)) {
         LOGGER.finer("MinimalDecrementAdjustmentDamper called with " + currentPressure);
      }

      if (currentPressure > this.adjustedPressureSetting) {
         this.adjustedPressureSetting = currentPressure;
         this.lastPressureAdjustmentTime = System.currentTimeMillis();
      } else if (currentPressure < this.adjustedPressureSetting) {
         long currentTime = System.currentTimeMillis();
         long timeSinceLastAdjustment = currentTime - this.lastPressureAdjustmentTime;
         if (timeSinceLastAdjustment >= this.minimalDecrementTime) {
            this.adjustedPressureSetting = currentPressure;
            this.lastPressureAdjustmentTime = currentTime;
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("Pressure reading dropped to " + currentPressure + ", damper allowed the drop, time since last adjustment: " + timeSinceLastAdjustment);
            }
         } else if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("Pressure reading dropped to " + currentPressure + ", but damper is leaving pressure at " + this.adjustedPressureSetting + ", time since last adjustment: " + timeSinceLastAdjustment);
         }
      }

      return this.adjustedPressureSetting;
   }
}
