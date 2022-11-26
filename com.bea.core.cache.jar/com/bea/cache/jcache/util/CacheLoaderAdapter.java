package com.bea.cache.jcache.util;

import com.bea.cache.jcache.CacheException;
import java.util.Collection;
import java.util.Map;
import weblogic.cache.CacheLoader;

public class CacheLoaderAdapter implements CacheLoader {
   private final com.bea.cache.jcache.CacheLoader delegate;

   public CacheLoaderAdapter(com.bea.cache.jcache.CacheLoader delegate) {
      this.delegate = delegate;
   }

   public Object load(Object key) {
      try {
         return this.delegate.load(key);
      } catch (CacheException var3) {
         throw ExceptionAdapter.getInstance().toInternal(var3);
      }
   }

   public Map loadAll(Collection keys) {
      try {
         return keys == null ? this.delegate.loadAll() : this.delegate.loadAll(keys);
      } catch (CacheException var3) {
         throw ExceptionAdapter.getInstance().toInternal(var3);
      }
   }

   public int hashCode() {
      return this.delegate.hashCode();
   }

   public boolean equals(Object other) {
      return other instanceof CacheLoaderAdapter ? this.delegate.equals(((CacheLoaderAdapter)other).delegate) : false;
   }
}
