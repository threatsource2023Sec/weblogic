package com.bea.security.saml2.config;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.IdentityAssertionService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.LoginSessionService;
import com.bea.common.security.service.SAMLKeyService;
import com.bea.common.store.service.StoreService;
import com.bea.security.saml2.artifact.ArtifactResolver;
import com.bea.security.saml2.artifact.ArtifactStore;
import com.bea.security.saml2.binding.BindingHandlerFactory;
import com.bea.security.saml2.registry.PartnerManager;
import com.bea.security.saml2.service.ServiceFactory;
import com.bea.security.saml2.util.key.SAML2KeyManager;

public interface SAML2ConfigSpi {
   String getRealmName();

   String getDomainName();

   LoggerSpi getLogger();

   CredentialMappingService getCredentialMappingService();

   Object getSAML2CredentialMapperMBean();

   IdentityAssertionService getIdentityAssertionService();

   Object getSAML2IdentityAsserterMBean();

   LoginSessionService getSessionService();

   IdentityService getIdentityService();

   StoreService getStoreService();

   AuditService getAuditService();

   SAMLKeyService getSAMLKeyService();

   SingleSignOnServicesConfigSpi getLocalConfiguration();

   ArtifactStore getArtifactStore();

   ArtifactResolver getArtifactResolver();

   PartnerManager getPartnerManager();

   ServiceFactory getServiceFactory();

   BindingHandlerFactory getBindingHandlerFactory();

   LegacyEncryptorSpi getEncryptSpi();

   SAML2KeyManager getSAML2KeyManager();
}
