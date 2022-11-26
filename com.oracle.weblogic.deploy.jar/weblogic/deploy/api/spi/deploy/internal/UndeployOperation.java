package weblogic.deploy.api.spi.deploy.internal;

import javax.enterprise.deploy.shared.CommandType;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;

public class UndeployOperation extends BasicOperation {
   public UndeployOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, DeploymentOptions options) {
      super(dm, options);
      this.cmd = CommandType.UNDEPLOY;
      this.tmids = tmids;
   }

   protected void initializeTask() throws Throwable {
      this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).undeploy(ApplicationVersionUtils.getApplicationIdWithPartition(this.appName, this.info.getPartition()), this.info, this.dm.getTaskId(), false);
   }

   protected final void validateForNoTMIDs() {
   }
}
