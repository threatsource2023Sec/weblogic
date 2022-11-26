package weblogic.deploy.internal.adminserver.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.AccessController;
import weblogic.application.archive.utils.ArchiveUtils;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.logging.Loggable;
import weblogic.management.ApplicationException;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;

public final class RedeployOperation extends AbstractOperation {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public RedeployOperation() {
      this.taskType = 9;
   }

   protected String getAutoDeployErrorMsg(String appName) {
      Loggable logger = DeployerRuntimeLogger.invalidRedeployOnAutodeployedAppLoggable(appName);
      return logger.getMessage();
   }

   protected final AppDeploymentMBean updateConfiguration(String source, String name, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      AppDeploymentMBean alternateDeployment = null;
      String appName = OperationHelper.getAppName(name, info, source);
      String versionId = OperationHelper.getVersionIdFromData(info, name);
      String taskName = OperationHelper.getTaskString(this.taskType);
      DomainMBean domain = this.beanFactory.getEditableDomain();
      if (isDebugEnabled()) {
         this.printDebugStartMessage(source, appName, versionId, info, id, taskName, stagingMode);
      }

      OperationHelper.assertNameIsNonNull(appName, taskName);
      AppDeploymentMBean deployment;
      if (versionId != null) {
         deployment = ApplicationUtils.getAppDeployment(domain, appName, versionId);
      } else {
         deployment = ApplicationUtils.getActiveAppDeployment(domain, appName);
         if (deployment == null) {
            deployment = ApplicationUtils.getActiveAppDeployment(domain, appName, true);
         }
      }

      if (deployment == null) {
         DomainMBean alternateDomain = this.beanFactory.getAlternateEditableDomain();
         if (alternateDomain != null) {
            if (versionId != null) {
               alternateDeployment = ApplicationUtils.getAppDeployment(alternateDomain, appName, versionId);
            } else {
               alternateDeployment = ApplicationUtils.getActiveAppDeployment(alternateDomain, appName);
               if (alternateDeployment == null) {
                  alternateDeployment = ApplicationUtils.getActiveAppDeployment(alternateDomain, appName, true);
               }
            }
         }
      }

      if (isDebugEnabled()) {
         this.debugSay("RedeployOperation partition:  " + info.getPartition());
         this.debugSay("RedeployOperation resource group:  " + info.getResourceGroup());
         this.debugSay("RedeployOperation resource group template:  " + info.getResourceGroupTemplate());
         this.debugSay("RedeployOperation deployment:  " + deployment);
      }

      String partition = info.getPartition();
      String resourceGroup = info.getResourceGroup();
      String template = info.getResourceGroupTemplate();
      AppDeploymentMBean newDeployable;
      if (deployment == null || partition != null) {
         newDeployable = ApplicationVersionUtils.getAppDeployment(domain, appName, versionId, template, resourceGroup, partition);
         if (newDeployable != null) {
            deployment = newDeployable;
         }

         this.debugSay("RedeployOperation mt deployment:  " + deployment);
      }

      if (info.getPartition() != null && info.getResourceGroup() != null && info.getResourceGroupTemplate() == null && deployment != null && deployment.getParent() instanceof ResourceGroupTemplateMBean) {
         return this.handlePartitionResourceGroup(domain, info, appName, versionId, taskName, deployment);
      } else if (info.getResourceGroup() != null && info.getPartition() == null && info.getResourceGroupTemplate() == null && deployment != null && deployment.getParent() instanceof ResourceGroupTemplateMBean) {
         return this.handleResourceGroup(domain, info, appName, versionId, taskName, deployment);
      } else {
         Loggable l;
         try {
            OperationHelper.assertAppIsNonNull(deployment, appName, versionId, taskName, alternateDeployment);
            if (source != null) {
               OperationHelper.assertAppIsActive(deployment, appName, versionId, taskName);
            }

            checkUpdateForArchiveApp(info, deployment);
            OperationHelper.validateTargets(domain, info, deployment, taskName);
            if (source == null) {
               OperationHelper.assertNoMixedTargeting(info);
               OperationHelper.assertNoChangedAltDDs(info);
               deployment = OperationHelper.getActiveVersionIfNeeded(domain, versionId, deployment, appName, info, taskName);
               DeploymentOptions options = info.getDeploymentOptions();
               if (options == null) {
                  options = new DeploymentOptions();
                  info.setDeploymentOptions(options);
               }

               if (options.getStageMode() != null) {
                  deployment.setStagingMode(options.getStageMode());
               }

               if (options.getPlanStageMode() != null) {
                  deployment.setPlanStagingMode(options.getPlanStageMode());
               } else if (options.getStageMode() != null) {
                  deployment.setPlanStagingMode(options.getStageMode());
                  options.setPlanStageMode(options.getStageMode());
               }

               this.reconcileMBeans(info, deployment, true);
               return deployment;
            } else {
               newDeployable = (new DeployOperation()).updateConfiguration(source, name, stagingMode, info, id, callerOwnsEditLock);
               DeploymentOptions options = info.getDeploymentOptions();
               if (options == null) {
                  options = new DeploymentOptions();
                  info.setDeploymentOptions(options);
               }

               if (options.getStageMode() != null) {
                  newDeployable.setStagingMode(options.getStageMode());
               }

               if (options.getPlanStageMode() != null) {
                  newDeployable.setPlanStagingMode(options.getPlanStageMode());
               } else if (options.getStageMode() != null) {
                  newDeployable.setPlanStagingMode(options.getStageMode());
                  options.setPlanStageMode(options.getStageMode());
               }

               this.taskType = 1;
               return newDeployable;
            }
         } catch (ApplicationException var18) {
            l = DeployerRuntimeLogger.logInvalidAppLoggable(deployment.getAbsoluteSourcePath(), appName, var18.getMessage());
            l.log();
            throw new ManagementException(l.getMessage(), var18);
         } catch (FileNotFoundException var19) {
            l = DeployerRuntimeLogger.logInvalidSourceLoggable(deployment.getAbsoluteSourcePath(), appName, var19.getMessage());
            l.log();
            throw new ManagementException(l.getMessage(), var19);
         }
      }
   }

