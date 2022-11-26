package weblogic.deploy.api.spi;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.management.security.DeploymentModel;

public final class DeploymentOptions implements Serializable {
   private static final long serialVersionUID = -8124655104926643928L;
   private boolean useNonexclusiveLock = true;
   private boolean operationInitiatedByAutoDeployPoller = false;
   private boolean testOption = false;
   private int retireTimeOption = -1;
   private boolean retireGracefulPolicy = true;
   private boolean gracefulProductionToAdmin = false;
   private boolean gracefulIgnoreSessions = false;
   private int gracefulRMIGracePeriodSecs = -1;
   private boolean undeployAllVersions = false;
   private String stageOption;
   private String planStageOption;
   private String name;
   private boolean nameFromLibrary;
   private long forceUndeployTimeoutSecs;
   private boolean disableModuleLevelStartStop;
   private long timeout;
   private int deploymentOrder;
   private String deploymentPrincipalName;
   private boolean succeedIfNameUsed;
   private boolean cacheInAppDirectory;
   private boolean specifiedTargetsOnly;
   private boolean removePlanOverride;
   private boolean startTask;
   private boolean appShutdownOnStop;
   private boolean nameFromSource;
   private boolean defaultSubmoduleTargets;
   private String altDD;
   private String altWlsDD;
   private boolean noVersion;
   private boolean remote;
   private boolean useExpiredLock;
   private String partition;
   private String resourceGroup;
   private String resourceGroupTemplate;
   private String[] specifiedModules;
   private String editSessionName;
   private String uploadPath;
   private boolean overwriteFile;
   private boolean tmidsFromConfig;
   public static final String STAGE_DEFAULT = null;
   public static final String PLAN_STAGE_DEFAULT = null;
   public static final String STAGE = "stage";
   public static final String PLAN_STAGE = "stage";
   public static final String NOSTAGE = "nostage";
   public static final String PLAN_NOSTAGE = "nostage";
   public static final String EXTERNAL_STAGE = "external_stage";
   public static final String PLAN_EXTERNAL_STAGE = "external_stage";
   public static final boolean FULL_ACCESS = false;
   public static final boolean ADMIN_ACCESS = true;
   private String securityModel;
   private boolean isSecurityValidationEnabled;
   private String archiveVersion;
   private String planVersion;
   private boolean isLibrary;
   private String libSpecVersion;
   private String libImplVersion;
   public static final int CLUSTER_DEPLOYMENT_TIMEOUT = 3600000;
   private int clusterTimeout;

   public DeploymentOptions() {
      this.stageOption = STAGE_DEFAULT;
      this.planStageOption = PLAN_STAGE_DEFAULT;
      this.name = null;
      this.nameFromLibrary = false;
      this.forceUndeployTimeoutSecs = 0L;
      this.disableModuleLevelStartStop = false;
      this.timeout = 0L;
      this.deploymentOrder = 100;
      this.deploymentPrincipalName = null;
      this.succeedIfNameUsed = false;
      this.cacheInAppDirectory = false;
      this.specifiedTargetsOnly = false;
      this.removePlanOverride = false;
      this.startTask = true;
      this.appShutdownOnStop = false;
      this.nameFromSource = false;
      this.defaultSubmoduleTargets = true;
      this.altDD = null;
      this.altWlsDD = null;
      this.noVersion = false;
      this.remote = false;
      this.useExpiredLock = false;
      this.partition = null;
      this.resourceGroup = null;
      this.resourceGroupTemplate = null;
      this.specifiedModules = null;
      this.editSessionName = null;
      this.uploadPath = null;
      this.overwriteFile = false;
      this.tmidsFromConfig = false;
      this.securityModel = null;
      this.isSecurityValidationEnabled = false;
      this.archiveVersion = null;
      this.planVersion = null;
      this.isLibrary = false;
      this.libSpecVersion = null;
      this.libImplVersion = null;
      this.clusterTimeout = 3600000;
   }

   public boolean usesNonExclusiveLock() {
      return this.useNonexclusiveLock;
   }

   public boolean isOperationInitiatedByAutoDeployPoller() {
      return this.operationInitiatedByAutoDeployPoller;
   }

