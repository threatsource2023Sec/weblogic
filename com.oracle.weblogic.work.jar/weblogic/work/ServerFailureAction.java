package weblogic.work;

import weblogic.health.HealthMonitorService;
import weblogic.management.configuration.ServerFailureTriggerMBean;

final class ServerFailureAction extends AbstractStuckThreadAction {
   public ServerFailureAction(ServerFailureTriggerMBean triggerMBean) {
      super((long)triggerMBean.getMaxStuckThreadTime(), triggerMBean.getStuckThreadCount());
   }

   public void execute() {
      HealthMonitorService.subsystemFailed("Thread Pool", "Server failed as the number of stuck threads has exceeded the max limit of " + this.maxCount);
   }

   public void withdraw() {
   }

   public String getName() {
      return "server-failure-trigger";
   }
}
