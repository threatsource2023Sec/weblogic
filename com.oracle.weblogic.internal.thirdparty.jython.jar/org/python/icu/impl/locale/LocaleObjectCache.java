package org.python.icu.impl.locale;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public abstract class LocaleObjectCache {
   private ConcurrentHashMap _map;
   private ReferenceQueue _queue;

   public LocaleObjectCache() {
      this(16, 0.75F, 16);
   }

   public LocaleObjectCache(int initialCapacity, float loadFactor, int concurrencyLevel) {
      this._queue = new ReferenceQueue();
      this._map = new ConcurrentHashMap(initialCapacity, loadFactor, concurrencyLevel);
   }

   public Object get(Object key) {
      Object value = null;
      this.cleanStaleEntries();
      CacheEntry entry = (CacheEntry)this._map.get(key);
      if (entry != null) {
         value = entry.get();
      }

      if (value == null) {
         key = this.normalizeKey(key);
         Object newVal = this.createObject(key);
         if (key == null || newVal == null) {
            return null;
         }

         for(CacheEntry newEntry = new CacheEntry(key, newVal, this._queue); value == null; value = entry.get()) {
            this.cleanStaleEntries();
            entry = (CacheEntry)this._map.putIfAbsent(key, newEntry);
            if (entry == null) {
               value = newVal;
               break;
            }
         }
      }

      return value;
   }

   private void cleanStaleEntries() {
      CacheEntry entry;
      while((entry = (CacheEntry)this._queue.poll()) != null) {
         this._map.remove(entry.getKey());
      }

   }

   protected abstract Object createObject(Object var1);

   protected Object normalizeKey(Object key) {
      return key;
   }

   private static class CacheEntry extends SoftReference {
      private Object _key;

      CacheEntry(Object key, Object value, ReferenceQueue queue) {
         super(value, queue);
         this._key = key;
      }

      Object getKey() {
         return this._key;
      }
   }
}
