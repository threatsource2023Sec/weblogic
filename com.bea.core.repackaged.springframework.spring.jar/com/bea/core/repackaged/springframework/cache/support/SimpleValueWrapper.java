package com.bea.core.repackaged.springframework.cache.support;

import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class SimpleValueWrapper implements Cache.ValueWrapper {
   @Nullable
   private final Object value;

   public SimpleValueWrapper(@Nullable Object value) {
      this.value = value;
   }

   @Nullable
   public Object get() {
      return this.value;
   }
}
