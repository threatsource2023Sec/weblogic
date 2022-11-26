package weblogic.cache;

import commonj.work.WorkManager;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.cache.configuration.CacheBean;
import weblogic.cache.configuration.CacheBeanAdapter;
import weblogic.cache.configuration.CacheProperties;
import weblogic.cache.configuration.ConfigurationException;
import weblogic.cache.store.LoadingScheme;
import weblogic.cache.store.ReadThroughScheme;
import weblogic.cache.store.WriteBackEvictionStrategy;
import weblogic.cache.store.WriteBehind;
import weblogic.cache.store.WritePolicy;
import weblogic.cache.store.WriteThrough;
import weblogic.cache.tx.TransactionalMapAdapterFactory;
import weblogic.cache.util.BaseCacheEntry;
import weblogic.cache.util.BaseEvictionStrategy;
import weblogic.cache.util.CacheRegistry;
import weblogic.cache.util.CacheRegistryEntry;
import weblogic.cache.util.ConcurrentCacheMap;
import weblogic.cache.util.FIFOEvictionStrategy;
import weblogic.cache.util.LFUEvictionStrategy;
import weblogic.cache.util.LRUEvictionStrategy;
import weblogic.cache.util.MonitoredMapAdapterFactory;
import weblogic.cache.util.NRUEvictionStrategy;
import weblogic.cache.util.RandomEvictionStrategy;

public class CacheManagerImpl implements CacheManager {
   private static final MapAdapterFactory[] FACTORIES = new MapAdapterFactory[]{new TransactionalMapAdapterFactory(), new MonitoredMapAdapterFactory()};
   protected final CacheRegistry registry = new CacheRegistry();
   private final CacheBeanAdapter beanAdapter = new CacheBeanAdapter();

   public CacheMap createCache(String name, Map environment) {
      CacheBean bean = this.beanAdapter.createCacheBean(name, environment);
      CacheMap cache = this.createCache(bean);
      this.registry.register(name, cache, bean);
      return cache;
   }

   public CacheMap createUnregisteredCache(Map environment) {
      CacheBean bean = this.beanAdapter.createCacheBean((String)null, environment);
      return this.createCache(bean);
   }

   public CacheMap createCache(CacheBean bean) {
      LoadingScheme loadingScheme = this.createLoadingScheme(bean);
      WritePolicy writePolicy = this.createWritePolicy(bean);
      BaseEvictionStrategy evictionStrategy = this.createEvictionStrategy(bean);
      if (Boolean.getBoolean("weblogic.cache.createWriteBack")) {
         assert writePolicy instanceof WriteBehind;

         evictionStrategy = new WriteBackEvictionStrategy((BaseEvictionStrategy)evictionStrategy, (WriteBehind)writePolicy);
         writePolicy = null;
      }

      CacheMap map = new ConcurrentCacheMap(bean.getMaxCacheUnits(), bean.getTTL(), bean.getIdleTime(), (EvictionStrategy)evictionStrategy, loadingScheme, writePolicy, bean.getLockingEnabled(), bean.getAsyncListeners() ? bean.getListenerWorkManager() : null);
      MapAdapterFactory[] var6 = FACTORIES;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         MapAdapterFactory factory = var6[var8];
         map = factory.adapt((CacheMap)map, bean);
      }

