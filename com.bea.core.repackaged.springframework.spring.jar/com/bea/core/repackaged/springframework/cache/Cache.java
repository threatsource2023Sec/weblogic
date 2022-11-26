package com.bea.core.repackaged.springframework.cache;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.concurrent.Callable;

public interface Cache {
   String getName();

   Object getNativeCache();

   @Nullable
   ValueWrapper get(Object var1);

   @Nullable
   Object get(Object var1, @Nullable Class var2);

   @Nullable
   Object get(Object var1, Callable var2);

   void put(Object var1, @Nullable Object var2);

   @Nullable
   ValueWrapper putIfAbsent(Object var1, @Nullable Object var2);

   void evict(Object var1);

   void clear();

   public static class ValueRetrievalException extends RuntimeException {
      @Nullable
      private final Object key;

      public ValueRetrievalException(@Nullable Object key, Callable loader, Throwable ex) {
         super(String.format("Value for key '%s' could not be loaded using '%s'", key, loader), ex);
         this.key = key;
      }

      @Nullable
      public Object getKey() {
         return this.key;
      }
   }

   @FunctionalInterface
   public interface ValueWrapper {
      @Nullable
      Object get();
   }
}
