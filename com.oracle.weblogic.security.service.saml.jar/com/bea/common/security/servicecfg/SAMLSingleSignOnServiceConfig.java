package com.bea.common.security.servicecfg;

import com.bea.common.security.legacy.spi.SAMLSingleSignOnServiceConfigInfoSpi;

public interface SAMLSingleSignOnServiceConfig {
   String getAuditServiceName();

   String getIdentityServiceName();

   String getSessionServiceName();

   String getCredMappingServiceName();

   String getIdentityAssertionServiceName();

   String getSAMLKeyServiceName();

   SAMLSingleSignOnServiceConfigInfoSpi getSAMLSingleSignOnServiceConfigInfo();
}
