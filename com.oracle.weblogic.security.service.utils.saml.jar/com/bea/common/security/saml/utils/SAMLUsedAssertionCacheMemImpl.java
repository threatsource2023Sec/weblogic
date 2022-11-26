package com.bea.common.security.saml.utils;

import com.bea.common.logger.spi.LoggerSpi;
import java.util.Properties;
import weblogic.security.providers.saml.SAMLUsedAssertionCache;

public class SAMLUsedAssertionCacheMemImpl extends SAMLAbstractMemCache implements SAMLUsedAssertionCache {
   private static final String NAME = "UsedAssertionCache";
   private static final long EXPIRE_PERIOD = 15000L;

   public SAMLUsedAssertionCacheMemImpl() {
      super((LoggerSpi)null, "UsedAssertionCache", 15000L);
   }

   public boolean initCache(Properties props) {
      return this.init();
   }

   public void releaseCache() {
      this.release();
   }

   public void flushCache() {
      this.flush();
   }

   public boolean cacheAssertion(String assertionId, String issuer, long expire) {
      return this.addEntry(assertionId + "@" + issuer, expire, assertionId);
   }
}
