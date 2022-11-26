package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.function.SingletonSupplier;

public abstract class AbstractCacheInvoker {
   protected SingletonSupplier errorHandler;

   protected AbstractCacheInvoker() {
      this.errorHandler = SingletonSupplier.of(SimpleCacheErrorHandler::new);
   }

   protected AbstractCacheInvoker(CacheErrorHandler errorHandler) {
      this.errorHandler = SingletonSupplier.of((Object)errorHandler);
   }

   public void setErrorHandler(CacheErrorHandler errorHandler) {
      this.errorHandler = SingletonSupplier.of((Object)errorHandler);
   }

   public CacheErrorHandler getErrorHandler() {
      return (CacheErrorHandler)this.errorHandler.obtain();
   }

   @Nullable
   protected Cache.ValueWrapper doGet(Cache cache, Object key) {
      try {
         return cache.get(key);
      } catch (RuntimeException var4) {
         this.getErrorHandler().handleCacheGetError(var4, cache, key);
         return null;
      }
   }

   protected void doPut(Cache cache, Object key, @Nullable Object result) {
      try {
         cache.put(key, result);
      } catch (RuntimeException var5) {
         this.getErrorHandler().handleCachePutError(var5, cache, key, result);
      }

   }

   protected void doEvict(Cache cache, Object key) {
      try {
         cache.evict(key);
      } catch (RuntimeException var4) {
         this.getErrorHandler().handleCacheEvictError(var4, cache, key);
      }

   }

   protected void doClear(Cache cache) {
      try {
         cache.clear();
      } catch (RuntimeException var3) {
         this.getErrorHandler().handleCacheClearError(var3, cache);
      }

   }
}
