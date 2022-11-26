package kodo.conf.descriptor;

public interface DataCachesBean {
   DefaultDataCacheBean[] getDefaultDataCache();

   DefaultDataCacheBean createDefaultDataCache(String var1);

   void destroyDefaultDataCache(DefaultDataCacheBean var1);

   KodoConcurrentDataCacheBean[] getKodoConcurrentDataCache();

   KodoConcurrentDataCacheBean createKodoConcurrentDataCache(String var1);

   void destroyKodoConcurrentDataCache(KodoConcurrentDataCacheBean var1);

   GemFireDataCacheBean[] getGemFireDataCache();

   GemFireDataCacheBean createGemFireDataCache(String var1);

   void destroyGemFireDataCache(GemFireDataCacheBean var1);

   LRUDataCacheBean[] getLRUDataCache();

   LRUDataCacheBean createLRUDataCache(String var1);

   void destroyLRUDataCache(LRUDataCacheBean var1);

   TangosolDataCacheBean[] getTangosolDataCache();

   TangosolDataCacheBean createTangosolDataCache(String var1);

   void destroyTangosolDataCache(TangosolDataCacheBean var1);

   CustomDataCacheBean[] getCustomDataCache();

   CustomDataCacheBean createCustomDataCache(String var1);

   void destroyCustomDataCache(CustomDataCacheBean var1);

   Class[] getDataCacheTypes();

   DataCacheBean[] getDataCaches();

   DataCacheBean createDataCache(Class var1, String var2);

   DataCacheBean lookupDataCache(String var1);

   void destroyDataCache(DataCacheBean var1);
}
