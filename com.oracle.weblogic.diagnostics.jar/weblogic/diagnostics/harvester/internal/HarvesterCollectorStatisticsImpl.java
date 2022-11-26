package weblogic.diagnostics.harvester.internal;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.harvester.HarvesterCollectorStatistics;

class HarvesterCollectorStatisticsImpl extends HarvesterStatisticsImpl implements HarvesterCollectorStatistics {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticsHarvester");
   int currentDataSampleCount = 0;
   long currentSnapshotElapsedTimeNanos = 0L;
   long currentSnapshotStartTimeMillis = -1L;

   public int getCurrentDataSampleCount() {
      return this.currentDataSampleCount;
   }

   public long getCurrentSnapshotElapsedTimeNanos() {
      return this.currentSnapshotElapsedTimeNanos;
   }

   public long getCurrentSnapshotStartTimeMillis() {
      return this.currentSnapshotStartTimeMillis;
   }

   public long getCurrentSnapshotStartTimeNanos() {
      return this.currentSnapshotStartTimeMillis < 0L ? this.currentSnapshotStartTimeMillis : this.currentSnapshotStartTimeMillis * 1000000L;
   }

   public long getOutlierCount() {
      return 0L;
   }

   public void updateStatistics(long cycleStartTime, int sampleCount, long timeToAddNanos) {
      this.incrementCycleCount();
      this.currentSnapshotStartTimeMillis = cycleStartTime;
      this.currentSnapshotElapsedTimeNanos = timeToAddNanos;
      this.currentDataSampleCount = sampleCount;
      this.totalConfiguredDataSampleCount += (long)this.currentDataSampleCount;
      this.totalSamplingTimeNanos += this.currentSnapshotElapsedTimeNanos;
      this.updateMinSamplingTime(this.currentSnapshotElapsedTimeNanos);
      this.maximumSamplingTimeNanos = Math.max(this.maximumSamplingTimeNanos, this.currentSnapshotElapsedTimeNanos);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("HS:              " + this.currentSnapshotElapsedTimeNanos + " " + this.totalSamplingTimeNanos);
      }

   }

   private long incrementCycleCount() {
      return (long)(this.totalSamplingCycles++);
   }
}
