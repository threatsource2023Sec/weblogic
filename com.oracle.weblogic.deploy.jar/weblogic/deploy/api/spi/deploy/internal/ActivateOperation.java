package weblogic.deploy.api.spi.deploy.internal;

import java.io.File;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.shared.WebLogicCommandType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;

public class ActivateOperation extends DeployOperation {
   public ActivateOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, File moduleArchive, File plan, DeploymentOptions options) {
      super(dm, tmids, moduleArchive, plan, options);
      this.cmd = WebLogicCommandType.ACTIVATE;
   }

   protected void initializeTask() throws Throwable {
      this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).activate(this.paths.getArchive().getPath(), this.appName, this.options.getStageMode(), this.info, this.dm.getTaskId(), false);
   }
}