   public void setOperationInitiatedByAutoDeployPoller(boolean val) {
      this.operationInitiatedByAutoDeployPoller = val;
   }

   public void setUseNonexclusiveLock(boolean useNonexclusiveLock) {
      this.useNonexclusiveLock = useNonexclusiveLock;
   }

   public String getAltDD() {
      return this.altDD;
   }

   public void setAltDD(String altDD) {
      this.altDD = altDD;
   }

   public String getAltWlsDD() {
      return this.altWlsDD;
   }

   public void setAltWlsDD(String altWlsDD) {
      this.altWlsDD = altWlsDD;
   }

   public String getSecurityModel() {
      return this.securityModel;
   }

   public void setSecurityModel(String model) throws IllegalArgumentException {
      if (DeploymentModel.isValidModel(model)) {
         this.securityModel = model;
      } else {
         throw new IllegalArgumentException(SPIDeployerLogger.invalidSecurityModel(model, "DDOnly|CustomRoles|CustomRolesAndPolicies|Advanced"));
      }
   }

   public boolean isSecurityValidationEnabled() {
      return this.isSecurityValidationEnabled;
   }

   public void setSecurityValidationEnabled(boolean enable) {
      this.isSecurityValidationEnabled = enable;
   }

   public String getArchiveVersion() {
      return this.archiveVersion;
   }

   public void setArchiveVersion(String version) {
      this.archiveVersion = version;
   }

   public String getPlanVersion() {
      return this.planVersion;
   }

   public void setPlanVersion(String planVersion) {
      this.planVersion = planVersion;
   }

   public String getVersionIdentifier() {
      return BaseApplicationVersionUtils.getVersionId(this.archiveVersion, this.planVersion);
   }

   public void setVersionIdentifier(String version) {
      this.archiveVersion = BaseApplicationVersionUtils.getArchiveVersion(version);
      this.planVersion = BaseApplicationVersionUtils.getPlanVersion(version);
   }

   public boolean isLibrary() {
      return this.isLibrary;
   }

   public void setLibrary(boolean isLibrary) {
      this.isLibrary = isLibrary;
   }

   public String getLibSpecVersion() {
      return this.libSpecVersion;
   }

   public void setLibSpecVersion(String version) {
      this.libSpecVersion = version;
   }

   public String getLibImplVersion() {
      return this.libImplVersion;
   }

   public void setLibImplVersion(String version) {
      this.libImplVersion = version;
   }

   public boolean isNameFromLibrary() {
      return this.nameFromLibrary;
   }

   public void setNameFromLibrary(boolean nameFromLibrary) {
      this.nameFromLibrary = nameFromLibrary;
   }

   public boolean isNameFromSource() {
      return this.nameFromSource;
   }

   public void setNameFromSource(boolean nuNameFromSource) {
      this.nameFromSource = nuNameFromSource;
   }

   /** @deprecated */
   @Deprecated
   public boolean isTestMode() {
      return this.isAdminMode();
   }

   /** @deprecated */
   @Deprecated
   public void setTestMode(boolean b) {
      this.setAdminMode(b);
   }

   public boolean isAdminMode() {
      return this.testOption;
   }

   public void setAdminMode(boolean b) {
      this.testOption = b;
   }

   public int getRetireTime() {
      return this.retireTimeOption;
   }

   public void setRetireTime(int i) {
      this.retireTimeOption = i;
      if (this.retireTimeOption != -1) {
         this.setRetireGracefully(false);
      }

   }

   public boolean isRetireGracefully() {
      return this.retireGracefulPolicy;
   }

   public void setRetireGracefully(boolean b) {
      this.retireGracefulPolicy = b;
   }

   public boolean isGracefulProductionToAdmin() {
      return this.gracefulProductionToAdmin;
   }

   public void setGracefulProductionToAdmin(boolean b) {
      this.gracefulProductionToAdmin = b;
   }

   public boolean isGracefulIgnoreSessions() {
      return this.gracefulIgnoreSessions;
   }

