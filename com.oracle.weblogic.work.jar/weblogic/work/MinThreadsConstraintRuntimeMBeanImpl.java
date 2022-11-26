package weblogic.work;

import weblogic.management.ManagementException;
import weblogic.management.runtime.MinThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class MinThreadsConstraintRuntimeMBeanImpl extends RuntimeMBeanDelegate implements MinThreadsConstraintRuntimeMBean {
   private final MinThreadsConstraint mtc;

   MinThreadsConstraintRuntimeMBeanImpl(MinThreadsConstraint mtc) throws ManagementException {
      super(mtc.getName());
      this.mtc = mtc;
   }

   MinThreadsConstraintRuntimeMBeanImpl(MinThreadsConstraint mtc, RuntimeMBean parent) throws ManagementException {
      this(mtc, parent, true);
   }

   MinThreadsConstraintRuntimeMBeanImpl(MinThreadsConstraint mtc, RuntimeMBean parent, boolean registerNow) throws ManagementException {
      super(mtc.getName(), parent, registerNow);
      this.mtc = mtc;
   }

   public int getExecutingRequests() {
      return this.mtc.getExecutingCount();
   }

   public long getCompletedRequests() {
      return this.mtc.getCompletedCount();
   }

   public int getPendingRequests() {
      return this.mtc.getPendingCount();
   }

   public long getOutOfOrderExecutionCount() {
      return this.mtc.getOutOfOrderExecutionCount();
   }

   public int getMustRunCount() {
      return this.mtc.getMustRunCount();
   }

   public long getMaxWaitTime() {
      return this.mtc.getMaxWaitTime();
   }

   public long getCurrentWaitTime() {
      return this.mtc.getCurrentWaitTime();
   }

   public boolean isPartitionLimitReached() {
      return this.mtc.isPartitionLimitReached();
   }

   public int getConfiguredCount() {
      return this.mtc.getConfiguredCount();
   }

   public int getCount() {
      return this.mtc.getCount();
   }
}
