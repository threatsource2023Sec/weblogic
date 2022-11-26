package com.bea.cache.jcache.util;

import com.bea.cache.jcache.CacheListener;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import weblogic.cache.CacheEntry;
import weblogic.cache.CacheLoadListener;
import weblogic.cache.CacheStoreListener;
import weblogic.cache.ChangeListener;
import weblogic.cache.EvictionListener;

public class CacheListenerAdapter implements ChangeListener, EvictionListener, CacheLoadListener, CacheStoreListener {
   private final CacheListener delegate;

   CacheListenerAdapter(CacheListener delegate) {
      this.delegate = delegate;
   }

   public void onClear() {
      this.delegate.onClear();
   }

   public void onCreate(CacheEntry entry) {
      this.delegate.onCreate(Collections.singletonMap(entry.getKey(), entry.getValue()));
   }

   public void onUpdate(CacheEntry entry, Object oldValue) {
      this.delegate.onUpdate(Collections.singletonMap(entry.getKey(), entry.getValue()));
   }

   public void onDelete(CacheEntry entry) {
      this.delegate.onRemove(Collections.singletonMap(entry.getKey(), entry.getValue()));
   }

   public void onEviction(Map m) {
      this.delegate.onEvict(m);
   }

   public void onLoad(Map loadedMap) {
      this.delegate.onLoad(loadedMap);
   }

   public void onLoadError(Collection keys, Throwable error) {
      this.delegate.onLoadError(keys, error);
   }

   public void onStore(Map storedMap) {
      this.delegate.onStore(storedMap);
   }

   public void onStoreError(Map storedMap, Throwable t) {
      this.delegate.onStoreError(storedMap, t);
   }
}
