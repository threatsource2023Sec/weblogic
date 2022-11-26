package com.bea.common.security.servicecfg;

import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;

public interface SAML2ServiceConfig {
   String getAuditServiceName();

   String getIdentityServiceName();

   String getSessionServiceName();

   String getCredMappingServiceName();

   String getSAML2CredMapperProviderServiceName();

   String getIdentityAssertionServiceName();

   String getSAML2IdentityAsserterProviderServiceName();

   String getSAMLKeyServiceName();

   String getStoreServiceName();

   SingleSignOnServicesConfigSpi getLocalConfig();

   String getDomainName();

   String getRealmName();

   LegacyEncryptorSpi getLegacyEncryptor();
}
