package weblogic.deploy.internal.adminserver.operations;

import weblogic.management.deploy.DeploymentData;

public class RetireOperation extends BaseRetireOperation {
   public RetireOperation() {
      this.taskType = 13;
   }

   protected AbstractOperation createCopy() {
      return new RetireOperation();
   }

   protected boolean isRemote(DeploymentData info) {
      return false;
   }
}
