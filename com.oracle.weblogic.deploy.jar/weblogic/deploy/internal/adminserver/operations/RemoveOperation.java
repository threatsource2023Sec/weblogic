package weblogic.deploy.internal.adminserver.operations;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.ApplicationRuntimeState;
import weblogic.management.deploy.internal.DeployerRuntimeExtendedLogger;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.RetirementManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StringUtils;

public final class RemoveOperation extends AbstractOperation {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String taskName;

   public RemoveOperation(int deployerTaskType) {
      this.taskType = 4;
      this.taskName = OperationHelper.getTaskString(deployerTaskType);
   }

   public final DeploymentTaskRuntimeMBean execute(String source, String name, String stagingMode, DeploymentData info, String taskId, boolean startIt, AuthenticatedSubject authenticatedSubject) throws ManagementException {
      String appName = OperationHelper.ensureAppName(name);
      String versionId = OperationHelper.getVersionIdFromData(info, name);
      if (isDebugEnabled()) {
         this.printDebugStartMessage(source, appName, versionId, info, taskId, this.taskName, stagingMode);
      }

      if (versionId != null && versionId.length() > 0) {
         return super.execute(source, name, stagingMode, info, taskId, startIt, authenticatedSubject);
      } else {
         ArrayList otherVersionsToRemove;
         if (OperationHelper.undeployAllVersions(info)) {
            otherVersionsToRemove = this.getNonActiveAppVersions(name, info);
         } else {
            otherVersionsToRemove = this.getRetiredAppVersions(name, info);
         }

         if (otherVersionsToRemove != null && otherVersionsToRemove.size() != 0) {
            if (this.editAccessHelper == null) {
               this.editAccessHelper = getEditAccessHelperForDeployment(kernelId, info.getDeploymentOptions());
            }

            boolean callerOwnsEditLock = this.editAccessHelper.isCurrentEditor(authenticatedSubject);

            try {
               if (callerOwnsEditLock && this.editAccessHelper.getEditDomainBean(authenticatedSubject) == null) {
                  callerOwnsEditLock = false;
               }

               if (!callerOwnsEditLock) {
                  this.editAccessHelper.startEditSession(false);
               }
            } catch (ManagementException var19) {
               this.deploymentManager.deploymentFailedBeforeStart(this.deployment, var19, callerOwnsEditLock, authenticatedSubject, this.editAccessHelper, this.controlOperation);
               throw var19;
            }

            info.getDeploymentOptions().setUseNonexclusiveLock(true);
            DeploymentTaskRuntimeMBean task = null;
            AppDeploymentMBean activeApp = ApplicationUtils.getActiveAppDeployment(appName, info);
            if (activeApp != null) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("RemoveOperation.execute active app " + activeApp);
               }

               task = super.execute(source, activeApp.getName(), stagingMode, info, taskId, startIt, authenticatedSubject);
            }

            DeploymentData deploymentData = new DeploymentData();
            deploymentData.getDeploymentOptions().setUseNonexclusiveLock(true);
            Iterator iter = otherVersionsToRemove.iterator();

            while(iter.hasNext()) {
               AppDeploymentMBean app = (AppDeploymentMBean)iter.next();
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("RemoveOperation.execute other app " + app);
               }

               DeploymentTaskRuntimeMBean curTask = (new RemoveOperation(this.taskType)).execute((String)null, app.getName(), app.getStagingMode(), deploymentData, RetirementManager.getRetireTaskId(app.getName()), true, authenticatedSubject);
               if (task == null) {
                  task = curTask;
               }
            }

            if (!callerOwnsEditLock) {
               try {
                  this.editAccessHelper.saveEditSessionChanges();
                  this.editAccessHelper.activateEditSessionChanges((long)info.getTimeOut());
               } catch (ManagementException var18) {
                  this.deploymentManager.deploymentFailedBeforeStart(this.deployment, var18, callerOwnsEditLock, authenticatedSubject, this.editAccessHelper, this.controlOperation);
                  throw var18;
               }
            }

