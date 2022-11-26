package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;

public class SimpleCacheResolver extends AbstractCacheResolver {
   public SimpleCacheResolver() {
   }

   public SimpleCacheResolver(CacheManager cacheManager) {
      super(cacheManager);
   }

   protected Collection getCacheNames(CacheOperationInvocationContext context) {
      return context.getOperation().getCacheNames();
   }

   @Nullable
   static SimpleCacheResolver of(@Nullable CacheManager cacheManager) {
      return cacheManager != null ? new SimpleCacheResolver(cacheManager) : null;
   }
}
