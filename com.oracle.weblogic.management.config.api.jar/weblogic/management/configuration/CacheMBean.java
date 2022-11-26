package weblogic.management.configuration;

public interface CacheMBean extends DeploymentMBean {
   String EVICTION_LRU = "LRU";
   String EVICTION_NRU = "NRU";
   String EVICTION_FIFO = "FIFO";
   String EVICTION_LFU = "LFU";

   String getJNDIName();

   void setJNDIName(String var1);

   int getMaxCacheUnits();

   void setMaxCacheUnits(int var1);

   boolean isMaxCacheUnitsSet();

   CacheExpirationMBean getExpiration();

   String getEvictionPolicy();

   void setEvictionPolicy(String var1);

   boolean isEvictionPolicySet();

   String getWorkManager();

   void setWorkManager(String var1);

   boolean isWorkManagerSet();

   CacheLoaderMBean getLoader();

   CacheStoreMBean getStore();

   CacheTransactionMBean getTransactional();

   CacheAsyncListenersMBean getAsyncListeners();
}
