package org.apache.openjpa.lib.util.concurrent;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import org.apache.openjpa.lib.util.ReferenceMap;
import org.apache.openjpa.lib.util.SizedMap;

public class ConcurrentReferenceHashMap extends AbstractMap implements ConcurrentMap, ReferenceMap, SizedMap, Cloneable {
   static final double[] RANDOMS = new double[1000];
   private transient Entry[] table;
   private transient int count;
   private int threshold;
   private float loadFactor;
   private int keyType;
   private int valueType;
   private final ReferenceQueue queue;
   private int randomEntry;
   private int maxSize;
   private transient Set keySet;
   private transient Set entrySet;
   private transient Collection values;
   private static final int KEYS = 0;
   private static final int VALUES = 1;
   private static final int ENTRIES = 2;

   protected boolean eq(Object x, Object y) {
      return x == y || x != null && x.equals(y);
   }

   protected int hc(Object o) {
      return o == null ? 0 : o.hashCode();
   }

   public ConcurrentReferenceHashMap(int keyType, int valueType, int initialCapacity, float loadFactor) {
      this.queue = new ReferenceQueue();
      this.randomEntry = 0;
      this.maxSize = Integer.MAX_VALUE;
      this.keySet = null;
      this.entrySet = null;
      this.values = null;
      if (initialCapacity < 0) {
         throw new IllegalArgumentException("Illegal Initial Capacity: " + initialCapacity);
      } else if (!(loadFactor > 1.0F) && !(loadFactor <= 0.0F)) {
         if (keyType != 0 && valueType != 0) {
            throw new IllegalArgumentException("Either keys or values must use hard references.");
         } else {
            this.keyType = keyType;
            this.valueType = valueType;
            this.loadFactor = loadFactor;
            this.table = new Entry[initialCapacity];
            this.threshold = (int)((float)initialCapacity * loadFactor);
         }
      } else {
         throw new IllegalArgumentException("Illegal Load factor: " + loadFactor);
      }
   }

   public ConcurrentReferenceHashMap(int keyType, int valueType, int initialCapacity) {
      this(keyType, valueType, initialCapacity, 0.75F);
   }

   public ConcurrentReferenceHashMap(int keyType, int valueType) {
      this(keyType, valueType, 11, 0.75F);
   }

   public ConcurrentReferenceHashMap(int keyType, int valueType, Map t) {
      this(keyType, valueType, Math.max(3 * t.size(), 11), 0.75F);
      this.putAll(t);
   }

   public int getMaxSize() {
      return this.maxSize;
   }

   public void setMaxSize(int maxSize) {
      this.maxSize = maxSize < 0 ? Integer.MAX_VALUE : maxSize;
      if (this.maxSize != Integer.MAX_VALUE) {
         this.removeOverflow(this.maxSize);
      }

   }

   public boolean isFull() {
      return this.maxSize != Integer.MAX_VALUE && this.size() >= this.maxSize;
   }

   public void overflowRemoved(Object key, Object value) {
   }

   public int size() {
      return this.count;
   }

   public boolean isEmpty() {
      return this.count == 0;
   }

