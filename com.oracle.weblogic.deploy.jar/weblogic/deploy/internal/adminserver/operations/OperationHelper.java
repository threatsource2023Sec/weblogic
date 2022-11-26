package weblogic.deploy.internal.adminserver.operations;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ApplicationFileManager;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DeploymentConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeployerRuntimeTextTextFormatter;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.ApplicationRuntimeState;
import weblogic.management.deploy.internal.DeployerRuntimeExtendedLogger;
import weblogic.management.deploy.internal.DeployerRuntimeImpl;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.RetirementManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.AssertionError;
import weblogic.utils.FileUtils;

public class OperationHelper {
   private static final AppRuntimeStateManager appRTStateMgr = AppRuntimeStateManager.getManager();
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DeployerRuntimeTextTextFormatter textformatter = new DeployerRuntimeTextTextFormatter();

   static boolean isLibrary(DeploymentData info) {
      return info != null && info.getDeploymentOptions() != null && info.getDeploymentOptions().isLibrary();
   }

   static String getAppName(String name, DeploymentData info, String src) throws ManagementException {
      return isLibrary(info) ? getAndValidateLibraryName(name, info, src) : ensureAppName(name);
   }

   private static String getAndValidateLibraryName(String name, DeploymentData info, String source) throws ManagementException {
      String libName = ensureAppName(name);
      String manifestLibName = getLibNameFromSource(name, source);
      if (libName != null && !libName.equals("")) {
         if (manifestLibName != null && !manifestLibName.equals("")) {
            if (!manifestLibName.equals(libName)) {
               Loggable l = DeployerRuntimeLogger.logLibNameMismatchLoggable(libName, manifestLibName, source);
               l.log();
               throw new ManagementException(l.getMessage());
            } else {
               if (isDebugEnabled()) {
                  debug("lib name = <" + libName + ">");
               }

               return libName;
            }
         } else {
            return libName;
         }
      } else {
         return manifestLibName != null && !manifestLibName.equals("") ? manifestLibName : getDefaultLibNameFromSource(source, info);
      }
   }

   private static String getLibNameFromSource(String name, String source) throws ManagementException {
      validateSource(name, source);
      return ApplicationVersionUtils.getLibName(source);
   }

