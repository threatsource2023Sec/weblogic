package weblogic.cache.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.cache.Action;
import weblogic.cache.ActionTrigger;
import weblogic.cache.CacheEntry;
import weblogic.cache.CacheLoadListener;
import weblogic.cache.CacheLoader;
import weblogic.cache.CacheMap;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.CacheStore;
import weblogic.cache.CacheStoreListener;
import weblogic.cache.ChangeListener;
import weblogic.cache.EvictionListener;
import weblogic.cache.KeyFilter;
import weblogic.cache.MapStatistics;
import weblogic.cache.locks.LockManager;

public class DelegatingCacheMap implements CacheMap {
   protected CacheMap delegate;
   protected ExceptionTranslator extrans;

   public DelegatingCacheMap(CacheMap delegate) {
      this(delegate, (ExceptionTranslator)null);
   }

   public DelegatingCacheMap(CacheMap delegate, ExceptionTranslator extrans) {
      this.delegate = delegate;
      this.extrans = extrans;
   }

   public CacheMap getDelegate() {
      return this.delegate;
   }

   public ExceptionTranslator getExceptionTranslator() {
      return this.extrans;
   }

   private RuntimeException translate(CacheRuntimeException ce) {
      return (RuntimeException)(this.extrans == null ? ce : this.extrans.fromInternal(ce));
   }

   public CacheMap getInnermostDelegate() {
      return this.getDelegate() instanceof DelegatingCacheMap ? ((DelegatingCacheMap)this.getDelegate()).getInnermostDelegate() : this.getDelegate();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingCacheMap) {
            other = ((DelegatingCacheMap)other).getDelegate();
         }

         return this.getDelegate().equals(other);
      }
   }

   public int hashCode() {
      return this.getDelegate().hashCode();
   }

   public int getCapacity() {
      try {
         return this.getDelegate().getCapacity();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public long getTTL() {
      try {
         return this.getDelegate().getTTL();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public CacheEntry getEntry(Object key) {
      try {
         return this.getDelegate().getEntry(key);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean restoreEntry(CacheEntry entry) {
      try {
         return this.getDelegate().restoreEntry(entry);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object putIfAbsent(Object key, Object value) {
      try {
         return this.getDelegate().putIfAbsent(key, value);
      } catch (CacheRuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public boolean remove(Object key, Object value) {
      try {
         return this.getDelegate().remove(key, value);
      } catch (CacheRuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      try {
         return this.getDelegate().replace(key, oldValue, newValue);
      } catch (CacheRuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public Object replace(Object key, Object newValue) {
      try {
         return this.getDelegate().replace(key, newValue);
      } catch (CacheRuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void addEvictionListener(EvictionListener listener) {
      try {
         this.getDelegate().addEvictionListener(listener);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void removeEvictionListener(EvictionListener listener) {
      try {
         this.getDelegate().removeEvictionListener(listener);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void loadAll(List keys) {
      try {
         this.getDelegate().loadAll(keys);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public ActionTrigger prepare(Action action) {
      try {
         return this.getDelegate().prepare(action);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public LockManager getLockManager() {
      try {
         LockManager lm = this.getDelegate().getLockManager();
         return (LockManager)(this.extrans == null ? lm : new DelegatingLockManager(lm, this.extrans));
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public int size() {
      try {
         return this.getDelegate().size();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isEmpty() {
      try {
         return this.getDelegate().isEmpty();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean containsKey(Object key) {
      try {
         return this.getDelegate().containsKey(key);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean containsValue(Object val) {
      try {
         return this.getDelegate().containsValue(val);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object get(Object key) {
      try {
         return this.getDelegate().get(key);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object put(Object key, Object val) {
      try {
         return this.getDelegate().put(key, val);
      } catch (CacheRuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object remove(Object key) {
      try {
         return this.getDelegate().remove(key);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void putAll(Map map) {
      try {
         this.getDelegate().putAll(map);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void clear() {
      try {
         this.getDelegate().clear();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Set keySet() {
      try {
         return this.getDelegate().keySet();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Collection values() {
      try {
         return this.getDelegate().values();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Set entrySet() {
      try {
         return this.getDelegate().entrySet();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void addCacheLoadListener(CacheLoadListener listener) {
      try {
         this.getDelegate().addCacheLoadListener(listener);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void addCacheStoreListener(CacheStoreListener listener, Object key) {
      try {
         this.getDelegate().addCacheStoreListener(listener, key);
      } catch (CacheRuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void addCacheStoreListener(CacheStoreListener listener, KeyFilter keys) {
      try {
         this.getDelegate().addCacheStoreListener(listener, keys);
      } catch (CacheRuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void addCacheStoreListener(CacheStoreListener listener) {
      try {
         this.getDelegate().addCacheStoreListener(listener);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void removeCacheLoadListener(CacheLoadListener listener) {
      try {
         this.getDelegate().removeCacheLoadListener(listener);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void removeCacheStoreListener(CacheStoreListener listener) {
      try {
         this.getDelegate().removeCacheStoreListener(listener);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void setCacheLoader(CacheLoader loader) {
      try {
         this.getDelegate().setCacheLoader(loader);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public MapStatistics getStatistics() {
      try {
         return this.getDelegate().getStatistics();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void addChangeListener(ChangeListener l) {
      try {
         this.getDelegate().addChangeListener(l);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void removeChangeListener(ChangeListener l) {
      try {
         this.getDelegate().removeChangeListener(l);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void evict() {
      try {
         this.getDelegate().evict();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setCacheStore(CacheStore store) {
      try {
         this.getDelegate().setCacheStore(store);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void stop() {
      try {
         this.getDelegate().stop();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void forceStop() {
      try {
         this.getDelegate().forceStop();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public CacheMap.CacheState getState() {
      return this.getDelegate().getState();
   }

   public void start() {
      this.getDelegate().start();
   }
}
