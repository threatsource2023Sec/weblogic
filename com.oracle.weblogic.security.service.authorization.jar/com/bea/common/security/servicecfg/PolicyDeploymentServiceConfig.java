package com.bea.common.security.servicecfg;

public interface PolicyDeploymentServiceConfig {
   String getAuditServiceName();

   String[] getPolicyDeployerNames();

   String getIdentityServiceName();
}
