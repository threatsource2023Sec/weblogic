package weblogic.work;

import weblogic.j2ee.descriptor.wl.WorkManagerShutdownTriggerBean;
import weblogic.management.configuration.WorkManagerShutdownTriggerMBean;

final class WorkManagerShutdownAction extends AbstractStuckThreadAction {
   private WorkManagerService wmService;
   private boolean resumeWhenUnstuck = true;

   public WorkManagerShutdownAction(WorkManagerShutdownTriggerBean trigger) {
      super((long)trigger.getMaxStuckThreadTime(), trigger.getStuckThreadCount());
   }

   public WorkManagerShutdownAction(WorkManagerShutdownTriggerMBean trigger) {
      super((long)trigger.getMaxStuckThreadTime(), trigger.getStuckThreadCount());
      this.resumeWhenUnstuck = trigger.isResumeWhenUnstuck();
   }

   public void execute() {
      this.wmService.forceShutdown();
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            WorkManagerLogger.logWorkManagerShutdownActionTriggered(WorkManagerShutdownAction.this.wmService.getName());
         }
      });
   }

   public void withdraw() {
      if (this.resumeWhenUnstuck) {
         this.wmService.start();
      }

   }

   public String getName() {
      return "work-manager-shutdown-trigger";
   }

   public void setWorkManagerService(WorkManagerService wmService) {
      this.wmService = wmService;
   }
}
