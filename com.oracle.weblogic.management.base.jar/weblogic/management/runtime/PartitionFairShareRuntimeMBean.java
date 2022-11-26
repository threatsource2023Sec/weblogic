package weblogic.management.runtime;

public interface PartitionFairShareRuntimeMBean extends RuntimeMBean {
   int getFairShare();

   int getConfiguredFairShare();

   long getThreadUse();

   double getPartitionAdjuster();
}
