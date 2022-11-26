package com.bea.core.repackaged.springframework.cache.concurrent;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.core.serializer.support.SerializationDelegate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapCacheManager implements CacheManager, BeanClassLoaderAware {
   private final ConcurrentMap cacheMap = new ConcurrentHashMap(16);
   private boolean dynamic = true;
   private boolean allowNullValues = true;
   private boolean storeByValue = false;
   @Nullable
   private SerializationDelegate serialization;

   public ConcurrentMapCacheManager() {
   }

   public ConcurrentMapCacheManager(String... cacheNames) {
      this.setCacheNames(Arrays.asList(cacheNames));
   }

   public void setCacheNames(@Nullable Collection cacheNames) {
      if (cacheNames != null) {
         Iterator var2 = cacheNames.iterator();

         while(var2.hasNext()) {
            String name = (String)var2.next();
            this.cacheMap.put(name, this.createConcurrentMapCache(name));
         }

         this.dynamic = false;
      } else {
         this.dynamic = true;
      }

   }

   public void setAllowNullValues(boolean allowNullValues) {
      if (allowNullValues != this.allowNullValues) {
         this.allowNullValues = allowNullValues;
         this.recreateCaches();
      }

   }

   public boolean isAllowNullValues() {
      return this.allowNullValues;
   }

   public void setStoreByValue(boolean storeByValue) {
      if (storeByValue != this.storeByValue) {
         this.storeByValue = storeByValue;
         this.recreateCaches();
      }

   }

   public boolean isStoreByValue() {
      return this.storeByValue;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.serialization = new SerializationDelegate(classLoader);
      if (this.isStoreByValue()) {
         this.recreateCaches();
      }

   }

   public Collection getCacheNames() {
      return Collections.unmodifiableSet(this.cacheMap.keySet());
   }

   @Nullable
   public Cache getCache(String name) {
      Cache cache = (Cache)this.cacheMap.get(name);
      if (cache == null && this.dynamic) {
         synchronized(this.cacheMap) {
            cache = (Cache)this.cacheMap.get(name);
            if (cache == null) {
               cache = this.createConcurrentMapCache(name);
               this.cacheMap.put(name, cache);
            }
         }
      }

      return cache;
   }

   private void recreateCaches() {
      Iterator var1 = this.cacheMap.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         entry.setValue(this.createConcurrentMapCache((String)entry.getKey()));
      }

   }

   protected Cache createConcurrentMapCache(String name) {
      SerializationDelegate actualSerialization = this.isStoreByValue() ? this.serialization : null;
      return new ConcurrentMapCache(name, new ConcurrentHashMap(256), this.isAllowNullValues(), actualSerialization);
   }
}
