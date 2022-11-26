package weblogic.management.configuration;

import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.ManagementException;

public interface AppDeploymentMBean extends BasicDeploymentMBean {
   String DEFAULT_STAGE = StageConstants.DEFAULT_STAGE;
   String NO_STAGE = "nostage";
   String STAGE = "stage";
   String EXTERNAL_STAGE = "external_stage";

   String getName();

   String getInstallDir();

   String getSourcePath();

   String getPlanDir();

   String getPlanPath();

   String getVersionIdentifier();

   boolean isValidateDDSecurityData();

   void setValidateDDSecurityData(boolean var1);

   String getSecurityDDModel();

   String getStagingMode();

   String getPlanStagingMode();

   String createPlan(String var1);

   String createPlan();

   void setInstallDir(String var1);

   void setSourcePath(String var1);

   void setPlanDir(String var1);

   void setPlanPath(String var1);

   void setStagingMode(String var1) throws ManagementException;

   void setPlanStagingMode(String var1) throws ManagementException;

   String getAltDescriptorPath();

   void setAltDescriptorPath(String var1);

   String getAltDescriptorDir();

   String getAltWLSDescriptorPath();

   void setAltWLSDescriptorPath(String var1);

   void setSecurityDDModel(String var1);

   String getApplicationIdentifier();

   String getApplicationName();

   ApplicationMBean getAppMBean();

   boolean isInternalApp();

   void setInternalApp(boolean var1);

   boolean isBackgroundDeployment();

   void setBackgroundDeployment(boolean var1);

   boolean isParallelDeployModules();

   void setParallelDeployModules(boolean var1);

   boolean isAutoDeployedApp();

   DeploymentPlanBean getDeploymentPlanDescriptor();

   void setDeploymentPlanDescriptor(DeploymentPlanBean var1);

   String[] getOnDemandContextPaths();

   void setOnDemandContextPaths(String[] var1);

   boolean isOnDemandDisplayRefresh();

   void setOnDemandDisplayRefresh(boolean var1);

   String getAbsoluteInstallDir();

   String getAbsolutePlanPath();

   String getAbsolutePlanDir();

   String getAbsoluteAltDescriptorPath();

   String getAbsoluteAltDescriptorDir();

   String getAbsoluteSourcePath();

   String getLocalInstallDir();

   String getLocalPlanPath();

   String getLocalPlanDir();

   String getLocalAltDescriptorPath();

   String getLocalSourcePath();

   String getRootStagingDir();

   String getStagingMode(String var1);

   byte[] getDeploymentPlan();

   byte[] getDeploymentPlanExternalDescriptors();

   void setCacheInAppDirectory(boolean var1);

   boolean isCacheInAppDirectory();

   boolean isUntargeted();

   void setUntargeted(boolean var1);

   String getConfiguredApplicationIdentifier();

   void setConfiguredApplicationIdentifier(String var1);

   boolean isMultiVersionApp();

   void setMultiVersionApp(boolean var1);
}
