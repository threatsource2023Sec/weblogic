package com.bea.core.repackaged.springframework.cache.concurrent;

import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.cache.support.AbstractValueAdaptingCache;
import com.bea.core.repackaged.springframework.core.serializer.support.SerializationDelegate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapCache extends AbstractValueAdaptingCache {
   private final String name;
   private final ConcurrentMap store;
   @Nullable
   private final SerializationDelegate serialization;

   public ConcurrentMapCache(String name) {
      this(name, new ConcurrentHashMap(256), true);
   }

   public ConcurrentMapCache(String name, boolean allowNullValues) {
      this(name, new ConcurrentHashMap(256), allowNullValues);
   }

   public ConcurrentMapCache(String name, ConcurrentMap store, boolean allowNullValues) {
      this(name, store, allowNullValues, (SerializationDelegate)null);
   }

   protected ConcurrentMapCache(String name, ConcurrentMap store, boolean allowNullValues, @Nullable SerializationDelegate serialization) {
      super(allowNullValues);
      Assert.notNull(name, (String)"Name must not be null");
      Assert.notNull(store, (String)"Store must not be null");
      this.name = name;
      this.store = store;
      this.serialization = serialization;
   }

   public final boolean isStoreByValue() {
      return this.serialization != null;
   }

   public final String getName() {
      return this.name;
   }

   public final ConcurrentMap getNativeCache() {
      return this.store;
   }

   @Nullable
   protected Object lookup(Object key) {
      return this.store.get(key);
   }

   @Nullable
   public Object get(Object key, Callable valueLoader) {
      return this.fromStoreValue(this.store.computeIfAbsent(key, (k) -> {
         try {
            return this.toStoreValue(valueLoader.call());
         } catch (Throwable var5) {
            throw new Cache.ValueRetrievalException(key, valueLoader, var5);
         }
      }));
   }

   public void put(Object key, @Nullable Object value) {
      this.store.put(key, this.toStoreValue(value));
   }

   @Nullable
   public Cache.ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
      Object existing = this.store.putIfAbsent(key, this.toStoreValue(value));
      return this.toValueWrapper(existing);
   }

   public void evict(Object key) {
      this.store.remove(key);
   }

   public void clear() {
      this.store.clear();
   }

   protected Object toStoreValue(@Nullable Object userValue) {
      Object storeValue = super.toStoreValue(userValue);
      if (this.serialization != null) {
         try {
            return this.serializeValue(this.serialization, storeValue);
         } catch (Throwable var4) {
            throw new IllegalArgumentException("Failed to serialize cache value '" + userValue + "'. Does it implement Serializable?", var4);
         }
      } else {
         return storeValue;
      }
   }

   private Object serializeValue(SerializationDelegate serialization, Object storeValue) throws IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();

      byte[] var4;
      try {
         serialization.serialize(storeValue, out);
         var4 = out.toByteArray();
      } finally {
         out.close();
      }

      return var4;
   }

   protected Object fromStoreValue(@Nullable Object storeValue) {
      if (storeValue != null && this.serialization != null) {
         try {
            return super.fromStoreValue(this.deserializeValue(this.serialization, storeValue));
         } catch (Throwable var3) {
            throw new IllegalArgumentException("Failed to deserialize cache value '" + storeValue + "'", var3);
         }
      } else {
         return super.fromStoreValue(storeValue);
      }
   }

   private Object deserializeValue(SerializationDelegate serialization, Object storeValue) throws IOException {
      ByteArrayInputStream in = new ByteArrayInputStream((byte[])((byte[])storeValue));

      Object var4;
      try {
         var4 = serialization.deserialize(in);
      } finally {
         in.close();
      }

      return var4;
   }
}
