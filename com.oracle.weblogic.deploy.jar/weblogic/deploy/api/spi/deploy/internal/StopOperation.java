package weblogic.deploy.api.spi.deploy.internal;

import javax.enterprise.deploy.shared.CommandType;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;

public class StopOperation extends BasicOperation {
   public StopOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, DeploymentOptions options) {
      super(dm, options);
      this.cmd = CommandType.STOP;
      this.tmids = tmids;
   }

   protected void initializeTask() throws Throwable {
      this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).stop(this.appName, this.info, this.dm.getTaskId(), false);
   }
}
