package weblogic.deploy.api.spi.deploy.internal;

import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.shared.WebLogicCommandType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;

public class RemoveOperation extends BasicOperation {
   public RemoveOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, DeploymentOptions options) {
      super(dm, options);
      this.cmd = WebLogicCommandType.REMOVE;
      this.tmids = tmids;
   }

   protected void initializeTask() throws Throwable {
      this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).remove(this.appName, this.info, this.dm.getTaskId(), false);
   }

   protected final void validateForNoTMIDs() {
   }
}
