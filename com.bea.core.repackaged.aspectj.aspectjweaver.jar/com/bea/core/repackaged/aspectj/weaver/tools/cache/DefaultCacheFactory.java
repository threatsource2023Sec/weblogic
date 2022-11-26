package com.bea.core.repackaged.aspectj.weaver.tools.cache;

public class DefaultCacheFactory implements CacheFactory {
   public CacheKeyResolver createResolver() {
      return new DefaultCacheKeyResolver();
   }

   public CacheBacking createBacking(String scope) {
      return DefaultFileCacheBacking.createBacking(scope);
   }
}
