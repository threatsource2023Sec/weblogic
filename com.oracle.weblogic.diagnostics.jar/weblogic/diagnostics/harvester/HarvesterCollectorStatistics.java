package weblogic.diagnostics.harvester;

public interface HarvesterCollectorStatistics extends HarvesterStatistics {
   int getCurrentDataSampleCount();

   long getCurrentSnapshotElapsedTimeNanos();

   long getCurrentSnapshotStartTimeMillis();

   long getCurrentSnapshotStartTimeNanos();
}