            return task;
         } else {
            return super.execute(source, name, stagingMode, info, taskId, startIt, authenticatedSubject);
         }
      }
   }

   protected String getAutoDeployErrorMsg(String appName) {
      Loggable logger = DeployerRuntimeLogger.invalidUndeployOnAutodeployedAppLoggable(appName);
      return logger.getMessage();
   }

   protected final AppDeploymentMBean updateConfiguration(String source, String name, String stagingMode, DeploymentData info, String id, boolean callerOwnsEditLock) throws ManagementException {
      AppDeploymentMBean alternateDeployable = null;
      String appName = OperationHelper.ensureAppName(name);
      String versionId = OperationHelper.getVersionIdFromData(info, name);
      if (ApplicationVersionUtils.getLibImplVersion(versionId) == null && ApplicationVersionUtils.getLibSpecVersion(versionId) != null) {
         versionId = ApplicationVersionUtils.getLibSpecVersion(versionId);
      }

      DomainMBean domain = this.beanFactory.getEditableDomain();
      OperationHelper.assertNameIsNonNull(name, this.taskName);
      AppDeploymentMBean deployable = ApplicationUtils.getAppDeployment(domain, appName, versionId, info);
      if (deployable == null) {
         DomainMBean alternateDomain = this.beanFactory.getAlternateEditableDomain();
         if (alternateDomain != null) {
            alternateDeployable = ApplicationUtils.getAppDeployment(alternateDomain, appName, versionId, info);
         }
      }

      String rg = info.getDeploymentOptions().getResourceGroup();
      String rgt = info.getDeploymentOptions().getResourceGroupTemplate();
      if (rg != null) {
         if (deployable != null && ApplicationUtils.isAppDeployedInResourceGroupTemplate(domain, appName, versionId, rg, info.getDeploymentOptions().getPartition())) {
            deployable = null;
         }

         OperationHelper.assertResourceGroupAppIsNonNull(deployable, rg, appName, versionId, this.taskName);
      } else {
         OperationHelper.assertAppIsNonNull(deployable, appName, versionId, this.taskName, alternateDeployable);
      }

      this.verifyIfTargetsAreValid(appName, versionId, info, deployable);
      boolean skipActiveVersionCheck = false;
      if (rgt != null && versionId == null) {
         AppDeploymentMBean[] deployments = AppDeploymentHelper.getAppsAndLibsForGivenScope(domain, rgt, (String)null, (String)null, false);
         int versionsFound = false;
         AppDeploymentMBean[] var17 = deployments;
         int var18 = deployments.length;

         for(int var19 = 0; var19 < var18; ++var19) {
            AppDeploymentMBean deployment = var17[var19];
            if (deployment.getApplicationName().equals(deployable.getApplicationName())) {
               if (versionsFound) {
                  throw new ManagementException(DeployerRuntimeExtendedLogger.logVersionRequiredForUndeploymentFromRGTLoggable().getMessage());
               }

               versionsFound = true;
            }
         }

         if (versionsFound) {
            skipActiveVersionCheck = true;
         }
      }

      if (!skipActiveVersionCheck) {
         deployable = OperationHelper.getActiveVersionIfNeeded(domain, versionId, deployable, appName, info, this.taskName);
      }

      versionId = deployable.getVersionIdentifier();
      OperationHelper.validateUndeployWhileRetire(appName, versionId, deployable, info, id);
      return deployable;
   }

   protected final void postTaskCreationConfigurationUpdate(AppDeploymentMBean deployable, String name, DeploymentData info) throws ManagementException {
      if (info.getDeploymentOptions().getSpecifiedTargetsOnly()) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Skip updating configuration for targeted undeploy");
         }

      } else {
         DomainMBean dmb = ManagementService.getRuntimeAccess(kernelId).getDomain();
         String versionedAppName = deployable.getName();
         String partition = info.getPartition();
         String appId = ApplicationVersionUtils.getApplicationIdWithPartition(versionedAppName, partition);
         AppDeploymentMBean realApp = ApplicationUtils.getAppDeployment(dmb, appId, (String)null, (DeploymentData)info);
         if (!ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(info) || info.hasModuleTargets()) {
            if (realApp == null && Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("RemoveOperation.postTaskCreationConfigurationUpdate gets null AppMBean from runtime tree for " + deployable.getName() + " !!!");
            }

            ((DeploymentTaskRuntime)this.deploymentTask).initMBeans(realApp);
            Set logicalTargets = info.getAllLogicalTargets();

            try {
               this.beanFactory.removeTargetsInDeploymentData(info, deployable);
            } catch (InvalidTargetException var12) {
               Loggable l = DeployerRuntimeLogger.logAppNotTargetedLoggable(var12.getMessage(), name);
               throw new ManagementException(l.getMessage());
            }
         }

         if ((TargetHelper.getAllTargetedServers(deployable).isEmpty() || TargetHelper.allTargetsSpecified(realApp, info)) && !this.isModuleRGOrRGTOperation(info)) {
            this.beanFactory.removeMBean(deployable);
            invalidateCache(deployable);
         }

      }
   }

   private boolean isModuleRGOrRGTOperation(DeploymentData data) {
      boolean isTargetedModuleUndeploy = false;
      if (!data.isStandaloneModule()) {
         isTargetedModuleUndeploy = data.hasModuleTargets();
      }

      return data.isRGOrRGTOperation() && isTargetedModuleUndeploy;
   }

   protected AbstractOperation createCopy() {
      return new RemoveOperation(this.taskType);
   }

   protected void mergeWithUndeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithUndeploy(otherOp);
      this.mergeWithSameOperationType(otherOp);
   }

   protected void mergeWithRedeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithRedeploy(otherOp);
      otherOp.mergeWithUndeploy(this);
   }

   protected void mergeWithDeploy(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithDeploy(otherOp);
      otherOp.mergeWithUndeploy(this);
   }

   protected void mergeWithUpdate(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithUpdate(otherOp);
      otherOp.mergeWithUndeploy(this);
   }

   protected void mergeWithDistribute(AbstractOperation otherOp) throws ManagementException {
      super.mergeWithDistribute(otherOp);
      otherOp.mergeWithUndeploy(this);
   }

   private ArrayList getNonActiveAppVersions(String name, DeploymentData info) {
      String appName = OperationHelper.ensureAppName(name);
      String rgt = info.getResourceGroupTemplate();
      String rg = info.getResourceGroup();
      String partition = info.getPartition();
      AppDeploymentMBean activeApp = ApplicationUtils.getActiveAppDeployment(appName, info);
      if (activeApp != null && activeApp.getVersionIdentifier() == null) {
         return null;
      } else {
         AppDeploymentMBean[] apps = ApplicationVersionUtils.getAppDeployments(appName, rgt, rg, partition);
         if (apps != null && apps.length != 0 && apps[0].getVersionIdentifier() != null) {
            ArrayList rtn = new ArrayList();

            for(int i = 0; i < apps.length; ++i) {
               if (activeApp == null || !apps[i].getName().equals(activeApp.getName())) {
                  if (AppRuntimeStateManager.getManager().isRetiredVersion(apps[i])) {
                     DeployerRuntimeLogger.logRemoveAllRetiredAppVersion(this.taskName, "allversions", appName, apps[i].getVersionIdentifier());
                  } else {
                     DeployerRuntimeLogger.logRemoveAllRetiringAppVersion(this.taskName, "allversions", appName, apps[i].getVersionIdentifier());
                  }

                  rtn.add(apps[i]);
               }
            }

            return rtn;
         } else {
            return null;
         }
      }
   }

   private ArrayList getRetiredAppVersions(String name, DeploymentData info) {
      String appName = OperationHelper.ensureAppName(name);
      String rgt = info.getResourceGroupTemplate();
      String rg = info.getResourceGroup();
      String partition = info.getPartition();
      AppDeploymentMBean activeApp = ApplicationUtils.getActiveAppDeployment(appName, info);
      if (activeApp != null && activeApp.getVersionIdentifier() == null) {
         return null;
      } else {
         AppDeploymentMBean[] apps = ApplicationVersionUtils.getAppDeployments(appName, rgt, rg, partition);
         if (apps != null && apps.length != 0 && apps[0].getVersionIdentifier() != null) {
            ArrayList retiredApps = new ArrayList();

            for(int i = 0; i < apps.length; ++i) {
               if (activeApp == null || !apps[i].getName().equals(activeApp.getName())) {
                  if (AppRuntimeStateManager.getManager().isRetiredVersion(apps[i])) {
                     DeployerRuntimeLogger.logRemoveRetiredAppVersion(this.taskName, appName, apps[i].getVersionIdentifier());
                     retiredApps.add(apps[i]);
                  } else {
                     DeployerRuntimeLogger.logRetiringAppVersionNotRemoved(this.taskName, appName, apps[i].getVersionIdentifier());
                  }
               }
            }

            return retiredApps;
         } else {
            return null;
         }
      }
   }

   private void verifyIfTargetsAreValid(String appName, String versionId, DeploymentData info, AppDeploymentMBean appMBean) throws DeploymentException, ManagementException {
      if (info != null) {
         if (info.hasModuleTargets()) {
            this.verifyModuleTargets(appMBean, info.getAllModuleTargets());
         }

         if (info.getGlobalTargets().length > 0) {
            OperationHelper.checkForClusterTargetSubset(this.beanFactory.getEditableDomain(), info, appMBean, this.taskName);
            this.verifyForPinnedDeployment(appName, versionId, info, appMBean);
         }

      }
   }

   private void verifyModuleTargets(AppDeploymentMBean appMBean, Map moduleTargets) throws DeploymentException {
      String appId = appMBean.getName();
      ApplicationRuntimeState ars = AppRuntimeStateManager.getManager().get(appId);
      if (ars != null) {
         Map moduleMap = ars.getModules();
         Map curModTargets = OperationHelper.createTargetMap(appMBean);
         Set moduleNames = moduleMap.keySet();
         Iterator userModNames = moduleTargets.keySet().iterator();

         while(userModNames.hasNext()) {
            String modName = (String)userModNames.next();
            if (!moduleNames.contains(modName) && !curModTargets.keySet().contains(modName)) {
               String validModules = StringUtils.join(AppRuntimeStateManager.getManager().getModuleIds(appId), ",");
               Loggable l = DeployerRuntimeLogger.logNoSuchModuleLoggable(appId, modName, validModules);
               throw new DeploymentException(l.getMessage());
            }

            Set curModTargetNames = curModTargets.keySet();
            String[] userModTargets = (String[])((String[])moduleTargets.get(modName));
            if (curModTargetNames.contains(modName)) {
               this.compareTargetNames(modName, userModTargets, (Set)curModTargets.get(modName));
            } else {
               this.compareTargetNames(modName, userModTargets, (Set)curModTargets.get(appId));
            }
         }

      }
   }

   private void compareTargetNames(String modName, String[] userTargets, Set targetList) throws DeploymentException {
      for(int m = 0; m < userTargets.length; ++m) {
         if (!targetList.contains(userTargets[m])) {
            Loggable l = DeployerRuntimeLogger.logAppNotTargetedLoggable(userTargets[m], modName);
            throw new DeploymentException(l.getMessage());
         }
      }

   }

   private void verifyForPinnedDeployment(String appName, String versionId, DeploymentData info, AppDeploymentMBean appMBean) throws DeploymentException {
      DomainMBean domain = this.beanFactory.getEditableDomain();
      ClusterMBean[] domainClusters = domain.getClusters();
      if (domainClusters != null && domainClusters.length != 0) {
         String[] userSuppliedTargets = info.getGlobalTargets();
         if (userSuppliedTargets != null && userSuppliedTargets.length != 0) {
            Map pinnedMap = new HashMap();
            TargetMBean[] appTargets = appMBean.getTargets();

            int i;
            for(i = 0; i < domainClusters.length; ++i) {
               ClusterMBean eachClust = domainClusters[i];
               List pinnedServers = this.getPinnedServers(eachClust, appTargets);
               if (!pinnedServers.isEmpty()) {
                  pinnedMap.put(eachClust, pinnedServers);
               }
            }

            if (!pinnedMap.isEmpty()) {
               for(i = 0; i < userSuppliedTargets.length; ++i) {
                  Iterator pinnedIter = pinnedMap.keySet().iterator();

                  while(pinnedIter.hasNext()) {
                     ClusterMBean cluster = (ClusterMBean)pinnedIter.next();
                     List pinnedServers = (List)pinnedMap.get(cluster);
                     if (!pinnedServers.contains(userSuppliedTargets[i])) {
                        Set clusterServers = cluster.getServerNames();
                        if (clusterServers.contains(userSuppliedTargets[i])) {
                           String appId = ApplicationVersionUtils.getApplicationId(appName, versionId);
                           Loggable logger = DeployerRuntimeLogger.logInvalidTargetForPinnedAppUndeployLoggable(appId, userSuppliedTargets[i], this.taskName);
                           throw new DeploymentException(logger.getMessage());
                        }
                     }
                  }
               }

            }
         }
      }
   }

   private List getPinnedServers(ClusterMBean cluster, TargetMBean[] appTargets) {
      List pinnedServers = new ArrayList();
      if (appTargets != null) {
         Set clusterServers = cluster.getServerNames();

         for(int i = 0; i < appTargets.length; ++i) {
            if (appTargets[i] instanceof ServerMBean) {
               String target = appTargets[i].getName();
               if (clusterServers.contains(target)) {
                  pinnedServers.add(target);
               }
            }
         }
      }

      return pinnedServers;
   }

   protected void defaultSubModuleTargets(String appName, String source, DeploymentData info, DomainMBean domain) throws ManagementException {
   }

   protected boolean isRemote(DeploymentData info) {
      return false;
   }
}