   public void setGracefulIgnoreSessions(boolean b) {
      this.gracefulIgnoreSessions = b;
   }

   public int getRMIGracePeriodSecs() {
      return this.gracefulRMIGracePeriodSecs;
   }

   public void setRMIGracePeriodSecs(int secs) {
      this.gracefulRMIGracePeriodSecs = secs;
   }

   public boolean isUndeployAllVersions() {
      return this.undeployAllVersions;
   }

   public void setUndeployAllVersions(boolean b) {
      this.undeployAllVersions = b;
   }

   public String getStageMode() {
      return this.stageOption;
   }

   public void setStageMode(String s) {
      this.stageOption = s;
   }

   public String getPlanStageMode() {
      return this.planStageOption;
   }

   public void setPlanStageMode(String s) {
      this.planStageOption = s;
   }

   public int getClusterDeploymentTimeout() {
      return this.clusterTimeout;
   }

   public void setClusterDeploymentTimeout(int timeInMillis) {
      this.clusterTimeout = timeInMillis;
   }

   public long getForceUndeployTimeout() {
      return this.forceUndeployTimeoutSecs;
   }

   public void setForceUndeployTimeout(long timeInSecs) {
      this.forceUndeployTimeoutSecs = timeInSecs;
   }

   public boolean usesExpiredLock() {
      return this.useExpiredLock;
   }

   public void setUseExpiredLock(boolean useExpiredLock) {
      this.useExpiredLock = useExpiredLock;
   }

   public boolean getSpecifiedTargetsOnly() {
      return this.specifiedTargetsOnly;
   }

   public void setSpecifiedTargetsOnly(boolean specifiedTargetsOnly) {
      this.specifiedTargetsOnly = specifiedTargetsOnly;
   }

   public boolean isRemovePlanOverride() {
      return this.removePlanOverride;
   }

