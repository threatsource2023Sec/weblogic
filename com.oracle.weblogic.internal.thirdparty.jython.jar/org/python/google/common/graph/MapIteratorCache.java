package org.python.google.common.graph;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.UnmodifiableIterator;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

class MapIteratorCache {
   private final Map backingMap;
   @Nullable
   private transient Map.Entry entrySetCache;

   MapIteratorCache(Map backingMap) {
      this.backingMap = (Map)Preconditions.checkNotNull(backingMap);
   }

   @CanIgnoreReturnValue
   public Object put(@Nullable Object key, @Nullable Object value) {
      this.clearCache();
      return this.backingMap.put(key, value);
   }

   @CanIgnoreReturnValue
   public Object remove(@Nullable Object key) {
      this.clearCache();
      return this.backingMap.remove(key);
   }

   public void clear() {
      this.clearCache();
      this.backingMap.clear();
   }

   public Object get(@Nullable Object key) {
      Object value = this.getIfCached(key);
      return value != null ? value : this.getWithoutCaching(key);
   }

   public final Object getWithoutCaching(@Nullable Object key) {
      return this.backingMap.get(key);
   }

   public final boolean containsKey(@Nullable Object key) {
      return this.getIfCached(key) != null || this.backingMap.containsKey(key);
   }

   public final Set unmodifiableKeySet() {
      return new AbstractSet() {
         public UnmodifiableIterator iterator() {
            final Iterator entryIterator = MapIteratorCache.this.backingMap.entrySet().iterator();
            return new UnmodifiableIterator() {
               public boolean hasNext() {
                  return entryIterator.hasNext();
               }

               public Object next() {
                  Map.Entry entry = (Map.Entry)entryIterator.next();
                  MapIteratorCache.this.entrySetCache = entry;
                  return entry.getKey();
               }
            };
         }

         public int size() {
            return MapIteratorCache.this.backingMap.size();
         }

         public boolean contains(@Nullable Object key) {
            return MapIteratorCache.this.containsKey(key);
         }
      };
   }

   protected Object getIfCached(@Nullable Object key) {
      Map.Entry entry = this.entrySetCache;
      return entry != null && entry.getKey() == key ? entry.getValue() : null;
   }

   protected void clearCache() {
      this.entrySetCache = null;
   }
}
