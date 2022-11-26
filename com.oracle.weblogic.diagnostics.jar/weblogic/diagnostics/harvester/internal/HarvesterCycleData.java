package weblogic.diagnostics.harvester.internal;

import weblogic.diagnostics.harvester.HarvesterCollectorStatistics;

class HarvesterCycleData {
   private HarvesterSnapshot cycleSnapshot;
   private HarvesterCollectorStatistics cycleStatistics;
   private String partitionName;

   public HarvesterCycleData(String partition, HarvesterSnapshot cycleSnapshot, HarvesterCollectorStatistics cycleStatistics) {
      this.partitionName = partition;
      this.cycleSnapshot = cycleSnapshot;
      this.cycleStatistics = cycleStatistics;
   }

   public long getSnapshotTimeMillis() {
      return this.cycleSnapshot.getSnapshotStartTimeMillis();
   }

   public HarvesterSnapshot getCycleSnapshot() {
      return this.cycleSnapshot;
   }

   public HarvesterCollectorStatistics getCycleStatistics() {
      return this.cycleStatistics;
   }

   public String getPartitionName() {
      return this.partitionName;
   }
}
