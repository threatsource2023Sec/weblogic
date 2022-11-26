package com.bea.core.repackaged.springframework.cache.annotation;

import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheErrorHandler;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheResolver;
import com.bea.core.repackaged.springframework.cache.interceptor.KeyGenerator;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface CachingConfigurer {
   @Nullable
   CacheManager cacheManager();

   @Nullable
   CacheResolver cacheResolver();

   @Nullable
   KeyGenerator keyGenerator();

   @Nullable
   CacheErrorHandler errorHandler();
}
