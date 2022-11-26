package com.bea.core.repackaged.springframework.cache.annotation;

import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheErrorHandler;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheResolver;
import com.bea.core.repackaged.springframework.cache.interceptor.KeyGenerator;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class CachingConfigurerSupport implements CachingConfigurer {
   @Nullable
   public CacheManager cacheManager() {
      return null;
   }

   @Nullable
   public CacheResolver cacheResolver() {
      return null;
   }

   @Nullable
   public KeyGenerator keyGenerator() {
      return null;
   }

   @Nullable
   public CacheErrorHandler errorHandler() {
      return null;
   }
}
