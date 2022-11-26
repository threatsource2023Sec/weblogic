package weblogic.cache.configuration;

import commonj.work.WorkManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.cache.CacheLoader;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.CacheStore;

enum CacheBeanPropertiesAdapter {
   instance;

   public CacheBean makeBean(Map propertyMap) {
      CacheBeanImpl bean = new CacheBeanImpl();

      try {
         this.setValues(bean, propertyMap);
      } catch (Exception var4) {
         throw new CacheRuntimeException("Unable to create bean", var4);
      }

      bean.validate();
      return bean;
   }

   public Map getValues(CacheBean bean) {
      Map propertyMap = new HashMap();
      if (bean.isAsyncListenersSet()) {
         propertyMap.put("AsyncListeners", bean.getAsyncListeners());
      }

      if (bean.isAsyncReplicaCountSet()) {
         propertyMap.put("AsyncReplicaCount", bean.getAsyncReplicaCount());
      }

      if (bean.isTransactionTypeSet()) {
         propertyMap.put("TransactionType", bean.getTransactionType());
      }

      if (bean.isTransactionIsolationSet()) {
         propertyMap.put("TransactionIsolation", bean.getTransactionIsolation());
      }

      if (bean.isTransactionManagerSet()) {
         propertyMap.put("TransactionManager", bean.getTransactionManager());
      }

      if (bean.isEvictionPolicySet()) {
         propertyMap.put("EvictionPolicy", bean.getEvictionPolicy());
      }

      if (bean.isListenerWorkManagerSet()) {
         propertyMap.put("ListenerWorkManager", bean.getListenerWorkManager());
      }

      if (bean.isLoadPolicySet()) {
         propertyMap.put("LoadPolicy", bean.getLoadPolicy());
      }

      if (bean.isCustomLoaderSet()) {
         propertyMap.put("CustomLoader", bean.getCustomLoader());
      }

      if (bean.isLockingEnabledSet()) {
         propertyMap.put("LockingEnabled", bean.getLockingEnabled());
      }

      if (bean.isMaxCacheUnitsSet()) {
         propertyMap.put("MaxCacheUnits", bean.getMaxCacheUnits());
      }

      if (bean.isNameSet()) {
         propertyMap.put("Name", bean.getName());
      }

      if (bean.isPartitionListSet()) {
         propertyMap.put("PartitionList", bean.getPartitionList());
      }

      if (bean.isPartitionAssignmentDelayMillisSet()) {
         propertyMap.put("PartitionAssignmentDelayMillis", bean.getPartitionAssignmentDelayMillis());
      }

      if (bean.isStoreBatchSizeSet()) {
         propertyMap.put("StoreBatchSize", bean.getStoreBatchSize());
      }

      if (bean.isStoreBufferMaxSizeSet()) {
         propertyMap.put("StoreBufferMaxSize", bean.getStoreBufferMaxSize());
      }

      if (bean.isStoreBufferWriteAttemptsSet()) {
         propertyMap.put("StoreBufferWriteAttempts", bean.getStoreBufferWriteAttempts());
      }

      if (bean.isStoreBufferWriteTimeoutSet()) {
         propertyMap.put("StoreBufferWriteTimeout", bean.getStoreBufferWriteTimeout());
      }

      if (bean.isCustomStoreSet()) {
         propertyMap.put("CustomStore", bean.getCustomStore());
      }

      if (bean.isStoreWorkManagerSet()) {
         propertyMap.put("StoreWorkManager", bean.getStoreWorkManager());
      }

      if (bean.isSyncReplicaCountSet()) {
         propertyMap.put("SyncReplicaCount", bean.getSyncReplicaCount());
      }

      if (bean.isTTLSet()) {
         propertyMap.put("TTL", bean.getTTL());
      }

      if (bean.isIdleTimeSet()) {
         propertyMap.put("IdleTime", bean.getIdleTime());
      }

      if (bean.isTypeSet()) {
         propertyMap.put("Type", bean.getType());
      }

      if (bean.isWorkManagerSet()) {
         propertyMap.put("WorkManager", bean.getWorkManager());
      }

      if (bean.isWritePolicySet()) {
         propertyMap.put("WritePolicy", bean.getWritePolicy());
      }

      return propertyMap;
   }

