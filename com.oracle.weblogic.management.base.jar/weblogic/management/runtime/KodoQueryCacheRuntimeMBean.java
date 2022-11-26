package weblogic.management.runtime;

public interface KodoQueryCacheRuntimeMBean extends RuntimeMBean {
   int getTotalCurrentEntries();

   int getCacheHitCount();

   int getCacheMissCount();

   double getCacheHitRatio();

   void clear();

   String getStatistics();
}
