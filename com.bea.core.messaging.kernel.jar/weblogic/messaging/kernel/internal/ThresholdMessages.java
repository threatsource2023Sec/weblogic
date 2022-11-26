package weblogic.messaging.kernel.internal;

import weblogic.work.WorkManager;

public final class ThresholdMessages extends ThresholdImpl {
   public ThresholdMessages(StatisticsImpl statistics, long high, long low, WorkManager workManager) {
      super(statistics, high, low, workManager);
   }

   public long getValue() {
      return (long)this.statistics.getMessagesCurrent();
   }
}
