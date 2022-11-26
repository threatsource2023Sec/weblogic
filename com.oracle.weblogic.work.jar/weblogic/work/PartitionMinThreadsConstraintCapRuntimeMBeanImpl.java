package weblogic.work;

import weblogic.management.ManagementException;
import weblogic.management.runtime.PartitionMinThreadsConstraintCapRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class PartitionMinThreadsConstraintCapRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PartitionMinThreadsConstraintCapRuntimeMBean {
   final PartitionMinThreadsConstraint partitionMinThreadsConstraint;

   public PartitionMinThreadsConstraintCapRuntimeMBeanImpl(PartitionMinThreadsConstraint partitionMinThreadsConstraint) throws ManagementException {
      super(partitionMinThreadsConstraint.getPartitionName());
      this.partitionMinThreadsConstraint = partitionMinThreadsConstraint;
   }

   public PartitionMinThreadsConstraintCapRuntimeMBeanImpl(PartitionMinThreadsConstraint partitionMinThreadsConstraint, RuntimeMBean parentArg) throws ManagementException {
      super(partitionMinThreadsConstraint.getPartitionName(), parentArg);
      this.partitionMinThreadsConstraint = partitionMinThreadsConstraint;
   }

   public int getExecutingRequests() {
      return this.partitionMinThreadsConstraint.getExecutingCount();
   }

   public int getSumMinThreadsConstraints() {
      return this.partitionMinThreadsConstraint.getSumMinConstraints();
   }
}