   private void setValues(CacheBean bean, Map propertyMap) {
      if (propertyMap != null) {
         Iterator iter = propertyMap.keySet().iterator();

         while(iter.hasNext()) {
            String key = (String)iter.next();
            if ("AsyncListeners".equals(key)) {
               bean.setAsyncListeners((Boolean)propertyMap.get(key));
            } else if ("AsyncReplicaCount".equals(key)) {
               bean.setAsyncReplicaCount((Integer)propertyMap.get(key));
            } else if ("TransactionType".equals(key)) {
               bean.setTransactionType((CacheProperties.TransactionTypeValue)propertyMap.get(key));
            } else if ("TransactionIsolation".equals(key)) {
               bean.setTransactionIsolation((CacheProperties.TransactionIsolationValue)propertyMap.get(key));
            } else if ("TransactionManager".equals(key)) {
               bean.setTransactionManager((CacheProperties.TransactionManagerValue)propertyMap.get(key));
            } else if ("EvictionPolicy".equals(key)) {
               bean.setEvictionPolicy((CacheProperties.EvictionPolicyValue)propertyMap.get(key));
            } else if ("ListenerWorkManager".equals(key)) {
               bean.setListenerWorkManager((WorkManager)propertyMap.get(key));
            } else if ("LoadPolicy".equals(key)) {
               bean.setLoadPolicy((CacheProperties.LoadPolicyValue)propertyMap.get(key));
            } else if ("CustomLoader".equals(key)) {
               bean.setCustomLoader((CacheLoader)propertyMap.get(key));
            } else if ("LockingEnabled".equals(key)) {
               bean.setLockingEnabled((Boolean)propertyMap.get(key));
            } else if ("MaxCacheUnits".equals(key)) {
               bean.setMaxCacheUnits((Integer)propertyMap.get(key));
            } else if ("Name".equals(key)) {
               bean.setName((String)propertyMap.get(key));
            } else if ("PartitionList".equals(key)) {
               bean.setPartitionList((ArrayList)propertyMap.get(key));
            } else if ("PartitionLocator".equals(key)) {
               bean.setPartitionLocator(propertyMap.get(key));
            } else if ("PartitionAssignmentDelayMillis".equals(key)) {
               bean.setPartitionAssignmentDelayMillis((Integer)propertyMap.get(key));
            } else if ("StoreBatchSize".equals(key)) {
               bean.setStoreBatchSize((Integer)propertyMap.get(key));
            } else if ("StoreBufferMaxSize".equals(key)) {
               bean.setStoreBufferMaxSize((Integer)propertyMap.get(key));
            } else if ("StoreBufferWriteAttempts".equals(key)) {
               bean.setStoreBufferWriteAttempts((Integer)propertyMap.get(key));
            } else if ("StoreBufferWriteTimeout".equals(key)) {
               bean.setStoreBufferWriteTimeout((Long)propertyMap.get(key));
            } else if ("CustomStore".equals(key)) {
               bean.setCustomStore((CacheStore)propertyMap.get(key));
            } else if ("StoreWorkManager".equals(key)) {
               bean.setStoreWorkManager((WorkManager)propertyMap.get(key));
            } else if ("SyncReplicaCount".equals(key)) {
               bean.setSyncReplicaCount((Integer)propertyMap.get(key));
            } else if ("TTL".equals(key)) {
               bean.setTTL((Long)propertyMap.get(key));
            } else if ("IdleTime".equals(key)) {
               bean.setIdleTime((Long)propertyMap.get(key));
            } else if ("Type".equals(key)) {
               bean.setType((CacheProperties.TypeValue)propertyMap.get(key));
            } else if ("WorkManager".equals(key)) {
               bean.setWorkManager((WorkManager)propertyMap.get(key));
            } else {
               if (!"WritePolicy".equals(key)) {
                  throw new CacheRuntimeException("Invalid property name: " + key);
               }

               bean.setWritePolicy((CacheProperties.WritePolicyValue)propertyMap.get(key));
            }
         }
      }

   }

   public CacheBean cloneBean(CacheBean original) {
      try {
         Map m = this.getValues(original);
         CacheBean clone = this.makeBean(m);
         return clone;
      } catch (CacheRuntimeException var4) {
         var4.printStackTrace();
         return null;
      }
   }
}
