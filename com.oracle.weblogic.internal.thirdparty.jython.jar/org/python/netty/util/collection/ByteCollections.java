package org.python.netty.util.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class ByteCollections {
   private static final ByteObjectMap EMPTY_MAP = new EmptyMap();

   private ByteCollections() {
   }

   public static ByteObjectMap emptyMap() {
      return EMPTY_MAP;
   }

   public static ByteObjectMap unmodifiableMap(ByteObjectMap map) {
      return new UnmodifiableMap(map);
   }

   private static final class UnmodifiableMap implements ByteObjectMap {
      private final ByteObjectMap map;
      private Set keySet;
      private Set entrySet;
      private Collection values;
      private Iterable entries;

      UnmodifiableMap(ByteObjectMap map) {
         this.map = map;
      }

      public Object get(byte key) {
         return this.map.get(key);
      }

      public Object put(byte key, Object value) {
         throw new UnsupportedOperationException("put");
      }

      public Object remove(byte key) {
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

      public boolean containsKey(byte key) {
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

      public Object put(Byte key, Object value) {
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

      private class EntryImpl implements ByteObjectMap.PrimitiveEntry {
         private final ByteObjectMap.PrimitiveEntry entry;

         EntryImpl(ByteObjectMap.PrimitiveEntry entry) {
            this.entry = entry;
         }

         public byte key() {
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

         public ByteObjectMap.PrimitiveEntry next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               return UnmodifiableMap.this.new EntryImpl((ByteObjectMap.PrimitiveEntry)this.iter.next());
            }
         }

         public void remove() {
            throw new UnsupportedOperationException("remove");
         }
      }
   }

   private static final class EmptyMap implements ByteObjectMap {
      private EmptyMap() {
      }

      public Object get(byte key) {
         return null;
      }

      public Object put(byte key, Object value) {
         throw new UnsupportedOperationException("put");
      }

      public Object remove(byte key) {
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

      public boolean containsKey(byte key) {
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

      public Object put(Byte key, Object value) {
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
