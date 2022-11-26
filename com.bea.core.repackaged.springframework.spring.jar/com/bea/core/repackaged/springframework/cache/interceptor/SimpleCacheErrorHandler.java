package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class SimpleCacheErrorHandler implements CacheErrorHandler {
   public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
      throw exception;
   }

   public void handleCachePutError(RuntimeException exception, Cache cache, Object key, @Nullable Object value) {
      throw exception;
   }

   public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
      throw exception;
   }

   public void handleCacheClearError(RuntimeException exception, Cache cache) {
      throw exception;
   }
}
