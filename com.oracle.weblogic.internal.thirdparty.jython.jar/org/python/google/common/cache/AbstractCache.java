package org.python.google.common.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.Maps;

@GwtCompatible
public abstract class AbstractCache implements Cache {
   protected AbstractCache() {
   }

   public Object get(Object key, Callable valueLoader) throws ExecutionException {
      throw new UnsupportedOperationException();
   }

   public ImmutableMap getAllPresent(Iterable keys) {
      Map result = Maps.newLinkedHashMap();
      Iterator var3 = keys.iterator();

      while(var3.hasNext()) {
         Object key = var3.next();
         if (!result.containsKey(key)) {
            Object value = this.getIfPresent(key);
            if (value != null) {
               result.put(key, value);
            }
         }
      }

      return ImmutableMap.copyOf((Map)result);
   }

   public void put(Object key, Object value) {
      throw new UnsupportedOperationException();
   }

   public void putAll(Map m) {
      Iterator var2 = m.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.put(entry.getKey(), entry.getValue());
      }

   }

   public void cleanUp() {
   }

   public long size() {
      throw new UnsupportedOperationException();
   }

   public void invalidate(Object key) {
      throw new UnsupportedOperationException();
   }

   public void invalidateAll(Iterable keys) {
      Iterator var2 = keys.iterator();

      while(var2.hasNext()) {
         Object key = var2.next();
         this.invalidate(key);
      }

   }

   public void invalidateAll() {
      throw new UnsupportedOperationException();
   }

   public CacheStats stats() {
      throw new UnsupportedOperationException();
   }

   public ConcurrentMap asMap() {
      throw new UnsupportedOperationException();
   }

   public static final class SimpleStatsCounter implements StatsCounter {
      private final LongAddable hitCount = LongAddables.create();
      private final LongAddable missCount = LongAddables.create();
      private final LongAddable loadSuccessCount = LongAddables.create();
      private final LongAddable loadExceptionCount = LongAddables.create();
      private final LongAddable totalLoadTime = LongAddables.create();
      private final LongAddable evictionCount = LongAddables.create();

      public void recordHits(int count) {
         this.hitCount.add((long)count);
      }

      public void recordMisses(int count) {
         this.missCount.add((long)count);
      }

      public void recordLoadSuccess(long loadTime) {
         this.loadSuccessCount.increment();
         this.totalLoadTime.add(loadTime);
      }

      public void recordLoadException(long loadTime) {
         this.loadExceptionCount.increment();
         this.totalLoadTime.add(loadTime);
      }

      public void recordEviction() {
         this.evictionCount.increment();
      }

      public CacheStats snapshot() {
         return new CacheStats(this.hitCount.sum(), this.missCount.sum(), this.loadSuccessCount.sum(), this.loadExceptionCount.sum(), this.totalLoadTime.sum(), this.evictionCount.sum());
      }

      public void incrementBy(StatsCounter other) {
         CacheStats otherStats = other.snapshot();
         this.hitCount.add(otherStats.hitCount());
         this.missCount.add(otherStats.missCount());
         this.loadSuccessCount.add(otherStats.loadSuccessCount());
         this.loadExceptionCount.add(otherStats.loadExceptionCount());
         this.totalLoadTime.add(otherStats.totalLoadTime());
         this.evictionCount.add(otherStats.evictionCount());
      }
   }

   public interface StatsCounter {
      void recordHits(int var1);

      void recordMisses(int var1);

      void recordLoadSuccess(long var1);

      void recordLoadException(long var1);

      void recordEviction();

      CacheStats snapshot();
   }
}
