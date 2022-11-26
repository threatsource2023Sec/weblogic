package weblogic.deploy.internal.adminserver.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.service.datatransferhandlers.SourceCache;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.logging.Loggable;
import weblogic.management.ApplicationException;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;

public class UpdateOperation extends AbstractOperation {
   public UpdateOperation() {
      this.taskType = 10;
   }

   protected String getAutoDeployErrorMsg(String appName) {
      Loggable logger = DeployerRuntimeLogger.invalidRedeployOnAutodeployedAppLoggable(appName);
      return logger.getMessage();
   }

   protected AppDeploymentMBean updateConfiguration(String source, String name, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      AppDeploymentMBean alternateDeployment = null;
      String appName = OperationHelper.ensureAppName(name);
      String versionId = OperationHelper.getVersionIdFromData(info, name);
      String partition = info.getPartition();
      String resourceGroup = info.getResourceGroup();
      String template = info.getResourceGroupTemplate();
      if (partition != null || resourceGroup != null || template != null) {
         this.taskType = 9;
      }

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
      }

      if (isDebugEnabled()) {
         this.debugSay("UpdateOperation partition:  " + info.getPartition());
         this.debugSay("UpdateOperation resource group:  " + info.getResourceGroup());
         this.debugSay("UpdateOperation resource group template:  " + info.getResourceGroupTemplate());
         this.debugSay("UpdateOperation deployment:  " + deployment);
      }

      if (deployment == null) {
         deployment = ApplicationVersionUtils.getAppDeployment(domain, appName, versionId, template, resourceGroup, partition);
         this.debugSay("UpdateOperation mt deployment:  " + deployment);
      }

      if (info.getPartition() != null && info.getResourceGroup() != null && info.getResourceGroupTemplate() == null && deployment != null && deployment.getParent() instanceof ResourceGroupTemplateMBean) {
         return this.handlePartitionResourceGroup(domain, info, appName, versionId, taskName, deployment);
      } else if (info.getResourceGroup() != null && info.getPartition() == null && info.getResourceGroupTemplate() == null && deployment != null && deployment.getParent() instanceof ResourceGroupTemplateMBean) {
         return this.handleResourceGroup(domain, info, appName, versionId, taskName, deployment);
      } else {
         deployment = ApplicationUtils.getAppDeployment(domain, appName, versionId, info);
         if (deployment == null) {
            DomainMBean alternateDomain = this.beanFactory.getAlternateEditableDomain();
            if (alternateDomain != null) {
               alternateDeployment = ApplicationUtils.getAppDeployment(alternateDomain, appName, versionId, info);
            }
         }

         if (isDebugEnabled()) {
            this.debugSay("UpdateOperation deployment:  " + deployment);
         }

         Loggable l;
         try {
            OperationHelper.assertAppIsNonNull(deployment, appName, versionId, taskName, alternateDeployment);
            OperationHelper.assertInfoIsNonNull(info, appName, versionId);
            OperationHelper.assertPlanIsNonNull(info);
            deployment = OperationHelper.getActiveVersionIfNeeded(domain, versionId, deployment, appName, info, taskName);
            this.reconcileMBeans(info, deployment);
            return deployment;
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

   protected void reconcileMBeans(DeploymentData info, AppDeploymentMBean deployment, boolean preservePlan) throws ApplicationException, FileNotFoundException, ManagementException {
      super.reconcileMBeans(info, deployment, preservePlan);
      String cd = info.getConfigDirectory();
      if (cd != null) {
         File f = new File(cd);
         String[] s = f.list();
         if (s == null || s.length == 0) {
            cd = null;
         }
      }

      deployment.setPlanDir(cd);
      deployment.setPlanPath(info.getDeploymentPlan());
      SourceCache.updateDescriptorsInCache(deployment);
   }

   protected AbstractOperation createCopy() {
      return new UpdateOperation();
   }

   protected void mergeWithUndeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithUndeploy(otherOp);
      throw new DeploymentException("UpdateOperation cannot be merged with UndeployOperation");
   }

   protected void mergeWithRedeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithRedeploy(otherOp);
      otherOp.mergeWithUpdate(this);
   }

   protected void mergeWithDeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithDeploy(otherOp);
      otherOp.mergeWithUpdate(this);
   }

   protected void mergeWithUpdate(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithUpdate(otherOp);
      this.mergeWithSameOperationType(otherOp);
   }

   protected void mergeWithDistribute(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithDistribute(otherOp);
      otherOp.mergeWithUpdate(this);
   }

   protected void setupDDPaths(DeploymentData info) {
      if (this.planBean != null && info != null && !info.hasFiles()) {
         ModuleOverrideBean[] mobs = this.planBean.getModuleOverrides();
         if (mobs != null) {
            String root = null;

            for(int i = 0; i < mobs.length; ++i) {
               if (this.planBean.rootModule(mobs[i].getModuleName())) {
                  root = mobs[i].getModuleName();
                  break;
               }
            }

            List files = new ArrayList();

            for(int i = 0; i < mobs.length; ++i) {
               boolean isRoot = mobs[i].getModuleName().equals(root);
               ModuleDescriptorBean[] mds = mobs[i].getModuleDescriptors();
               if (mds != null) {
                  for(int j = 0; j < mds.length; ++j) {
                     String uri = mds[j].getUri();
                     if (!isRoot) {
                        uri = mobs[i].getModuleName() + "/" + uri;
                     }

                     if (this.hasOverrides(mds[j], uri, info) && !files.contains(uri)) {
                        files.add(uri);
                     }
                  }
               }
            }

            info.setFile((String[])((String[])files.toArray(new String[files.size()])));
         }
      }
   }

   private boolean hasOverrides(ModuleDescriptorBean md, String uri, DeploymentData info) {
      if (md.getVariableAssignments() != null && md.getVariableAssignments().length > 0) {
         return true;
      } else if (md.isExternal() && info.getConfigDirectory() != null) {
         File f = new File(info.getConfigDirectory(), uri);
         return f.exists();
      } else {
         return false;
      }
   }
}
