package com.bea.core.repackaged.springframework.cache.support;

import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class AbstractValueAdaptingCache implements Cache {
   private final boolean allowNullValues;

   protected AbstractValueAdaptingCache(boolean allowNullValues) {
      this.allowNullValues = allowNullValues;
   }

   public final boolean isAllowNullValues() {
      return this.allowNullValues;
   }

   @Nullable
   public Cache.ValueWrapper get(Object key) {
      Object value = this.lookup(key);
      return this.toValueWrapper(value);
   }

   @Nullable
   public Object get(Object key, @Nullable Class type) {
      Object value = this.fromStoreValue(this.lookup(key));
      if (value != null && type != null && !type.isInstance(value)) {
         throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
      } else {
         return value;
      }
   }

   @Nullable
   protected abstract Object lookup(Object var1);

   @Nullable
   protected Object fromStoreValue(@Nullable Object storeValue) {
      return this.allowNullValues && storeValue == NullValue.INSTANCE ? null : storeValue;
   }

   protected Object toStoreValue(@Nullable Object userValue) {
      if (userValue == null) {
         if (this.allowNullValues) {
            return NullValue.INSTANCE;
         } else {
            throw new IllegalArgumentException("Cache '" + this.getName() + "' is configured to not allow null values but null was provided");
         }
      } else {
         return userValue;
      }
   }

   @Nullable
   protected Cache.ValueWrapper toValueWrapper(@Nullable Object storeValue) {
      return storeValue != null ? new SimpleValueWrapper(this.fromStoreValue(storeValue)) : null;
   }
}
