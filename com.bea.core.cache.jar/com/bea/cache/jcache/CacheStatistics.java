package com.bea.cache.jcache;

public interface CacheStatistics {
   int getCacheHits();

   int getCacheMisses();

   void clearStatistics();
}
