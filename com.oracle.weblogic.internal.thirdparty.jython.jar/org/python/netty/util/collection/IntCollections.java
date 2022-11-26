package org.python.netty.util.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class IntCollections {
   private static final IntObjectMap EMPTY_MAP = new EmptyMap();

   private IntCollections() {
   }

   public static IntObjectMap emptyMap() {
      return EMPTY_MAP;
   }

   public static IntObjectMap unmodifiableMap(IntObjectMap map) {
      return new UnmodifiableMap(map);
   }

   private static final class UnmodifiableMap implements IntObjectMap {
      private final IntObjectMap map;
      private Set keySet;
      private Set entrySet;
      private Collection values;
      private Iterable entries;

      UnmodifiableMap(IntObjectMap map) {
         this.map = map;
      }

      public Object get(int key) {
         return this.map.get(key);
      }

      public Object put(int key, Object value) {
         throw new UnsupportedOperationException("put");
      }

      public Object remove(int key) {
         throw new UnsupportedOperationException("remove");
      }

      public int size() {
         return this.map.size();
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public void clear() {
         throw new UnsupportedOperationException("clear");
      }

      public boolean containsKey(int key) {
         return this.map.containsKey(key);
      }

      public boolean containsValue(Object value) {
         return this.map.containsValue(value);
      }

      public boolean containsKey(Object key) {
         return this.map.containsKey(key);
      }

      public Object get(Object key) {
         return this.map.get(key);
      }

      public Object put(Integer key, Object value) {
         throw new UnsupportedOperationException("put");
      }

      public Object remove(Object key) {
         throw new UnsupportedOperationException("remove");
      }

      public void putAll(Map m) {
         throw new UnsupportedOperationException("putAll");
      }

      public Iterable entries() {
         if (this.entries == null) {
            this.entries = new Iterable() {
               public Iterator iterator() {
                  return UnmodifiableMap.this.new IteratorImpl(UnmodifiableMap.this.map.entries().iterator());
               }
            };
         }

         return this.entries;
      }

      public Set keySet() {
         if (this.keySet == null) {
            this.keySet = Collections.unmodifiableSet(this.map.keySet());
         }

         return this.keySet;
      }

      public Set entrySet() {
         if (this.entrySet == null) {
            this.entrySet = Collections.unmodifiableSet(this.map.entrySet());
         }

         return this.entrySet;
      }

      public Collection values() {
         if (this.values == null) {
            this.values = Collections.unmodifiableCollection(this.map.values());
         }

         return this.values;
      }

      private class EntryImpl implements IntObjectMap.PrimitiveEntry {
         private final IntObjectMap.PrimitiveEntry entry;

         EntryImpl(IntObjectMap.PrimitiveEntry entry) {
            this.entry = entry;
         }

         public int key() {
            return this.entry.key();
         }

         public Object value() {
            return this.entry.value();
         }

         public void setValue(Object value) {
            throw new UnsupportedOperationException("setValue");
         }
      }

      private class IteratorImpl implements Iterator {
         final Iterator iter;

         IteratorImpl(Iterator iter) {
            this.iter = iter;
         }

         public boolean hasNext() {
            return this.iter.hasNext();
         }

         public IntObjectMap.PrimitiveEntry next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               return UnmodifiableMap.this.new EntryImpl((IntObjectMap.PrimitiveEntry)this.iter.next());
            }
         }

         public void remove() {
            throw new UnsupportedOperationException("remove");
         }
      }
   }

   private static final class EmptyMap implements IntObjectMap {
      private EmptyMap() {
      }

      public Object get(int key) {
         return null;
      }

      public Object put(int key, Object value) {
         throw new UnsupportedOperationException("put");
      }

      public Object remove(int key) {
         return null;
      }

      public int size() {
         return 0;
      }

      public boolean isEmpty() {
         return true;
      }

      public boolean containsKey(Object key) {
         return false;
      }

      public void clear() {
      }

      public Set keySet() {
         return Collections.emptySet();
      }

      public boolean containsKey(int key) {
         return false;
      }

      public boolean containsValue(Object value) {
         return false;
      }

      public Iterable entries() {
         return Collections.emptySet();
      }

      public Object get(Object key) {
         return null;
      }

      public Object put(Integer key, Object value) {
         throw new UnsupportedOperationException();
      }

      public Object remove(Object key) {
         return null;
      }

      public void putAll(Map m) {
         throw new UnsupportedOperationException();
      }

      public Collection values() {
         return Collections.emptyList();
      }

      public Set entrySet() {
         return Collections.emptySet();
      }

      // $FF: synthetic method
      EmptyMap(Object x0) {
         this();
      }
   }
}
