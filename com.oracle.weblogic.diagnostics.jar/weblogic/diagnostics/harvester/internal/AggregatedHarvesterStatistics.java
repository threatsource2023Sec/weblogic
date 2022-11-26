package weblogic.diagnostics.harvester.internal;

import weblogic.diagnostics.harvester.HarvesterCollectorStatistics;

class AggregatedHarvesterStatistics extends HarvesterStatisticsImpl {
   public void merge(HarvesterCollectorStatistics statistics) {
      ++this.totalSamplingCycles;
      this.totalConfiguredDataSampleCount += (long)statistics.getCurrentDataSampleCount();
      this.totalSamplingTimeNanos += statistics.getCurrentSnapshotElapsedTimeNanos();
      this.updateMinSamplingTime(statistics.getCurrentSnapshotElapsedTimeNanos());
      this.maximumSamplingTimeNanos = Math.max(this.maximumSamplingTimeNanos, statistics.getCurrentSnapshotElapsedTimeNanos());
   }
}