   private static String getDefaultLibNameFromSource(String source, DeploymentData info) throws ManagementException {
      try {
         String name = (new File(source)).getCanonicalFile().getName();
         int dotIndex = name.indexOf(46);
         if (dotIndex != -1) {
            name = name.substring(0, dotIndex);
         }

         info.setIsNameFromSource(true);
         return name;
      } catch (IOException var4) {
         Loggable l = DeployerRuntimeLogger.logNoLibNameLoggable(source);
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   private static String getArchiveVersionIdFromData(DeploymentData data, String name) {
      String versionId = null;
      if (data != null && data.getDeploymentOptions() != null) {
         String specVer = data.getDeploymentOptions().getLibSpecVersion();
         String implVer = data.getDeploymentOptions().getLibImplVersion();
         if (!isLibrary(data) || specVer == null && implVer == null) {
            versionId = data.getDeploymentOptions().getArchiveVersion();
         } else {
            versionId = ApplicationVersionUtils.getLibVersionId(specVer, implVer);
         }
      }

      return versionId == null ? ApplicationVersionUtils.getVersionId(name) : versionId;
   }

   static String getVersionIdFromData(DeploymentData data, String name) throws ManagementException {
      return getCompositeVersionId(name, getArchiveVersionIdFromData(data, name), data);
   }

   private static String getArchiveVersionIdFromSource(String fullPath, DeploymentData deployData, String appName) throws ManagementException {
      validateSource(appName, fullPath);
      return isLibrary(deployData) ? ApplicationVersionUtils.getLibVersionId(fullPath) : ApplicationVersionUtils.getManifestVersion(fullPath);
   }

   private static String getCompositeVersionId(String appName, String archiveVersion, DeploymentData deployData) throws ManagementException {
      String planVersion = null;
      planVersion = ApplicationVersionUtils.getPlanVersion(archiveVersion);
      if (planVersion != null) {
         return archiveVersion;
      } else {
         if (deployData != null && deployData.getDeploymentOptions() != null) {
            planVersion = deployData.getDeploymentOptions().getPlanVersion();
         }

         if (archiveVersion == null && planVersion != null) {
            Loggable l = DeployerRuntimeLogger.logPlanVersionNotAllowedLoggable(appName, planVersion);
            l.log();
            throw new ManagementException(l.getMessage());
         } else {
            return ApplicationVersionUtils.getVersionId(archiveVersion, planVersion);
         }
      }
   }

   static String getAndValidateVersionIdWithSrc(DeploymentData info, String name, String source, String appName) throws ManagementException {
      String archiveVersionIdFromData = getArchiveVersionIdFromData(info, name);
      String compositeVersionId = null;
      if (source != null) {
         String archiveVersionIdFromSrc = getArchiveVersionIdFromSource(source, info, appName);
         if (archiveVersionIdFromData == null) {
            compositeVersionId = getCompositeVersionId(appName, archiveVersionIdFromSrc, info);
         } else {
            String libSpecData = ApplicationVersionUtils.getLibSpecVersion(archiveVersionIdFromData);
            String libSpecSrc = ApplicationVersionUtils.getLibSpecVersion(archiveVersionIdFromSrc);
            String libImplData = ApplicationVersionUtils.getLibImplVersion(archiveVersionIdFromData);
            String libImplSrc = ApplicationVersionUtils.getLibImplVersion(archiveVersionIdFromSrc);
            if (libSpecSrc != null && libSpecData != null && !libSpecData.equals(libSpecSrc) || libImplSrc != null && libImplData != null && !libImplData.equals(libImplSrc)) {
               Loggable l = logAppVersionMismatchLoggable(appName, info, archiveVersionIdFromData, archiveVersionIdFromSrc);
               l.log();
               throw new ManagementException(l.getMessage());
            }

            String newLibVersionId;
            if (libImplData == null && libImplSrc == null) {
               newLibVersionId = libSpecSrc == null ? libSpecData : libSpecSrc;
            } else {
               newLibVersionId = ApplicationVersionUtils.getLibVersionId(libSpecSrc == null ? libSpecData : libSpecSrc, libImplSrc == null ? libImplData : libImplSrc);
            }

            compositeVersionId = getCompositeVersionId(appName, newLibVersionId, info);
         }
      }

      if (compositeVersionId != null) {
         Debug.deploymentLogger.debug("new app version = <" + compositeVersionId + ">");
      }

      return compositeVersionId;
   }

   private static Loggable logAppVersionMismatchLoggable(String appName, DeploymentData info, String archiveVersionFromData, String archiveVersionFromSrc) {
      if (isLibrary(info)) {
         String libSpecData = ApplicationVersionUtils.getLibSpecVersion(archiveVersionFromData);
         libSpecData = libSpecData == null ? "" : libSpecData;
         String libImplData = ApplicationVersionUtils.getLibImplVersion(archiveVersionFromData);
         libImplData = libImplData == null ? "" : libImplData;
         String libSpecSrc = ApplicationVersionUtils.getLibSpecVersion(archiveVersionFromSrc);
         libSpecSrc = libSpecSrc == null ? "" : libSpecSrc;
         String libImplSrc = ApplicationVersionUtils.getLibImplVersion(archiveVersionFromSrc);
         libImplSrc = libImplSrc == null ? "" : libImplSrc;
         return DeployerRuntimeLogger.logLibVersionMismatchLoggable(appName, libSpecData, libImplData, libSpecSrc, libImplSrc);
      } else {
         return DeployerRuntimeLogger.logAppVersionMismatchLoggable(appName, archiveVersionFromData, archiveVersionFromSrc);
      }
   }

   static boolean undeployAllVersions(DeploymentData info) {
      return info != null && info.getDeploymentOptions() != null && info.getDeploymentOptions().isUndeployAllVersions();
   }

   static AppDeploymentMBean getActiveVersionIfNeeded(DomainMBean domain, String specifiedVersionId, AppDeploymentMBean dmbFound, String appName, DeploymentData info, String operation) throws ManagementException {
      AppDeploymentMBean deployMBean = dmbFound;
      if (specifiedVersionId == null && dmbFound.getVersionIdentifier() != null) {
         deployMBean = ApplicationUtils.getActiveAppDeployment(domain, appName, info, isAdminMode(info));
         if (undeployAllVersions(info)) {
            if (deployMBean != null) {
               DeployerRuntimeLogger.logRemoveAllActiveAppVersion(operation, "allversions", appName, deployMBean.getVersionIdentifier());
            }
         } else {
            if (deployMBean == null) {
               Loggable l = DeployerRuntimeLogger.logNoActiveAppLoggable(appName, operation);
               l.log();
               throw new ManagementException(l.getMessage());
            }

            DeployerRuntimeLogger.logActiveAppVersionWarning(appName, deployMBean.getVersionIdentifier(), operation);
         }
      }

      return deployMBean;
   }

   static AppDeploymentMBean getActiveVersion(DomainMBean domain, AppDeploymentMBean newApp, DeploymentData info) throws ManagementException {
      return newApp != null && newApp.getVersionIdentifier() == null ? ApplicationUtils.getActiveAppDeployment(domain, newApp.getApplicationName(), info, isAdminMode(info)) : null;
   }

   private static void validateSource(String appName, String source) throws ManagementException {
      if (source != null && !source.equals("")) {
         if (!(new File(source)).exists()) {
            Loggable l = DeployerRuntimeLogger.logInvalidSourceLoggable(source, appName, "No application files exist");
            l.log();
            throw new ManagementException(l.getMessage());
         }
      }
   }

   static void validateVersionForDeprecatedOp(DeploymentData info, String name, String deprecatedOp, int preferredOpType) throws ManagementException {
      String versionId = getArchiveVersionIdFromData(info, name);
      if (versionId != null) {
         Loggable l = DeployerRuntimeLogger.logVersionNotAllowedForDeprecatedOpLoggable(ApplicationVersionUtils.getDisplayName(ensureAppName(name), versionId), deprecatedOp, getTaskString(preferredOpType));
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void validateMaxAppVersions(DomainMBean domain, String appName, String versionId, DeploymentData info) throws ManagementException {
      if (versionId != null && !versionId.equals("")) {
         AppDeploymentMBean[] apps = ApplicationVersionUtils.getAppDeployments(domain, appName, info.getResourceGroupTemplate(), info.getResourceGroup(), info.getPartition(), true);
         DeploymentConfigurationMBean depConfig = domain.getDeploymentConfiguration();
         if (depConfig == null) {
            Loggable l = DeployerRuntimeLogger.logFailedToObtainConfigLoggable();
            l.log();
            throw new ManagementException(l.getMessage());
         } else {
            int maxAppVersions = depConfig.getMaxAppVersions();
            if (apps != null && apps.length >= maxAppVersions) {
               StringBuffer curVersions = new StringBuffer();

               for(int i = 0; i < apps.length; ++i) {
                  String curVersion = apps[i].getVersionIdentifier();
                  if (versionId.equals(curVersion)) {
                     return;
                  }

                  curVersions.append(curVersion);
                  if (i != apps.length - 1) {
                     curVersions.append(", ");
                  }
               }

               Loggable l = DeployerRuntimeLogger.logMaxAppVersionsExceededLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId), appName, maxAppVersions, curVersions.toString());
               l.log();
               throw new ManagementException(l.getMessage());
            }
         }
      }
   }

   static void validateVersionIdFormat(String appName, String versionId) throws ManagementException {
      if (versionId != null && !versionId.equals("")) {
         Loggable l;
         if (versionId.length() > 215) {
            l = DeployerRuntimeLogger.logVersionIdLengthExceededLoggable(appName, ApplicationVersionUtils.getDisplayVersionId(versionId), 215);
            l.log();
            throw new ManagementException(l.getMessage());
         } else if (!ApplicationVersionUtils.isVersionIdValid(versionId)) {
            l = DeployerRuntimeLogger.logInvalidVersionIdLoggable(appName, ApplicationVersionUtils.getDisplayVersionId(versionId));
            l.log();
            throw new ManagementException(l.getMessage());
         }
      }
   }

   static void validateSourceVersion(AppDeploymentMBean deployable, DeploymentData deployData, String appName) throws ManagementException {
      String mbeanVersion = ApplicationVersionUtils.getArchiveVersion(deployable);
      String srcVersion = getArchiveVersionIdFromSource(deployable.getAbsoluteSourcePath(), deployData, appName);
      if (mbeanVersion != null && !mbeanVersion.equals(srcVersion)) {
         Loggable l = DeployerRuntimeLogger.logAppVersionMismatch2Loggable(deployable.getApplicationName(), mbeanVersion, srcVersion);
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void validateNonVersionWithVersion(String currentVersionId, AppDeploymentMBean dmbFound, String appName, String operation) throws ManagementException {
      if (currentVersionId == null && dmbFound.getVersionIdentifier() != null) {
         Loggable l = DeployerRuntimeLogger.logInvalidAppVersion2Loggable(appName, dmbFound.getVersionIdentifier(), operation);
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void validateVersionWithNonVersion(DomainMBean domain, String currentVersionId, String appName, String operation, DeploymentData info) throws ManagementException {
      if (currentVersionId != null) {
         AppDeploymentMBean dmbFound = ApplicationUtils.getAppDeployment(domain, appName, (String)null, (DeploymentData)info);
         if (dmbFound != null && dmbFound.getVersionIdentifier() == null) {
            Loggable l = DeployerRuntimeLogger.logInvalidAppVersionLoggable(appName, currentVersionId, operation);
            l.log();
            throw new ManagementException(l.getMessage());
         }
      }

   }

   static void validateRetireTimeout(DomainMBean domain, String appName, String version, DeploymentData info) throws ManagementException {
      if (!info.getDeploymentOptions().isRetireGracefully() && info.getDeploymentOptions().getRetireTime() > -1) {
         AppDeploymentMBean dmbFound = ApplicationUtils.getAppDeployment(domain, appName, (String)null, (DeploymentData)info);
         if (dmbFound == null) {
            throw new ManagementException(DeployerRuntimeLogger.invalidRetireTimeout(appName, version, info.getDeploymentOptions().getRetireTime()));
         }
      }

   }

   static void validateVersionTargets(DomainMBean domain, String appName, DeploymentData info, String versionId) throws ManagementException {
      if (versionId != null && info != null && info.hasTargets()) {
         AppDeploymentMBean app = ApplicationUtils.getAppDeployment(domain, appName, (String)null, (DeploymentData)info);
         if (app != null && app.getVersionIdentifier() != null) {
            Set oldTargets = TargetHelper.getAllTargetedServers(app);

            Set newTargets;
            try {
               newTargets = info.getAllTargetedServers(info.getAllLogicalTargets(), domain);
            } catch (Throwable var8) {
               if (isDebugEnabled()) {
                  debug("Cannot obtain targeted servers for " + appName + " from " + info, var8);
               }

               return;
            }

            if (!oldTargets.containsAll(newTargets) || !newTargets.containsAll(oldTargets)) {
               Loggable l = DeployerRuntimeLogger.logInvalidTargetsForAppVersionLoggable(appName, versionId, oldTargets.toString(), newTargets.toString());
               l.log();
               throw new ManagementException(l.getMessage());
            }
         }

      }
   }

   static void validateTargets(DomainMBean domain, DeploymentData info, AppDeploymentMBean appMBean, String operation) throws ManagementException {
      if (info.hasTargets()) {
         checkForClusterTargetSubset(domain, info, appMBean, operation);
         checkForTargetListSubset(appMBean, info);
      }
   }

   static void checkForTargetListSubset(AppDeploymentMBean appMBean, DeploymentData info) {
      Map confTargetMap = createTargetMap(appMBean);
      if (info.getGlobalTargets().length != 0) {
         Set confTargets = (Set)confTargetMap.get(appMBean.getName());
         Set userTargets = createTargetSet(info.getGlobalTargets());
         verifyTargets(appMBean.getApplicationIdentifier(), confTargets, userTargets);
      }

      Set userModTargets;
      Set confModTargets;
      String modName;
      if (info.hasModuleTargets()) {
         for(Iterator moduleNames = info.getAllModuleTargets().keySet().iterator(); moduleNames.hasNext(); verifyTargets(modName, confModTargets, userModTargets)) {
            modName = (String)moduleNames.next();
            userModTargets = createTargetSet((String[])((String[])info.getAllModuleTargets().get(modName)));
            confModTargets = (Set)confTargetMap.get(appMBean.getName());
            if (confTargetMap.keySet().contains(modName)) {
               confModTargets = (Set)confTargetMap.get(modName);
            }
         }
      }

   }

   static void checkForClusterTargetSubset(DomainMBean domain, DeploymentData info, AppDeploymentMBean appMBean, String operation) throws ManagementException {
      checkForClusterTargetSubset(domain, info, appMBean, operation, false);
   }

   static void checkForClusterTargetSubset(DomainMBean domain, DeploymentData info, AppDeploymentMBean appMBean, String operation, boolean controlOperation) throws ManagementException {
      boolean specifiedTargetsOnly = false;
      DeploymentOptions deplOpts = info.getDeploymentOptions();
      if (deplOpts != null) {
         specifiedTargetsOnly = deplOpts.getSpecifiedTargetsOnly();
      }

      if (isAppHasClusterTargets(appMBean) && !specifiedTargetsOnly) {
         String[] userSuppliedTargets = info.getGlobalTargets();
         List clusterTargets = getAppClusterTargets(appMBean);
         List userTargets = new ArrayList();

         for(int i = 0; i < userSuppliedTargets.length; ++i) {
            userTargets.add(userSuppliedTargets[i]);
         }

         checkTargetsPartOfCluster(domain, appMBean.getApplicationIdentifier(), clusterTargets, userTargets, operation, controlOperation);
      }

   }

   private static List getAppClusterTargets(AppDeploymentMBean appMBean) {
      List clusterTargets = new ArrayList();
      TargetMBean[] appTargets = appMBean.getTargets();
      if (appTargets != null) {
         for(int i = 0; i < appTargets.length; ++i) {
            if (appTargets[i] instanceof ClusterMBean) {
               clusterTargets.add(appTargets[i]);
            }
         }
      }

      return clusterTargets;
   }

   private static void checkTargetsPartOfCluster(DomainMBean domain, String appId, List clusterTargets, List userTargets, String operation, boolean controlOperation) throws ManagementException {
      Iterator targs = userTargets.iterator();

      while(true) {
         String target;
         ServerMBean serverTarget;
         do {
            if (!targs.hasNext()) {
               return;
            }

            target = (String)targs.next();
            serverTarget = domain.lookupServer(target);
         } while(serverTarget == null);

         Iterator iter = clusterTargets.iterator();

         while(iter.hasNext()) {
            ClusterMBean eachCluster = (ClusterMBean)iter.next();
            Set serverNames = eachCluster.getServerNames();
            if (serverNames.contains(target) && !userTargets.contains(eachCluster.getName())) {
               if (!controlOperation) {
                  Loggable logger = DeployerRuntimeLogger.logInvalidIndividualTargetLoggable(appId, eachCluster.getName(), target, operation);
                  throw new ManagementException(logger.getMessage());
               }

               DeployerRuntimeLogger.logPartialClusterTarget(appId, eachCluster.getName(), target, operation);
            }
         }
      }
   }

   private static boolean isAppHasClusterTargets(AppDeploymentMBean appMBean) {
      TargetMBean[] appTargets = appMBean.getTargets();
      if (appTargets != null) {
         for(int i = 0; i < appTargets.length; ++i) {
            if (appTargets[i] instanceof ClusterMBean) {
               return true;
            }
         }
      }

      return false;
   }

   static Map createTargetMap(AppDeploymentMBean appMBean) {
      Map targetMap = new HashMap();
      targetMap.put(appMBean.getName(), createTargetSet(appMBean.getTargets()));
      SubDeploymentMBean[] subDeps = appMBean.getSubDeployments();
      if (subDeps != null) {
         for(int i = 0; i < subDeps.length; ++i) {
            targetMap.put(subDeps[i].getName(), createTargetSet(subDeps[i].getTargets()));
         }
      }

      return targetMap;
   }

   static void verifyTargets(String name, Set confTargets, Set userTargets) {
      if (!userTargets.containsAll(confTargets) && containsAny(userTargets, confTargets)) {
         Loggable l = DeployerRuntimeLogger.logInvalidTargetSubsetLoggable(name, confTargets.toString(), userTargets.toString());
         l.log();
      }

   }

   private static boolean containsAny(Set collection, Set any) {
      Iterator iter = any.iterator();

      Object tmp;
      do {
         if (!iter.hasNext()) {
            return false;
         }

         tmp = iter.next();
      } while(!collection.contains(tmp));

      return true;
   }

   static Set createTargetSet(TargetMBean[] targets) {
      Set targetSet = new HashSet();

      for(int i = 0; i < targets.length; ++i) {
         targetSet.add(targets[i].getName());
      }

      return targetSet;
   }

   static Set createTargetSet(String[] targets) {
      Set targetSet = new HashSet();

      for(int i = 0; i < targets.length; ++i) {
         targetSet.add(targets[i]);
      }

      return targetSet;
   }

   static boolean isOnlyAdminServerTarget(DomainMBean domain, DeploymentData info) throws ManagementException {
      if (null == info) {
         return true;
      } else if (!info.hasTargets()) {
         return true;
      } else {
         try {
            HashSet expectedTargets = new HashSet();
            expectedTargets.add(domain.getAdminServerName());
            Set operationTargets = info.getAllTargetedServers(info.getAllLogicalTargets(), domain);
            if (!expectedTargets.equals(operationTargets)) {
               return false;
            }
         } catch (InvalidTargetException var4) {
         }

         return true;
      }
   }

   static void validateAutoDeployTarget(DomainMBean domain, String appName, DeploymentData info) throws ManagementException {
      AppDeploymentMBean app = ApplicationUtils.getAppDeployment(domain, appName, (String)null, (DeploymentData)info);
      if (null != app && app.isAutoDeployedApp() && !isOnlyAdminServerTarget(domain, info)) {
         Loggable l = DeployerRuntimeLogger.logNoReTargetOnAutoDeployedAppLoggable(appName);
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void validateSplitDirTarget(DomainMBean domain, String appName, DeploymentData info) throws ManagementException {
      AppDeploymentMBean app = ApplicationUtils.getAppDeployment(domain, appName, (String)null, (DeploymentData)info);
      if (null != app) {
         try {
            ApplicationFileManager afm = ApplicationFileManager.newInstance(app.getAbsoluteSourcePath());
            if (afm.isSplitDirectory() && !isOnlyAdminServerTarget(domain, info)) {
               Loggable l = DeployerRuntimeLogger.logNoReTargetOnSplitDirAppLoggable(appName);
               l.log();
               throw new ManagementException(l.getMessage());
            }

            isOnlyAdminServerTarget(domain, info);
         } catch (IOException var6) {
         }
      }

   }

   static void validateDeployWhileRetire(String appName, String versionId, AppDeploymentMBean deployable) throws ManagementException {
      if (!RetirementManager.cancelIfNeeded(appName, versionId)) {
         Loggable l = DeployerRuntimeLogger.logActivateWhileRetireInProgressLoggable(ApplicationVersionUtils.getDisplayName(deployable));
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void validateUndeployWhileRetire(String appName, String versionId, AppDeploymentMBean deployMBean, DeploymentData deployData, String taskId) throws ManagementException {
      if (!isRetirementTask(taskId)) {
         if (isGracefulProdToAdmin(deployData)) {
            if (RetirementManager.isRetirementInProgress(appName, versionId)) {
               Loggable l = DeployerRuntimeLogger.logGracefulUndeployWhileRetireInProgressLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId));
               l.log();
               throw new ManagementException(l.getMessage());
            }
         } else {
            RetirementManager.cancelIfNeeded(appName, versionId);
         }

      }
   }

   static void validateDeployWhileStop(String appName, String versionId, DeployerRuntimeImpl deployerRuntime) throws ManagementException {
      DeploymentTaskRuntimeMBean[] var3 = deployerRuntime.list();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DeploymentTaskRuntimeMBean taskMBean = var3[var5];
         if (taskMBean.getState() == 1 && taskMBean.getTask() == 8 && taskMBean.getApplicationName().equals(appName) && (versionId == null && taskMBean.getApplicationVersionIdentifier() == null || versionId != null && versionId.equals(taskMBean.getApplicationVersionIdentifier()))) {
            Loggable l = DeployerRuntimeLogger.logActivateWhileStopInProgressLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId));
            l.log();
            throw new ManagementException(l.getMessage());
         }
      }

   }

   static void validateModuleType(String appName, String versionId, AppDeploymentMBean deployMBean) throws ManagementException {
      if (versionId != null && !DeployHelper.isModuleType(deployMBean, ModuleType.EAR) && !DeployHelper.isModuleType(deployMBean, ModuleType.WAR) && !(deployMBean instanceof LibraryMBean)) {
         String moduleType = deployMBean.getModuleType();
         Loggable l = DeployerRuntimeLogger.logModuleTypeNotSupportedLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId), moduleType == null ? null : moduleType.toUpperCase(Locale.US), "EAR, WAR (Webapps, including stateless Webservices)");
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void validatePath(String path, AppDeploymentMBean deployable) throws ManagementException {
      if (path != null) {
         try {
            String fullPath = (new File(path)).getCanonicalPath();
            deployable.setSourcePath(path);
         } catch (IOException var4) {
            if (!"nostage".equals(deployable.getStagingMode())) {
               Loggable l = DeployerRuntimeLogger.logInvalidSourceLoggable(path, ApplicationVersionUtils.getDisplayName(deployable), var4.getMessage());
               l.log();
               throw new ManagementException(l.getMessage(), var4);
            }
         }

      }
   }

   static boolean isGracefulProdToAdmin(DeploymentData deployData) {
      return deployData != null && deployData.getDeploymentOptions() != null ? deployData.getDeploymentOptions().isGracefulProductionToAdmin() : false;
   }

   static boolean isSameCanonicalPaths(String fullPath, AppDeploymentMBean deployable) throws IOException {
      String sourcePath = deployable.getAbsoluteSourcePath();
      if (sourcePath == null && fullPath != null) {
         return false;
      } else if (sourcePath == null && fullPath == null) {
         return true;
      } else {
         String appFullPath = (new File(sourcePath)).getCanonicalPath();
         if (isDebugEnabled()) {
            debug("Validating new path, " + fullPath + ", against configured path, " + appFullPath);
         }

         return fullPath.equals(appFullPath);
      }
   }

   static void assertModeIsStage(AppDeploymentMBean deployable, String appName) throws ManagementException {
      String stagingMode = deployable.getStagingMode();
      if (stagingMode != null && !stagingMode.equals("stage")) {
         Loggable l = DeployerRuntimeLogger.logErrorStagingModeLoggable(appName, stagingMode);
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void assertAppIsNonNull(AppDeploymentMBean deployable, String appName, String versionId, String operation) throws ManagementException {
      assertAppIsNonNull(deployable, appName, versionId, operation, (AppDeploymentMBean)null);
   }

   static void assertAppIsNonNull(AppDeploymentMBean deployable, String appName, String versionId, String operation, AppDeploymentMBean alternateDeployable) throws ManagementException {
      if (deployable == null) {
         Loggable l;
         if (ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isRestartRequired()) {
            l = DeployerRuntimeLogger.logNullAppNonDynamicLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId), operation);
         } else if (alternateDeployable != null) {
            l = DeployerRuntimeExtendedLogger.logExistingChangeLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId), operation);
         } else {
            l = DeployerRuntimeLogger.logNullAppLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId), operation);
         }

         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void assertResourceGroupAppIsNonNull(AppDeploymentMBean deployable, String rg, String appName, String versionId, String operation) throws ManagementException {
      if (deployable == null) {
         Loggable l;
         if (ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isRestartRequired()) {
            l = DeployerRuntimeExtendedLogger.logNullRGAppNonDynamicLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId), operation, rg);
         } else {
            l = DeployerRuntimeExtendedLogger.logNullRGAppLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId), operation, rg);
         }

         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void assertAppIsActive(AppDeploymentMBean deployable, String appName, String versionId, String operation) throws ManagementException {
      if (deployable != null) {
         String state = appRTStateMgr.getCurrentState(deployable);
         if (state != null && (state.equals("STATE_RETIRED") || state.equals("STATE_UPDATE_PENDING"))) {
            Loggable l = DeployerRuntimeLogger.logNonActiveAppLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId), state, operation);
            l.log();
            throw new ManagementException(l.getMessage());
         }
      }
   }

   static void assertAppIsNotRetired(DomainMBean domain, AppDeploymentMBean deployable, String appName, String versionId, String operation) throws ManagementException {
      if (deployable != null) {
         String state = appRTStateMgr.getCurrentState(deployable);
         if (state != null && state.equals("STATE_RETIRED")) {
            AppDeploymentMBean activeDeploymentMBean = ApplicationUtils.getActiveAppDeployment(domain, appName, false);
            if (activeDeploymentMBean == null) {
               activeDeploymentMBean = ApplicationUtils.getActiveAppDeployment(domain, appName, true);
            }

            if (activeDeploymentMBean != null) {
               String activeAppState = appRTStateMgr.getCurrentState(activeDeploymentMBean);
               if (activeAppState != null && (activeAppState.equals("STATE_ACTIVE") || activeAppState.equals("STATE_ADMIN"))) {
                  Loggable l = DeployerRuntimeLogger.logInvalidAppStateLoggable(ApplicationVersionUtils.getDisplayName(appName, versionId), state, ApplicationVersionUtils.getDisplayName(activeDeploymentMBean), operation);
                  l.log();
                  throw new ManagementException(l.getMessage());
               }
            }
         }

      }
   }

   static void assertNameIsNonNull(String appName, String operation) throws ManagementException {
      if (appName == null) {
         Loggable l = DeployerRuntimeLogger.logNullAppLoggable("null", operation);
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void assertSourceIsNonNull(String source, String name, String versionId) throws ManagementException {
      if (source == null) {
         Loggable l = DeployerRuntimeLogger.logNoSourceLoggable(ApplicationVersionUtils.getDisplayName(name, versionId));
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   static void assertInfoIsNonNull(DeploymentData info, String name, String versionId) throws ManagementException {
      if (info == null || !info.hasFiles()) {
         Loggable l = DeployerRuntimeLogger.logNullInfoLoggable(ApplicationVersionUtils.getDisplayName(name, versionId));
         l.log();
      }

   }

   static void assertTargetIsNonNull(DeploymentData deployData) throws ManagementException {
      if (deployData == null || !deployData.hasTargets()) {
         String msg = DeployerRuntimeLogger.operationRequiresTarget();
         throw new ManagementException(msg);
      }
   }

   static void assertPlanIsNonNull(DeploymentData deployData) throws ManagementException {
      if (deployData == null || deployData.getDeploymentPlan() == null) {
         String msg = DeployerRuntimeLogger.operationRequiresPlan();
         throw new ManagementException(msg);
      }
   }

   static boolean isAdminMode(DeploymentData deployData) {
      return deployData != null && deployData.getDeploymentOptions() != null ? deployData.getDeploymentOptions().isTestMode() : false;
   }

   public static void setAdminMode(AppDeploymentMBean dep, DeploymentData deployData, String defaultState) throws ManagementException {
      if (dep == null) {
         if (isDebugEnabled()) {
            debug("OperationHelper.setAdminMode(): Deployment is null");
         }

      } else {
         if (isDebugEnabled()) {
            debug("OperationHelper.setAdminMode(): For application = " + dep.getName() + ", defaultState = " + defaultState);
         }

         boolean adminMode = isAdminMode(deployData);
         if (isDebugEnabled()) {
            debug("OperationHelper.setAdminMode(): AdminMode from deployData: " + adminMode);
         }

         String s = adminMode ? "STATE_ADMIN" : defaultState;
         setState(dep, deployData, s);
      }
   }

   public static void setState(AppDeploymentMBean dep, DeploymentData deployData, String state) throws ManagementException {
      if (dep != null) {
         resetUnconfiguredTargets(dep);
         if (isDebugEnabled()) {
            debug("OperationHelper.setState(): Setting intended state for app : " + dep.getName() + " to : " + state);
         }

         String appId = ApplicationVersionUtils.getApplicationId(dep);
         appRTStateMgr.setState(appId, (String[])((String[])deployData.getAllLogicalTargets().toArray(new String[0])), state);
         deployData.setIntendedState(state);
      }
   }

   private static void resetUnconfiguredTargets(AppDeploymentMBean dep) throws ManagementException {
      String depNameWithPartition = ApplicationVersionUtils.getApplicationId(dep);
      ApplicationRuntimeState ars = appRTStateMgr.get(depNameWithPartition);
      if (ars != null) {
         List resets = new ArrayList();
         Map ats = ars.getAppTargetState();
         if (ats != null) {
            Set names = DeployHelper.getAllTargetNames((BasicDeploymentMBean)dep);
            Iterator iterator;
            String s;
            if (isDebugEnabled()) {
               String t = "";

               for(iterator = names.iterator(); iterator.hasNext(); t = t + s + " ") {
                  s = (String)iterator.next();
               }

               debug("Targets assoc with " + depNameWithPartition + ": " + t);
            }

            Set atsNames = ats.keySet();
            iterator = atsNames.iterator();

            while(iterator.hasNext()) {
               s = (String)iterator.next();
               if (!names.contains(s)) {
                  resets.add(s);
                  if (isDebugEnabled()) {
                     debug("Resetting intended state for " + depNameWithPartition + " on target " + s);
                  }
               }
            }
         }

         if (!resets.isEmpty()) {
            appRTStateMgr.resetState(depNameWithPartition, (String[])((String[])resets.toArray(new String[0])));
         }
      }

   }

   static boolean hasFiles(DeploymentData deployData) {
      return deployData.getFiles() != null && deployData.getFiles().length > 0;
   }

   static String ensureAppName(String name) {
      return ApplicationVersionUtils.getApplicationName(name);
   }

   static void validateRedeploySource(String appName, String versionId, AppDeploymentMBean appMBean) throws ManagementException {
      Loggable l;
      if (versionId == null) {
         l = DeployerRuntimeLogger.logRedeployWithSrcNotAllowedForNonVersionLoggable(appName);
         l.log();
         throw new ManagementException(l.getMessage());
      } else if (appMBean != null && versionId.equals(appMBean.getVersionIdentifier())) {
         l = DeployerRuntimeLogger.logRedeployWithSrcNotAllowedForSameVersionLoggable(ApplicationVersionUtils.getDisplayName(appMBean));
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   public static void logTaskFailed(String name, int taskType) {
      logTaskFailed(name, getTaskString(taskType));
   }

   public static void logTaskFailed(String name, int taskType, Throwable t) {
      logTaskFailed(name, getTaskString(taskType), t);
   }

   public static String getTaskString(int tasktype) {
      switch (tasktype) {
         case 1:
            return textformatter.messageActivate();
         case 2:
         default:
            throw new AssertionError();
         case 3:
            return textformatter.messageDeactivate();
         case 4:
            return textformatter.messageRemove();
         case 5:
            return textformatter.messageUnprepare();
         case 6:
            return textformatter.messageDistribute();
         case 7:
            return textformatter.messageStart();
         case 8:
            return textformatter.messageStop();
         case 9:
            return textformatter.messageRedeploy();
         case 10:
            return textformatter.messageUpdate();
         case 11:
            return textformatter.messageDeploy();
         case 12:
            return textformatter.messageUndeploy();
         case 13:
            return textformatter.messageRetire();
         case 14:
            return textformatter.messageExtendLoader();
      }
   }

   private static boolean isRetirementTask(String taskId) {
      return RetirementManager.isRetireTaskId(taskId);
   }

   private static void logTaskFailed(String name, String taskType, Throwable t) {
      if (t == null) {
         if (name != null) {
            DeployerRuntimeLogger.logTaskFailed(ApplicationVersionUtils.getDisplayName(name), taskType);
         } else {
            DeployerRuntimeLogger.logTaskFailedNoApp(taskType);
         }
      } else if (name != null) {
         DeployerRuntimeLogger.logTaskFailedWithError(ApplicationVersionUtils.getDisplayName(name), taskType, t.getMessage());
      } else {
         DeployerRuntimeLogger.logTaskFailedNoAppWithError(taskType, t.getMessage());
      }

   }

   private static void logTaskFailed(String name, String taskType) {
      logTaskFailed(name, taskType, (Throwable)null);
   }

   public static void assertNoMixedTargeting(DeploymentData info) throws ManagementException {
      if (info != null && !info.hasFiles() && !info.isTargetsFromConfig() && info.hasSubModuleTargets() && info.hasModuleTargets()) {
         Thread.dumpStack();
         throw new ManagementException(DeployerRuntimeLogger.mixedTargetError());
      }
   }

   public static void assertNoChangedAltDDs(DeploymentData info) throws ManagementException {
      if (info != null) {
         String altDDPath = info.getAltDescriptorPath();
         String altWLSDDPath = info.getAltWLSDescriptorPath();
         if (altDDPath != null && !altDDPath.equals("")) {
            throw new ManagementException(DeployerRuntimeLogger.invalidAltDDDuringRedeploy());
         }

         if (altWLSDDPath != null && !altWLSDDPath.equals("")) {
            throw new ManagementException(DeployerRuntimeLogger.invalidAltDDDuringRedeploy());
         }
      }

   }

   public static String normalizePaths(String source, DeploymentData info) {
      source = normalize(source);
      info.setRootDirectory(normalize(info.getRootDirectory()));
      info.setDeploymentPlan(normalize(info.getDeploymentPlan()));
      info.setConfigDirectory(normalize(info.getConfigDirectory()));
      return source;
   }

   private static String normalize(String dir) {
      return FileUtils.normalize(dir);
   }

   private static void debug(String msg, Throwable t) {
      Debug.deploymentDebug(msg, t);
   }

   private static void debug(String msg) {
      Debug.deploymentDebug(msg);
   }

   private static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }
}
