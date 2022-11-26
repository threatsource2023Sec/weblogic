package com.bea.cache.jcache.util;

import com.bea.cache.jcache.CacheStatistics;
import weblogic.cache.MapStatistics;

public class CacheStatisticsAdapter implements CacheStatistics {
   private final MapStatistics delegate;

   CacheStatisticsAdapter(MapStatistics delegate) {
      this.delegate = delegate;
   }

   public int getCacheHits() {
      return (int)this.delegate.getReadCount();
   }

   public int getCacheMisses() {
      return (int)(this.delegate.getQueryCount() - this.delegate.getReadCount());
   }

   public void clearStatistics() {
      this.delegate.reset();
   }
}
