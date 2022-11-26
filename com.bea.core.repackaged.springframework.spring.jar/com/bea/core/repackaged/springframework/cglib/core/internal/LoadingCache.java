package com.bea.core.repackaged.springframework.cglib.core.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class LoadingCache {
   protected final ConcurrentMap map;
   protected final Function loader;
   protected final Function keyMapper;
   public static final Function IDENTITY = new Function() {
      public Object apply(Object key) {
         return key;
      }
   };

   public LoadingCache(Function keyMapper, Function loader) {
      this.keyMapper = keyMapper;
      this.loader = loader;
      this.map = new ConcurrentHashMap();
   }

   public static Function identity() {
      return IDENTITY;
   }

   public Object get(Object key) {
      Object cacheKey = this.keyMapper.apply(key);
      Object v = this.map.get(cacheKey);
      return v != null && !(v instanceof FutureTask) ? v : this.createEntry(key, cacheKey, v);
   }

   protected Object createEntry(final Object key, Object cacheKey, Object v) {
      boolean creator = false;
      FutureTask task;
      Object result;
      if (v != null) {
         task = (FutureTask)v;
      } else {
         task = new FutureTask(new Callable() {
            public Object call() throws Exception {
               return LoadingCache.this.loader.apply(key);
            }
         });
         result = this.map.putIfAbsent(cacheKey, task);
         if (result == null) {
            creator = true;
            task.run();
         } else {
            if (!(result instanceof FutureTask)) {
               return result;
            }

            task = (FutureTask)result;
         }
      }

      try {
         result = task.get();
      } catch (InterruptedException var9) {
         throw new IllegalStateException("Interrupted while loading cache item", var9);
      } catch (ExecutionException var10) {
         Throwable cause = var10.getCause();
         if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
         }

         throw new IllegalStateException("Unable to load cache item", cause);
      }

      if (creator) {
         this.map.put(cacheKey, result);
      }

      return result;
   }
}
