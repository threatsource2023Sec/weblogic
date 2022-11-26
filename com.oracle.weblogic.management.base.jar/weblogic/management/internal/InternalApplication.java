package weblogic.management.internal;

import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public interface InternalApplication {
   String getLocation();

   boolean isClustered();

   String getSourceFileName();

   String getName();

   String getSuffix();

   boolean isLib();

   boolean isBackground();

   String[] getOnDemandContextPaths();

   boolean isOnDemandDisplayRefresh();

   boolean isCritical();

   boolean isAdminOnly();

   boolean isMtEnabled();

   boolean isDeployPerVT();

   String getOptionalFeatureName();

   boolean isOptionalFeatureEnabled();

   String getVirtualHostName();

   DeploymentPlanBean getDeploymentPlanBean();
}
