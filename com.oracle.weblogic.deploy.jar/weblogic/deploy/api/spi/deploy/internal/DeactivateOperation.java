package weblogic.deploy.api.spi.deploy.internal;

import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.shared.WebLogicCommandType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;

public class DeactivateOperation extends StopOperation {
   public DeactivateOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, DeploymentOptions options) {
      super(dm, tmids, options);
      this.cmd = WebLogicCommandType.DEACTIVATE;
   }

   protected void initializeTask() throws Throwable {
      this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).deactivate(this.appName, this.info, this.dm.getTaskId(), false);
   }
}
