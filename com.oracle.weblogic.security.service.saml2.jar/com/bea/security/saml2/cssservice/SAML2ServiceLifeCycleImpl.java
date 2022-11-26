package com.bea.security.saml2.cssservice;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.IdentityAssertionService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.LoginSessionService;
import com.bea.common.security.service.SAML2Service;
import com.bea.common.security.service.SAMLKeyService;
import com.bea.common.security.servicecfg.SAML2ServiceConfig;
import com.bea.common.store.service.StoreService;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.config.impl.SAML2ConfigSpiImpl;
import com.bea.security.saml2.util.key.KeyManagerException;
import com.bea.security.saml2.util.key.SAML2KeyManager;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;

public class SAML2ServiceLifeCycleImpl implements ServiceLifecycleSpi {
   private LoggerSpi log;
   private SAML2ConfigSpiImpl saml2config = null;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      Thread t = Thread.currentThread();
      ClassLoader cl = t.getContextClassLoader();

      SAML2ServiceImpl var20;
      try {
         t.setContextClassLoader(SAML2ServiceLifeCycleImpl.class.getClassLoader());
         LoggerService loggerService = (LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME);
         this.log = loggerService.getLogger(SAML2Service.class.getName());
         if (this.log.isDebugEnabled()) {
            this.log.debug("initializing SAML 2 service.");
         }

         SAML2ServiceConfig svcConfig = (SAML2ServiceConfig)config;
         AuditService auditService = (AuditService)dependentServices.getService(svcConfig.getAuditServiceName());
         IdentityService identityService = (IdentityService)dependentServices.getService(svcConfig.getIdentityServiceName());
         LoginSessionService sessionService = (LoginSessionService)dependentServices.getService(svcConfig.getSessionServiceName());
         CredentialMappingService cmService = null;
         if (svcConfig.getLocalConfig().isIdentityProviderEnabled()) {
            cmService = (CredentialMappingService)dependentServices.getService(svcConfig.getCredMappingServiceName());
         }

         IdentityAssertionService iaService = null;
         if (svcConfig.getLocalConfig().isServiceProviderEnabled()) {
            iaService = (IdentityAssertionService)dependentServices.getService(svcConfig.getIdentityAssertionServiceName());
         }

         StoreService storeService = (StoreService)dependentServices.getService(svcConfig.getStoreServiceName());
         SAMLKeyService keyService = (SAMLKeyService)dependentServices.getService(svcConfig.getSAMLKeyServiceName());

         try {
            InitializationService.initialize();
         } catch (InitializationException var28) {
            Saml2Logger.logCanNotInitialization(this.log, var28.getMessage());
            if (this.log.isDebugEnabled()) {
               this.log.debug("Error initializing OpenSAML", var28);
            }

            throw new ServiceInitializationException(var28);
         }

         String iaProviderName = svcConfig.getSAML2IdentityAsserterProviderServiceName();
         Object iaProvider = null;
         if (iaProviderName != null) {
            iaProvider = dependentServices.getServiceManagementObject(iaProviderName);
         }

         String cmProviderName = svcConfig.getSAML2CredMapperProviderServiceName();
         Object cmProvider = null;
         if (cmProviderName != null) {
            cmProvider = dependentServices.getServiceManagementObject(cmProviderName);
         }

         this.saml2config = new SAML2ConfigSpiImpl(this.log, cmService, cmProvider, iaService, iaProvider, sessionService, storeService, auditService, identityService, svcConfig.getLocalConfig(), keyService);
         this.saml2config.setRealmName(svcConfig.getRealmName());
         this.saml2config.setDomainName(svcConfig.getDomainName());
         this.saml2config.setEncryptSpi(svcConfig.getLegacyEncryptor());
         SAML2KeyManager keyManager = null;

         try {
            keyManager = new SAML2KeyManager(this.saml2config);
         } catch (KeyManagerException var27) {
            Saml2Logger.logCanNotInitialization(this.log, var27.getMessage());
            if (this.log.isDebugEnabled()) {
               this.log.debug("Key manager initialize error", var27);
            }

            throw new ServiceInitializationException(var27);
         }

         this.saml2config.setSAML2KeyManager(keyManager);
         SAML2Service saml2Service = null;

         try {
            saml2Service = new SAML2ServiceImpl(this.saml2config);
         } catch (Exception var26) {
            Saml2Logger.logCanNotInitialization(this.log, var26.getMessage());
            if (this.log.isDebugEnabled()) {
               this.log.debug("SAML2Service initialize error", var26);
            }

            throw new ServiceInitializationException(var26);
         }

         var20 = saml2Service;
      } finally {
         t.setContextClassLoader(cl);
      }

      return var20;
   }

   public void shutdown() {
      if (this.log.isDebugEnabled()) {
         this.log.debug("SAML2 service shutdown.");
      }

      if (this.saml2config != null) {
         this.saml2config.close();
      }

   }
}
