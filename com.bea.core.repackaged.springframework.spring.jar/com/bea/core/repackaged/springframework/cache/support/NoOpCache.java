package com.bea.core.repackaged.springframework.cache.support;

import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.concurrent.Callable;

public class NoOpCache implements Cache {
   private final String name;

   public NoOpCache(String name) {
      Assert.notNull(name, (String)"Cache name must not be null");
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public Object getNativeCache() {
      return this;
   }

   @Nullable
   public Cache.ValueWrapper get(Object key) {
      return null;
   }

   @Nullable
   public Object get(Object key, @Nullable Class type) {
      return null;
   }

   @Nullable
   public Object get(Object key, Callable valueLoader) {
      try {
         return valueLoader.call();
      } catch (Exception var4) {
         throw new Cache.ValueRetrievalException(key, valueLoader, var4);
      }
   }

   public void put(Object key, @Nullable Object value) {
   }

   @Nullable
   public Cache.ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
      return null;
   }

   public void evict(Object key) {
   }

   public void clear() {
   }
}
