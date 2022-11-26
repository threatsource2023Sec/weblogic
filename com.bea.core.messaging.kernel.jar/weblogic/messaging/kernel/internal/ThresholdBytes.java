package weblogic.messaging.kernel.internal;

import weblogic.work.WorkManager;

public final class ThresholdBytes extends ThresholdImpl {
   ThresholdBytes(StatisticsImpl statistics, long high, long low, WorkManager workManager) {
      super(statistics, high, low, workManager);
   }

   public long getValue() {
      return this.statistics.getBytesCurrent();
   }
}
