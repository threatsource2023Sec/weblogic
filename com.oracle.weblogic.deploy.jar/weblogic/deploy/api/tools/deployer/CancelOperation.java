package weblogic.deploy.api.tools.deployer;

import weblogic.deploy.utils.MBeanHomeTool;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;

public class CancelOperation extends TaskOperation {
   private DeploymentTaskRuntimeMBean task;

   public CancelOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void setAllowedOptions() {
      this.allowedOptions.add("id");
   }

   public void validate() throws IllegalArgumentException, DeployerException {
      super.validate();
      if (this.options.id == null) {
         throw new IllegalArgumentException(cat.errorMissingId());
      }
   }

   public void execute() throws Exception {
      this.task = this.helper.getTaskByID(this.options.id);
      if (this.task == null) {
         throw new DeployerException(cat.errorTaskNotFound(this.options.id));
      } else {
         this.task.cancel();
      }
   }

   public int report() {
      int failures = 0;
      String taskID = this.task.getId();

      while(this.task.isRunning()) {
      }

      int cancelState = this.task.getCancelState();
      if (cancelState != 0 && cancelState != 2 && cancelState != 4) {
         System.out.println(cat.cancelSucceeded(taskID));
      } else {
         System.out.println(cat.cancelFailed(taskID));
         failures = 1;
      }

      return failures;
   }

   public String getOperation() {
      return "cancel";
   }
}
