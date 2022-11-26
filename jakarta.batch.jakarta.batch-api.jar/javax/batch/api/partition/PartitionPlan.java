package javax.batch.api.partition;

import java.util.Properties;

public interface PartitionPlan {
   void setPartitions(int var1);

   void setPartitionsOverride(boolean var1);

   boolean getPartitionsOverride();

   void setThreads(int var1);

   void setPartitionProperties(Properties[] var1);

   int getPartitions();

   int getThreads();

   Properties[] getPartitionProperties();
}
