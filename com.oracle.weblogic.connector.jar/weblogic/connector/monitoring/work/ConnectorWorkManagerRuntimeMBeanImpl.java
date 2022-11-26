package weblogic.connector.monitoring.work;

import weblogic.connector.work.LongRunningWorkManager;
import weblogic.connector.work.WorkManager;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ConnectorWorkManagerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class ConnectorWorkManagerRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ConnectorWorkManagerRuntimeMBean {
   private LongRunningWorkManager lrWM;

   public ConnectorWorkManagerRuntimeMBeanImpl(String name, RuntimeMBean parent, WorkManager wm) throws ManagementException {
      super(name, parent, false);
      this.initialize(wm);
      this.register();
   }

   public void initialize(WorkManager wm) {
      this.lrWM = wm.getLongRunningWorkManager();
   }

   public int getActiveLongRunningRequests() {
      return this.lrWM.getActiveWorkCount();
   }

   public int getCompletedLongRunningRequests() {
      return this.lrWM.getCompletedWorkCount();
   }

   public int getMaxConcurrentLongRunningRequests() {
      return this.lrWM.getMaxConcurrentRequests();
   }

   public void setMaxConcurrentLongRunningRequests(int limit) {
      this.lrWM.setMaxConcurrentRequests(limit);
   }
}
