package weblogic.deploy.internal.adminserver.operations;

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

public class ActivateOperation extends AbstractOperation {
   private boolean removeBeansOnFailure;
   private boolean redeployWithSource;
   private AppDeploymentMBean createdApp;

   public ActivateOperation() {
      this.removeBeansOnFailure = false;
      this.redeployWithSource = false;
      this.createdApp = null;
      this.taskType = 1;
   }

   public ActivateOperation(boolean redeployWithSource) {
      this();
      this.redeployWithSource = redeployWithSource;
   }

   protected AppDeploymentMBean updateConfiguration(String source, String name, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      String versionId = null;
      String appName = OperationHelper.getAppName(name, info, source);
      String taskName = OperationHelper.getTaskString(this.taskType);
      DomainMBean domain = this.beanFactory.getEditableDomain();
      boolean ignoreVersion = info.getDeploymentOptions().isNoVersion();
      OperationHelper.assertNameIsNonNull(appName, taskName);
      if (isDebugEnabled()) {
         this.printDebugStartMessage(source, appName, versionId, info, id, taskName, stagingMode);
      }

      if (!ignoreVersion) {
         versionId = OperationHelper.getAndValidateVersionIdWithSrc(info, name, source, appName);
      }

      AppDeploymentMBean deployable = ApplicationUtils.getAppDeployment(domain, appName, versionId, info);
      if (deployable != null) {
         if (source == null) {
            deployable = OperationHelper.getActiveVersionIfNeeded(domain, versionId, deployable, appName, info, taskName);
            versionId = deployable.getVersionIdentifier();
            if (!ignoreVersion) {
               OperationHelper.validateSourceVersion(deployable, info, appName);
            }
         }

         OperationHelper.validateNonVersionWithVersion(versionId, deployable, appName, taskName);
         DeploymentOptions options = info.getDeploymentOptions();
         if (options == null) {
            options = new DeploymentOptions();
            info.setDeploymentOptions(options);
         }

         if (options.getStageMode() != null) {
            deployable.setStagingMode(options.getStageMode());
         } else {
            options.setStageMode(deployable.getStagingMode());
         }

         if (options.getPlanStageMode() != null) {
            deployable.setPlanStagingMode(options.getPlanStageMode());
         } else {
            options.setPlanStageMode(deployable.getPlanStagingMode());
         }

         OperationHelper.validateTargets(domain, info, deployable, taskName);
         OperationHelper.validatePath(source, deployable);
      } else {
         if (!ignoreVersion && stagingMode == null) {
            AppDeploymentMBean deployedApp = ApplicationUtils.getAppDeployment(domain, appName, (String)null, (DeploymentData)info);
            if (deployedApp != null) {
               stagingMode = deployedApp.getStagingMode();
            }
         }

         OperationHelper.validateRetireTimeout(domain, appName, versionId, info);
         OperationHelper.validateVersionIdFormat(appName, versionId);
         OperationHelper.validateVersionWithNonVersion(domain, versionId, appName, taskName, info);
         if (!OperationHelper.isLibrary(info)) {
            OperationHelper.validateMaxAppVersions(domain, appName, versionId, info);
            OperationHelper.validateVersionTargets(domain, appName, info, versionId);
         }

         OperationHelper.assertSourceIsNonNull(source, appName, versionId);
         if (!callerOwnsEditLock) {
            this.removeBeansOnFailure = true;
         }
      }

      OperationHelper.validateDeployWhileRetire(appName, versionId, deployable);
      OperationHelper.validateDeployWhileStop(appName, versionId, this.deploymentManager.getDeployerRuntime());
      if (source != null) {
         addAdminServerAsDefaultTarget(info);
         deployable = this.createOrReconcileMBeans(source, appName, info, versionId, deployable, stagingMode);
         if (this.removeBeans()) {
            this.createdApp = deployable;
         }

         invalidateCache(deployable);
      }

      OperationHelper.validateModuleType(appName, versionId, deployable);
      if (!this.redeployWithSource) {
         deployable.setCacheInAppDirectory(info.getDeploymentOptions().isCacheInAppDirectory());
      }

      return deployable;
   }

   protected AbstractOperation createCopy() {
      return new ActivateOperation();
   }

   protected void mergeWithUndeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithUndeploy(otherOp);
      otherOp.mergeUndeployWithDistributeOrDeployOrRedeploy(this);
   }

   protected void mergeWithRedeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithRedeploy(otherOp);
      otherOp.mergeWithDeploy(this);
   }

   protected void mergeWithDeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithDeploy(otherOp);
      this.mergeWithSameOperationType(otherOp);
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
      if (!this.areTargetsSame(otherOp)) {
         DeploymentTaskRuntime theTask = this.getTaskRuntime();
         DeploymentData theData = theTask.getDeploymentData();
         DeploymentTaskRuntime otherTask = this.getTaskRuntime();
         DeploymentData otherData = otherTask.getDeploymentData();
         theData.setDeploymentPlan(otherData.getDeploymentPlan());
         theData.addGlobalTargets(otherData.getGlobalTargets());
         if (otherData.hasModuleTargets()) {
            theData.addOrUpdateModuleTargets(otherData.getAllModuleTargets());
         }

         if (otherData.hasSubModuleTargets()) {
            theData.addOrUpdateSubModuleTargets(otherData.getAllSubModuleTargets());
         }
      }

      otherOp.setAsDelegatorTo(this);
   }

   protected boolean isSameOperationType(AbstractOperation theOp) {
      return theOp instanceof ActivateOperation;
   }

   protected void checkVersionSupport(DeploymentData info, String name, String deprecatedOp) throws ManagementException {
      OperationHelper.validateVersionForDeprecatedOp(info, name, deprecatedOp, 7);
   }

   protected boolean removeBeans() {
      return this.removeBeansOnFailure;
   }

   public void rollback(AuthenticatedSubject initiator) {
      if (this.removeBeans() && this.createdApp != null) {
         this.editAccessHelper.rollback(this.createdApp, this.beanFactory, initiator);
      }

      this.createdApp = null;
   }

   protected String getAutoDeployErrorMsg(String appName) {
      Loggable logger = DeployerRuntimeLogger.invalidRedeployOnAutodeployedAppLoggable(appName);
      return logger.getMessage();
   }
}
