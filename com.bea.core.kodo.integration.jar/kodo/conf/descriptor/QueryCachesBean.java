package kodo.conf.descriptor;

public interface QueryCachesBean {
   DefaultQueryCacheBean getDefaultQueryCache();

   DefaultQueryCacheBean createDefaultQueryCache();

   void destroyDefaultQueryCache();

   KodoConcurrentQueryCacheBean getKodoConcurrentQueryCache();

   KodoConcurrentQueryCacheBean createKodoConcurrentQueryCache();

   void destroyKodoConcurrentQueryCache();

   GemFireQueryCacheBean getGemFireQueryCache();

   GemFireQueryCacheBean createGemFireQueryCache();

   void destroyGemFireQueryCache();

   LRUQueryCacheBean getLRUQueryCache();

   LRUQueryCacheBean createLRUQueryCache();

   void destroyLRUQueryCache();

   TangosolQueryCacheBean getTangosolQueryCache();

   TangosolQueryCacheBean createTangosolQueryCache();

   void destroyTangosolQueryCache();

   DisabledQueryCacheBean getDisabledQueryCache();

   DisabledQueryCacheBean createDisabledQueryCache();

   void destroyDisabledQueryCache();

   CustomQueryCacheBean getCustomQueryCache();

   CustomQueryCacheBean createCustomQueryCache();

   void destroyCustomQueryCache();

   Class[] getQueryCacheTypes();

   QueryCacheBean getQueryCache();

   QueryCacheBean createQueryCache(Class var1);

   void destroyQueryCache();
}
