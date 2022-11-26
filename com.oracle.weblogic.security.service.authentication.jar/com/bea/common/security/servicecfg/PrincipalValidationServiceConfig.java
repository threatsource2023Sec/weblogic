package com.bea.common.security.servicecfg;

public interface PrincipalValidationServiceConfig {
   String getAuditServiceName();

   String[] getPrincipalValidationProviderNames();
}
