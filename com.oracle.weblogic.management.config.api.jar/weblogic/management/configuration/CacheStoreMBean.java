package weblogic.management.configuration;

public interface CacheStoreMBean extends ConfigurationMBean {
   String NONE = "None";
   String WRITE_THROUGH = "WriteThrough";
   String WRITE_BEHIND = "WriteBehind";

   String getCustomStore();

   void setCustomStore(String var1);

   boolean isCustomStoreSet();

   String getWritePolicy();

   void setWritePolicy(String var1);

   boolean isWritePolicySet();

   String getWorkManager();

   void setWorkManager(String var1);

   boolean isWorkManagerSet();

   int getBufferMaxSize();

   void setBufferMaxSize(int var1);

   boolean isBufferMaxSizeSet();

   long getBufferWriteTimeout();

   void setBufferWriteTimeout(long var1);

   boolean isBufferWriteTimeoutSet();

   int getBufferWriteAttempts();

   void setBufferWriteAttempts(int var1);

   boolean isBufferWriteAttemptsSet();

   int getStoreBatchSize();

   void setStoreBatchSize(int var1);

   boolean isStoreBatchSizeSet();
}
