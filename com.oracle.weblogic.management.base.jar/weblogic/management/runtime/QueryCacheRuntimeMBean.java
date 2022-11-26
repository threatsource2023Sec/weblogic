package weblogic.management.runtime;

public interface QueryCacheRuntimeMBean extends RuntimeMBean {
   int getTotalCachedQueriesCount();

   long getCacheAccessCount();

   long getCacheHitCount();

   long getTotalCacheMissCount();

   long getCacheMissByTimeoutCount();

   long getCacheMissByBeanEvictionCount();

   long getCacheMissByRelatedQueryMissCount();

   long getCacheMissByDependentQueryMissCount();
}