   public void setRemovePlanOverride(boolean removePlanOverride) {
      this.removePlanOverride = removePlanOverride;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("{").append("isRetireGracefully=").append(this.isRetireGracefully()).append(",").append("isGracefulProductionToAdmin=").append(this.isGracefulProductionToAdmin()).append(",").append("isGracefulIgnoreSessions=").append(this.isGracefulIgnoreSessions()).append(",").append("rmiGracePeriod=").append(this.getRMIGracePeriodSecs()).append(",").append("retireTimeoutSecs=").append(this.getRetireTime()).append(",").append("undeployAllVersions=").append(this.isUndeployAllVersions()).append(",").append("archiveVersion=").append(this.getArchiveVersion()).append(",").append("planVersion=").append(this.getPlanVersion()).append(",").append("isLibrary=").append(this.isLibrary()).append(",").append("libSpecVersion=").append(this.getLibSpecVersion()).append(",").append("libImplVersion=").append(this.getLibImplVersion()).append(",").append("stageMode=").append(this.getStageMode()).append(",").append("clusterTimeout=").append(this.getClusterDeploymentTimeout()).append(",").append("altDD=").append(this.getAltDD()).append(",").append("altWlsDD=").append(this.getAltWlsDD()).append(",").append("name=").append(this.getName()).append(",").append("securityModel=").append(this.getSecurityModel()).append(",").append("securityValidationEnabled=").append(this.isSecurityValidationEnabled()).append(",").append("versionIdentifier=").append(this.getVersionIdentifier()).append(",").append("isTestMode=").append(this.isTestMode()).append(",").append("forceUndeployTimeout=").append(this.getForceUndeployTimeout()).append(",").append("defaultSubmoduleTargets=").append(this.isDefaultSubmoduleTargets()).append(",").append("timeout=").append(this.getTimeout()).append(",").append("deploymentPrincipalName=").append(this.getDeploymentPrincipalName()).append(",").append("useExpiredLock=").append(this.usesExpiredLock()).append(",").append("specifiedTargetsOnly=").append(this.getSpecifiedTargetsOnly()).append(",").append("uploadPath=").append(this.getUploadPath()).append(",").append("specifiedModules=").append(Arrays.toString(this.getSpecifiedModules())).append(",").append("removePlanOverride=").append(this.isRemovePlanOverride()).append(",").append("}");
      return sb.toString();
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isDefaultSubmoduleTargets() {
      return this.defaultSubmoduleTargets;
   }

   public void setDefaultSubmoduleTargets(boolean defaultSubmoduleTargets) {
      this.defaultSubmoduleTargets = defaultSubmoduleTargets;
   }

   public boolean isNoVersion() {
      return this.noVersion;
   }

   public void setNoVersion(boolean ignoreVersion) {
      this.noVersion = ignoreVersion;
   }

   public long getTimeout() {
      return this.timeout;
   }

   public void setTimeout(long timeout) {
      this.timeout = timeout;
   }

   public int getDeploymentOrder() {
      return this.deploymentOrder;
   }

   public void setDeploymentOrder(int order) {
      this.deploymentOrder = order;
   }

   public boolean isRemote() {
      return this.remote;
   }

   public void setRemote(boolean setRemote) {
      this.remote = setRemote;
   }

   public boolean isDisableModuleLevelStartStop() {
      return this.disableModuleLevelStartStop;
   }

   public void setDisableModuleLevelStartStop(boolean disableModuleLevelStartStop) {
      this.disableModuleLevelStartStop = disableModuleLevelStartStop;
   }

   public void setDeploymentPrincipalName(String principal) {
      this.deploymentPrincipalName = principal;
   }

   public String getDeploymentPrincipalName() {
      return this.deploymentPrincipalName;
   }

   public void setPartition(String par) {
      this.partition = par;
   }

   public String getPartition() {
      return this.partition;
   }

   public void setResourceGroup(String rgp) {
      this.resourceGroup = rgp;
   }

   public String getResourceGroup() {
      return this.resourceGroup;
   }

   public void setResourceGroupTemplate(String rgt) {
      this.resourceGroupTemplate = rgt;
   }

   public String getResourceGroupTemplate() {
      return this.resourceGroupTemplate;
   }

   public void setUploadPath(String path) {
      this.uploadPath = path;
   }

   public String getUploadPath() {
      return this.uploadPath;
   }

   public void setOverwriteFile(boolean overwrite) {
      this.overwriteFile = overwrite;
   }

   public boolean getOverwriteFile() {
      return this.overwriteFile;
   }

   public boolean isRGOrRGTOperation() {
      return this.getResourceGroupTemplate() != null || this.getResourceGroup() != null || this.getPartition() != null;
   }

   public void setSpecifiedModules(String specifiedModules) {
      if (specifiedModules != null) {
         this.setSpecifiedModules(specifiedModules.split(","));
      }

   }

   public void setSpecifiedModules(String[] specifiedModules) {
      this.specifiedModules = specifiedModules;
   }

   public String[] getSpecifiedModules() {
      return this.specifiedModules;
   }

   public void setSucceedIfNameUsed(boolean val) {
      this.succeedIfNameUsed = val;
   }

   public boolean isSucceedIfNameUsed() {
      return this.succeedIfNameUsed;
   }

   public void setCacheInAppDirectory(boolean val) {
      this.cacheInAppDirectory = val;
   }

   public boolean isCacheInAppDirectory() {
      return this.cacheInAppDirectory;
   }

   public void setStartTask(boolean val) {
      this.startTask = val;
   }

   public boolean isStartTask() {
      return this.startTask;
   }

   public void setAppShutdownOnStop(boolean val) {
      this.appShutdownOnStop = val;
   }

   public boolean isAppShutdownOnStop() {
      return this.appShutdownOnStop;
   }

   public String getEditSessionName() {
      return this.editSessionName;
   }

   public void setEditSessionName(String editSessionName) {
      this.editSessionName = editSessionName;
   }

   public File getAltDDFile() {
      if (this.altDD != null && this.altDD.trim().length() != 0) {
         File f = new File(this.altDD);
         return f.exists() ? f : null;
      } else {
         return null;
      }
   }

   public boolean isTmidsFromConfig() {
      return this.tmidsFromConfig;
   }

   public void setTmidsFromConfig(boolean tmidsFromConfig) {
      this.tmidsFromConfig = tmidsFromConfig;
   }
}
