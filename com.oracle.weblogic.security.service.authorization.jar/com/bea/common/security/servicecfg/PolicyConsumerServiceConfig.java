package com.bea.common.security.servicecfg;

public interface PolicyConsumerServiceConfig {
   String getAuditServiceName();

   String[] getPolicyConsumerNames();

   String getIdentityServiceName();
}
