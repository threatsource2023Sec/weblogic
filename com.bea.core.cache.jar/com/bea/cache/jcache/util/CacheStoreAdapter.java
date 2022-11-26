package com.bea.cache.jcache.util;

import com.bea.cache.jcache.CacheException;
import java.util.Map;
import weblogic.cache.CacheStore;

public class CacheStoreAdapter implements CacheStore {
   private final com.bea.cache.jcache.CacheStore delegate;

   public CacheStoreAdapter(com.bea.cache.jcache.CacheStore delegate) {
      this.delegate = delegate;
   }

   public void store(Object key, Object value) {
      try {
         this.delegate.store(key, value);
      } catch (CacheException var4) {
         throw ExceptionAdapter.getInstance().toInternal(var4);
      }
   }

   public void storeAll(Map map) {
      try {
         this.delegate.storeAll(map);
      } catch (CacheException var3) {
         throw ExceptionAdapter.getInstance().toInternal(var3);
      }
   }

   public int hashCode() {
      return this.delegate.hashCode();
   }

   public boolean equals(Object other) {
      return other instanceof CacheStoreAdapter ? this.delegate.equals(((CacheStoreAdapter)other).delegate) : false;
   }
}
