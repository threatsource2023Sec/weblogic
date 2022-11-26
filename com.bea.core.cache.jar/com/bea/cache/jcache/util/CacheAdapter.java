package com.bea.cache.jcache.util;

import com.bea.cache.jcache.Cache;
import com.bea.cache.jcache.CacheEntry;
import com.bea.cache.jcache.CacheException;
import com.bea.cache.jcache.CacheListener;
import com.bea.cache.jcache.CacheStatistics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.cache.CacheMap;
import weblogic.cache.util.DelegatingCacheMap;

public class CacheAdapter implements Cache {
   private final DelegatingCacheMap delegate;
   private Map listeners = new HashMap();

   public CacheAdapter(CacheMap delegate) {
      this.delegate = new DelegatingCacheMap(delegate, ExceptionAdapter.getInstance());
   }

   public CacheMap getDelegate() {
      return this.delegate.getDelegate();
   }

   public boolean containsKey(Object key) {
      return this.delegate.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.delegate.containsValue(value);
   }

   public Set entrySet() {
      return this.delegate.entrySet();
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   public Set keySet() {
      return this.delegate.keySet();
   }

   public void putAll(Map t) {
      this.delegate.putAll(t);
   }

   public int size() {
      return this.delegate.size();
   }

   public Collection values() {
      return this.delegate.values();
   }

   public Object get(Object key) {
      return this.delegate.get(key);
   }

   public void load(Object key) {
      this.delegate.loadAll(Arrays.asList(key));
   }

   public void loadAll(Collection keys) {
      if (keys == null) {
         throw new CacheException("Non null key collection required");
      } else {
         this.delegate.loadAll(new ArrayList(keys));
      }
   }

   public void loadAll() {
      this.delegate.loadAll((List)null);
   }

   public Object put(Object key, Object value) {
      return this.delegate.put(key, value);
   }

   public CacheEntry getCacheEntry(Object key) {
      weblogic.cache.CacheEntry entry = this.delegate.getEntry(key);
      return entry == null ? null : new CacheEntryAdapter(entry);
   }

   public CacheStatistics getCacheStatistics() {
      return new CacheStatisticsAdapter(this.delegate.getStatistics());
   }

   public Object remove(Object key) {
      return this.delegate.remove(key);
   }

   public void clear() {
      this.delegate.clear();
   }

   public void addListener(CacheListener listener) {
      if (listener == null) {
         throw new IllegalArgumentException("Non-null listener object expected");
      } else {
         CacheListenerAdapter adapter = new CacheListenerAdapter(listener);
         this.delegate.addChangeListener(adapter);
         this.delegate.addCacheLoadListener(adapter);
         this.delegate.addEvictionListener(adapter);
         this.delegate.addCacheStoreListener(adapter);
         this.listeners.put(listener, adapter);
      }
   }

   public void readdLoaderListeners() {
      Iterator var1 = this.listeners.values().iterator();

      while(var1.hasNext()) {
         CacheListenerAdapter adapter = (CacheListenerAdapter)var1.next();
         this.delegate.addCacheLoadListener(adapter);
      }

   }

   public void readdStoreListeners() {
      Iterator var1 = this.listeners.values().iterator();

      while(var1.hasNext()) {
         CacheListenerAdapter adapter = (CacheListenerAdapter)var1.next();
         this.delegate.addCacheStoreListener(adapter);
      }

   }

   public void copyListners(CacheAdapter from) {
      this.listeners.putAll(from.listeners);
   }

   public void removeListener(CacheListener listener) {
      CacheListenerAdapter adapter = (CacheListenerAdapter)this.listeners.get(listener);
      if (adapter != null) {
         this.delegate.removeCacheStoreListener(adapter);
         this.delegate.removeEvictionListener(adapter);
         this.delegate.removeCacheLoadListener(adapter);
         this.delegate.removeChangeListener(adapter);
         this.listeners.remove(listener);
      }

   }

   public int hashCode() {
      return this.delegate.hashCode();
   }

   public boolean equals(Object other) {
      return other instanceof CacheAdapter ? this.delegate.equals(((CacheAdapter)other).delegate) : false;
   }

   public int getCapacity() {
      return this.delegate.getCapacity();
   }

   public long getTTL() {
      return this.delegate.getTTL();
   }
}
