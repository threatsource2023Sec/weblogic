package weblogic.management.runtime;

public interface PartitionWorkManagerRuntimeMBean extends RuntimeMBean {
   int getPendingUserRequestCount();

   int getSharedCapacityForWorkManagers();

   int getOverloadRejectedRequestsCount();

   MaxThreadsConstraintRuntimeMBean getMaxThreadsConstraintRuntime();

   void setMaxThreadsConstraintRuntime(MaxThreadsConstraintRuntimeMBean var1);

   PartitionMinThreadsConstraintCapRuntimeMBean getMinThreadsConstraintCapRuntime();

   void setMinThreadsConstraintCapRuntime(PartitionMinThreadsConstraintCapRuntimeMBean var1);

   PartitionFairShareRuntimeMBean getFairShareRuntime();

   void setFairShareRuntime(PartitionFairShareRuntimeMBean var1);
}
