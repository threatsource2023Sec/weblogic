package weblogic.cache.configuration;

import commonj.work.WorkManager;
import java.util.ArrayList;
import java.util.List;
import weblogic.cache.CacheLoader;
import weblogic.cache.CacheStore;

public class CacheBeanImpl implements CacheBean {
   private List setBitList = new ArrayList();
   private Boolean _AsyncListeners = false;
   private Integer _AsyncReplicaCount = 0;
   private CacheProperties.TransactionTypeValue _TransactionType;
   private CacheProperties.EvictionPolicyValue _EvictionPolicy;
   private CacheProperties.TransactionIsolationValue _TransactionIsolation;
   private CacheProperties.TransactionManagerValue _TransactionManager;
   private WorkManager _ListenerWorkManager;
   private CacheProperties.LoadPolicyValue _LoadPolicy;
   private CacheLoader _CustomLoader;
   private Boolean _LockingEnabled;
   private Integer _MaxCacheUnits;
   private String _Name;
   private ArrayList _PartitionList;
   private Object _PartitionLocator;
   private Integer _StoreBatchSize;
   private Integer _StoreBufferMaxSize;
   private Integer _StoreBufferWriteAttempts;
   private Long _StoreBufferWriteTimeout;
   private CacheStore _CustomStore;
   private WorkManager _StoreWorkManager;
   private Integer _SyncReplicaCount;
   private Long _TTL;
   private Long _IdleTime;
   private CacheProperties.TypeValue _Type;
   private WorkManager _WorkManager;
   private CacheProperties.WritePolicyValue _WritePolicy;
   private int _PartitionAssignmentDelayMillis;

   CacheBeanImpl() {
      this._TransactionType = CacheProperties.TransactionTypeValue.None;
      this._EvictionPolicy = CacheProperties.EvictionPolicyValue.LFU;
      this._TransactionIsolation = CacheProperties.TransactionIsolationValue.RepeatableRead;
      this._TransactionManager = CacheProperties.TransactionManagerValue.Local;
      this._ListenerWorkManager = null;
      this._LoadPolicy = null;
      this._CustomLoader = null;
      this._LockingEnabled = false;
      this._MaxCacheUnits = 64;
      this._Name = null;
      this._PartitionList = null;
      this._PartitionLocator = null;
      this._StoreBatchSize = 1;
      this._StoreBufferMaxSize = 100;
      this._StoreBufferWriteAttempts = 1;
      this._StoreBufferWriteTimeout = 100L;
      this._CustomStore = null;
      this._StoreWorkManager = null;
      this._SyncReplicaCount = 0;
      this._TTL = 0L;
      this._IdleTime = 0L;
      this._Type = CacheProperties.TypeValue.Local;
      this._WorkManager = null;
      this._WritePolicy = null;
      this._PartitionAssignmentDelayMillis = 120000;
   }

   public String getName() {
      return this._Name;
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this._Name = param0;
      this.setBitList.add("Name");
   }

   public boolean isNameSet() {
      return this.setBitList.contains("Name");
   }

   public CacheProperties.TypeValue getType() {
      return this._Type;
   }

   public void setType(CacheProperties.TypeValue param0) {
      this._Type = param0;
      this.setBitList.add("Type");
   }

   public boolean isTypeSet() {
      return this.setBitList.contains("Type");
   }

   public Integer getMaxCacheUnits() {
      return this._MaxCacheUnits;
   }

   public void setMaxCacheUnits(Integer param0) {
      CacheBeanImpl.LegalCheck.checkLegalMin("MaxCacheUnits", param0, 1);
      this._MaxCacheUnits = param0;
      this.setBitList.add("MaxCacheUnits");
   }

   public boolean isMaxCacheUnitsSet() {
      return this.setBitList.contains("MaxCacheUnits");
   }

   public Long getTTL() {
      return this._TTL;
   }

   public void setTTL(Long param0) {
      this._TTL = param0;
      this.setBitList.add("TTL");
   }

   public boolean isTTLSet() {
      return this.setBitList.contains("TTL");
   }

   public Long getIdleTime() {
      return this._IdleTime;
   }

   public void setIdleTime(Long param0) {
      this._IdleTime = param0;
      this.setBitList.add("IdleTime");
   }

   public boolean isIdleTimeSet() {
      return this.setBitList.contains("IdleTime");
   }

   public CacheLoader getCustomLoader() {
      return this._CustomLoader;
   }

   public void setCustomLoader(CacheLoader param0) {
      CacheBeanImpl.LegalCheck.checkNotNull("CustomLoader", param0);
      this._CustomLoader = param0;
      this.setBitList.add("CustomLoader");
   }

   public boolean isCustomLoaderSet() {
      return this.setBitList.contains("CustomLoader");
   }

   public CacheProperties.LoadPolicyValue getLoadPolicy() {
      if (!this.isLoadPolicySet()) {
         return this.getCustomLoader() != null ? CacheProperties.LoadPolicyValue.ReadThrough : CacheProperties.LoadPolicyValue.None;
      } else {
         return this._LoadPolicy;
      }
   }

