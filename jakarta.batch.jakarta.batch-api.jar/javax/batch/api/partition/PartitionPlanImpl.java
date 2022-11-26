package javax.batch.api.partition;

import java.util.Properties;

public class PartitionPlanImpl implements PartitionPlan {
   private int partitions = 0;
   private boolean override = false;
   private int threads = 0;
   Properties[] partitionProperties = null;

   public void setPartitions(int count) {
      this.partitions = count;
      if (this.threads == 0) {
         this.threads = count;
      }

   }

   public void setThreads(int count) {
      this.threads = count;
   }

   public void setPartitionsOverride(boolean override) {
      this.override = override;
   }

   public boolean getPartitionsOverride() {
      return this.override;
   }

   public void setPartitionProperties(Properties[] props) {
      this.partitionProperties = props;
   }

   public int getPartitions() {
      return this.partitions;
   }

   public int getThreads() {
      return this.threads;
   }

   public Properties[] getPartitionProperties() {
      return this.partitionProperties;
   }
}
