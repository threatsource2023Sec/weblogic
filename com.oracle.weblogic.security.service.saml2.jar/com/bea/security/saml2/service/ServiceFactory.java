package com.bea.security.saml2.service;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.service.acs.AssertionConsumerServiceImpl;
import com.bea.security.saml2.service.ars.ArtifactResolutionServiceImpl;
import com.bea.security.saml2.service.spinitiator.SPInitiatorImpl;
import com.bea.security.saml2.service.sso.SingleSignOnServiceImpl;
import com.bea.security.saml2.util.cache.SAML2CacheException;
import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
   public static final String SAML2_SP_INITIATOR_SERVICE = "SPinitiator";
   public static final String SAML2_SSO_SERVICE = "SSO";
   public static final String SAML2_ACS_SERVICE = "ACS";
   public static final String SAML2_ARS_SERVICE = "ARS";
   private final Map services = new HashMap();

   private ServiceFactory(SAML2ConfigSpi config) {
      this.initialize(config);
   }

   public void initialize(SAML2ConfigSpi config) {
      this.services.clear();
      SPInitiatorImpl spInitiator = new SPInitiatorImpl(config);
      this.services.put("SPinitiator", spInitiator);
      AssertionConsumerServiceImpl assertionConsumerService = new AssertionConsumerServiceImpl(config);
      assertionConsumerService.setAuthnReqCache(spInitiator.getAuthnReqCache());
      this.services.put("ACS", assertionConsumerService);
      this.services.put("ARS", new ArtifactResolutionServiceImpl(config));
      this.services.put("SSO", new SingleSignOnServiceImpl(config));
   }

   public void updateConfig(SAML2ConfigSpi config) {
      SPInitiatorImpl spInit;
      if (config == null) {
         spInit = (SPInitiatorImpl)this.services.get("SPinitiator");
         spInit.removePartnerCache();
         this.services.clear();
      }

      spInit = (SPInitiatorImpl)this.services.get("SPinitiator");
      if (spInit != null) {
         try {
            spInit.getAuthnReqCache().configUpdated(config.getLocalConfiguration().getAuthnRequestMaxCacheSize(), config.getLocalConfiguration().getAuthnRequestTimeout());
         } catch (SAML2CacheException var5) {
            LoggerSpi logger = config.getLogger();
            if (logger != null && logger.isDebugEnabled()) {
               logger.debug("authn req cache update failed: ", var5);
            }
         }
      }

      this.initialize(config);
   }

   public static ServiceFactory newServiceFactory(SAML2ConfigSpi config) {
      return new ServiceFactory(config);
   }

   public Service getService(String type) {
      return (Service)this.services.get(type);
   }
}
