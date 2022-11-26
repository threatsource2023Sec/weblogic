package com.bea.cache.jcache.util;

import com.bea.cache.jcache.CacheEntry;

public class CacheEntryAdapter implements CacheEntry {
   private final weblogic.cache.CacheEntry delegate;

   CacheEntryAdapter(weblogic.cache.CacheEntry delegate) {
      this.delegate = delegate;
   }

   public long getLastAccessTime() {
      return this.delegate.getLastAccessTime();
   }

   public long getLastUpdateTime() {
      return this.delegate.getLastUpdateTime();
   }

   public long getCreationTime() {
      return this.delegate.getCreationTime();
   }

   public long getExpirationTime() {
      return this.delegate.getExpirationTime();
   }

   public Object getKey() {
      return this.delegate.getKey();
   }

   public Object getValue() {
      return this.delegate.getValue();
   }

   public Object setValue(Object value) {
      return this.delegate.setValue(value);
   }
}
