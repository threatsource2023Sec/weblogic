package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface CacheErrorHandler {
   void handleCacheGetError(RuntimeException var1, Cache var2, Object var3);

   void handleCachePutError(RuntimeException var1, Cache var2, Object var3, @Nullable Object var4);

   void handleCacheEvictError(RuntimeException var1, Cache var2, Object var3);

   void handleCacheClearError(RuntimeException var1, Cache var2);
}
