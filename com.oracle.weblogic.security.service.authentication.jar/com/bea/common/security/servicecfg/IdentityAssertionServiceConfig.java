package com.bea.common.security.servicecfg;

public interface IdentityAssertionServiceConfig {
   String getAuditServiceName();

   String getIdentityAssertionTokenServiceName();

   String getIdentityAssertionCallbackServiceName();
}
