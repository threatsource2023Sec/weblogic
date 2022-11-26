package weblogic.management.patching.commands;

import weblogic.management.patching.PatchingDebugLogger;

public class TimeoutUtils {
   public static final String TIMEOUT_PERCENTAGE_FACTOR_PROP = "weblogic.patching.TimeoutPercentageFactor";

   public long convertTimeoutByPercentageFactor(long timeout) {
      Integer percentFactor = Integer.getInteger("weblogic.patching.TimeoutPercentageFactor");
      if (percentFactor != null) {
         if (percentFactor <= 0) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Invalid weblogic.patching.TimeoutPercentageFactor property. Value must be greater than 0, but was " + percentFactor);
            }
         } else {
            timeout = timeout * (long)percentFactor / 100L;
         }
      }

      return timeout;
   }

   public long convertIntervalByFactorIfLarger(long interval) {
      long adjustedInterval = this.convertTimeoutByPercentageFactor(interval);
      if (adjustedInterval > interval) {
         interval = adjustedInterval;
      }

      return interval;
   }
}
