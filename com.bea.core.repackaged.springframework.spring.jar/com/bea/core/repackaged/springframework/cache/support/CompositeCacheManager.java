package com.bea.core.repackaged.springframework.cache.support;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CompositeCacheManager implements CacheManager, InitializingBean {
   private final List cacheManagers = new ArrayList();
   private boolean fallbackToNoOpCache = false;

   public CompositeCacheManager() {
   }

   public CompositeCacheManager(CacheManager... cacheManagers) {
      this.setCacheManagers(Arrays.asList(cacheManagers));
   }

   public void setCacheManagers(Collection cacheManagers) {
      this.cacheManagers.addAll(cacheManagers);
   }

   public void setFallbackToNoOpCache(boolean fallbackToNoOpCache) {
      this.fallbackToNoOpCache = fallbackToNoOpCache;
   }

   public void afterPropertiesSet() {
      if (this.fallbackToNoOpCache) {
         this.cacheManagers.add(new NoOpCacheManager());
      }

   }

   @Nullable
   public Cache getCache(String name) {
      Iterator var2 = this.cacheManagers.iterator();

      Cache cache;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         CacheManager cacheManager = (CacheManager)var2.next();
         cache = cacheManager.getCache(name);
      } while(cache == null);

      return cache;
   }

   public Collection getCacheNames() {
      Set names = new LinkedHashSet();
      Iterator var2 = this.cacheManagers.iterator();

      while(var2.hasNext()) {
         CacheManager manager = (CacheManager)var2.next();
         names.addAll(manager.getCacheNames());
      }

      return Collections.unmodifiableSet(names);
   }
}
