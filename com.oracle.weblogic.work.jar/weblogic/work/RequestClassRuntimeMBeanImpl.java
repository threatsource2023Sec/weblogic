package weblogic.work;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RequestClassRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class RequestClassRuntimeMBeanImpl extends RuntimeMBeanDelegate implements RequestClassRuntimeMBean {
   private final ServiceClassSupport rc;

   RequestClassRuntimeMBeanImpl(RequestClass rc) throws ManagementException {
      super(rc.getName());
      this.rc = (ServiceClassSupport)rc;
   }

   RequestClassRuntimeMBeanImpl(RequestClass rc, RuntimeMBean parent) throws ManagementException {
      this(rc, parent, true);
   }

   RequestClassRuntimeMBeanImpl(RequestClass rc, RuntimeMBean parent, boolean registerNow) throws ManagementException {
      super(rc.getName(), parent, registerNow);
      this.rc = (ServiceClassSupport)rc;
   }

   public String getRequestClassType() {
      if (this.rc instanceof FairShareRequestClass) {
         return "fairshare";
      } else {
         return this.rc instanceof ResponseTimeRequestClass ? "responsetime" : "context";
      }
   }

   public long getCompletedCount() {
      return this.rc.getCompleted();
   }

   public long getTotalThreadUse() {
      return this.rc.getThreadUse();
   }

   public long getThreadUseSquares() {
      return this.rc.getThreadUseSquares();
   }

   public long getDeltaFirst() {
      return this.rc.getDeltaFirst();
   }

   public long getDeltaRepeat() {
      return this.rc.getDelta();
   }

   public long getMyLast() {
      return this.rc.getMyLast();
   }

   public int getPendingRequestCount() {
      int count = this.rc.getPendingRequestsCount();
      return count >= 0 ? count : 0;
   }

   public long getVirtualTimeIncrement() {
      return this.rc.getVirtualTimeIncrement();
   }

   public double getInterval() {
      return !(this.rc instanceof ResponseTimeRequestClass) ? -1.0 : ((ResponseTimeRequestClass)this.rc).getInterval();
   }
}
