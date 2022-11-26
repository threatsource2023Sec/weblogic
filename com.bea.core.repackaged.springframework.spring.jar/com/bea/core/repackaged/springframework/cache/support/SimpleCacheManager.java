package com.bea.core.repackaged.springframework.cache.support;

import java.util.Collection;
import java.util.Collections;

public class SimpleCacheManager extends AbstractCacheManager {
   private Collection caches = Collections.emptySet();

   public void setCaches(Collection caches) {
      this.caches = caches;
   }

   protected Collection loadCaches() {
      return this.caches;
   }
}