      return (CacheMap)map;
   }

   private BaseEvictionStrategy createEvictionStrategy(CacheBean bean) {
      CacheProperties.EvictionPolicyValue policy = bean.getEvictionPolicy();
      long ttl = bean.getTTL();
      long idleTime = bean.getIdleTime();
      if (CacheProperties.EvictionPolicyValue.LRU == policy) {
         return new LRUEvictionStrategy(ttl, idleTime);
      } else if (CacheProperties.EvictionPolicyValue.LFU == policy) {
         return new LFUEvictionStrategy(ttl, idleTime);
      } else if (CacheProperties.EvictionPolicyValue.FIFO == policy) {
         return new FIFOEvictionStrategy(ttl, idleTime);
      } else if (CacheProperties.EvictionPolicyValue.NRU == policy) {
         return new NRUEvictionStrategy(ttl, idleTime);
      } else if (CacheProperties.EvictionPolicyValue.Random == policy) {
         return new RandomEvictionStrategy(ttl, idleTime);
      } else {
         throw new AssertionError("Illegal eviction policy: " + policy);
      }
   }

   private LoadingScheme createLoadingScheme(CacheBean bean) throws ConfigurationException {
      CacheProperties.LoadPolicyValue loadingScheme = bean.getLoadPolicy();
      CacheLoader loader = bean.getCustomLoader();
      if (loadingScheme == CacheProperties.LoadPolicyValue.ReadThrough) {
         return new ReadThroughScheme(loader, -1L, bean.getAsyncListeners() ? bean.getListenerWorkManager() : null);
      } else if (loadingScheme == CacheProperties.LoadPolicyValue.None) {
         return null;
      } else {
         throw new AssertionError("Illegal loading scheme value: " + loadingScheme);
      }
   }

   private WritePolicy createWritePolicy(CacheBean bean) {
      CacheProperties.WritePolicyValue writePolicy = bean.getWritePolicy();
      if (writePolicy == CacheProperties.WritePolicyValue.None) {
         return null;
      } else {
         CacheStore store = bean.getCustomStore();
         if (writePolicy == CacheProperties.WritePolicyValue.WriteThrough) {
            return new WriteThrough(store, bean.getAsyncListeners() ? bean.getListenerWorkManager() : null);
         } else if (writePolicy == CacheProperties.WritePolicyValue.WriteBehind) {
            if (bean.getStoreWorkManager() == null) {
               throw new CacheRuntimeException("Unable to initialize write behind policy. No work manager specified");
            } else {
               return new WriteBehind(store, bean.getStoreWorkManager(), bean.getStoreBufferMaxSize(), bean.getStoreBufferWriteTimeout(), bean.getStoreBufferWriteAttempts(), bean.getStoreBatchSize(), 50L, bean.getAsyncListeners() ? bean.getListenerWorkManager() : null);
            }
         } else {
            throw new AssertionError("Illegal write policy: " + writePolicy);
         }
      }
   }

   public CacheMap getCache(String name) {
      try {
         return this.registry.lookup(name).getCache();
      } catch (CacheRuntimeException var3) {
         return null;
      }
   }

   public Set getCacheNames() {
      return this.registry.keySet();
   }

   public Map reconfigureCache(String cacheName, Map environment, boolean ignoreNonDynamicChanges) {
      CacheRegistryEntry entry = this.registry.lookup(cacheName);
      CacheMap cache = entry.getCache();
      if (cache == null) {
         throw new CacheRuntimeException(cacheName + "?");
      } else {
         CacheBean oldBean = entry.getConfiguration();
         CacheBean newBean = this.beanAdapter.createCacheBean(cacheName, environment);
         Map diffMap = this.beanAdapter.diffBeans(oldBean, newBean, ignoreNonDynamicChanges);
         CacheMap base = cache;

         for(int i = FACTORIES.length - 1; i >= 0; --i) {
            FACTORIES[i].reconfigure(base, oldBean, newBean, diffMap);
            base = FACTORIES[i].unwrap(base);
         }

         ConcurrentCacheMap cacheImpl = (ConcurrentCacheMap)base;
         Iterator var11 = diffMap.keySet().iterator();

         while(true) {
            while(var11.hasNext()) {
               String key = (String)var11.next();
               if (key.equals("MaxCacheUnits")) {
                  cacheImpl.setCapacity(newBean.getMaxCacheUnits());
               } else if (key.equals("CustomLoader")) {
                  if (!diffMap.containsKey("LoadPolicy")) {
                     if (this.checkChangeInLoadPolicy(oldBean, newBean)) {
                        cacheImpl.setLoadingScheme(this.createLoadingScheme(newBean));
                     } else {
                        cacheImpl.setCacheLoader(newBean.getCustomLoader());
                     }
                  }
               } else if (key.equals("LoadPolicy")) {
                  cacheImpl.setLoadingScheme(this.createLoadingScheme(newBean));
               } else if (key.equals("CustomStore")) {
                  if (!diffMap.containsKey("WritePolicy")) {
                     if (this.checkChangeInWritePolicy(oldBean, newBean)) {
                        cacheImpl.setWritePolicy(this.createWritePolicy(newBean));
                     } else {
                        cacheImpl.setCacheStore(newBean.getCustomStore());
                     }
                  }
               } else if (key.equals("WritePolicy")) {
                  cacheImpl.setWritePolicy(this.createWritePolicy(newBean));
               } else {
                  Iterator var13;
                  Object e;
                  if (!key.equals("TTL")) {
                     if (key.equals("IdleTime")) {
                        ((BaseEvictionStrategy)cacheImpl.getEvictionStrategy()).setIdleTime(newBean.getIdleTime());
                        var13 = cacheImpl.entrySet().iterator();

                        while(var13.hasNext()) {
                           e = var13.next();
                           ((BaseCacheEntry)e).setIdleTime(newBean.getIdleTime());
                        }
                     } else if (key.equals("StoreBufferWriteAttempts")) {
                        if (this.checkNoChangeInWriteBehindPolicy(oldBean, newBean)) {
                           ((WriteBehind)cacheImpl.getWritePolicy()).setOfferAttempts(newBean.getStoreBufferWriteAttempts());
                        }
                     } else if (key.equals("StoreBufferWriteTimeout")) {
                        if (this.checkNoChangeInWriteBehindPolicy(oldBean, newBean)) {
                           ((WriteBehind)cacheImpl.getWritePolicy()).setOfferTimeout(newBean.getStoreBufferWriteTimeout());
                        }
                     } else if (key.equals("StoreBatchSize")) {
                        if (this.checkNoChangeInWriteBehindPolicy(oldBean, newBean)) {
                           ((WriteBehind)cacheImpl.getWritePolicy()).setBatchSize(newBean.getStoreBatchSize());
                        }
                     } else if (key.equals("StoreWorkManager")) {
                        if (this.checkNoChangeInWriteBehindPolicy(oldBean, newBean)) {
                           if (newBean.getStoreWorkManager() == null) {
                              throw new CacheRuntimeException("Unable to re-configure write behind policy. Work Manager not specified or set to null");
                           }

                           ((WriteBehind)cacheImpl.getWritePolicy()).setStoreWorkManager(newBean.getStoreWorkManager());
                        }
                     } else if (key.equals("AsyncListeners")) {
                        this.reconfigureListenerWorkManager(cache, newBean.getAsyncListeners() ? newBean.getListenerWorkManager() : null);
                     } else if (key.equals("ListenerWorkManager") && oldBean.getAsyncListeners() && newBean.getAsyncListeners()) {
                        this.reconfigureListenerWorkManager(cache, newBean.getListenerWorkManager());
                     }
                  } else {
                     ((BaseEvictionStrategy)cacheImpl.getEvictionStrategy()).setTTL(newBean.getTTL());
                     var13 = cacheImpl.entrySet().iterator();

                     while(var13.hasNext()) {
                        e = var13.next();
                        ((BaseCacheEntry)e).setTTL(newBean.getTTL());
                     }

                     cacheImpl.setTTL(newBean.getTTL());
                  }
               }
            }

            entry.setConfiguration(newBean);
            return diffMap;
         }
      }
   }

   private boolean checkNoChangeInWriteBehindPolicy(CacheBean oldBean, CacheBean newBean) {
      return oldBean.getWritePolicy() == CacheProperties.WritePolicyValue.WriteBehind && newBean.getWritePolicy() == CacheProperties.WritePolicyValue.WriteBehind;
   }

   private boolean checkChangeInWritePolicy(CacheBean oldBean, CacheBean newBean) {
      return oldBean.getWritePolicy() != newBean.getWritePolicy();
   }

   private boolean checkChangeInLoadPolicy(CacheBean oldBean, CacheBean newBean) {
      return oldBean.getLoadPolicy() != newBean.getLoadPolicy();
   }

   private void reconfigureListenerWorkManager(CacheMap cache, WorkManager workManager) {
      CacheMap base = cache;

      for(int i = FACTORIES.length - 1; i >= 0; --i) {
         base = FACTORIES[i].unwrap(base);
      }

      ConcurrentCacheMap cacheImpl = (ConcurrentCacheMap)base;
      cacheImpl.setListenerWorkManager(workManager);
      if (cacheImpl.getWritePolicy() != null) {
         cacheImpl.getWritePolicy().setListenerWorkManager(workManager);
      }

      if (cacheImpl.getLoadingScheme() != null) {
         cacheImpl.getLoadingScheme().setListenerWorkManager(workManager);
      }

   }

   public void startCache(String cacheName) {
      CacheMap cache = this.getCache(cacheName);
      if (cache != null) {
         cache.start();
      } else {
         throw new CacheRuntimeException("Unable to find cache named " + cacheName);
      }
   }

   public void stopCache(String cacheName) {
      CacheMap cache = this.getCache(cacheName);
      if (cache != null) {
         cache.stop();
      } else {
         throw new CacheRuntimeException("Unable to find cache named " + cacheName);
      }
   }

   public void forceStopCache(String cacheName) {
      CacheMap cache = this.getCache(cacheName);
      if (cache != null) {
         cache.forceStop();
      } else {
         throw new CacheRuntimeException("Unable to find cache named " + cacheName);
      }
   }

   public void unregisterCache(String cacheName) {
      CacheMap cache = this.getCache(cacheName);
      if (cache != null) {
         if (cache.getState() == CacheMap.CacheState.Started) {
            throw new CacheRuntimeException("The cache must be first stopped before unregistration");
         } else {
            this.registry.remove(cacheName);
         }
      } else {
         throw new CacheRuntimeException("Unable to find cache named " + cacheName);
      }
   }

   public void destroyCache(String cacheName) {
      CacheMap cache = this.getCache(cacheName);
      if (cache != null) {
         cache.forceStop();
         this.registry.remove(cacheName);
      } else {
         throw new CacheRuntimeException("Unable to find cache named " + cacheName);
      }
   }
}
