package weblogic.diagnostics.harvester.internal;

import weblogic.diagnostics.harvester.HarvesterStatistics;

class HarvesterStatisticsImpl implements HarvesterInternalConstants, HarvesterStatistics {
   protected long maximumSamplingTimeNanos = 0L;
   protected long minimumSamplingTimeNanos = 0L;
   protected long totalConfiguredDataSampleCount = 0L;
   protected int totalSamplingCycles = 0;
   protected long totalSamplingTimeNanos = 0L;

   public long getMaximumSamplingTimeNanos() {
      return this.maximumSamplingTimeNanos;
   }

   public long getMinimumSamplingTimeNanos() {
      return this.minimumSamplingTimeNanos;
   }

   public long getTotalConfiguredDataSampleCount() {
      return this.totalConfiguredDataSampleCount;
   }

   public int getTotalSamplingCycles() {
      return this.totalSamplingCycles;
   }

   public long getTotalSamplingTimeNanos() {
      return this.totalSamplingTimeNanos;
   }

   public long getAverageSamplingTimeNanos() {
      float averageSamplingTimeNanos = (float)this.totalSamplingTimeNanos / (float)this.totalSamplingCycles;
      return (long)Math.round(averageSamplingTimeNanos);
   }

   protected void updateMinSamplingTime(long currentSnapshotElapsedTimeNanos) {
      this.minimumSamplingTimeNanos = this.minimumSamplingTimeNanos <= 0L ? currentSnapshotElapsedTimeNanos : Math.min(this.minimumSamplingTimeNanos, currentSnapshotElapsedTimeNanos);
   }
}
