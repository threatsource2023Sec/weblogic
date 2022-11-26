package com.bea.security.saml2.util.cache;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.store.service.StoreService;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.config.SAML2ConfigSpi;

public class SAML2CacheFactory {
   static final String ARTIFACT_STORE_CACHE = "artifact_store";
   static final String AUTH_REQ_CACHE = "authn_request";
   static final String USED_ASSERTION_CACHE = "used_assertion";
   static final String CREDENTIAL_CACHE = "saml2_credential";
   static final int DEFAULT_TIMEOUT = 300;
   static final int DEFAULT_MAX_CACHE_SIZE = 0;

   public static SAML2Cache createArtifactStoreCache(SAML2ConfigSpi config) throws IllegalArgumentException {
      if (config == null) {
         throw new IllegalArgumentException(Saml2Logger.getParameterMustNotBeNull("SAML2CacheFactory.createArtifactStoreCache", "config"));
      } else {
         return createCache(config.getLogger(), config.getStoreService(), config.getDomainName(), config.getRealmName(), config.getLocalConfiguration().getArtifactMaxCacheSize(), config.getLocalConfiguration().getArtifactTimeout(), config.getLocalConfiguration().isReplicatedCacheEnabled(), "artifact_store");
      }
   }

   public static SAML2Cache createAuthRequestCache(SAML2ConfigSpi config) throws IllegalArgumentException {
      if (config == null) {
         throw new IllegalArgumentException(Saml2Logger.getParameterMustNotBeNull("SAML2CacheFactory.createAuthRequestCache", "config"));
      } else {
         return createCache(config.getLogger(), config.getStoreService(), config.getDomainName(), config.getRealmName(), config.getLocalConfiguration().getAuthnRequestMaxCacheSize(), config.getLocalConfiguration().getAuthnRequestTimeout(), config.getLocalConfiguration().isReplicatedCacheEnabled(), "authn_request");
      }
   }

   public static SAML2Cache createUsedAssertionCache(LoggerSpi log, StoreService store, String domainName, String realmName, boolean replicated) {
      return createCache(log, store, domainName, realmName, 0, 300, replicated, "used_assertion");
   }

   public static SAML2Cache createCredentialCache(LoggerSpi log, int maxCacheSize) {
      return new SAML2InMemoryCacheImpl("saml2_credential", log, maxCacheSize, 300);
   }

   static SAML2Cache createCache(LoggerSpi log, StoreService store, String domainName, String realmName, int maxSize, int timeout, boolean replicated, String cacheName) {
      return (SAML2Cache)(!replicated ? new SAML2InMemoryCacheImpl(cacheName, log, maxSize, timeout) : new SAML2StoreServiceBasedCacheImpl(log, store, domainName, realmName, cacheName, maxSize, timeout));
   }
}
