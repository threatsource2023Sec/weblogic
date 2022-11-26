package weblogic.management.runtime;

public interface KodoDataCacheRuntimeMBean extends RuntimeMBean {
   int getTotalCurrentEntries();

   int getCacheHitCount();

   int getCacheMissCount();

   double getCacheHitRatio();

   void clear();

   String getStatistics();
}
