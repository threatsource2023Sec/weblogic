package com.bea.xbean.common.utils.collections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentWeakHashMap extends AbstractMap implements ConcurrentMap, Serializable {
   private static final long serialVersionUID = 7249069246763182397L;
   static final int DEFAULT_INITIAL_CAPACITY = 16;
   static final float DEFAULT_LOAD_FACTOR = 0.75F;
   static final int DEFAULT_CONCURRENCY_LEVEL = 16;
   static final int MAXIMUM_CAPACITY = 1073741824;
   static final int MAX_SEGMENTS = 65536;
   static final int RETRIES_BEFORE_LOCK = 2;
   final int segmentMask;
   final int segmentShift;
   final Segment[] segments;
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

   public ConcurrentWeakHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
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
         this.segments = ConcurrentWeakHashMap.Segment.newArray(ssize);
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

         for(int i = 0; i < this.segments.length; ++i) {
            this.segments[i] = new Segment(cap, loadFactor);
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   public ConcurrentWeakHashMap(int initialCapacity, float loadFactor) {
      this(initialCapacity, loadFactor, 16);
   }

   public ConcurrentWeakHashMap(int initialCapacity) {
      this(initialCapacity, 0.75F, 16);
   }

   public ConcurrentWeakHashMap() {
      this(16, 0.75F, 16);
   }

   public ConcurrentWeakHashMap(Map m) {
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
      int hash = hash(key.hashCode());
      return this.segmentFor(hash).get(key, hash);
   }

   public boolean containsKey(Object key) {
      int hash = hash(key.hashCode());
      return this.segmentFor(hash).containsKey(key, hash);
   }

   public boolean containsValue(Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         Segment[] segments = this.segments;
         int[] mc = new int[segments.length];

         int i;
         for(i = 0; i < 2; ++i) {
            int sum = false;
            int mcsum = 0;

            int i;
            for(int i = 0; i < segments.length; ++i) {
               i = segments[i].count;
               mcsum += mc[i] = segments[i].modCount;
               if (segments[i].containsValue(value)) {
                  return true;
               }
            }

            boolean cleanSweep = true;
            if (mcsum != 0) {
               for(i = 0; i < segments.length; ++i) {
                  int c = segments[i].count;
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
         boolean var13 = false;

         int i;
         try {
            var13 = true;
            i = 0;

            while(true) {
               if (i >= segments.length) {
                  var13 = false;
                  break;
               }

               if (segments[i].containsValue(value)) {
                  found = true;
                  var13 = false;
                  break;
               }

               ++i;
            }
         } finally {
            if (var13) {
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
         int hash = hash(key.hashCode());
         return this.segmentFor(hash).put(key, hash, value, false);
      }
   }

   public Object putIfAbsent(Object key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key.hashCode());
         return this.segmentFor(hash).put(key, hash, value, true);
      }
   }

   public void putAll(Map m) {
      Iterator i$ = m.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry e = (Map.Entry)i$.next();
         this.put(e.getKey(), e.getValue());
      }

   }

   public Object remove(Object key) {
      int hash = hash(key.hashCode());
      return this.segmentFor(hash).remove(key, hash, (Object)null, false);
   }

   public boolean remove(Object key, Object value) {
      int hash = hash(key.hashCode());
      if (value == null) {
         return false;
      } else {
         return this.segmentFor(hash).remove(key, hash, value, false) != null;
      }
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      if (oldValue != null && newValue != null) {
         int hash = hash(key.hashCode());
         return this.segmentFor(hash).replace(key, hash, oldValue, newValue);
      } else {
         throw new NullPointerException();
      }
   }

   public Object replace(Object key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key.hashCode());
         return this.segmentFor(hash).replace(key, hash, value);
      }
   }

   public void clear() {
      for(int i = 0; i < this.segments.length; ++i) {
         this.segments[i].clear();
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
                  Object key = e.keyRef.get();
                  if (key != null) {
                     s.writeObject(key);
                     s.writeObject(e.value);
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

   final class EntrySet extends AbstractSet {
      public Iterator iterator() {
         return ConcurrentWeakHashMap.this.new EntryIterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            Object v = ConcurrentWeakHashMap.this.get(e.getKey());
            return v != null && v.equals(e.getValue());
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            return ConcurrentWeakHashMap.this.remove(e.getKey(), e.getValue());
         }
      }

      public int size() {
         return ConcurrentWeakHashMap.this.size();
      }

      public boolean isEmpty() {
         return ConcurrentWeakHashMap.this.isEmpty();
      }

      public void clear() {
         ConcurrentWeakHashMap.this.clear();
      }
   }

   final class Values extends AbstractCollection {
      public Iterator iterator() {
         return ConcurrentWeakHashMap.this.new ValueIterator();
      }

      public int size() {
         return ConcurrentWeakHashMap.this.size();
      }

      public boolean isEmpty() {
         return ConcurrentWeakHashMap.this.isEmpty();
      }

      public boolean contains(Object o) {
         return ConcurrentWeakHashMap.this.containsValue(o);
      }

      public void clear() {
         ConcurrentWeakHashMap.this.clear();
      }
   }

   final class KeySet extends AbstractSet {
      public Iterator iterator() {
         return ConcurrentWeakHashMap.this.new KeyIterator();
      }

      public int size() {
         return ConcurrentWeakHashMap.this.size();
      }

      public boolean isEmpty() {
         return ConcurrentWeakHashMap.this.isEmpty();
      }

      public boolean contains(Object o) {
         return ConcurrentWeakHashMap.this.containsKey(o);
      }

      public boolean remove(Object o) {
         return ConcurrentWeakHashMap.this.remove(o) != null;
      }

      public void clear() {
         ConcurrentWeakHashMap.this.clear();
      }
   }

   final class EntryIterator extends HashIterator implements Iterator {
      EntryIterator() {
         super();
      }

      public Map.Entry next() {
         HashEntry e = super.nextEntry();
         return ConcurrentWeakHashMap.this.new WriteThroughEntry(e.keyRef.get(), e.value);
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
            ConcurrentWeakHashMap.this.put(this.getKey(), value);
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
         return super.nextEntry().value;
      }

      public Object nextElement() {
         return super.nextEntry().value;
      }
   }

   final class KeyIterator extends HashIterator implements Iterator, Enumeration {
      KeyIterator() {
         super();
      }

      public Object next() {
         return super.nextEntry().keyRef.get();
      }

      public Object nextElement() {
         return super.nextEntry().keyRef.get();
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
         this.nextSegmentIndex = ConcurrentWeakHashMap.this.segments.length - 1;
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

                  seg = ConcurrentWeakHashMap.this.segments[this.nextSegmentIndex--];
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
            if (this.nextEntry.keyRef.get() != null) {
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
            this.currentKey = this.lastReturned.keyRef.get();
            this.advance();
         } while(this.currentKey == null);

         return this.lastReturned;
      }

      public void remove() {
         if (this.lastReturned == null) {
            throw new IllegalStateException();
         } else {
            ConcurrentWeakHashMap.this.remove(this.currentKey);
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

      Segment(int initialCapacity, float lf) {
         this.loadFactor = lf;
         this.setTable(ConcurrentWeakHashMap.HashEntry.newArray(initialCapacity));
      }

      static final Segment[] newArray(int i) {
         return new Segment[i];
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

      Object readValueUnderLock(HashEntry e) {
         this.lock();

         Object var2;
         try {
            this.removeStale();
            var2 = e.value;
         } finally {
            this.unlock();
         }

         return var2;
      }

      Object get(Object key, int hash) {
         if (this.count != 0) {
            for(HashEntry e = this.getFirst(hash); e != null; e = e.next) {
               if (e.hash == hash && key.equals(e.keyRef.get())) {
                  Object v = e.value;
                  if (v != null) {
                     return v;
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
               if (e.hash == hash && key.equals(e.keyRef.get())) {
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
                  Object v = e.value;
                  if (v == null) {
                     v = this.readValueUnderLock(e);
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
            for(e = this.getFirst(hash); e != null && (e.hash != hash || !key.equals(e.keyRef.get())); e = e.next) {
            }

            boolean replaced = false;
            if (e != null && oldValue.equals(e.value)) {
               replaced = true;
               e.value = newValue;
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
            for(e = this.getFirst(hash); e != null && (e.hash != hash || !key.equals(e.keyRef.get())); e = e.next) {
            }

            Object oldValue = null;
            if (e != null) {
               oldValue = e.value;
               e.value = newValue;
            }

            var6 = oldValue;
         } finally {
            this.unlock();
         }

         return var6;
      }

      Object put(Object key, int hash, Object value, boolean onlyIfAbsent) {
         this.lock();

         Object var11;
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
            for(e = first; e != null && (e.hash != hash || !key.equals(e.keyRef.get())); e = e.next) {
            }

            Object oldValue;
            if (e != null) {
               oldValue = e.value;
               if (!onlyIfAbsent) {
                  e.value = value;
               }
            } else {
               oldValue = null;
               ++this.modCount;
               tab[index] = new HashEntry(key, hash, first, value, this.refQueue);
               this.count = c;
            }

            var11 = oldValue;
         } finally {
            this.unlock();
         }

         return var11;
      }

      int rehash() {
         HashEntry[] oldTable = this.table;
         int oldCapacity = oldTable.length;
         if (oldCapacity >= 1073741824) {
            return 0;
         } else {
            HashEntry[] newTable = ConcurrentWeakHashMap.HashEntry.newArray(oldCapacity << 1);
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
                        Object key = p.keyRef.get();
                        if (key == null) {
                           ++reduce;
                        } else {
                           int k = p.hash & sizeMask;
                           HashEntry n = newTable[k];
                           newTable[k] = new HashEntry(key, p.hash, n, p.value, this.refQueue);
                        }
                     }
                  }
               }
            }

            this.table = newTable;
            return reduce;
         }
      }

      Object remove(Object key, int hash, Object value, boolean weakRemove) {
         this.lock();

         Object v;
         try {
            if (!weakRemove) {
               this.removeStale();
            }

            int c = this.count - 1;
            HashEntry[] tab = this.table;
            int index = hash & tab.length - 1;
            HashEntry first = tab[index];

            HashEntry e;
            for(e = first; e != null && (!weakRemove || key != e.keyRef) && (e.hash != hash || !key.equals(e.keyRef.get())); e = e.next) {
            }

            Object oldValue = null;
            if (e != null) {
               v = e.value;
               if (value == null || value.equals(v)) {
                  oldValue = v;
                  ++this.modCount;
                  HashEntry newFirst = e.next;

                  for(HashEntry p = first; p != e; p = p.next) {
                     Object pKey = p.keyRef.get();
                     if (pKey == null) {
                        --c;
                     } else {
                        newFirst = new HashEntry(pKey, p.hash, newFirst, p.value, this.refQueue);
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

      void removeStale() {
         WeakKeyReference ref;
         while((ref = (WeakKeyReference)this.refQueue.poll()) != null) {
            this.remove(ref, ref.hash, (Object)null, true);
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
      final WeakReference keyRef;
      final int hash;
      volatile Object value;
      final HashEntry next;

      HashEntry(Object key, int hash, HashEntry next, Object value, ReferenceQueue refQueue) {
         this.keyRef = new WeakKeyReference(key, hash, refQueue);
         this.hash = hash;
         this.next = next;
         this.value = value;
      }

      static final HashEntry[] newArray(int i) {
         return new HashEntry[i];
      }
   }

   static final class WeakKeyReference extends WeakReference {
      final int hash;

      WeakKeyReference(Object key, int hash, ReferenceQueue refQueue) {
         super(key, refQueue);
         this.hash = hash;
      }
   }
}