   public boolean containsValue(Object value) {
      Entry[] tab = this.table;
      int i;
      Entry e;
      if (value == null) {
         if (this.valueType != 0) {
            return false;
         }

         i = tab.length;

         while(i-- > 0) {
            for(e = tab[i]; e != null; e = e.getNext()) {
               if (e.getValue() == null) {
                  return true;
               }
            }
         }
      } else {
         i = tab.length;

         while(i-- > 0) {
            for(e = tab[i]; e != null; e = e.getNext()) {
               if (this.eq(value, e.getValue())) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean containsKey(Object key) {
      if (key == null && this.keyType != 0) {
         return false;
      } else {
         Entry[] tab = this.table;
         int hash = this.hc(key);
         int index = (hash & Integer.MAX_VALUE) % tab.length;

         for(Entry e = tab[index]; e != null; e = e.getNext()) {
            if (e.getHash() == hash && this.eq(key, e.getKey())) {
               return true;
            }
         }

         return false;
      }
   }

   public Object get(Object key) {
      if (key == null && this.keyType != 0) {
         return null;
      } else {
         Entry[] tab = this.table;
         int hash = this.hc(key);
         int index = (hash & Integer.MAX_VALUE) % tab.length;

         for(Entry e = tab[index]; e != null; e = e.getNext()) {
            if (e.getHash() == hash && this.eq(key, e.getKey())) {
               return e.getValue();
            }
         }

         return null;
      }
   }

   private void rehash() {
      int oldCapacity = this.table.length;
      Entry[] oldMap = this.table;
      int newCapacity = oldCapacity * 2 + 1;
      Entry[] newMap = new Entry[newCapacity];
      int i = oldCapacity;

      label32:
      while(i-- > 0) {
         Entry old = oldMap[i];

         while(true) {
            while(true) {
               if (old == null) {
                  continue label32;
               }

               Entry e;
               if (this.keyType != 0 && old.getKey() == null || this.valueType != 0 && old.getValue() == null) {
                  e = old;
                  old = old.getNext();
                  e.setNext((Entry)null);
                  --this.count;
               } else {
                  e = (Entry)old.clone(this.queue);
                  old = old.getNext();
                  int index = (e.getHash() & Integer.MAX_VALUE) % newCapacity;
                  e.setNext(newMap[index]);
                  newMap[index] = e;
               }
            }
         }
      }

      this.threshold = (int)((float)newCapacity * this.loadFactor);
      this.table = newMap;
   }

   public Object put(Object key, Object value) {
      if (key == null && this.keyType != 0 || value == null && this.valueType != 0) {
         throw new IllegalArgumentException("Null references not supported");
      } else {
         int hash = this.hc(key);
         synchronized(this) {
            this.expungeStaleEntries();
            Entry[] tab = this.table;
            int index = false;
            int index = (hash & Integer.MAX_VALUE) % tab.length;
            Entry e = tab[index];

            for(Entry prev = null; e != null; e = e.getNext()) {
               if (e.getHash() == hash && this.eq(key, e.getKey())) {
                  Object old = e.getValue();
                  if (this.valueType == 0) {
                     e.setValue(value);
                  } else {
                     e = this.newEntry(hash, e.getKey(), value, e.getNext());
                     if (prev == null) {
                        tab[index] = e;
                     } else {
                        prev.setNext(e);
                     }
                  }

                  return old;
               }

               prev = e;
            }

            if (this.count >= this.threshold) {
               this.rehash();
               tab = this.table;
               index = (hash & Integer.MAX_VALUE) % tab.length;
            }

            if (this.maxSize != Integer.MAX_VALUE) {
               this.removeOverflow(this.maxSize - 1);
            }

            tab[index] = this.newEntry(hash, key, value, tab[index]);
            ++this.count;
            return null;
         }
      }
   }

   private Entry newEntry(int hash, Object key, Object value, Entry next) {
      int refType = this.keyType != 0 ? this.keyType : this.valueType;
      switch (refType) {
         case 1:
            return new WeakEntry(hash, key, value, refType == this.keyType, next, this.queue);
         case 2:
            return new SoftEntry(hash, key, value, refType == this.keyType, next, this.queue);
         default:
            return new HardEntry(hash, key, value, next);
      }
   }

   private void removeOverflow(int maxSize) {
      while(true) {
         if (this.count > maxSize) {
            Map.Entry entry = this.removeRandom();
            if (entry != null) {
               this.overflowRemoved(entry.getKey(), entry.getValue());
               continue;
            }
         }

         return;
      }
   }

   public Object remove(Object key) {
      if (key == null && this.keyType != 0) {
         return null;
      } else {
         int hash = this.hc(key);
         synchronized(this) {
            this.expungeStaleEntries();
            Entry[] tab = this.table;
            int index = (hash & Integer.MAX_VALUE) % tab.length;
            Entry e = tab[index];

            for(Entry prev = null; e != null; e = e.getNext()) {
               if (e.getHash() == hash && this.eq(key, e.getKey())) {
                  if (prev != null) {
                     prev.setNext(e.getNext());
                  } else {
                     tab[index] = e.getNext();
                  }

                  --this.count;
                  return e.getValue();
               }

               prev = e;
            }

            return null;
         }
      }
   }

   public void removeExpired() {
      synchronized(this) {
         this.expungeStaleEntries();
      }
   }

   public void keyExpired(Object value) {
   }

   public void valueExpired(Object key) {
   }

   private int randomEntryIndex() {
      if (this.randomEntry == RANDOMS.length) {
         this.randomEntry = 0;
      }

      return (int)(RANDOMS[this.randomEntry++] * (double)this.table.length);
   }

   public Map.Entry removeRandom() {
      synchronized(this) {
         this.expungeStaleEntries();
         if (this.count == 0) {
            return null;
         } else {
            int random = this.randomEntryIndex();
            int index = this.findEntry(random, random % 2 == 0, false);
            if (index == -1) {
               return null;
            } else {
               Entry rem = this.table[index];
               this.table[index] = rem.getNext();
               --this.count;
               return rem;
            }
         }
      }
   }

   private int findEntry(int start, boolean forward, boolean searchedOther) {
      int i;
      if (forward) {
         for(i = start; i < this.table.length; ++i) {
            if (this.table[i] != null) {
               return i;
            }
         }

         return !searchedOther && start != 0 ? this.findEntry(start - 1, false, true) : -1;
      } else {
         for(i = start; i >= 0; --i) {
            if (this.table[i] != null) {
               return i;
            }
         }

         return !searchedOther && start != this.table.length - 1 ? this.findEntry(start + 1, true, true) : -1;
      }
   }

   public Iterator randomEntryIterator() {
      return new HashIterator(2, this.randomEntryIndex());
   }

   public void putAll(Map t) {
      Iterator i = t.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry e = (Map.Entry)i.next();
         this.put(e.getKey(), e.getValue());
      }

   }

   public synchronized void clear() {
      while(this.queue.poll() != null) {
      }

      this.table = new Entry[this.table.length];
      this.count = 0;

      while(this.queue.poll() != null) {
      }

   }

   public synchronized Object clone() {
      try {
         this.expungeStaleEntries();
         ConcurrentReferenceHashMap t = (ConcurrentReferenceHashMap)super.clone();
         t.table = new Entry[this.table.length];
         int i = this.table.length;

         while(true) {
            Entry e;
            do {
               if (i-- <= 0) {
                  t.keySet = null;
                  t.entrySet = null;
                  t.values = null;
                  return t;
               }

               e = this.table[i];
            } while(e == null);

            t.table[i] = (Entry)e.clone(t.queue);
            e = e.getNext();

            for(Entry k = t.table[i]; e != null; e = e.getNext()) {
               k.setNext((Entry)e.clone(t.queue));
               k = k.getNext();
            }
         }
      } catch (CloneNotSupportedException var5) {
         throw new InternalError();
      }
   }

   public Set keySet() {
      if (this.keySet == null) {
         this.keySet = new AbstractSet() {
            public Iterator iterator() {
               return ConcurrentReferenceHashMap.this.new HashIterator(0, ConcurrentReferenceHashMap.this.table.length - 1);
            }

            public int size() {
               return ConcurrentReferenceHashMap.this.count;
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
         };
      }

      return this.keySet;
   }

   public Collection values() {
      if (this.values == null) {
         this.values = new AbstractCollection() {
            public Iterator iterator() {
               return ConcurrentReferenceHashMap.this.new HashIterator(1, ConcurrentReferenceHashMap.this.table.length - 1);
            }

            public int size() {
               return ConcurrentReferenceHashMap.this.count;
            }

            public boolean contains(Object o) {
               return ConcurrentReferenceHashMap.this.containsValue(o);
            }

            public void clear() {
               ConcurrentReferenceHashMap.this.clear();
            }
         };
      }

      return this.values;
   }

   public Set entrySet() {
      if (this.entrySet == null) {
         this.entrySet = new AbstractSet() {
            public Iterator iterator() {
               return ConcurrentReferenceHashMap.this.new HashIterator(2, ConcurrentReferenceHashMap.this.table.length - 1);
            }

            public boolean contains(Object o) {
               if (!(o instanceof Map.Entry)) {
                  return false;
               } else {
                  Map.Entry entry = (Map.Entry)o;
                  Object key = entry.getKey();
                  Entry[] tab = ConcurrentReferenceHashMap.this.table;
                  int hash = ConcurrentReferenceHashMap.this.hc(key);
                  int index = (hash & Integer.MAX_VALUE) % tab.length;

                  for(Entry e = tab[index]; e != null; e = e.getNext()) {
                     if (e.getHash() == hash && ConcurrentReferenceHashMap.this.eq(e, entry)) {
                        return true;
                     }
                  }

                  return false;
               }
            }

            public boolean remove(Object o) {
               if (!(o instanceof Map.Entry)) {
                  return false;
               } else {
                  Map.Entry entry = (Map.Entry)o;
                  Object key = entry.getKey();
                  synchronized(ConcurrentReferenceHashMap.this) {
                     Entry[] tab = ConcurrentReferenceHashMap.this.table;
                     int hash = ConcurrentReferenceHashMap.this.hc(key);
                     int index = (hash & Integer.MAX_VALUE) % tab.length;
                     Entry e = tab[index];

                     for(Entry prev = null; e != null; e = e.getNext()) {
                        if (e.getHash() == hash && ConcurrentReferenceHashMap.this.eq(e, entry)) {
                           if (prev != null) {
                              prev.setNext(e.getNext());
                           } else {
                              tab[index] = e.getNext();
                           }

                           ConcurrentReferenceHashMap.this.count--;
                           return true;
                        }

                        prev = e;
                     }

                     return false;
                  }
               }
            }

            public int size() {
               return ConcurrentReferenceHashMap.this.count;
            }

            public void clear() {
               ConcurrentReferenceHashMap.this.clear();
            }
         };
      }

      return this.entrySet;
   }

   private void expungeStaleEntries() {
      label28:
      while(true) {
         Reference r;
         if ((r = this.queue.poll()) != null) {
            Entry entry = (Entry)r;
            int hash = entry.getHash();
            Entry[] tab = this.table;
            int index = (hash & Integer.MAX_VALUE) % tab.length;
            Entry e = tab[index];
            Entry prev = null;

            while(true) {
               if (e == null) {
                  continue label28;
               }

               if (e == entry) {
                  if (prev != null) {
                     prev.setNext(e.getNext());
                  } else {
                     tab[index] = e.getNext();
                  }

                  --this.count;
                  if (this.keyType == 0) {
                     this.valueExpired(e.getKey());
                  } else {
                     this.keyExpired(e.getValue());
                  }
               }

               prev = e;
               e = e.getNext();
            }
         }

         return;
      }
   }

   int capacity() {
      return this.table.length;
   }

   float loadFactor() {
      return this.loadFactor;
   }

   static {
      Random random = new Random();

      for(int i = 0; i < RANDOMS.length; ++i) {
         RANDOMS[i] = random.nextDouble();
      }

   }

   private class HashIterator implements Iterator {
      final Entry[] table;
      final int type;
      int startIndex;
      int stopIndex;
      int index;
      Entry entry;
      Entry lastReturned;

      HashIterator(int type, int startIndex) {
         this.table = ConcurrentReferenceHashMap.this.table;
         this.stopIndex = 0;
         this.entry = null;
         this.lastReturned = null;
         this.type = type;
         this.startIndex = startIndex;
         this.index = startIndex;
      }

      public boolean hasNext() {
         if (this.entry != null) {
            return true;
         } else {
            while(this.index >= this.stopIndex) {
               if ((this.entry = this.table[this.index--]) != null) {
                  return true;
               }
            }

            if (this.stopIndex == 0) {
               this.index = this.table.length - 1;
               this.stopIndex = this.startIndex + 1;

               while(this.index >= this.stopIndex) {
                  if ((this.entry = this.table[this.index--]) != null) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Entry e = this.lastReturned = this.entry;
            this.entry = e.getNext();
            return this.type == 0 ? e.getKey() : (this.type == 1 ? e.getValue() : e);
         }
      }

      public void remove() {
         if (this.lastReturned == null) {
            throw new IllegalStateException();
         } else {
            synchronized(ConcurrentReferenceHashMap.this) {
               Entry[] tab = ConcurrentReferenceHashMap.this.table;
               int index = (this.lastReturned.getHash() & Integer.MAX_VALUE) % tab.length;
               Entry e = tab[index];

               for(Entry prev = null; e != null; e = e.getNext()) {
                  if (e == this.lastReturned) {
                     if (prev == null) {
                        tab[index] = e.getNext();
                     } else {
                        prev.setNext(e.getNext());
                     }

                     ConcurrentReferenceHashMap.this.count--;
                     this.lastReturned = null;
                     return;
                  }

                  prev = e;
               }

               throw new Error("Iterated off table when doing remove");
            }
         }
      }
   }

   private class SoftEntry extends SoftReference implements Entry {
      private int hash;
      private Object hard;
      private boolean keyRef;
      private Entry next;

      SoftEntry(int hash, Object key, Object value, boolean keyRef, Entry next, ReferenceQueue queue) {
         super(keyRef ? key : value, queue);
         this.hash = hash;
         this.hard = keyRef ? value : key;
         this.keyRef = keyRef;
         this.next = next;
      }

      public int getHash() {
         return this.hash;
      }

      public Entry getNext() {
         return this.next;
      }

      public void setNext(Entry next) {
         this.next = next;
      }

      public Object clone(ReferenceQueue queue) {
         return ConcurrentReferenceHashMap.this.new SoftEntry(this.hash, this.getKey(), this.getValue(), this.keyRef, (Entry)null, queue);
      }

      public Object getKey() {
         return this.keyRef ? super.get() : this.hard;
      }

      public Object getValue() {
         return this.keyRef ? this.hard : super.get();
      }

      public Object setValue(Object value) {
         if (!this.keyRef) {
            throw new Error("Attempt to reset reference value.");
         } else {
            Object oldValue = this.hard;
            this.hard = value;
            return oldValue;
         }
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            return ConcurrentReferenceHashMap.this.eq(this.getKey(), e.getKey()) && ConcurrentReferenceHashMap.this.eq(this.getValue(), e.getValue());
         }
      }

      public int hashCode() {
         Object val = this.getValue();
         return this.hash ^ (val == null ? 0 : val.hashCode());
      }

      public String toString() {
         return this.getKey() + "=" + this.getValue();
      }
   }

   private class WeakEntry extends WeakReference implements Entry {
      private int hash;
      private Object hard;
      private boolean keyRef;
      private Entry next;

      WeakEntry(int hash, Object key, Object value, boolean keyRef, Entry next, ReferenceQueue queue) {
         super(keyRef ? key : value, queue);
         this.hash = hash;
         this.hard = keyRef ? value : key;
         this.keyRef = keyRef;
         this.next = next;
      }

      public int getHash() {
         return this.hash;
      }

      public Entry getNext() {
         return this.next;
      }

      public void setNext(Entry next) {
         this.next = next;
      }

      public Object clone(ReferenceQueue queue) {
         return ConcurrentReferenceHashMap.this.new WeakEntry(this.hash, this.getKey(), this.getValue(), this.keyRef, (Entry)null, queue);
      }

      public Object getKey() {
         return this.keyRef ? super.get() : this.hard;
      }

      public Object getValue() {
         return this.keyRef ? this.hard : super.get();
      }

      public Object setValue(Object value) {
         if (!this.keyRef) {
            throw new Error("Attempt to reset reference value.");
         } else {
            Object oldValue = this.hard;
            this.hard = value;
            return oldValue;
         }
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            return ConcurrentReferenceHashMap.this.eq(this.getKey(), e.getKey()) && ConcurrentReferenceHashMap.this.eq(this.getValue(), e.getValue());
         }
      }

      public int hashCode() {
         Object val = this.getValue();
         return this.hash ^ (val == null ? 0 : val.hashCode());
      }

      public String toString() {
         return this.getKey() + "=" + this.getValue();
      }
   }

   private class HardEntry implements Entry {
      private int hash;
      private Object key;
      private Object value;
      private Entry next;

      HardEntry(int hash, Object key, Object value, Entry next) {
         this.hash = hash;
         this.key = key;
         this.value = value;
         this.next = next;
      }

      public int getHash() {
         return this.hash;
      }

      public Entry getNext() {
         return this.next;
      }

      public void setNext(Entry next) {
         this.next = next;
      }

      public Object clone(ReferenceQueue queue) {
         return ConcurrentReferenceHashMap.this.new HardEntry(this.hash, this.key, this.value, (Entry)null);
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
            boolean var10000;
            label38: {
               label27: {
                  Map.Entry e = (Map.Entry)o;
                  Object k1 = this.key;
                  Object k2 = e.getKey();
                  if (k1 == null) {
                     if (k2 != null) {
                        break label27;
                     }
                  } else if (!ConcurrentReferenceHashMap.this.eq(k1, k2)) {
                     break label27;
                  }

                  if (this.value == null) {
                     if (e.getValue() == null) {
                        break label38;
                     }
                  } else if (ConcurrentReferenceHashMap.this.eq(this.value, e.getValue())) {
                     break label38;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      }

      public int hashCode() {
         return this.hash ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public String toString() {
         return this.key + "=" + this.value.toString();
      }
   }

   private interface Entry extends Map.Entry {
      int getHash();

      Entry getNext();

      void setNext(Entry var1);

      Object clone(ReferenceQueue var1);
   }
}
