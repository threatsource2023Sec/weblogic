package org.hibernate.validator.internal.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

public final class ConcurrentReferenceHashMap extends AbstractMap implements ConcurrentMap, Serializable {
   private static final long serialVersionUID = 7249069246763182397L;
   static final ReferenceType DEFAULT_KEY_TYPE;
   static final ReferenceType DEFAULT_VALUE_TYPE;
   static final int DEFAULT_INITIAL_CAPACITY = 16;
   static final float DEFAULT_LOAD_FACTOR = 0.75F;
   static final int DEFAULT_CONCURRENCY_LEVEL = 16;
   static final int MAXIMUM_CAPACITY = 1073741824;
   static final int MAX_SEGMENTS = 65536;
   static final int RETRIES_BEFORE_LOCK = 2;
   final int segmentMask;
   final int segmentShift;
   final Segment[] segments;
   boolean identityComparisons;
   transient Set keySet;
   transient Set entrySet;
   transient Collection values;

   private static int hash(int h) {
      h += h << 15 ^ -12931;
      h ^= h >>> 10;
      h += h << 3;
      h ^= h >>> 6;
      h += (h << 2) + (h << 14);
      return h ^ h >>> 16;
   }

   final Segment segmentFor(int hash) {
      return this.segments[hash >>> this.segmentShift & this.segmentMask];
   }

   private int hashOf(Object key) {
      return hash(this.identityComparisons ? System.identityHashCode(key) : key.hashCode());
   }

