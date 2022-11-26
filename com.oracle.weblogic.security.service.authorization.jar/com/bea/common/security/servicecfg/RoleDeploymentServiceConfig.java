package com.bea.common.security.servicecfg;

public interface RoleDeploymentServiceConfig {
   String getAuditServiceName();

   String[] getRoleDeployerNames();

   String getIdentityServiceName();
}
