package weblogic.deploy.api.spi.deploy.internal;

import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;

public final class UnprepareOperation extends StopOperation {
   public UnprepareOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, DeploymentOptions options) {
      super(dm, tmids, options);
   }

   protected void initializeTask() throws Throwable {
      this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).unprepare(this.appName, this.info, this.dm.getTaskId(), false);
   }
}
