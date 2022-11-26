package com.bea.common.security.servicecfg;

public interface IdentityImpersonationServiceConfig {
   String getAuditServiceName();

   String getIdentityServiceName();

   String getIdentityCacheServiceName();

   String getJAASIdentityAssertionConfigurationServiceName();

   String getJAASLoginServiceName();
}