   public void setLoadPolicy(CacheProperties.LoadPolicyValue param0) {
      this._LoadPolicy = param0;
      this.setBitList.add("LoadPolicy");
   }

   public boolean isLoadPolicySet() {
      return this.setBitList.contains("LoadPolicy");
   }

   public CacheProperties.WritePolicyValue getWritePolicy() {
      if (!this.isWritePolicySet()) {
         return this.getCustomStore() != null ? CacheProperties.WritePolicyValue.WriteThrough : CacheProperties.WritePolicyValue.None;
      } else {
         return this._WritePolicy;
      }
   }

   public void setWritePolicy(CacheProperties.WritePolicyValue param0) {
      this._WritePolicy = param0;
      this.setBitList.add("WritePolicy");
   }

   public boolean isWritePolicySet() {
      return this.setBitList.contains("WritePolicy");
   }

   public CacheStore getCustomStore() {
      return this._CustomStore;
   }

   public void setCustomStore(CacheStore param0) {
      CacheBeanImpl.LegalCheck.checkNotNull("CustomStore", param0);
      this._CustomStore = param0;
      this.setBitList.add("CustomStore");
   }

   public boolean isCustomStoreSet() {
      return this.setBitList.contains("CustomStore");
   }

   public CacheProperties.EvictionPolicyValue getEvictionPolicy() {
      return this._EvictionPolicy;
   }

   public void setEvictionPolicy(CacheProperties.EvictionPolicyValue param0) {
      this._EvictionPolicy = param0;
      this.setBitList.add("EvictionPolicy");
   }

   public boolean isEvictionPolicySet() {
      return this.setBitList.contains("EvictionPolicy");
   }

   public CacheProperties.TransactionTypeValue getTransactionType() {
      return this._TransactionType;
   }

   public void setTransactionType(CacheProperties.TransactionTypeValue param0) {
      this._TransactionType = param0;
      this.setBitList.add("TransactionType");
   }

   public boolean isTransactionTypeSet() {
      return this.setBitList.contains("TransactionType");
   }

   public CacheProperties.TransactionIsolationValue getTransactionIsolation() {
      return this._TransactionIsolation;
   }

   public void setTransactionIsolation(CacheProperties.TransactionIsolationValue param0) {
      this._TransactionIsolation = param0;
      this.setBitList.add("TransactionIsolation");
   }

   public boolean isTransactionIsolationSet() {
      return this.setBitList.contains("TransactionIsolation");
   }

   public CacheProperties.TransactionManagerValue getTransactionManager() {
      return this._TransactionManager;
   }

   public void setTransactionManager(CacheProperties.TransactionManagerValue param0) {
      this._TransactionManager = param0;
      this.setBitList.add("TransactionManager");
   }

   public boolean isTransactionManagerSet() {
      return this.setBitList.contains("TransactionManager");
   }

   public Boolean getLockingEnabled() {
      return !this.isLockingEnabledSet() ? this.getTransactionType() != CacheProperties.TransactionTypeValue.None : this._LockingEnabled;
   }

   public void setLockingEnabled(Boolean param0) {
      this._LockingEnabled = param0;
      this.setBitList.add("LockingEnabled");
   }

   public boolean isLockingEnabledSet() {
      return this.setBitList.contains("LockingEnabled");
   }

   public ArrayList getPartitionList() {
      return this._PartitionList;
   }

   public void setPartitionList(ArrayList param0) {
      this._PartitionList = param0;
      this.setBitList.add("PartitionList");
   }

   public boolean isPartitionListSet() {
      return this.setBitList.contains("PartitionList");
   }

   public Object getPartitionLocator() {
      return this._PartitionLocator;
   }

   public void setPartitionLocator(Object o) {
      this._PartitionLocator = o;
      this.setBitList.add("PartitionLocator");
   }

   public boolean isPartitionLocatorSet() {
      return this.setBitList.contains("PartitionLocator");
   }

   public int getPartitionAssignmentDelayMillis() {
      return this._PartitionAssignmentDelayMillis;
   }

   public void setPartitionAssignmentDelayMillis(int i) {
      this._PartitionAssignmentDelayMillis = i;
      this.setBitList.add("PartitionAssignmentDelayMillis");
   }

   public boolean isPartitionAssignmentDelayMillisSet() {
      return this.setBitList.contains("PartitionAssignmentDelayMillis");
   }

   public Integer getSyncReplicaCount() {
      return this._SyncReplicaCount;
   }

   public void setSyncReplicaCount(Integer param0) {
      this._SyncReplicaCount = param0;
      this.setBitList.add("SyncReplicaCount");
   }

   public boolean isSyncReplicaCountSet() {
      return this.setBitList.contains("SyncReplicaCount");
   }

   public Integer getAsyncReplicaCount() {
      return this._AsyncReplicaCount;
   }

   public void setAsyncReplicaCount(Integer param0) {
      this._AsyncReplicaCount = param0;
      this.setBitList.add("AsyncReplicaCount");
   }

   public boolean isAsyncReplicaCountSet() {
      return this.setBitList.contains("AsyncReplicaCount");
   }

