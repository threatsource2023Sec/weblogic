package org.python.google.common.graph;

import java.util.Map;
import javax.annotation.Nullable;

class MapRetrievalCache extends MapIteratorCache {
   @Nullable
   private transient CacheEntry cacheEntry1;
   @Nullable
   private transient CacheEntry cacheEntry2;

   MapRetrievalCache(Map backingMap) {
      super(backingMap);
   }

   public Object get(@Nullable Object key) {
      Object value = this.getIfCached(key);
      if (value != null) {
         return value;
      } else {
         value = this.getWithoutCaching(key);
         if (value != null) {
            this.addToCache(key, value);
         }

         return value;
      }
   }

   protected Object getIfCached(@Nullable Object key) {
      Object value = super.getIfCached(key);
      if (value != null) {
         return value;
      } else {
         CacheEntry entry = this.cacheEntry1;
         if (entry != null && entry.key == key) {
            return entry.value;
         } else {
            entry = this.cacheEntry2;
            if (entry != null && entry.key == key) {
               this.addToCache(entry);
               return entry.value;
            } else {
               return null;
            }
         }
      }
   }

   protected void clearCache() {
      super.clearCache();
      this.cacheEntry1 = null;
      this.cacheEntry2 = null;
   }

   private void addToCache(Object key, Object value) {
      this.addToCache(new CacheEntry(key, value));
   }

   private void addToCache(CacheEntry entry) {
      this.cacheEntry2 = this.cacheEntry1;
      this.cacheEntry1 = entry;
   }

   private static final class CacheEntry {
      final Object key;
      final Object value;

      CacheEntry(Object key, Object value) {
         this.key = key;
         this.value = value;
      }
   }
}
