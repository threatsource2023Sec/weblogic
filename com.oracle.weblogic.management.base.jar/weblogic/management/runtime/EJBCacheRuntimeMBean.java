package weblogic.management.runtime;

public interface EJBCacheRuntimeMBean extends RuntimeMBean {
   int getCachedBeansCurrentCount();

   long getCacheAccessCount();

   /** @deprecated */
   @Deprecated
   long getCacheHitCount();

   long getCacheMissCount();

   long getActivationCount();

   long getPassivationCount();

   void reInitializeCacheAndPools();
}