   public WorkManager getWorkManager() {
      return this._WorkManager;
   }

   public void setWorkManager(WorkManager param0) {
      CacheBeanImpl.LegalCheck.checkNotNull("WorkManager", param0);
      this._WorkManager = param0;
      this.setBitList.add("WorkManager");
   }

   public boolean isWorkManagerSet() {
      return this.setBitList.contains("WorkManager");
   }

   public WorkManager getStoreWorkManager() {
      return !this.isStoreWorkManagerSet() ? this.getWorkManager() : this._StoreWorkManager;
   }

   public void setStoreWorkManager(WorkManager param0) {
      CacheBeanImpl.LegalCheck.checkNotNull("StoreWorkManager", param0);
      this._StoreWorkManager = param0;
      this.setBitList.add("StoreWorkManager");
   }

   public boolean isStoreWorkManagerSet() {
      return this.setBitList.contains("StoreWorkManager");
   }

   public Boolean getAsyncListeners() {
      return this._AsyncListeners;
   }

   public void setAsyncListeners(Boolean param0) {
      this._AsyncListeners = param0;
      this.setBitList.add("AsyncListeners");
   }

   public boolean isAsyncListenersSet() {
      return this.setBitList.contains("AsyncListeners");
   }

   public WorkManager getListenerWorkManager() {
      return !this.isListenerWorkManagerSet() ? this.getWorkManager() : this._ListenerWorkManager;
   }

   public void setListenerWorkManager(WorkManager param0) {
      CacheBeanImpl.LegalCheck.checkNotNull("ListenerWorkManager", param0);
      this._ListenerWorkManager = param0;
      this.setBitList.add("ListenerWorkManager");
   }

   public boolean isListenerWorkManagerSet() {
      return this.setBitList.contains("ListenerWorkManager");
   }

   public Integer getStoreBufferMaxSize() {
      return this._StoreBufferMaxSize;
   }

   public void setStoreBufferMaxSize(Integer param0) {
      CacheBeanImpl.LegalCheck.checkLegalMin("StoreBufferMaxSize", param0, 1);
      this._StoreBufferMaxSize = param0;
      this.setBitList.add("StoreBufferMaxSize");
   }

   public boolean isStoreBufferMaxSizeSet() {
      return this.setBitList.contains("StoreBufferMaxSize");
   }

   public Long getStoreBufferWriteTimeout() {
      return this._StoreBufferWriteTimeout;
   }

   public void setStoreBufferWriteTimeout(Long param0) {
      CacheBeanImpl.LegalCheck.checkLegalMin("StoreBufferWriteTimeout", param0, 1L);
      this._StoreBufferWriteTimeout = param0;
      this.setBitList.add("StoreBufferWriteTimeout");
   }

   public boolean isStoreBufferWriteTimeoutSet() {
      return this.setBitList.contains("StoreBufferWriteTimeout");
   }

   public Integer getStoreBufferWriteAttempts() {
      return this._StoreBufferWriteAttempts;
   }

   public void setStoreBufferWriteAttempts(Integer param0) {
      CacheBeanImpl.LegalCheck.checkLegalMin("StoreBufferWriteAttempts", param0, 1);
      this._StoreBufferWriteAttempts = param0;
      this.setBitList.add("StoreBufferWriteAttempts");
   }

   public boolean isStoreBufferWriteAttemptsSet() {
      return this.setBitList.contains("StoreBufferWriteAttempts");
   }

   public Integer getStoreBatchSize() {
      return this._StoreBatchSize;
   }

   public void setStoreBatchSize(Integer param0) {
      CacheBeanImpl.LegalCheck.checkLegalMin("StoreBatchSize", param0, 1);
      this._StoreBatchSize = param0;
      this.setBitList.add("StoreBatchSize");
   }

   public boolean isStoreBatchSizeSet() {
      return this.setBitList.contains("StoreBatchSize");
   }

   public boolean isSet(String propertyName) {
      return this.setBitList.contains(propertyName);
   }

   public void validate() {
      if (this.getWritePolicy() != CacheProperties.WritePolicyValue.None && !this.isCustomStoreSet()) {
         throw new ConfigurationException(BEACacheLogger.logUnableToConfigureWritePolicyWithoutStoreLoggable(this.getWritePolicy().toString()).getMessage());
      }
   }

   private static class LegalCheck {
      private static void throwException(String propertyName, Object value, String error) {
         throw new IllegalArgumentException("Incorrect property configuration, Name=[" + propertyName + "], Value=[" + value + "] : " + error);
      }

      private static void checkLegalMin(String propertyName, Integer value, Integer legalMin) {
         if (value < legalMin) {
            throwException(propertyName, value, "Minimum value allowed is " + legalMin);
         }

      }

      private static void checkLegalMin(String propertyName, Long value, Long legalMin) {
         if (value < legalMin) {
            throwException(propertyName, value, "Minimum value allowed is " + legalMin + "l");
         }

      }

      private static void checkNotNull(String propertyName, Object value) {
         if (value == null) {
            throwException(propertyName, value, "Null value not allowed");
         }

      }
   }
}
