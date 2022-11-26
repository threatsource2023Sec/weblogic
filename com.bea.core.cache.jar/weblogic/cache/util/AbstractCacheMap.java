package weblogic.cache.util;

import java.util.AbstractMap;
import java.util.List;
import weblogic.cache.Action;
import weblogic.cache.ActionTrigger;
import weblogic.cache.CacheEntry;
import weblogic.cache.CacheLoadListener;
import weblogic.cache.CacheLoader;
import weblogic.cache.CacheMap;
import weblogic.cache.CacheStore;
import weblogic.cache.CacheStoreListener;
import weblogic.cache.ChangeListener;
import weblogic.cache.EvictionListener;
import weblogic.cache.KeyFilter;
import weblogic.cache.MapStatistics;
import weblogic.cache.locks.LockManager;

public abstract class AbstractCacheMap extends AbstractMap implements CacheMap {
   public int getCapacity() {
      throw new UnsupportedOperationException("getCapacity()");
   }

   public long getTTL() {
      throw new UnsupportedOperationException("getTTL()");
   }

   public Object putIfAbsent(Object key, Object value) {
      throw new UnsupportedOperationException("putIfAbsent()");
   }

   public boolean remove(Object key, Object value) {
      throw new UnsupportedOperationException("remove()");
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      throw new UnsupportedOperationException("replace()");
   }

   public Object replace(Object key, Object newValue) {
      throw new UnsupportedOperationException("replace()");
   }

   public void addEvictionListener(EvictionListener listener) {
      throw new UnsupportedOperationException("addEvictionListener()");
   }

   public void removeEvictionListener(EvictionListener listener) {
      throw new UnsupportedOperationException("removeEvictionListener()");
   }

   public void loadAll(List keys) {
      throw new UnsupportedOperationException("loadAll()");
   }

   public void setCacheLoader(CacheLoader loader) {
      throw new UnsupportedOperationException("setCacheLoader()");
   }

   public void addCacheLoadListener(CacheLoadListener listener) {
      throw new UnsupportedOperationException("addCacheLoadListener()");
   }

   public void removeCacheLoadListener(CacheLoadListener listener) {
      throw new UnsupportedOperationException("removeCacheLoadListener()");
   }

   public void setCacheStore(CacheStore store) {
      throw new UnsupportedOperationException("setCacheStore()");
   }

   public void addCacheStoreListener(CacheStoreListener listener) {
      throw new UnsupportedOperationException("addCacheStoreListner()");
   }

   public void addCacheStoreListener(CacheStoreListener listener, Object key) {
      throw new UnsupportedOperationException("addCacheStoreListener()");
   }

   public void addCacheStoreListener(CacheStoreListener listener, KeyFilter keys) {
      throw new UnsupportedOperationException("addCacheStoreListener()");
   }

   public void removeCacheStoreListener(CacheStoreListener listener) {
      throw new UnsupportedOperationException("removeCacheStoreListener()");
   }

   public ActionTrigger prepare(Action action) {
      throw new UnsupportedOperationException("prepare()");
   }

   public CacheEntry getEntry(Object key) {
      throw new UnsupportedOperationException("getEntry()");
   }

   public boolean restoreEntry(CacheEntry entry) {
      if (this.containsKey(entry.getKey())) {
         return false;
      } else {
         this.put(entry.getKey(), entry.getValue());
         return true;
      }
   }

   public LockManager getLockManager() {
      return null;
   }

   public MapStatistics getStatistics() {
      throw new UnsupportedOperationException("getStatistics()");
   }

   public void addChangeListener(ChangeListener l) {
      throw new UnsupportedOperationException("addChangeListener()");
   }

   public void removeChangeListener(ChangeListener l) {
      throw new UnsupportedOperationException("removeChangeListener()");
   }

   public void evict() {
      throw new UnsupportedOperationException("evict()");
   }

   public void stop() {
      throw new UnsupportedOperationException("stop()");
   }

   public void forceStop() {
      throw new UnsupportedOperationException("forceStop()");
   }

   public CacheMap.CacheState getState() {
      throw new UnsupportedOperationException("getState()");
   }

   public void start() {
      throw new UnsupportedOperationException("start()");
   }
}