   protected final void postTaskCreationConfigurationUpdate(AppDeploymentMBean deployable, String name, DeploymentData info) throws ManagementException {
      if (info.getDelete()) {
         try {
            String up = ManagementService.getRuntimeAccess(kernelId).getServer().getUploadDirectoryName();
            up = (new File(up)).getCanonicalPath().toLowerCase();
            String src = (new File(deployable.getAbsoluteSourcePath())).getCanonicalPath().toLowerCase();
            if (src.startsWith(up)) {
               File f = new File(deployable.getAbsoluteSourcePath());
               String[] fileNames = info.getFiles();

               for(int i = 0; i < fileNames.length; ++i) {
                  File toDelete = new File(f, fileNames[i]);
                  if (isDebugEnabled()) {
                     debugDeployer("Removing " + toDelete.getPath() + " from upload dir");
                  }

                  FileUtils.remove(toDelete);
               }
            }
         } catch (IOException var10) {
            throw new ManagementException(var10);
         }
      }

      if (info.getResourceGroupTemplate() != null) {
         DomainMBean domain = this.beanFactory.getEditableDomain();
         AppDeploymentMBean deployment = AppDeploymentHelper.lookupAppOrLibInResourceGroupOrTemplate(name, domain);
         if (deployment != null && AppDeploymentHelper.isDeploymentRGTConfigOverride(deployment, info.getResourceGroupTemplate())) {
            this.beanFactory.removeMBean(deployment);
            invalidateCache(deployment);
         }
      }

   }

   protected AbstractOperation createCopy() {
      return new RedeployOperation();
   }

   protected void mergeWithUndeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithDeploy(otherOp);
      otherOp.mergeUndeployWithDistributeOrDeployOrRedeploy(this);
   }

   protected void mergeWithRedeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithRedeploy(otherOp);
      this.mergeWithSameOperationType(otherOp);
   }

   protected void mergeWithDeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithDeploy(otherOp);
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

   private static void checkUpdateForArchiveApp(DeploymentData info, AppDeploymentMBean deployment) throws DeploymentException {
      if (info != null) {
         if (OperationHelper.hasFiles(info) || info.hasModuleTargets()) {
            File appFile = new File(deployment.getAbsoluteSourcePath());
            if (appFile.isFile() && ArchiveUtils.isValidArchiveName(appFile.getName())) {
               Loggable l;
               if (deployment.getVersionIdentifier() == null) {
                  l = DeployerRuntimeLogger.logPartialRedeployOfArchiveLoggable(deployment.getName());
               } else {
                  l = DeployerRuntimeLogger.logPartialRedeployOfVersionedArchiveLoggable(ApplicationVersionUtils.getDisplayName(deployment));
               }

               throw new DeploymentException(l.getMessage());
            }
         }

      }
   }
}
