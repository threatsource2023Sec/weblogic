package weblogic.cache.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.cache.CacheRuntimeException;

public class CacheBeanAdapter {
   private static CacheBeanPropertiesAdapter adapter;
   public static final List dynamicProperties;

   public CacheBeanAdapter() {
      if (adapter == null) {
         throw new IllegalArgumentException("Cannot initialize bean adapters");
      }
   }

   public CacheBean createCacheBean(String name, Map env) {
      CacheBean bean = this.createCacheBean(env);
      bean.setName(name);
      return bean;
   }

   public CacheBean createCacheBean(Map env) {
      try {
         return adapter.makeBean(env);
      } catch (IllegalArgumentException var3) {
         throw new CacheRuntimeException("Illegal configuration", var3);
      }
   }

   public Map createMap(CacheBean bean) {
      return adapter.getValues(bean);
   }

   public CacheBean cloneCacheBean(CacheBean original) {
      return adapter.cloneBean(original);
   }

   public Map diffBeans(CacheBean original, CacheBean proposed, boolean ignoreNonDynamicChanges) {
      Map oMap = adapter.getValues(original);
      Map pMap = adapter.getValues(proposed);
      Map diffMap = new HashMap();
      Iterator var7 = pMap.keySet().iterator();

      while(true) {
         String key;
         while(var7.hasNext()) {
            key = (String)var7.next();
            if (oMap.containsKey(key)) {
               if ((oMap.get(key) == null || pMap.get(key) == null || !oMap.get(key).equals(pMap.get(key))) && (oMap.get(key) != null || pMap.get(key) != null)) {
                  if (!dynamicProperties.contains(key)) {
                     if (!ignoreNonDynamicChanges) {
                        throw new CacheRuntimeException("Non-dynamic property [" + key + "] changed from [" + oMap.get(key) + "] to [" + pMap.get(key) + "]");
                     }
                  } else {
                     diffMap.put(key, CacheBeanAdapter.PropertyChange.Update);
                  }
               }

               oMap.remove(key);
            } else if (!dynamicProperties.contains(key)) {
               if (!ignoreNonDynamicChanges) {
                  throw new CacheRuntimeException("Non-dynamic property [" + key + "] added with value [" + pMap.get(key) + "]");
               }
            } else {
               diffMap.put(key, CacheBeanAdapter.PropertyChange.Add);
            }
         }

         var7 = oMap.keySet().iterator();

         while(var7.hasNext()) {
            key = (String)var7.next();
            if (!dynamicProperties.contains(key)) {
               if (!ignoreNonDynamicChanges) {
                  throw new CacheRuntimeException("Non-dynamic property [" + key + "] removed. Original value [" + oMap.get(key) + "]");
               }
            } else {
               diffMap.put(key, CacheBeanAdapter.PropertyChange.Remove);
            }
         }

         return diffMap;
      }
   }

   static {
      adapter = CacheBeanPropertiesAdapter.instance;
      dynamicProperties = Arrays.asList("MaxCacheUnits", "CustomLoader", "LoadPolicy", "CustomStore", "WritePolicy", "TTL", "IdleTime", "StoreBufferWriteAttempts", "StoreBufferWriteTimeout", "StoreBatchSize", "AsyncListeners", "ListenerWorkManager", "StoreWorkManager", "TransactionIsolation");
   }

   public static enum PropertyChange {
      Add,
      Remove,
      Update;
   }
}
