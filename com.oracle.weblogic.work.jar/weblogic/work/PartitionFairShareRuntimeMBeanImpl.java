package weblogic.work;

import weblogic.management.ManagementException;
import weblogic.management.runtime.PartitionFairShareRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class PartitionFairShareRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PartitionFairShareRuntimeMBean {
   final PartitionFairShare partitionFairShare;

   public PartitionFairShareRuntimeMBeanImpl(PartitionFairShare partitionFairShare) throws ManagementException {
      super(partitionFairShare.getName());
      this.partitionFairShare = partitionFairShare;
   }

   public PartitionFairShareRuntimeMBeanImpl(PartitionFairShare partitionFairShare, RuntimeMBean parentArg) throws ManagementException {
      super(partitionFairShare.getName(), parentArg);
      this.partitionFairShare = partitionFairShare;
   }

   public int getFairShare() {
      return this.partitionFairShare.getFairShare();
   }

   public int getConfiguredFairShare() {
      return this.partitionFairShare.getConfiguredFairShare();
   }

   public long getThreadUse() {
      return this.partitionFairShare.getThreadUseSumSnapShot();
   }

   public double getPartitionAdjuster() {
      return this.partitionFairShare.getPartitionAdjusterSnapShot();
   }
}
