package com.bea.core.repackaged.springframework.cache.support;

import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NoOpCacheManager implements CacheManager {
   private final ConcurrentMap caches = new ConcurrentHashMap(16);
   private final Set cacheNames = new LinkedHashSet(16);

   @Nullable
   public Cache getCache(String name) {
      Cache cache = (Cache)this.caches.get(name);
      if (cache == null) {
         this.caches.computeIfAbsent(name, (key) -> {
            return new NoOpCache(name);
         });
         synchronized(this.cacheNames) {
            this.cacheNames.add(name);
         }
      }

      return (Cache)this.caches.get(name);
   }

   public Collection getCacheNames() {
      synchronized(this.cacheNames) {
         return Collections.unmodifiableSet(this.cacheNames);
      }
   }
}
