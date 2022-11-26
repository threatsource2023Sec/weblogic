package weblogic.deploy.api.spi.deploy.internal;

import java.io.File;
import java.io.InputStream;
import javax.enterprise.deploy.shared.CommandType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;

public class DistributeOperation extends BasicOperation {
   protected boolean targetMode;
   protected boolean streams = false;

   protected DistributeOperation(WebLogicDeploymentManager dm, InputStream moduleArchive, InputStream plan, DeploymentOptions options) {
      super(dm, moduleArchive, plan, options);
   }

   public DistributeOperation(WebLogicDeploymentManager dm, Target[] targetList, File moduleArchive, File plan, DeploymentOptions options) {
      super(dm, moduleArchive, plan, options);
      this.cmd = CommandType.DISTRIBUTE;
      this.targetList = targetList;
      this.targetMode = true;
   }

   public DistributeOperation(WebLogicDeploymentManager dm, Target[] targetList, InputStream moduleArchive, InputStream plan, DeploymentOptions options) {
      super(dm, moduleArchive, plan, options);
      this.cmd = CommandType.DISTRIBUTE;
      this.targetList = targetList;
      this.targetMode = true;
   }

   public DistributeOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, File moduleArchive, File plan, DeploymentOptions options) {
      super(dm, moduleArchive, plan, options);
      this.cmd = CommandType.DISTRIBUTE;
      this.tmids = tmids;
      this.targetMode = false;
   }

   protected void initializeTask() throws Throwable {
      this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).distribute(this.paths.getArchive().getPath(), this.appName, this.info, this.dm.getTaskId(), false);
   }

   protected void buildDeploymentData() {
      if (this.targetMode) {
         this.info = this.getDeploymentData();
      } else {
         this.info = this.createDeploymentData();
      }

   }

   protected void validateParams() throws FailedOperationException {
      super.validateParams();

      try {
         if (!this.streams) {
            ConfigHelper.checkParam("moduleArchive", this.moduleArchive);
         }

      } catch (IllegalArgumentException var2) {
         throw new FailedOperationException(this.failOperation(var2));
      }
   }
}
