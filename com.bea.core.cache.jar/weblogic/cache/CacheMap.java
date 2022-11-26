package weblogic.cache;

import java.util.List;
import java.util.Map;
import weblogic.cache.locks.LockManager;

public interface CacheMap extends Map {
   int getCapacity();

   long getTTL();

   CacheEntry getEntry(Object var1);

   boolean restoreEntry(CacheEntry var1);

   Object putIfAbsent(Object var1, Object var2);

   boolean remove(Object var1, Object var2);

   boolean replace(Object var1, Object var2, Object var3);

   Object replace(Object var1, Object var2);

   void addEvictionListener(EvictionListener var1);

   void removeEvictionListener(EvictionListener var1);

   void loadAll(List var1);

   void setCacheLoader(CacheLoader var1);

   void addCacheLoadListener(CacheLoadListener var1);

   void removeCacheLoadListener(CacheLoadListener var1);

   void setCacheStore(CacheStore var1);

   void addCacheStoreListener(CacheStoreListener var1);

   void addCacheStoreListener(CacheStoreListener var1, Object var2);

   void addCacheStoreListener(CacheStoreListener var1, KeyFilter var2);

   void removeCacheStoreListener(CacheStoreListener var1);

   ActionTrigger prepare(Action var1);

   LockManager getLockManager();

   MapStatistics getStatistics();

   void addChangeListener(ChangeListener var1);

   void removeChangeListener(ChangeListener var1);

   void evict();

   CacheState getState();

   void start();

   void stop();

   void forceStop();

   public static enum CacheState {
      Started,
      Stopped;
   }
}
