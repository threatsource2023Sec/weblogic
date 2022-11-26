package org.python.netty.util.collection;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.python.netty.util.internal.MathUtil;

public class LongObjectHashMap implements LongObjectMap {
   public static final int DEFAULT_CAPACITY = 8;
   public static final float DEFAULT_LOAD_FACTOR = 0.5F;
   private static final Object NULL_VALUE = new Object();
   private int maxSize;
   private final float loadFactor;
   private long[] keys;
   private Object[] values;
   private int size;
   private int mask;
   private final Set keySet;
   private final Set entrySet;
   private final Iterable entries;

   public LongObjectHashMap() {
      this(8, 0.5F);
   }

   public LongObjectHashMap(int initialCapacity) {
      this(initialCapacity, 0.5F);
   }

   public LongObjectHashMap(int initialCapacity, float loadFactor) {
      this.keySet = new KeySet();
      this.entrySet = new EntrySet();
      this.entries = new Iterable() {
         public Iterator iterator() {
            return LongObjectHashMap.this.new PrimitiveIterator();
         }
      };
      if (!(loadFactor <= 0.0F) && !(loadFactor > 1.0F)) {
         this.loadFactor = loadFactor;
         int capacity = MathUtil.safeFindNextPositivePowerOfTwo(initialCapacity);
         this.mask = capacity - 1;
         this.keys = new long[capacity];
         Object[] temp = (Object[])(new Object[capacity]);
         this.values = temp;
         this.maxSize = this.calcMaxSize(capacity);
      } else {
         throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
      }
   }

   private static Object toExternal(Object value) {
      assert value != null : "null is not a legitimate internal value. Concurrent Modification?";

      return value == NULL_VALUE ? null : value;
   }

   private static Object toInternal(Object value) {
      return value == null ? NULL_VALUE : value;
   }

   public Object get(long key) {
      int index = this.indexOf(key);
      return index == -1 ? null : toExternal(this.values[index]);
   }

   public Object put(long key, Object value) {
      int startIndex = this.hashIndex(key);
      int index = startIndex;

      while(this.values[index] != null) {
         if (this.keys[index] == key) {
            Object previousValue = this.values[index];
            this.values[index] = toInternal(value);
            return toExternal(previousValue);
         }

         if ((index = this.probeNext(index)) == startIndex) {
            throw new IllegalStateException("Unable to insert");
         }
      }

      this.keys[index] = key;
      this.values[index] = toInternal(value);
      this.growSize();
      return null;
   }

