package com.oracle.cmm.lowertier.pressure;

import com.oracle.cmm.lowertier.gathering.ProcMemInfo;
import com.oracle.cmm.lowertier.gathering.ProcVmstat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LinuxPressureEvaluator implements ResourcePressureEvaluator {
   private static final Logger LOGGER = Logger.getLogger(LinuxPressureEvaluator.class.getPackage().getName());
   private static final String SWAPOUT_CONSECUTIVE_THRESHOLD = "SwapOutPerSecConsecutivePeriodsThreshold";
   private static final String SWAPIN_CONSECUTIVE_THRESHOLD = "SwapInPerSecConsecutivePeriodsThreshold";
   private static final long DEFAULT_SWAPOUT_CONSECUTIVE_THRESHOLD = 10L;
   private static final long DEFAULT_SWAPIN_CONSECUTIVE_THRESHOLD = 50L;
   private ProcMemInfo procMemInfo = null;
   private ProcVmstat procVmstat = null;
   private long pswpinPerSecConsecutiveThreshold;
   private long pswpoutPerSecConsecutiveThreshold;

   public LinuxPressureEvaluator() {
      this.procMemInfo = new ProcMemInfo();
      this.procVmstat = new ProcVmstat();
   }

   public void initialize(String initialValue) {
      Properties props = new Properties();
      ParseUtils.parseCommaSeparatedNamedValues(initialValue, props);
      this.pswpinPerSecConsecutiveThreshold = ParseUtils.getLongValueOrDefault(props, "SwapInPerSecConsecutivePeriodsThreshold", 50L);
      this.pswpoutPerSecConsecutiveThreshold = ParseUtils.getLongValueOrDefault(props, "SwapOutPerSecConsecutivePeriodsThreshold", 10L);
   }

   public int evaluateMemoryPressure() {
      int computedPressure = false;
      this.procMemInfo.gatherStatistics();
      if (LOGGER.isLoggable(Level.FINER)) {
         LOGGER.finer(this.procMemInfo.toString());
      }

      this.procVmstat.gatherStatistics();
      if (LOGGER.isLoggable(Level.FINER)) {
         LOGGER.finer(this.procVmstat.toString());
      }

      if (LOGGER.isLoggable(Level.FINER)) {
         LOGGER.finer("memTotal: " + this.procMemInfo.getMemTotal() + " committedAS: " + this.procMemInfo.getCommittedAS() + " pswpinPerSecConsecutiveTrailingIntervalsOverZero: " + this.procVmstat.getpswpinPerSecConsecutiveTrailingIntervalsOverZero() + " pswpoutPerSecConsecutiveTrailingIntervalsOverZero: " + this.procVmstat.getpswpoutPerSecConsecutiveTrailingIntervalsOverZero());
      }

      if (this.procVmstat.getpswpoutPerSecConsecutiveTrailingIntervalsOverZero() > this.pswpoutPerSecConsecutiveThreshold) {
         return 9;
      } else if (this.procVmstat.getpswpinPerSecConsecutiveTrailingIntervalsOverZero() > this.pswpinPerSecConsecutiveThreshold) {
         return 6;
      } else {
         return this.procMemInfo.getCommittedAS() > this.procMemInfo.getMemTotal() ? 3 : 0;
      }
   }
}
