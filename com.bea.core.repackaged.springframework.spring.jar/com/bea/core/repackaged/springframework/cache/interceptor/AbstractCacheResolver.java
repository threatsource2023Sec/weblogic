package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public abstract class AbstractCacheResolver implements CacheResolver, InitializingBean {
   @Nullable
   private CacheManager cacheManager;

   protected AbstractCacheResolver() {
   }

   protected AbstractCacheResolver(CacheManager cacheManager) {
      this.cacheManager = cacheManager;
   }

   public void setCacheManager(CacheManager cacheManager) {
      this.cacheManager = cacheManager;
   }

   public CacheManager getCacheManager() {
      Assert.state(this.cacheManager != null, "No CacheManager set");
      return this.cacheManager;
   }

   public void afterPropertiesSet() {
      Assert.notNull(this.cacheManager, (String)"CacheManager is required");
   }

   public Collection resolveCaches(CacheOperationInvocationContext context) {
      Collection cacheNames = this.getCacheNames(context);
      if (cacheNames == null) {
         return Collections.emptyList();
      } else {
         Collection result = new ArrayList(cacheNames.size());
         Iterator var4 = cacheNames.iterator();

         while(var4.hasNext()) {
            String cacheName = (String)var4.next();
            Cache cache = this.getCacheManager().getCache(cacheName);
            if (cache == null) {
               throw new IllegalArgumentException("Cannot find cache named '" + cacheName + "' for " + context.getOperation());
            }

            result.add(cache);
         }

         return result;
      }
   }

   @Nullable
   protected abstract Collection getCacheNames(CacheOperationInvocationContext var1);
}