   public void putAll(Map sourceMap) {
      if (sourceMap instanceof LongObjectHashMap) {
         LongObjectHashMap source = (LongObjectHashMap)sourceMap;

         for(int i = 0; i < source.values.length; ++i) {
            Object sourceValue = source.values[i];
            if (sourceValue != null) {
               this.put(source.keys[i], sourceValue);
            }
         }

      } else {
         Iterator var2 = sourceMap.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.put((Long)entry.getKey(), entry.getValue());
         }

      }
   }

   public Object remove(long key) {
      int index = this.indexOf(key);
      if (index == -1) {
         return null;
      } else {
         Object prev = this.values[index];
         this.removeAt(index);
         return toExternal(prev);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public void clear() {
      Arrays.fill(this.keys, 0L);
      Arrays.fill(this.values, (Object)null);
      this.size = 0;
   }

   public boolean containsKey(long key) {
      return this.indexOf(key) >= 0;
   }

   public boolean containsValue(Object value) {
      Object v1 = toInternal(value);
      Object[] var3 = this.values;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object v2 = var3[var5];
         if (v2 != null && v2.equals(v1)) {
            return true;
         }
      }

      return false;
   }

   public Iterable entries() {
      return this.entries;
   }

   public Collection values() {
      return new AbstractCollection() {
         public Iterator iterator() {
            return new Iterator() {
               final PrimitiveIterator iter = LongObjectHashMap.this.new PrimitiveIterator();

               public boolean hasNext() {
                  return this.iter.hasNext();
               }

               public Object next() {
                  return this.iter.next().value();
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }

         public int size() {
            return LongObjectHashMap.this.size;
         }
      };
   }

   public int hashCode() {
      int hash = this.size;
      long[] var2 = this.keys;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         long key = var2[var4];
         hash ^= hashCode(key);
      }

      return hash;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof LongObjectMap)) {
         return false;
      } else {
         LongObjectMap other = (LongObjectMap)obj;
         if (this.size != other.size()) {
            return false;
         } else {
            for(int i = 0; i < this.values.length; ++i) {
               Object value = this.values[i];
               if (value != null) {
                  long key = this.keys[i];
                  Object otherValue = other.get(key);
                  if (value == NULL_VALUE) {
                     if (otherValue != null) {
                        return false;
                     }
                  } else if (!value.equals(otherValue)) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   public boolean containsKey(Object key) {
      return this.containsKey(this.objectToKey(key));
   }

   public Object get(Object key) {
      return this.get(this.objectToKey(key));
   }

   public Object put(Long key, Object value) {
      return this.put(this.objectToKey(key), value);
   }

   public Object remove(Object key) {
      return this.remove(this.objectToKey(key));
   }

   public Set keySet() {
      return this.keySet;
   }

   public Set entrySet() {
      return this.entrySet;
   }

   private long objectToKey(Object key) {
      return (Long)key;
   }

   private int indexOf(long key) {
      int startIndex = this.hashIndex(key);
      int index = startIndex;

      while(this.values[index] != null) {
         if (key == this.keys[index]) {
            return index;
         }

         if ((index = this.probeNext(index)) == startIndex) {
            return -1;
         }
      }

      return -1;
   }

   private int hashIndex(long key) {
      return hashCode(key) & this.mask;
   }

   private static int hashCode(long key) {
      return (int)(key ^ key >>> 32);
   }

   private int probeNext(int index) {
      return index + 1 & this.mask;
   }

   private void growSize() {
      ++this.size;
      if (this.size > this.maxSize) {
         if (this.keys.length == Integer.MAX_VALUE) {
            throw new IllegalStateException("Max capacity reached at size=" + this.size);
         }

         this.rehash(this.keys.length << 1);
      }

   }

   private boolean removeAt(int index) {
      --this.size;
      this.keys[index] = 0L;
      this.values[index] = null;
      int nextFree = index;
      int i = this.probeNext(index);

      for(Object value = this.values[i]; value != null; value = this.values[i = this.probeNext(i)]) {
         long key = this.keys[i];
         int bucket = this.hashIndex(key);
         if (i < bucket && (bucket <= nextFree || nextFree <= i) || bucket <= nextFree && nextFree <= i) {
            this.keys[nextFree] = key;
            this.values[nextFree] = value;
            this.keys[i] = 0L;
            this.values[i] = null;
            nextFree = i;
         }
      }

      return nextFree != index;
   }

   private int calcMaxSize(int capacity) {
      int upperBound = capacity - 1;
      return Math.min(upperBound, (int)((float)capacity * this.loadFactor));
   }

   private void rehash(int newCapacity) {
      long[] oldKeys = this.keys;
      Object[] oldVals = this.values;
      this.keys = new long[newCapacity];
      Object[] temp = (Object[])(new Object[newCapacity]);
      this.values = temp;
      this.maxSize = this.calcMaxSize(newCapacity);
      this.mask = newCapacity - 1;

      for(int i = 0; i < oldVals.length; ++i) {
         Object oldVal = oldVals[i];
         if (oldVal != null) {
            long oldKey = oldKeys[i];

            int index;
            for(index = this.hashIndex(oldKey); this.values[index] != null; index = this.probeNext(index)) {
            }

            this.keys[index] = oldKey;
            this.values[index] = oldVal;
         }
      }

   }

   public String toString() {
      if (this.isEmpty()) {
         return "{}";
      } else {
         StringBuilder sb = new StringBuilder(4 * this.size);
         sb.append('{');
         boolean first = true;

         for(int i = 0; i < this.values.length; ++i) {
            Object value = this.values[i];
            if (value != null) {
               if (!first) {
                  sb.append(", ");
               }

               sb.append(this.keyToString(this.keys[i])).append('=').append(value == this ? "(this Map)" : toExternal(value));
               first = false;
            }
         }

         return sb.append('}').toString();
      }
   }

   protected String keyToString(long key) {
      return Long.toString(key);
   }

   final class MapEntry implements Map.Entry {
      private final int entryIndex;

      MapEntry(int entryIndex) {
         this.entryIndex = entryIndex;
      }

      public Long getKey() {
         this.verifyExists();
         return LongObjectHashMap.this.keys[this.entryIndex];
      }

      public Object getValue() {
         this.verifyExists();
         return LongObjectHashMap.toExternal(LongObjectHashMap.this.values[this.entryIndex]);
      }

      public Object setValue(Object value) {
         this.verifyExists();
         Object prevValue = LongObjectHashMap.toExternal(LongObjectHashMap.this.values[this.entryIndex]);
         LongObjectHashMap.this.values[this.entryIndex] = LongObjectHashMap.toInternal(value);
         return prevValue;
      }

      private void verifyExists() {
         if (LongObjectHashMap.this.values[this.entryIndex] == null) {
            throw new IllegalStateException("The map entry has been removed");
         }
      }
   }

   private final class MapIterator implements Iterator {
      private final PrimitiveIterator iter;

      private MapIterator() {
         this.iter = LongObjectHashMap.this.new PrimitiveIterator();
      }

      public boolean hasNext() {
         return this.iter.hasNext();
      }

      public Map.Entry next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.iter.next();
            return LongObjectHashMap.this.new MapEntry(this.iter.entryIndex);
         }
      }

      public void remove() {
         this.iter.remove();
      }

      // $FF: synthetic method
      MapIterator(Object x1) {
         this();
      }
   }

   private final class PrimitiveIterator implements Iterator, LongObjectMap.PrimitiveEntry {
      private int prevIndex;
      private int nextIndex;
      private int entryIndex;

      private PrimitiveIterator() {
         this.prevIndex = -1;
         this.nextIndex = -1;
         this.entryIndex = -1;
      }

      private void scanNext() {
         while(++this.nextIndex != LongObjectHashMap.this.values.length && LongObjectHashMap.this.values[this.nextIndex] == null) {
         }

      }

      public boolean hasNext() {
         if (this.nextIndex == -1) {
            this.scanNext();
         }

         return this.nextIndex != LongObjectHashMap.this.values.length;
      }

      public LongObjectMap.PrimitiveEntry next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.prevIndex = this.nextIndex;
            this.scanNext();
            this.entryIndex = this.prevIndex;
            return this;
         }
      }

      public void remove() {
         if (this.prevIndex == -1) {
            throw new IllegalStateException("next must be called before each remove.");
         } else {
            if (LongObjectHashMap.this.removeAt(this.prevIndex)) {
               this.nextIndex = this.prevIndex;
            }

            this.prevIndex = -1;
         }
      }

      public long key() {
         return LongObjectHashMap.this.keys[this.entryIndex];
      }

      public Object value() {
         return LongObjectHashMap.toExternal(LongObjectHashMap.this.values[this.entryIndex]);
      }

      public void setValue(Object value) {
         LongObjectHashMap.this.values[this.entryIndex] = LongObjectHashMap.toInternal(value);
      }

      // $FF: synthetic method
      PrimitiveIterator(Object x1) {
         this();
      }
   }

   private final class KeySet extends AbstractSet {
      private KeySet() {
      }

      public int size() {
         return LongObjectHashMap.this.size();
      }

      public boolean contains(Object o) {
         return LongObjectHashMap.this.containsKey(o);
      }

      public boolean remove(Object o) {
         return LongObjectHashMap.this.remove(o) != null;
      }

      public boolean retainAll(Collection retainedKeys) {
         boolean changed = false;
         Iterator iter = LongObjectHashMap.this.entries().iterator();

         while(iter.hasNext()) {
            LongObjectMap.PrimitiveEntry entry = (LongObjectMap.PrimitiveEntry)iter.next();
            if (!retainedKeys.contains(entry.key())) {
               changed = true;
               iter.remove();
            }
         }

         return changed;
      }

      public void clear() {
         LongObjectHashMap.this.clear();
      }

      public Iterator iterator() {
         return new Iterator() {
            private final Iterator iter;

            {
               this.iter = LongObjectHashMap.this.entrySet.iterator();
            }

            public boolean hasNext() {
               return this.iter.hasNext();
            }

            public Long next() {
               return (Long)((Map.Entry)this.iter.next()).getKey();
            }

            public void remove() {
               this.iter.remove();
            }
         };
      }

      // $FF: synthetic method
      KeySet(Object x1) {
         this();
      }
   }

   private final class EntrySet extends AbstractSet {
      private EntrySet() {
      }

      public Iterator iterator() {
         return LongObjectHashMap.this.new MapIterator();
      }

      public int size() {
         return LongObjectHashMap.this.size();
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }
}
