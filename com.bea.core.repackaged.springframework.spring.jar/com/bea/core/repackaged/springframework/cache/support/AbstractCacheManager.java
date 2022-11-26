package com.bea.core.repackaged.springframework.cache.support;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractCacheManager implements CacheManager, InitializingBean {
   private final ConcurrentMap cacheMap = new ConcurrentHashMap(16);
   private volatile Set cacheNames = Collections.emptySet();

   public void afterPropertiesSet() {
      this.initializeCaches();
   }

   public void initializeCaches() {
      Collection caches = this.loadCaches();
      synchronized(this.cacheMap) {
         this.cacheNames = Collections.emptySet();
         this.cacheMap.clear();
         Set cacheNames = new LinkedHashSet(caches.size());
         Iterator var4 = caches.iterator();

         while(var4.hasNext()) {
            Cache cache = (Cache)var4.next();
            String name = cache.getName();
            this.cacheMap.put(name, this.decorateCache(cache));
            cacheNames.add(name);
         }

         this.cacheNames = Collections.unmodifiableSet(cacheNames);
      }
   }

   protected abstract Collection loadCaches();

   @Nullable
   public Cache getCache(String name) {
      Cache cache = (Cache)this.cacheMap.get(name);
      if (cache != null) {
         return cache;
      } else {
         Cache missingCache = this.getMissingCache(name);
         if (missingCache != null) {
            synchronized(this.cacheMap) {
               cache = (Cache)this.cacheMap.get(name);
               if (cache == null) {
                  cache = this.decorateCache(missingCache);
                  this.cacheMap.put(name, cache);
                  this.updateCacheNames(name);
               }
            }
         }

         return cache;
      }
   }

   public Collection getCacheNames() {
      return this.cacheNames;
   }

   @Nullable
   protected final Cache lookupCache(String name) {
      return (Cache)this.cacheMap.get(name);
   }

   /** @deprecated */
   @Deprecated
   protected final void addCache(Cache cache) {
      String name = cache.getName();
      synchronized(this.cacheMap) {
         if (this.cacheMap.put(name, this.decorateCache(cache)) == null) {
            this.updateCacheNames(name);
         }

      }
   }

   private void updateCacheNames(String name) {
      Set cacheNames = new LinkedHashSet(this.cacheNames.size() + 1);
      cacheNames.addAll(this.cacheNames);
      cacheNames.add(name);
      this.cacheNames = Collections.unmodifiableSet(cacheNames);
   }

   protected Cache decorateCache(Cache cache) {
      return cache;
   }

   @Nullable
   protected Cache getMissingCache(String name) {
      return null;
   }
}
