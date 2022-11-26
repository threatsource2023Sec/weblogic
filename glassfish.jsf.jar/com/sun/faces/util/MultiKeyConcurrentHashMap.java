package com.sun.faces.util;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class MultiKeyConcurrentHashMap {
   static int DEFAULT_INITIAL_CAPACITY = 16;
   static final int MAXIMUM_CAPACITY = 1073741824;
   static final float DEFAULT_LOAD_FACTOR = 0.75F;
   static final int DEFAULT_SEGMENTS = 16;
   static final int MAX_SEGMENTS = 65536;
   static final int RETRIES_BEFORE_LOCK = 2;
   final int segmentMask;
   final int segmentShift;
   final Segment[] segments;

   static int hash(Object x1, Object x2, Object x3, Object x4) {
      int h = 0;
      h ^= x1.hashCode();
      if (x2 != null) {
         h ^= x2.hashCode();
      }

      if (x3 != null) {
         h ^= x3.hashCode();
      }

      if (x4 != null) {
         h ^= x4.hashCode();
      }

      h += ~(h << 9);
      h ^= h >>> 14;
      h += h << 4;
      h ^= h >>> 10;
      return h;
   }

   final Segment segmentFor(int hash) {
      return this.segments[hash >>> this.segmentShift & this.segmentMask];
   }

   public MultiKeyConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
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
         this.segments = new Segment[ssize];
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

   public MultiKeyConcurrentHashMap(int initialCapacity) {
      this(initialCapacity, 0.75F, 16);
   }

   public MultiKeyConcurrentHashMap() {
      this(DEFAULT_INITIAL_CAPACITY, 0.75F, 16);
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
      int hash = hash(key, (Object)null, (Object)null, (Object)null);
      return this.segmentFor(hash).get(key, (Object)null, (Object)null, (Object)null, hash);
   }

   public Object get(Object key1, Object key2) {
      int hash = hash(key1, key2, (Object)null, (Object)null);
      return this.segmentFor(hash).get(key1, key2, (Object)null, (Object)null, hash);
   }

   public Object get(Object key1, Object key2, Object key3) {
      int hash = hash(key1, key2, key3, (Object)null);
      return this.segmentFor(hash).get(key1, key2, key3, (Object)null, hash);
   }

   public Object get(Object key1, Object key2, Object key3, Object key4) {
      int hash = hash(key1, key2, key3, key4);
      return this.segmentFor(hash).get(key1, key2, key3, key4, hash);
   }

   public boolean containsKey(Object key) {
      int hash = hash(key, (Object)null, (Object)null, (Object)null);
      return this.segmentFor(hash).containsKey(key, (Object)null, (Object)null, (Object)null, hash);
   }

   public boolean containsKey(Object key1, Object key2) {
      int hash = hash(key1, key2, (Object)null, (Object)null);
      return this.segmentFor(hash).containsKey(key1, key2, (Object)null, (Object)null, hash);
   }

   public boolean containsKey(Object key1, Object key2, Object key3) {
      int hash = hash(key1, key2, key3, (Object)null);
      return this.segmentFor(hash).containsKey(key1, key2, key3, (Object)null, hash);
   }

   public boolean containsKey(Object key1, Object key2, Object key3, Object key4) {
      int hash = hash(key1, key2, key3, key4);
      return this.segmentFor(hash).containsKey(key1, key2, key3, key4, hash);
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
         int hash = hash(key, (Object)null, (Object)null, (Object)null);
         return this.segmentFor(hash).put(key, (Object)null, (Object)null, (Object)null, hash, value, false);
      }
   }

   public Object put(Object key1, Object key2, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key1, key2, (Object)null, (Object)null);
         return this.segmentFor(hash).put(key1, key2, (Object)null, (Object)null, hash, value, false);
      }
   }

   public Object put(Object key1, Object key2, Object key3, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key1, key2, key3, (Object)null);
         return this.segmentFor(hash).put(key1, key2, key3, (Object)null, hash, value, false);
      }
   }

   public Object put(Object key1, Object key2, Object key3, Object key4, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key1, key2, key3, key4);
         return this.segmentFor(hash).put(key1, key2, key3, key4, hash, value, false);
      }
   }

   public Object putIfAbsent(Object key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key, (Object)null, (Object)null, (Object)null);
         return this.segmentFor(hash).put(key, (Object)null, (Object)null, (Object)null, hash, value, true);
      }
   }

   public Object putIfAbsent(Object key1, Object key2, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key1, key2, (Object)null, (Object)null);
         return this.segmentFor(hash).put(key1, key2, (Object)null, (Object)null, hash, value, true);
      }
   }

   public Object putIfAbsent(Object key1, Object key2, Object key3, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key1, key2, key3, (Object)null);
         return this.segmentFor(hash).put(key1, key2, key3, (Object)null, hash, value, true);
      }
   }

   public Object putIfAbsent(Object key1, Object key2, Object key3, Object key4, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key1, key2, key3, key4);
         return this.segmentFor(hash).put(key1, key2, key3, key4, hash, value, true);
      }
   }

   public Object remove(Object key) {
      int hash = hash(key, (Object)null, (Object)null, (Object)null);
      return this.segmentFor(hash).remove(key, (Object)null, (Object)null, (Object)null, hash, (Object)null);
   }

   public Object remove(Object key1, Object key2) {
      int hash = hash(key1, key2, (Object)null, (Object)null);
      return this.segmentFor(hash).remove(key1, key2, (Object)null, (Object)null, hash, (Object)null);
   }

   public Object remove(Object key1, Object key2, Object key3) {
      int hash = hash(key1, key2, key3, (Object)null);
      return this.segmentFor(hash).remove(key1, key2, (Object)null, (Object)null, hash, (Object)null);
   }

   public Object remove(Object key1, Object key2, Object key3, Object key4) {
      int hash = hash(key1, key2, key3, key4);
      return this.segmentFor(hash).remove(key1, key2, key3, key4, hash, (Object)null);
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      if (oldValue != null && newValue != null) {
         int hash = hash(key, (Object)null, (Object)null, (Object)null);
         return this.segmentFor(hash).replace(key, (Object)null, (Object)null, (Object)null, hash, oldValue, newValue);
      } else {
         throw new NullPointerException();
      }
   }

   public Object replace(Object key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key, (Object)null, (Object)null, (Object)null);
         return this.segmentFor(hash).replace(key, (Object)null, (Object)null, (Object)null, hash, value);
      }
   }

   public void clear() {
      for(int i = 0; i < this.segments.length; ++i) {
         this.segments[i].clear();
      }

   }

   public Set keySet() {
      throw new UnsupportedOperationException();
   }

   public Collection values() {
      throw new UnsupportedOperationException();
   }

   public Set entrySet() {
      throw new UnsupportedOperationException();
   }

   static final class Segment extends ReentrantLock {
      volatile int count;
      int modCount;
      int threshold;
      volatile HashEntry[] table;
      final float loadFactor;

      Segment(int initialCapacity, float lf) {
         this.loadFactor = lf;
         this.setTable(new HashEntry[initialCapacity]);
      }

      void setTable(HashEntry[] newTable) {
         this.threshold = (int)((float)newTable.length * this.loadFactor);
         this.table = newTable;
      }

      HashEntry getFirst(int hash) {
         HashEntry[] tab = this.table;
         return tab[hash & tab.length - 1];
      }

      Object readValueUnderLock(HashEntry e) {
         this.lock();

         Object var2;
         try {
            var2 = e.value;
         } finally {
            this.unlock();
         }

         return var2;
      }

      Object get(Object key1, Object key2, Object key3, Object key4, int hash) {
         if (this.count != 0) {
            for(HashEntry e = this.getFirst(hash); e != null; e = e.next) {
               if (e.hash == hash && key1.equals(e.key1) && (key2 == null && e.key2 == null || key2 != null && key2.equals(e.key2)) && (key3 == null && e.key3 == null || key3 != null && key3.equals(e.key3)) && (key4 == null && e.key4 == null || key4 != null && key4.equals(e.key4))) {
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

      boolean containsKey(Object key1, Object key2, Object key3, Object key4, int hash) {
         if (this.count != 0) {
            for(HashEntry e = this.getFirst(hash); e != null; e = e.next) {
               if (e.hash == hash && key1.equals(e.key1) && (key2 == null && e.key2 == null || key2 != null && key2.equals(e.key2)) && (key3 == null && e.key3 == null || key3 != null && key3.equals(e.key3)) && (key4 == null && e.key4 == null || key4 != null && key4.equals(e.key4))) {
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

      boolean replace(Object key1, Object key2, Object key3, Object key4, int hash, Object oldValue, Object newValue) {
         this.lock();

         boolean var10;
         try {
            HashEntry e;
            for(e = this.getFirst(hash); e != null && (e.hash != hash || key1 != null && !key1.equals(e.key1) || key2 != null && !key2.equals(e.key2) || key3 != null && !key3.equals(e.key3) || key4 != null && !key4.equals(e.key4)); e = e.next) {
            }

            boolean replaced = false;
            if (e != null && oldValue.equals(e.value)) {
               replaced = true;
               e.value = newValue;
            }

            var10 = replaced;
         } finally {
            this.unlock();
         }

         return var10;
      }

      Object replace(Object key1, Object key2, Object key3, Object key4, int hash, Object newValue) {
         this.lock();

         Object var9;
         try {
            HashEntry e;
            for(e = this.getFirst(hash); e != null && (e.hash != hash || key1 != null && !key1.equals(e.key1) || key2 != null && !key2.equals(e.key2) || key3 != null && !key3.equals(e.key3) || key4 != null && !key4.equals(e.key4)); e = e.next) {
            }

            Object oldValue = null;
            if (e != null) {
               oldValue = e.value;
               e.value = newValue;
            }

            var9 = oldValue;
         } finally {
            this.unlock();
         }

         return var9;
      }

      Object put(Object key1, Object key2, Object key3, Object key4, int hash, Object value, boolean onlyIfAbsent) {
         this.lock();

         Object var14;
         try {
            int c = this.count;
            if (c++ > this.threshold) {
               this.rehash();
            }

            HashEntry[] tab = this.table;
            int index = hash & tab.length - 1;
            HashEntry first = tab[index];

            HashEntry e;
            for(e = first; e != null && (e.hash != hash || key1 != null && !key1.equals(e.key1) || key2 != null && !key2.equals(e.key2) || key3 != null && !key3.equals(e.key3) || key4 != null && !key4.equals(e.key4)); e = e.next) {
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
               tab[index] = new HashEntry(key1, key2, key3, key4, hash, first, value);
               this.count = c;
            }

            var14 = oldValue;
         } finally {
            this.unlock();
         }

         return var14;
      }

      void rehash() {
         HashEntry[] oldTable = this.table;
         int oldCapacity = oldTable.length;
         if (oldCapacity < 1073741824) {
            HashEntry[] newTable = new HashEntry[oldCapacity << 1];
            this.threshold = (int)((float)newTable.length * this.loadFactor);
            int sizeMask = newTable.length - 1;

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
                     int k;
                     for(p = next; p != null; p = p.next) {
                        k = p.hash & sizeMask;
                        if (k != lastIdx) {
                           lastIdx = k;
                           lastRun = p;
                        }
                     }

                     newTable[lastIdx] = lastRun;

                     for(p = e; p != lastRun; p = p.next) {
                        k = p.hash & sizeMask;
                        HashEntry n = newTable[k];
                        newTable[k] = new HashEntry(p.key1, p.key2, p.key3, p.key4, p.hash, n, p.value);
                     }
                  }
               }
            }

            this.table = newTable;
         }
      }

      Object remove(Object key1, Object key2, Object key3, Object key4, int hash, Object value) {
         this.lock();

         Object v;
         try {
            int c = this.count - 1;
            HashEntry[] tab = this.table;
            int index = hash & tab.length - 1;
            HashEntry first = tab[index];

            HashEntry e;
            for(e = first; e != null && (e.hash != hash || key1 != null && !key1.equals(e.key1) || key2 != null && !key2.equals(e.key2) || key3 != null && !key3.equals(e.key3) || key4 != null && !key4.equals(e.key4)); e = e.next) {
            }

            Object oldValue = null;
            if (e != null) {
               v = e.value;
               if (value == null || value.equals(v)) {
                  oldValue = v;
                  ++this.modCount;
                  HashEntry newFirst = e.next;

                  for(HashEntry p = first; p != e; p = p.next) {
                     newFirst = new HashEntry(p.key1, p.key2, p.key3, p.key4, p.hash, newFirst, p.value);
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

      void clear() {
         if (this.count != 0) {
            this.lock();

            try {
               HashEntry[] tab = this.table;

               for(int i = 0; i < tab.length; ++i) {
                  tab[i] = null;
               }

               ++this.modCount;
               this.count = 0;
            } finally {
               this.unlock();
            }
         }

      }
   }

   static final class HashEntry {
      final Object key1;
      final Object key2;
      final Object key3;
      final Object key4;
      final int hash;
      volatile Object value;
      final HashEntry next;

      HashEntry(Object key1, Object key2, Object key3, Object key4, int hash, HashEntry next, Object value) {
         this.key1 = key1;
         this.key2 = key2;
         this.key3 = key3;
         this.key4 = key4;
         this.hash = hash;
         this.next = next;
         this.value = value;
      }
   }
}
