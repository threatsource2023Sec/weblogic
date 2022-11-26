package weblogic.cache.configuration;

import commonj.work.WorkManager;
import java.util.ArrayList;
import weblogic.cache.CacheLoader;
import weblogic.cache.CacheStore;

public interface CacheBean {
   String getName();

   void setName(String var1);

   boolean isNameSet();

   CacheProperties.TypeValue getType();

   void setType(CacheProperties.TypeValue var1);

   boolean isTypeSet();

   Integer getMaxCacheUnits();

   void setMaxCacheUnits(Integer var1);

   boolean isMaxCacheUnitsSet();

   Long getTTL();

   void setTTL(Long var1);

   boolean isTTLSet();

   Long getIdleTime();

   void setIdleTime(Long var1);

   boolean isIdleTimeSet();

   CacheLoader getCustomLoader();

   void setCustomLoader(CacheLoader var1);

   boolean isCustomLoaderSet();

   CacheProperties.LoadPolicyValue getLoadPolicy();

   void setLoadPolicy(CacheProperties.LoadPolicyValue var1);

   boolean isLoadPolicySet();

   CacheProperties.WritePolicyValue getWritePolicy();

   void setWritePolicy(CacheProperties.WritePolicyValue var1);

   boolean isWritePolicySet();

   CacheStore getCustomStore();

   void setCustomStore(CacheStore var1);

   boolean isCustomStoreSet();

   CacheProperties.EvictionPolicyValue getEvictionPolicy();

   void setEvictionPolicy(CacheProperties.EvictionPolicyValue var1);

   boolean isEvictionPolicySet();

   CacheProperties.TransactionTypeValue getTransactionType();

   void setTransactionType(CacheProperties.TransactionTypeValue var1);

   boolean isTransactionTypeSet();

   CacheProperties.TransactionIsolationValue getTransactionIsolation();

   void setTransactionIsolation(CacheProperties.TransactionIsolationValue var1);

   boolean isTransactionIsolationSet();

   CacheProperties.TransactionManagerValue getTransactionManager();

   void setTransactionManager(CacheProperties.TransactionManagerValue var1);

   boolean isTransactionManagerSet();

   Boolean getLockingEnabled();

   void setLockingEnabled(Boolean var1);

   boolean isLockingEnabledSet();

   ArrayList getPartitionList();

   void setPartitionList(ArrayList var1);

   boolean isPartitionListSet();

   Object getPartitionLocator();

   void setPartitionLocator(Object var1);

   boolean isPartitionLocatorSet();

   int getPartitionAssignmentDelayMillis();

   void setPartitionAssignmentDelayMillis(int var1);

   boolean isPartitionAssignmentDelayMillisSet();

   Integer getSyncReplicaCount();

   void setSyncReplicaCount(Integer var1);

   boolean isSyncReplicaCountSet();

   Integer getAsyncReplicaCount();

   void setAsyncReplicaCount(Integer var1);

   boolean isAsyncReplicaCountSet();

   WorkManager getWorkManager();

   void setWorkManager(WorkManager var1);

   boolean isWorkManagerSet();

   WorkManager getStoreWorkManager();

   void setStoreWorkManager(WorkManager var1);

   boolean isStoreWorkManagerSet();

   Boolean getAsyncListeners();

   void setAsyncListeners(Boolean var1);

   boolean isAsyncListenersSet();

   WorkManager getListenerWorkManager();

   void setListenerWorkManager(WorkManager var1);

   boolean isListenerWorkManagerSet();

   Integer getStoreBufferMaxSize();

   void setStoreBufferMaxSize(Integer var1);

   boolean isStoreBufferMaxSizeSet();

   Long getStoreBufferWriteTimeout();

   void setStoreBufferWriteTimeout(Long var1);

   boolean isStoreBufferWriteTimeoutSet();

   Integer getStoreBufferWriteAttempts();

   void setStoreBufferWriteAttempts(Integer var1);

   boolean isStoreBufferWriteAttemptsSet();

   Integer getStoreBatchSize();

   void setStoreBatchSize(Integer var1);

   boolean isStoreBatchSizeSet();
}
