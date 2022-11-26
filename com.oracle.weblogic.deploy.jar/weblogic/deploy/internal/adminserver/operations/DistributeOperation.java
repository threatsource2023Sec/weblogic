package weblogic.deploy.internal.adminserver.operations;

import java.io.FileNotFoundException;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;

public final class DistributeOperation extends AbstractOperation {
   private boolean removeBeansOnFailure = false;
   private AppDeploymentMBean createdApp = null;

   public DistributeOperation() {
      this.taskType = 6;
   }

   protected final AppDeploymentMBean updateConfiguration(String source, String name, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      String versionId = null;
      String appName = OperationHelper.getAppName(name, info, source);
      String taskName = OperationHelper.getTaskString(this.taskType);
      DomainMBean domain = this.beanFactory.getEditableDomain();
      boolean ignoreVersion = info.getDeploymentOptions().isNoVersion();
      OperationHelper.assertNameIsNonNull(appName, taskName);
      if (!ignoreVersion) {
         versionId = OperationHelper.getAndValidateVersionIdWithSrc(info, (String)null, source, appName);
      }

      if (isDebugEnabled()) {
         this.printDebugStartMessage(source, appName, versionId, info, id, taskName, stagingMode);
      }

      AppDeploymentMBean deployable = ApplicationUtils.getAppDeployment(this.beanFactory.getEditableDomain(), appName, versionId, info);
      if (deployable != null) {
         OperationHelper.validateNonVersionWithVersion(versionId, deployable, appName, taskName);
         OperationHelper.validateTargets(domain, info, deployable, taskName);
         OperationHelper.validatePath(source, deployable);
         deployable = this.createOrReconcileMBeans(source, appName, info, versionId, deployable, stagingMode);
      } else {
         OperationHelper.validateRetireTimeout(domain, appName, versionId, info);
         OperationHelper.validateVersionWithNonVersion(domain, versionId, appName, taskName, info);
         addAdminServerAsDefaultTarget(info);
         OperationHelper.assertSourceIsNonNull(source, appName, versionId);
         if (!callerOwnsEditLock) {
            this.removeBeansOnFailure = true;
         }

         deployable = this.createMBeansForDistribute(deployable, versionId, appName, source, info, stagingMode);
         deployable.setCacheInAppDirectory(info.getDeploymentOptions().isCacheInAppDirectory());
         this.createdApp = deployable;
      }

      return deployable;
   }

   protected AbstractOperation createCopy() {
      return new DistributeOperation();
   }

   protected void mergeWithUndeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithUndeploy(otherOp);
      otherOp.mergeUndeployWithDistributeOrDeployOrRedeploy(this);
   }

   protected void mergeWithRedeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithRedeploy(otherOp);
      otherOp.mergeWithDistribute(this);
   }

   protected void mergeWithDeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithDeploy(otherOp);
      otherOp.mergeWithDistribute(this);
   }

   protected void mergeWithUpdate(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithUpdate(otherOp);
      DeploymentTaskRuntime theTask = this.getTaskRuntime();
      DeploymentData theData = theTask.getDeploymentData();
      DeploymentTaskRuntime otherTask = this.getTaskRuntime();
      DeploymentData otherData = otherTask.getDeploymentData();
      if (theData.hasFiles()) {
         theData.setPlanUpdate(otherData.isPlanUpdate());
      }

      theData.setDeploymentPlan(otherData.getDeploymentPlan());
      theData.addGlobalTargets(otherData.getGlobalTargets());
      if (otherData.hasModuleTargets()) {
         theData.addOrUpdateModuleTargets(otherData.getAllModuleTargets());
      }

      if (otherData.hasSubModuleTargets()) {
         theData.addOrUpdateSubModuleTargets(otherData.getAllSubModuleTargets());
      }

      otherOp.setAsDelegatorTo(this);
   }

   protected void mergeWithDistribute(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithDistribute(otherOp);
      this.mergeWithSameOperationType(otherOp);
   }

   private AppDeploymentMBean createMBeansForDistribute(AppDeploymentMBean deployable, String versionId, String appName, String source, DeploymentData info, String stagingMode) throws ManagementException {
      try {
         deployable = this.createMBeans(source, appName, info, versionId);
         DeploymentOptions deplOpts = info.getDeploymentOptions();
         this.setStagingMode(stagingMode, deplOpts, deployable);
         this.setDeploymentOrder(deplOpts, deployable);
         if (stagingMode != null) {
            deployable.setStagingMode(stagingMode);
         }

         return deployable;
      } catch (FileNotFoundException var9) {
         Loggable l = DeployerRuntimeLogger.logInvalidSourceLoggable(source, appName, var9.getMessage());
         l.log();
         if (deployable != null) {
            this.beanFactory.removeMBean(deployable);
         }

         throw new ManagementException(l.getMessage(), var9);
      }
   }

   protected final boolean removeBeans() {
      return this.removeBeansOnFailure;
   }

   public void rollback(AuthenticatedSubject initiator) {
      if (this.removeBeans() && this.createdApp != null) {
         this.editAccessHelper.rollback(this.createdApp, this.beanFactory, initiator);
      }

      this.createdApp = null;
   }

   protected String getAutoDeployErrorMsg(String appName) {
      Loggable logger = DeployerRuntimeLogger.invalidDistributeOnAutodeployedAppLoggable(appName);
      return logger.getMessage();
   }
}
