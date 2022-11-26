package weblogic.cache.configuration;

public interface CacheProperties {
   String AsyncListeners = "AsyncListeners";
   String AsyncReplicaCount = "AsyncReplicaCount";
   String TransactionType = "TransactionType";
   String PartitionAssignmentDelayMillis = "PartitionAssignmentDelayMillis";
   String TransactionIsolation = "TransactionIsolation";
   String TransactionManager = "TransactionManager";
   String EvictionPolicy = "EvictionPolicy";
   String ListenerWorkManager = "ListenerWorkManager";
   String LoadPolicy = "LoadPolicy";
   String CustomLoader = "CustomLoader";
   String LockingEnabled = "LockingEnabled";
   String MaxCacheUnits = "MaxCacheUnits";
   String Name = "Name";
   String PartitionList = "PartitionList";
   String PartitionLocator = "PartitionLocator";
   String StoreBatchSize = "StoreBatchSize";
   String StoreBufferMaxSize = "StoreBufferMaxSize";
   String StoreBufferWriteAttempts = "StoreBufferWriteAttempts";
   String StoreBufferWriteTimeout = "StoreBufferWriteTimeout";
   String CustomStore = "CustomStore";
   String StoreWorkManager = "StoreWorkManager";
   String SyncReplicaCount = "SyncReplicaCount";
   String TTL = "TTL";
   String IdleTime = "IdleTime";
   String Type = "Type";
   String WorkManager = "WorkManager";
   String WritePolicy = "WritePolicy";

   public static enum WritePolicyValue {
      None,
      WriteThrough,
      WriteBehind;
   }

   public static enum TypeValue {
      Local,
      ReplicatedWeak,
      ReplicatedStrong,
      Partitioned;
   }

   public static enum LoadPolicyValue {
      None,
      ReadThrough;
   }

   public static enum EvictionPolicyValue {
      LRU,
      NRU,
      FIFO,
      LFU,
      Random;
   }

   public static enum TransactionManagerValue {
      Local,
      WLS;
   }

   public static enum TransactionIsolationValue {
      ReadUncommitted,
      ReadCommitted,
      RepeatableRead;
   }

   public static enum TransactionTypeValue {
      None,
      Pessimistic,
      Optimistic;
   }
}
