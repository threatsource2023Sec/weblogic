package com.bea.cache.jcache;

import com.bea.cache.jcache.util.CacheAdapter;
import com.bea.cache.jcache.util.CacheLoaderAdapter;
import com.bea.cache.jcache.util.CacheStoreAdapter;
import com.bea.cache.jcache.util.ExceptionAdapter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.cache.CacheManagerFactory;
import weblogic.cache.CacheMap;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.configuration.CacheBeanAdapter;

public class CacheManager {
   private final weblogic.cache.CacheManager delegate;
   private final Map adapters;
   private static String[] preferredManagerFactoryName = new String[]{"weblogic.cache.cluster.DistributedCacheManagerFactory"};
   public static final int State_Started = 1;
   public static final int State_Stopped = 2;

   public static CacheManager getInstance() {
      return CacheManager.Manager.instance;
   }

   private CacheManager() {
      this.adapters = new HashMap();
      CacheManagerFactory factory = null;

      for(int count = 0; factory == null && count < preferredManagerFactoryName.length; ++count) {
         try {
            try {
               factory = (CacheManagerFactory)this.getClass().getClassLoader().loadClass(preferredManagerFactoryName[count]).newInstance();
            } catch (InstantiationException var4) {
            } catch (IllegalAccessException var5) {
            }
         } catch (ClassNotFoundException var6) {
         }
      }

      if (factory == null) {
         factory = new CacheManagerFactory();
      }

      this.delegate = factory.createCacheManager();
   }

   public Cache getCache(String cacheName) {
      CacheMap backingCache = this.delegate.getCache(cacheName);
      CacheAdapter adapter = null;
      if (backingCache != null) {
         adapter = (CacheAdapter)this.adapters.get(cacheName);
         if (adapter == null || adapter.getDelegate() != backingCache) {
            CacheAdapter newAdapter = new CacheAdapter(backingCache);
            newAdapter.copyListners(adapter);
            adapter = newAdapter;
            this.adapters.put(cacheName, newAdapter);
         }
      }

      return adapter;
   }

   public Set getCacheNames() {
      return new HashSet(this.delegate.getCacheNames());
   }

   public Cache createCache(String name, Map env) {
      try {
         CacheMap backingCache = this.delegate.createCache(name, this.replaceVariables(env));
         CacheAdapter adapter = new CacheAdapter(backingCache);
         this.adapters.put(name, adapter);
         return adapter;
      } catch (CacheRuntimeException var5) {
         throw ExceptionAdapter.getInstance().fromInternal(var5);
      }
   }

   private Map replaceVariables(Map env) {
      if (env != null && (env.containsKey("CustomLoader") || env.containsKey("CustomStore"))) {
         Map newEnv = new HashMap();
         Iterator var3 = env.keySet().iterator();

         while(true) {
            while(var3.hasNext()) {
               Object key = var3.next();
               if ("CustomLoader".equals(key) && env.get(key) != null) {
                  newEnv.put(key, new CacheLoaderAdapter((CacheLoader)env.get(key)));
               } else if ("CustomStore".equals(key) && env.get(key) != null) {
                  newEnv.put(key, new CacheStoreAdapter((CacheStore)env.get(key)));
               } else {
                  newEnv.put(key, env.get(key));
               }
            }

            return newEnv;
         }
      } else {
         return env;
      }
   }

   public void reconfigureCache(String cacheName, Map env, boolean ignoreNonDynamicChanges) {
      try {
         Map diffMap = this.delegate.reconfigureCache(cacheName, this.replaceVariables(env), ignoreNonDynamicChanges);
         CacheAdapter cache = (CacheAdapter)this.adapters.get(cacheName);
         if (cache != null) {
            this.reattachListenersIfNeeded(cache, diffMap);
         }

      } catch (CacheRuntimeException var6) {
         throw ExceptionAdapter.getInstance().fromInternal(var6);
      }
   }

   private void reattachListenersIfNeeded(CacheAdapter cache, Map diffs) {
      CacheBeanAdapter.PropertyChange loaderChange = (CacheBeanAdapter.PropertyChange)diffs.get("CustomLoader");
      if (loaderChange == CacheBeanAdapter.PropertyChange.Add) {
         cache.readdLoaderListeners();
      }

      CacheBeanAdapter.PropertyChange storeChange = (CacheBeanAdapter.PropertyChange)diffs.get("CustomStore");
      if (storeChange == CacheBeanAdapter.PropertyChange.Add) {
         cache.readdStoreListeners();
      }

   }

   public void shutdownCache(String cacheName) {
      try {
         this.delegate.stopCache(cacheName);
         this.delegate.unregisterCache(cacheName);
      } catch (CacheRuntimeException var6) {
         throw ExceptionAdapter.getInstance().fromInternal(var6);
      } finally {
         this.adapters.remove(cacheName);
      }

   }

   public void startCache(String cacheName) {
      try {
         this.delegate.startCache(cacheName);
      } catch (CacheRuntimeException var3) {
         throw ExceptionAdapter.getInstance().fromInternal(var3);
      }
   }

   public void stopCache(String cacheName) {
      try {
         this.delegate.stopCache(cacheName);
      } catch (CacheRuntimeException var3) {
         throw ExceptionAdapter.getInstance().fromInternal(var3);
      }
   }

   public void forceStopCache(String cacheName) {
      try {
         this.delegate.stopCache(cacheName);
      } catch (CacheRuntimeException var3) {
         throw ExceptionAdapter.getInstance().fromInternal(var3);
      }
   }

   public void unregisterCache(String cacheName) {
      try {
         this.delegate.unregisterCache(cacheName);
      } catch (CacheRuntimeException var3) {
         throw ExceptionAdapter.getInstance().fromInternal(var3);
      }
   }

   public int getCacheState(String cacheName) {
      CacheMap backingCache = this.delegate.getCache(cacheName);
      return backingCache.getState() == CacheMap.CacheState.Started ? 1 : 2;
   }

   // $FF: synthetic method
   CacheManager(Object x0) {
      this();
   }

   private static final class Manager {
      static final CacheManager instance = new CacheManager();
   }
}