   public ConcurrentReferenceHashMap(int initialCapacity, float loadFactor, int concurrencyLevel, ReferenceType keyType, ReferenceType valueType, EnumSet options) {
      if (loadFactor > 0.0F && initialCapacity >= 0 && concurrencyLevel > 0) {
         if (concurrencyLevel > 65536) {
            concurrencyLevel = 65536;
         }

         int sshift = 0;

         int ssize;
         for(ssize = 1; ssize < concurrencyLevel; ssize <<= 1) {
            ++sshift;
         }

         this.segmentShift = 32 - sshift;
         this.segmentMask = ssize - 1;
         this.segments = ConcurrentReferenceHashMap.Segment.newArray(ssize);
         if (initialCapacity > 1073741824) {
            initialCapacity = 1073741824;
         }

         int c = initialCapacity / ssize;
         if (c * ssize < initialCapacity) {
            ++c;
         }

         int cap;
         for(cap = 1; cap < c; cap <<= 1) {
         }

         this.identityComparisons = options != null && options.contains(ConcurrentReferenceHashMap.Option.IDENTITY_COMPARISONS);

         for(int i = 0; i < this.segments.length; ++i) {
            this.segments[i] = new Segment(cap, loadFactor, keyType, valueType, this.identityComparisons);
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   public ConcurrentReferenceHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
      this(initialCapacity, loadFactor, concurrencyLevel, DEFAULT_KEY_TYPE, DEFAULT_VALUE_TYPE, (EnumSet)null);
   }

   public ConcurrentReferenceHashMap(int initialCapacity, float loadFactor) {
      this(initialCapacity, loadFactor, 16);
   }

   public ConcurrentReferenceHashMap(int initialCapacity, ReferenceType keyType, ReferenceType valueType) {
      this(initialCapacity, 0.75F, 16, keyType, valueType, (EnumSet)null);
   }

   public ConcurrentReferenceHashMap(int initialCapacity) {
      this(initialCapacity, 0.75F, 16);
   }

   public ConcurrentReferenceHashMap() {
      this(16, 0.75F, 16);
   }

   public ConcurrentReferenceHashMap(Map m) {
      this(Math.max((int)((float)m.size() / 0.75F) + 1, 16), 0.75F, 16);
      this.putAll(m);
   }

   public boolean isEmpty() {
      Segment[] segments = this.segments;
      int[] mc = new int[segments.length];
      int mcsum = 0;

      int i;
      for(i = 0; i < segments.length; ++i) {
         if (segments[i].count != 0) {
            return false;
         }

         mcsum += mc[i] = segments[i].modCount;
      }

      if (mcsum != 0) {
         for(i = 0; i < segments.length; ++i) {
            if (segments[i].count != 0 || mc[i] != segments[i].modCount) {
               return false;
            }
         }
      }

      return true;
   }

   public int size() {
      Segment[] segments = this.segments;
      long sum = 0L;
      long check = 0L;
      int[] mc = new int[segments.length];

      int i;
      for(i = 0; i < 2; ++i) {
         check = 0L;
         sum = 0L;
         int mcsum = 0;

         int i;
         for(i = 0; i < segments.length; ++i) {
            sum += (long)segments[i].count;
            mcsum += mc[i] = segments[i].modCount;
         }

         if (mcsum != 0) {
            for(i = 0; i < segments.length; ++i) {
               check += (long)segments[i].count;
               if (mc[i] != segments[i].modCount) {
                  check = -1L;
                  break;
               }
            }
         }

         if (check == sum) {
            break;
         }
      }

      if (check != sum) {
         sum = 0L;

         for(i = 0; i < segments.length; ++i) {
            segments[i].lock();
         }

         for(i = 0; i < segments.length; ++i) {
            sum += (long)segments[i].count;
         }

         for(i = 0; i < segments.length; ++i) {
            segments[i].unlock();
         }
      }

      return sum > 2147483647L ? Integer.MAX_VALUE : (int)sum;
   }

   public Object get(Object key) {
      int hash = this.hashOf(key);
      return this.segmentFor(hash).get(key, hash);
   }

   public boolean containsKey(Object key) {
      int hash = this.hashOf(key);
      return this.segmentFor(hash).containsKey(key, hash);
   }

   public boolean containsValue(Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         Segment[] segments = this.segments;
         int[] mc = new int[segments.length];

         int i;
         int i;
         for(i = 0; i < 2; ++i) {
            i = 0;

            for(int i = 0; i < segments.length; ++i) {
               i += mc[i] = segments[i].modCount;
               if (segments[i].containsValue(value)) {
                  return true;
               }
            }

            boolean cleanSweep = true;
            if (i != 0) {
               for(int i = 0; i < segments.length; ++i) {
                  if (mc[i] != segments[i].modCount) {
                     cleanSweep = false;
                     break;
                  }
               }
            }

            if (cleanSweep) {
               return false;
            }
         }

         for(i = 0; i < segments.length; ++i) {
            segments[i].lock();
         }

         boolean found = false;
         boolean var11 = false;

         try {
            var11 = true;
            i = 0;

            while(true) {
               if (i >= segments.length) {
                  var11 = false;
                  break;
               }

               if (segments[i].containsValue(value)) {
                  found = true;
                  var11 = false;
                  break;
               }

               ++i;
            }
         } finally {
            if (var11) {
               for(int i = 0; i < segments.length; ++i) {
                  segments[i].unlock();
               }

            }
         }

         for(i = 0; i < segments.length; ++i) {
            segments[i].unlock();
         }

         return found;
      }
   }

   public boolean contains(Object value) {
      return this.containsValue(value);
   }

   public Object put(Object key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = this.hashOf(key);
         return this.segmentFor(hash).put(key, hash, value, false);
      }
   }

