package weblogic.work;

import weblogic.management.ManagementException;
import weblogic.management.runtime.MaxThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.PartitionFairShareRuntimeMBean;
import weblogic.management.runtime.PartitionMinThreadsConstraintCapRuntimeMBean;
import weblogic.management.runtime.PartitionWorkManagerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class PartitionWorkManagerRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PartitionWorkManagerRuntimeMBean {
   final OverloadManager partitionOverloadManager;
   MaxThreadsConstraintRuntimeMBean maxThreadsConstraintRuntime;
   PartitionMinThreadsConstraintCapRuntimeMBean minThreadsConstraintCapRuntime;
   PartitionFairShareRuntimeMBean fairShareRuntime;

   public PartitionWorkManagerRuntimeMBeanImpl(String partitionName, OverloadManager partitionOverloadManager) throws ManagementException {
      super(partitionName);
      this.partitionOverloadManager = partitionOverloadManager;
   }

   public PartitionWorkManagerRuntimeMBeanImpl(String partitionName, OverloadManager partitionOverloadManager, RuntimeMBean parentArg) throws ManagementException {
      super(partitionName, parentArg);
      this.partitionOverloadManager = partitionOverloadManager;
   }

   public int getPendingUserRequestCount() {
      return this.partitionOverloadManager.getQueueDepth();
   }

   public int getSharedCapacityForWorkManagers() {
      return this.partitionOverloadManager.getCapacity();
   }

   public int getOverloadRejectedRequestsCount() {
      return this.partitionOverloadManager.getRejectedRequestsCount();
   }

   public MaxThreadsConstraintRuntimeMBean getMaxThreadsConstraintRuntime() {
      return this.maxThreadsConstraintRuntime;
   }

   public void setMaxThreadsConstraintRuntime(MaxThreadsConstraintRuntimeMBean maxThreadsConstraintRuntime) {
      this.maxThreadsConstraintRuntime = maxThreadsConstraintRuntime;
   }

   public PartitionMinThreadsConstraintCapRuntimeMBean getMinThreadsConstraintCapRuntime() {
      return this.minThreadsConstraintCapRuntime;
   }

   public void setMinThreadsConstraintCapRuntime(PartitionMinThreadsConstraintCapRuntimeMBean minThreadsConstraintCapRuntime) {
      this.minThreadsConstraintCapRuntime = minThreadsConstraintCapRuntime;
   }

   public PartitionFairShareRuntimeMBean getFairShareRuntime() {
      return this.fairShareRuntime;
   }

   public void setFairShareRuntime(PartitionFairShareRuntimeMBean fairShareRuntime) {
      this.fairShareRuntime = fairShareRuntime;
   }
}
