package weblogic.work;

import weblogic.management.ManagementException;
import weblogic.management.runtime.MaxThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class MaxThreadsConstraintRuntimeMBeanImpl extends RuntimeMBeanDelegate implements MaxThreadsConstraintRuntimeMBean {
   private final MaxThreadsConstraint mtc;

   MaxThreadsConstraintRuntimeMBeanImpl(MaxThreadsConstraint mtc) throws ManagementException {
      super(mtc.getName());
      this.mtc = mtc;
   }

   MaxThreadsConstraintRuntimeMBeanImpl(MaxThreadsConstraint mtc, RuntimeMBean parent) throws ManagementException {
      this(mtc, parent, true);
   }

   MaxThreadsConstraintRuntimeMBeanImpl(MaxThreadsConstraint mtc, RuntimeMBean parent, boolean registerNow) throws ManagementException {
      super(mtc.getName(), parent, registerNow);
      this.mtc = mtc;
   }

   public int getExecutingRequests() {
      return this.mtc.getExecutingCount();
   }

   public int getDeferredRequests() {
      return this.mtc.getQueueSize();
   }

   public int getConfiguredCount() {
      return this.mtc.getConfiguredCount();
   }

   public int getCount() {
      return this.mtc.getCount();
   }
}
