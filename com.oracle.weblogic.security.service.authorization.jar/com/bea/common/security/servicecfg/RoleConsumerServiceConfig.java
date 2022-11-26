package com.bea.common.security.servicecfg;

public interface RoleConsumerServiceConfig {
   String getAuditServiceName();

   String[] getRoleConsumerNames();

   String getIdentityServiceName();
}
