package com.bea.common.security.servicecfg;

public interface IdentityCacheServiceConfig {
   boolean isIdentityCacheEnabled();

   int getMaxIdentitiesInCache();

   long getIdentityCacheTTL();

   String[] getIdentityAssertionDoNotCacheContextElements();
}
