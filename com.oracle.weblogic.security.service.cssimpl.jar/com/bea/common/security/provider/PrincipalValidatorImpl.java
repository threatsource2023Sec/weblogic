package com.bea.common.security.provider;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.utils.HMAC;
import java.security.Principal;
import weblogic.management.security.RealmMBean;
import weblogic.security.principal.PrincipalCacheKey;
import weblogic.security.principal.PrincipalConfigurationDelegate;
import weblogic.security.principal.WLSAbstractPrincipal;
import weblogic.security.principal.WLSPrincipal;
import weblogic.security.service.SecurityManager;
import weblogic.security.spi.PrincipalValidator;
import weblogic.utils.collections.SecondChanceCacheMap;

public class PrincipalValidatorImpl implements PrincipalValidator {
   private byte[] secret;
   private LoggerSpi logger;
   private SecondChanceCacheMap sigCache;
   private PrincipalConfigurationDelegate delegate;

   public PrincipalValidatorImpl(LoggerSpi logger, byte[] secret) {
      this(logger, secret, true, 500);
   }

   public PrincipalValidatorImpl(LoggerSpi logger, byte[] secret, RealmMBean realmMBean) {
      this(logger, secret, realmMBean.isEnableWebLogicPrincipalValidatorCache(), realmMBean.getMaxWebLogicPrincipalsInCache());
   }

   public PrincipalValidatorImpl(LoggerSpi logger, byte[] secret, boolean cacheEnabled, int sigCacheSize) {
      this.logger = null;
      this.sigCache = null;
      this.delegate = null;
      this.logger = logger;
      this.secret = secret;
      SecurityManager.checkKernelPermission();
      if (cacheEnabled) {
         if (sigCacheSize <= 0) {
            throw new IllegalArgumentException("sigCacheSize=" + sigCacheSize);
         }

         this.sigCache = new SecondChanceCacheMap(sigCacheSize);
      }

      if (logger.isDebugEnabled()) {
         logger.debug("Principal validator cache enabled: " + cacheEnabled);
         if (cacheEnabled) {
            logger.debug("Principal validator cache size: " + sigCacheSize);
         }
      }

      this.delegate = PrincipalConfigurationDelegate.getInstance();
   }

   public boolean validate(Principal principal) throws SecurityException {
      if (!(principal instanceof WLSPrincipal)) {
         return false;
      } else {
         this.checkInteropAndThirdPartyPrincipal(principal);
         WLSPrincipal wlsPrincipal = (WLSPrincipal)principal;
         byte[] signature = wlsPrincipal.getSignature();
         if (signature == null) {
            return false;
         } else {
            byte[] signedData = wlsPrincipal.getSignedData();
            byte[] salt = wlsPrincipal.getSalt();
            boolean valid = HMAC.verify(signature, signedData, this.secret, salt);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Validate WLS principal " + wlsPrincipal.getName() + " returns " + valid);
            }

            return valid;
         }
      }
   }

   private void checkInteropAndThirdPartyPrincipal(Principal principal) {
      boolean equalsCaseInsensitive = false;
      boolean equalsCompareDnAndGuid = false;
      equalsCaseInsensitive = this.delegate.isEqualsCaseInsensitive();
      equalsCompareDnAndGuid = this.delegate.isEqualsCompareDnAndGuid();
      if (principal instanceof WLSAbstractPrincipal) {
         WLSAbstractPrincipal wlsPrincipal = (WLSAbstractPrincipal)principal;
         if (!wlsPrincipal.isPrincipalFactoryCreated()) {
            wlsPrincipal.setEqualsCaseInsensitive(equalsCaseInsensitive);
            wlsPrincipal.setEqualsCompareDnAndGuid(equalsCompareDnAndGuid);
         }
      }

   }

   public boolean sign(Principal principal) {
      if (!(principal instanceof WLSPrincipal)) {
         return false;
      } else {
         this.checkInteropAndThirdPartyPrincipal(principal);
         WLSPrincipal wlsPrincipal = (WLSPrincipal)principal;
         String wlsPrincipalName = wlsPrincipal.getName();
         boolean equalsCaseInsensitive = false;
         Class var5 = PrincipalConfigurationDelegate.class;
         synchronized(PrincipalConfigurationDelegate.class) {
            equalsCaseInsensitive = this.delegate.isEqualsCaseInsensitive();
         }

         boolean useCache = this.sigCache != null && principal instanceof WLSAbstractPrincipal;
         PrincipalCacheKey key = null;
         if (useCache) {
            key = new PrincipalCacheKey((WLSAbstractPrincipal)principal, equalsCaseInsensitive);
            SigCacheEntry sigEntry = (SigCacheEntry)this.sigCache.get(key);
            if (sigEntry != null) {
               ((WLSAbstractPrincipal)wlsPrincipal).setSalt(sigEntry.salt);
               wlsPrincipal.setSignature(sigEntry.sig);
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Signed WLS principal " + wlsPrincipalName);
               }

               return true;
            }
         }

         byte[] salt = wlsPrincipal.getSalt();
         byte[] sig = HMAC.digest(wlsPrincipal.getSignedData(), this.secret, salt);
         wlsPrincipal.setSignature(sig);
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Generated signature and signed WLS principal " + wlsPrincipalName);
         }

         if (useCache) {
            if (null == key) {
               throw new IllegalStateException("Null principal cache key.");
            }

            this.sigCache.put(key, new SigCacheEntry(sig, salt));
         }

         return true;
      }
   }

   public Class getPrincipalBaseClass() {
      return WLSPrincipal.class;
   }

   private final class SigCacheEntry {
      byte[] sig;
      byte[] salt;

      SigCacheEntry(byte[] sig, byte[] salt) {
         this.sig = sig;
         this.salt = salt;
      }
   }
}
