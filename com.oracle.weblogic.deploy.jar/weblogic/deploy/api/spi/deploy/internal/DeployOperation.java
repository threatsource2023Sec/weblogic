package weblogic.deploy.api.spi.deploy.internal;

import java.io.File;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.shared.WebLogicCommandType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;

public class DeployOperation extends DistributeOperation {
   public DeployOperation(WebLogicDeploymentManager dm, Target[] targetList, File moduleArchive, File plan, DeploymentOptions options) {
      super(dm, targetList, moduleArchive, plan, options);
      this.cmd = WebLogicCommandType.DEPLOY;
   }

   public DeployOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, File moduleArchive, File plan, DeploymentOptions options) {
      super(dm, tmids, moduleArchive, plan, options);
      this.cmd = WebLogicCommandType.DEPLOY;
   }

   protected void initializeTask() throws Throwable {
      this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).deploy(this.paths.getArchive().getPath(), this.appName, this.options.getStageMode(), this.info, this.dm.getTaskId(), false);
   }

   protected boolean defaultTmidsWithOnlySubModules() {
      return this.tmidsHaveOnlySubModules();
   }
}
