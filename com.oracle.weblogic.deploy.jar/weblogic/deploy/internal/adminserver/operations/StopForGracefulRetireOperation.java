package weblogic.deploy.internal.adminserver.operations;

import weblogic.management.deploy.DeploymentData;

public class StopForGracefulRetireOperation extends BaseRetireOperation {
   public StopForGracefulRetireOperation() {
      this.taskType = 8;
   }

   protected AbstractOperation createCopy() {
      return new StopForGracefulRetireOperation();
   }

   protected boolean isRemote(DeploymentData info) {
      return false;
   }
}