   public Object putIfAbsent(Object key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = this.hashOf(key);
         return this.segmentFor(hash).put(key, hash, value, true);
      }
   }

   public void putAll(Map m) {
      Iterator var2 = m.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry e = (Map.Entry)var2.next();
         this.put(e.getKey(), e.getValue());
      }

   }

   public Object remove(Object key) {
      int hash = this.hashOf(key);
      return this.segmentFor(hash).remove(key, hash, (Object)null, false);
   }

   public boolean remove(Object key, Object value) {
      int hash = this.hashOf(key);
      if (value == null) {
         return false;
      } else {
         return this.segmentFor(hash).remove(key, hash, value, false) != null;
      }
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      if (oldValue != null && newValue != null) {
         int hash = this.hashOf(key);
         return this.segmentFor(hash).replace(key, hash, oldValue, newValue);
      } else {
         throw new NullPointerException();
      }
   }

   public Object replace(Object key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = this.hashOf(key);
         return this.segmentFor(hash).replace(key, hash, value);
      }
   }

   public void clear() {
      for(int i = 0; i < this.segments.length; ++i) {
         this.segments[i].clear();
      }

   }

   public void purgeStaleEntries() {
      for(int i = 0; i < this.segments.length; ++i) {
         this.segments[i].removeStale();
      }

   }

   public Set keySet() {
      Set ks = this.keySet;
      return ks != null ? ks : (this.keySet = new KeySet());
   }

   public Collection values() {
      Collection vs = this.values;
      return vs != null ? vs : (this.values = new Values());
   }

   public Set entrySet() {
      Set es = this.entrySet;
      return es != null ? es : (this.entrySet = new EntrySet());
   }

   public Enumeration keys() {
      return new KeyIterator();
   }

   public Enumeration elements() {
      return new ValueIterator();
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();

      for(int k = 0; k < this.segments.length; ++k) {
         Segment seg = this.segments[k];
         seg.lock();

         try {
            HashEntry[] tab = seg.table;

            for(int i = 0; i < tab.length; ++i) {
               for(HashEntry e = tab[i]; e != null; e = e.next) {
                  Object key = e.key();
                  if (key != null) {
                     s.writeObject(key);
                     s.writeObject(e.value());
                  }
               }
            }
         } finally {
            seg.unlock();
         }
      }

      s.writeObject((Object)null);
      s.writeObject((Object)null);
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();

      for(int i = 0; i < this.segments.length; ++i) {
         this.segments[i].setTable(new HashEntry[1]);
      }

      while(true) {
         Object key = s.readObject();
         Object value = s.readObject();
         if (key == null) {
            return;
         }

         this.put(key, value);
      }
   }

   static {
      DEFAULT_KEY_TYPE = ConcurrentReferenceHashMap.ReferenceType.WEAK;
      DEFAULT_VALUE_TYPE = ConcurrentReferenceHashMap.ReferenceType.STRONG;
   }

   final class EntrySet extends AbstractSet {
      public Iterator iterator() {
         return ConcurrentReferenceHashMap.this.new EntryIterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            Object v = ConcurrentReferenceHashMap.this.get(e.getKey());
            return v != null && v.equals(e.getValue());
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            return ConcurrentReferenceHashMap.this.remove(e.getKey(), e.getValue());
         }
      }

      public int size() {
         return ConcurrentReferenceHashMap.this.size();
      }

      public boolean isEmpty() {
         return ConcurrentReferenceHashMap.this.isEmpty();
      }

      public void clear() {
         ConcurrentReferenceHashMap.this.clear();
      }
   }

   final class Values extends AbstractCollection {
      public Iterator iterator() {
         return ConcurrentReferenceHashMap.this.new ValueIterator();
      }

      public int size() {
         return ConcurrentReferenceHashMap.this.size();
      }

      public boolean isEmpty() {
         return ConcurrentReferenceHashMap.this.isEmpty();
      }

      public boolean contains(Object o) {
         return ConcurrentReferenceHashMap.this.containsValue(o);
      }

      public void clear() {
         ConcurrentReferenceHashMap.this.clear();
      }
   }

   final class KeySet extends AbstractSet {
      public Iterator iterator() {
         return ConcurrentReferenceHashMap.this.new KeyIterator();
      }

      public int size() {
         return ConcurrentReferenceHashMap.this.size();
      }

      public boolean isEmpty() {
         return ConcurrentReferenceHashMap.this.isEmpty();
      }

      public boolean contains(Object o) {
         return ConcurrentReferenceHashMap.this.containsKey(o);
      }

      public boolean remove(Object o) {
         return ConcurrentReferenceHashMap.this.remove(o) != null;
      }

      public void clear() {
         ConcurrentReferenceHashMap.this.clear();
      }
   }

   final class EntryIterator extends HashIterator implements Iterator {
      EntryIterator() {
         super();
      }

      public Map.Entry next() {
         HashEntry e = super.nextEntry();
         return ConcurrentReferenceHashMap.this.new WriteThroughEntry(e.key(), e.value());
      }
   }

   final class WriteThroughEntry extends SimpleEntry {
      private static final long serialVersionUID = -7900634345345313646L;

      WriteThroughEntry(Object k, Object v) {
         super(k, v);
      }

      public Object setValue(Object value) {
         if (value == null) {
            throw new NullPointerException();
         } else {
            Object v = super.setValue(value);
            ConcurrentReferenceHashMap.this.put(this.getKey(), value);
            return v;
         }
      }
   }

   static class SimpleEntry implements Map.Entry, Serializable {
      private static final long serialVersionUID = -8499721149061103585L;
      private final Object key;
      private Object value;

      public SimpleEntry(Object key, Object value) {
         this.key = key;
         this.value = value;
      }

      public SimpleEntry(Map.Entry entry) {
         this.key = entry.getKey();
         this.value = entry.getValue();
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         Object oldValue = this.value;
         this.value = value;
         return oldValue;
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            return eq(this.key, e.getKey()) && eq(this.value, e.getValue());
         }
      }

      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public String toString() {
         return this.key + "=" + this.value;
      }

      private static boolean eq(Object o1, Object o2) {
         return o1 == null ? o2 == null : o1.equals(o2);
      }
   }

   final class ValueIterator extends HashIterator implements Iterator, Enumeration {
      ValueIterator() {
         super();
      }

      public Object next() {
         return super.nextEntry().value();
      }

      public Object nextElement() {
         return super.nextEntry().value();
      }
   }

   final class KeyIterator extends HashIterator implements Iterator, Enumeration {
      KeyIterator() {
         super();
      }

      public Object next() {
         return super.nextEntry().key();
      }

      public Object nextElement() {
         return super.nextEntry().key();
      }
   }

   abstract class HashIterator {
      int nextSegmentIndex;
      int nextTableIndex;
      HashEntry[] currentTable;
      HashEntry nextEntry;
      HashEntry lastReturned;
      Object currentKey;

      HashIterator() {
         this.nextSegmentIndex = ConcurrentReferenceHashMap.this.segments.length - 1;
         this.nextTableIndex = -1;
         this.advance();
      }

      public boolean hasMoreElements() {
         return this.hasNext();
      }

      final void advance() {
         if (this.nextEntry == null || (this.nextEntry = this.nextEntry.next) == null) {
            while(this.nextTableIndex >= 0) {
               if ((this.nextEntry = this.currentTable[this.nextTableIndex--]) != null) {
                  return;
               }
            }

            while(true) {
               Segment seg;
               do {
                  if (this.nextSegmentIndex < 0) {
                     return;
                  }

                  seg = ConcurrentReferenceHashMap.this.segments[this.nextSegmentIndex--];
               } while(seg.count == 0);

               this.currentTable = seg.table;

               for(int j = this.currentTable.length - 1; j >= 0; --j) {
                  if ((this.nextEntry = this.currentTable[j]) != null) {
                     this.nextTableIndex = j - 1;
                     return;
                  }
               }
            }
         }
      }

      public boolean hasNext() {
         while(this.nextEntry != null) {
            if (this.nextEntry.key() != null) {
               return true;
            }

            this.advance();
         }

         return false;
      }

      HashEntry nextEntry() {
         do {
            if (this.nextEntry == null) {
               throw new NoSuchElementException();
            }

            this.lastReturned = this.nextEntry;
            this.currentKey = this.lastReturned.key();
            this.advance();
         } while(this.currentKey == null);

         return this.lastReturned;
      }

      public void remove() {
         if (this.lastReturned == null) {
            throw new IllegalStateException();
         } else {
            ConcurrentReferenceHashMap.this.remove(this.currentKey);
            this.lastReturned = null;
         }
      }
   }

   static final class Segment extends ReentrantLock implements Serializable {
      private static final long serialVersionUID = 2249069246763182397L;
      transient volatile int count;
      transient int modCount;
      transient int threshold;
      transient volatile HashEntry[] table;
      final float loadFactor;
      transient volatile ReferenceQueue refQueue;
      final ReferenceType keyType;
      final ReferenceType valueType;
      final boolean identityComparisons;

      Segment(int initialCapacity, float lf, ReferenceType keyType, ReferenceType valueType, boolean identityComparisons) {
         this.loadFactor = lf;
         this.keyType = keyType;
         this.valueType = valueType;
         this.identityComparisons = identityComparisons;
         this.setTable(ConcurrentReferenceHashMap.HashEntry.newArray(initialCapacity));
      }

      static final Segment[] newArray(int i) {
         return new Segment[i];
      }

      private boolean keyEq(Object src, Object dest) {
         return this.identityComparisons ? src == dest : src.equals(dest);
      }

      void setTable(HashEntry[] newTable) {
         this.threshold = (int)((float)newTable.length * this.loadFactor);
         this.table = newTable;
         this.refQueue = new ReferenceQueue();
      }

      HashEntry getFirst(int hash) {
         HashEntry[] tab = this.table;
         return tab[hash & tab.length - 1];
      }

      HashEntry newHashEntry(Object key, int hash, HashEntry next, Object value) {
         return new HashEntry(key, hash, next, value, this.keyType, this.valueType, this.refQueue);
      }

      Object readValueUnderLock(HashEntry e) {
         this.lock();

         Object var2;
         try {
            this.removeStale();
            var2 = e.value();
         } finally {
            this.unlock();
         }

         return var2;
      }

      Object get(Object key, int hash) {
         if (this.count != 0) {
            for(HashEntry e = this.getFirst(hash); e != null; e = e.next) {
               if (e.hash == hash && this.keyEq(key, e.key())) {
                  Object opaque = e.valueRef;
                  if (opaque != null) {
                     return e.dereferenceValue(opaque);
                  }

                  return this.readValueUnderLock(e);
               }
            }
         }

         return null;
      }

      boolean containsKey(Object key, int hash) {
         if (this.count != 0) {
            for(HashEntry e = this.getFirst(hash); e != null; e = e.next) {
               if (e.hash == hash && this.keyEq(key, e.key())) {
                  return true;
               }
            }
         }

         return false;
      }

      boolean containsValue(Object value) {
         if (this.count != 0) {
            HashEntry[] tab = this.table;
            int len = tab.length;

            for(int i = 0; i < len; ++i) {
               for(HashEntry e = tab[i]; e != null; e = e.next) {
                  Object opaque = e.valueRef;
                  Object v;
                  if (opaque == null) {
                     v = this.readValueUnderLock(e);
                  } else {
                     v = e.dereferenceValue(opaque);
                  }

                  if (value.equals(v)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }

      boolean replace(Object key, int hash, Object oldValue, Object newValue) {
         this.lock();

         try {
            this.removeStale();

            HashEntry e;
            for(e = this.getFirst(hash); e != null && (e.hash != hash || !this.keyEq(key, e.key())); e = e.next) {
            }

            boolean replaced = false;
            if (e != null && oldValue.equals(e.value())) {
               replaced = true;
               e.setValue(newValue, this.valueType, this.refQueue);
            }

            boolean var7 = replaced;
            return var7;
         } finally {
            this.unlock();
         }
      }

      Object replace(Object key, int hash, Object newValue) {
         this.lock();

         Object var6;
         try {
            this.removeStale();

            HashEntry e;
            for(e = this.getFirst(hash); e != null && (e.hash != hash || !this.keyEq(key, e.key())); e = e.next) {
            }

            Object oldValue = null;
            if (e != null) {
               oldValue = e.value();
               e.setValue(newValue, this.valueType, this.refQueue);
            }

            var6 = oldValue;
         } finally {
            this.unlock();
         }

         return var6;
      }

      Object put(Object key, int hash, Object value, boolean onlyIfAbsent) {
         this.lock();

         try {
            this.removeStale();
            int c = this.count;
            if (c++ > this.threshold) {
               int reduced = this.rehash();
               if (reduced > 0) {
                  this.count = (c -= reduced) - 1;
               }
            }

            HashEntry[] tab = this.table;
            int index = hash & tab.length - 1;
            HashEntry first = tab[index];

            HashEntry e;
            for(e = first; e != null && (e.hash != hash || !this.keyEq(key, e.key())); e = e.next) {
            }

            Object oldValue;
            if (e != null) {
               oldValue = e.value();
               if (!onlyIfAbsent || oldValue == null) {
                  e.setValue(value, this.valueType, this.refQueue);
               }
            } else {
               oldValue = null;
               ++this.modCount;
               tab[index] = this.newHashEntry(key, hash, first, value);
               this.count = c;
            }

            Object var11 = oldValue;
            return var11;
         } finally {
            this.unlock();
         }
      }

      int rehash() {
         HashEntry[] oldTable = this.table;
         int oldCapacity = oldTable.length;
         if (oldCapacity >= 1073741824) {
            return 0;
         } else {
            HashEntry[] newTable = ConcurrentReferenceHashMap.HashEntry.newArray(oldCapacity << 1);
            this.threshold = (int)((float)newTable.length * this.loadFactor);
            int sizeMask = newTable.length - 1;
            int reduce = 0;

            for(int i = 0; i < oldCapacity; ++i) {
               HashEntry e = oldTable[i];
               if (e != null) {
                  HashEntry next = e.next;
                  int idx = e.hash & sizeMask;
                  if (next == null) {
                     newTable[idx] = e;
                  } else {
                     HashEntry lastRun = e;
                     int lastIdx = idx;

                     HashEntry p;
                     for(p = next; p != null; p = p.next) {
                        int k = p.hash & sizeMask;
                        if (k != lastIdx) {
                           lastIdx = k;
                           lastRun = p;
                        }
                     }

                     newTable[lastIdx] = lastRun;

                     for(p = e; p != lastRun; p = p.next) {
                        Object key = p.key();
                        if (key == null) {
                           ++reduce;
                        } else {
                           int k = p.hash & sizeMask;
                           HashEntry n = newTable[k];
                           newTable[k] = this.newHashEntry(key, p.hash, n, p.value());
                        }
                     }
                  }
               }
            }

            this.table = newTable;
            return reduce;
         }
      }

      Object remove(Object key, int hash, Object value, boolean refRemove) {
         this.lock();

         Object v;
         try {
            if (!refRemove) {
               this.removeStale();
            }

            int c = this.count - 1;
            HashEntry[] tab = this.table;
            int index = hash & tab.length - 1;
            HashEntry first = tab[index];

            HashEntry e;
            for(e = first; e != null && key != e.keyRef && (refRemove || hash != e.hash || !this.keyEq(key, e.key())); e = e.next) {
            }

            Object oldValue = null;
            if (e != null) {
               v = e.value();
               if (value == null || value.equals(v)) {
                  oldValue = v;
                  ++this.modCount;
                  HashEntry newFirst = e.next;

                  for(HashEntry p = first; p != e; p = p.next) {
                     Object pKey = p.key();
                     if (pKey == null) {
                        --c;
                     } else {
                        newFirst = this.newHashEntry(pKey, p.hash, newFirst, p.value());
                     }
                  }

                  tab[index] = newFirst;
                  this.count = c;
               }
            }

            v = oldValue;
         } finally {
            this.unlock();
         }

         return v;
      }

      final void removeStale() {
         KeyReference ref;
         while((ref = (KeyReference)this.refQueue.poll()) != null) {
            this.remove(ref.keyRef(), ref.keyHash(), (Object)null, true);
         }

      }

      void clear() {
         if (this.count != 0) {
            this.lock();

            try {
               HashEntry[] tab = this.table;

               for(int i = 0; i < tab.length; ++i) {
                  tab[i] = null;
               }

               ++this.modCount;
               this.refQueue = new ReferenceQueue();
               this.count = 0;
            } finally {
               this.unlock();
            }
         }

      }
   }

   static final class HashEntry {
      final Object keyRef;
      final int hash;
      volatile Object valueRef;
      final HashEntry next;

      HashEntry(Object key, int hash, HashEntry next, Object value, ReferenceType keyType, ReferenceType valueType, ReferenceQueue refQueue) {
         this.hash = hash;
         this.next = next;
         this.keyRef = this.newKeyReference(key, keyType, refQueue);
         this.valueRef = this.newValueReference(value, valueType, refQueue);
      }

      final Object newKeyReference(Object key, ReferenceType keyType, ReferenceQueue refQueue) {
         if (keyType == ConcurrentReferenceHashMap.ReferenceType.WEAK) {
            return new WeakKeyReference(key, this.hash, refQueue);
         } else {
            return keyType == ConcurrentReferenceHashMap.ReferenceType.SOFT ? new SoftKeyReference(key, this.hash, refQueue) : key;
         }
      }

      final Object newValueReference(Object value, ReferenceType valueType, ReferenceQueue refQueue) {
         if (valueType == ConcurrentReferenceHashMap.ReferenceType.WEAK) {
            return new WeakValueReference(value, this.keyRef, this.hash, refQueue);
         } else {
            return valueType == ConcurrentReferenceHashMap.ReferenceType.SOFT ? new SoftValueReference(value, this.keyRef, this.hash, refQueue) : value;
         }
      }

      final Object key() {
         return this.keyRef instanceof KeyReference ? ((Reference)this.keyRef).get() : this.keyRef;
      }

      final Object value() {
         return this.dereferenceValue(this.valueRef);
      }

      final Object dereferenceValue(Object value) {
         return value instanceof KeyReference ? ((Reference)value).get() : value;
      }

      final void setValue(Object value, ReferenceType valueType, ReferenceQueue refQueue) {
         this.valueRef = this.newValueReference(value, valueType, refQueue);
      }

      static final HashEntry[] newArray(int i) {
         return new HashEntry[i];
      }
   }

   static final class SoftValueReference extends SoftReference implements KeyReference {
      final Object keyRef;
      final int hash;

      SoftValueReference(Object value, Object keyRef, int hash, ReferenceQueue refQueue) {
         super(value, refQueue);
         this.keyRef = keyRef;
         this.hash = hash;
      }

      public final int keyHash() {
         return this.hash;
      }

      public final Object keyRef() {
         return this.keyRef;
      }
   }

   static final class WeakValueReference extends WeakReference implements KeyReference {
      final Object keyRef;
      final int hash;

      WeakValueReference(Object value, Object keyRef, int hash, ReferenceQueue refQueue) {
         super(value, refQueue);
         this.keyRef = keyRef;
         this.hash = hash;
      }

      public final int keyHash() {
         return this.hash;
      }

      public final Object keyRef() {
         return this.keyRef;
      }
   }

   static final class SoftKeyReference extends SoftReference implements KeyReference {
      final int hash;

      SoftKeyReference(Object key, int hash, ReferenceQueue refQueue) {
         super(key, refQueue);
         this.hash = hash;
      }

      public final int keyHash() {
         return this.hash;
      }

      public final Object keyRef() {
         return this;
      }
   }

   static final class WeakKeyReference extends WeakReference implements KeyReference {
      final int hash;

      WeakKeyReference(Object key, int hash, ReferenceQueue refQueue) {
         super(key, refQueue);
         this.hash = hash;
      }

      public final int keyHash() {
         return this.hash;
      }

      public final Object keyRef() {
         return this;
      }
   }

   interface KeyReference {
      int keyHash();

      Object keyRef();
   }

   public static enum Option {
      IDENTITY_COMPARISONS;
   }

   public static enum ReferenceType {
      STRONG,
      WEAK,
      SOFT;
   }
}
