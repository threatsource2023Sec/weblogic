package com.bea.common.security.servicecfg;

public interface IdentityAssertionCallbackServiceConfig {
   String getAuditServiceName();

   String getIdentityServiceName();

   String getIdentityCacheServiceName();

   String getJAASIdentityAssertionConfigurationServiceName();

   String getJAASLoginServiceName();
}
