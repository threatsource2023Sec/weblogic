package com.bea.xbean.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ConcurrentReaderHashMap extends AbstractMap implements Map, Cloneable, Serializable {
   protected final BarrierLock barrierLock;
   protected transient Object lastWrite;
   public static int DEFAULT_INITIAL_CAPACITY = 32;
   private static final int MINIMUM_CAPACITY = 4;
   private static final int MAXIMUM_CAPACITY = 1073741824;
   public static final float DEFAULT_LOAD_FACTOR = 0.75F;
   protected transient Entry[] table;
   protected transient int count;
   protected int threshold;
   protected float loadFactor;
   protected transient Set keySet;
   protected transient Set entrySet;
   protected transient Collection values;

   protected final void recordModification(Object x) {
      synchronized(this.barrierLock) {
         this.lastWrite = x;
      }
   }

   protected final Entry[] getTableForReading() {
      synchronized(this.barrierLock) {
         return this.table;
      }
   }

   private int p2capacity(int initialCapacity) {
      int cap = initialCapacity;
      int result;
      if (initialCapacity <= 1073741824 && initialCapacity >= 0) {
         for(result = 4; result < cap; result <<= 1) {
         }
      } else {
         result = 1073741824;
      }

      return result;
   }

   private static int hash(Object x) {
      int h = x.hashCode();
      return (h << 7) - h + (h >>> 9) + (h >>> 17);
   }

   protected boolean eq(Object x, Object y) {
      return x == y || x.equals(y);
   }

   public ConcurrentReaderHashMap(int initialCapacity, float loadFactor) {
      this.barrierLock = new BarrierLock();
      this.keySet = null;
      this.entrySet = null;
      this.values = null;
      if (loadFactor <= 0.0F) {
         throw new IllegalArgumentException("Illegal Load factor: " + loadFactor);
      } else {
         this.loadFactor = loadFactor;
         int cap = this.p2capacity(initialCapacity);
         this.table = new Entry[cap];
         this.threshold = (int)((float)cap * loadFactor);
      }
   }

   public ConcurrentReaderHashMap(int initialCapacity) {
      this(initialCapacity, 0.75F);
   }

   public ConcurrentReaderHashMap() {
      this(DEFAULT_INITIAL_CAPACITY, 0.75F);
   }

   public ConcurrentReaderHashMap(Map t) {
      this(Math.max((int)((float)t.size() / 0.75F) + 1, 16), 0.75F);
      this.putAll(t);
   }

   public synchronized int size() {
      return this.count;
   }

   public synchronized boolean isEmpty() {
      return this.count == 0;
   }

   public Object get(Object key) {
      int hash = hash(key);
      Entry[] tab = this.table;
      int index = hash & tab.length - 1;
      Entry first = tab[index];
      Entry e = first;

      while(true) {
         while(e != null) {
            if (e.hash == hash && this.eq(key, e.key)) {
               Object value = e.value;
               if (value != null) {
                  return value;
               }

               synchronized(this) {
                  tab = this.table;
               }

               e = first = tab[index = hash & tab.length - 1];
            } else {
               e = e.next;
            }
         }

         Entry[] reread = this.getTableForReading();
         if (tab == reread && first == tab[index]) {
            return null;
         }

         tab = reread;
         e = first = reread[index = hash & reread.length - 1];
      }
   }

   public boolean containsKey(Object key) {
      return this.get(key) != null;
   }

   public Object put(Object key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int hash = hash(key);
         Entry[] tab = this.table;
         int index = hash & tab.length - 1;
         Entry first = tab[index];

         Entry e;
         for(e = first; e != null && (e.hash != hash || !this.eq(key, e.key)); e = e.next) {
         }

         synchronized(this) {
            if (tab == this.table) {
               if (e == null) {
                  if (first == tab[index]) {
                     Entry newEntry = new Entry(hash, key, value, first);
                     tab[index] = newEntry;
                     if (++this.count >= this.threshold) {
                        this.rehash();
                     } else {
                        this.recordModification(newEntry);
                     }

                     return null;
                  }
               } else {
                  Object oldValue = e.value;
                  if (first == tab[index] && oldValue != null) {
                     e.value = value;
                     return oldValue;
                  }
               }
            }

            return this.sput(key, value, hash);
         }
      }
   }

   protected Object sput(Object key, Object value, int hash) {
      Entry[] tab = this.table;
      int index = hash & tab.length - 1;
      Entry first = tab[index];

      for(Entry e = first; e != null; e = e.next) {
         if (e.hash == hash && this.eq(key, e.key)) {
            Object oldValue = e.value;
            e.value = value;
            return oldValue;
         }
      }

      Entry newEntry = new Entry(hash, key, value, first);
      tab[index] = newEntry;
      if (++this.count >= this.threshold) {
         this.rehash();
      } else {
         this.recordModification(newEntry);
      }

      return null;
   }

   protected void rehash() {
      Entry[] oldTable = this.table;
      int oldCapacity = oldTable.length;
      if (oldCapacity >= 1073741824) {
         this.threshold = Integer.MAX_VALUE;
      } else {
         int newCapacity = oldCapacity << 1;
         int mask = newCapacity - 1;
         this.threshold = (int)((float)newCapacity * this.loadFactor);
         Entry[] newTable = new Entry[newCapacity];

         for(int i = 0; i < oldCapacity; ++i) {
            Entry e = oldTable[i];
            if (e != null) {
               int idx = e.hash & mask;
               Entry next = e.next;
               if (next == null) {
                  newTable[idx] = e;
               } else {
                  Entry lastRun = e;
                  int lastIdx = idx;

                  Entry p;
                  int k;
                  for(p = next; p != null; p = p.next) {
                     k = p.hash & mask;
                     if (k != lastIdx) {
                        lastIdx = k;
                        lastRun = p;
                     }
                  }

                  newTable[lastIdx] = lastRun;

                  for(p = e; p != lastRun; p = p.next) {
                     k = p.hash & mask;
                     newTable[k] = new Entry(p.hash, p.key, p.value, newTable[k]);
                  }
               }
            }
         }

         this.table = newTable;
         this.recordModification(newTable);
      }
   }

   public Object remove(Object key) {
      int hash = hash(key);
      Entry[] tab = this.table;
      int index = hash & tab.length - 1;
      Entry first = tab[index];

      Entry e;
      for(e = first; e != null && (e.hash != hash || !this.eq(key, e.key)); e = e.next) {
      }

      synchronized(this) {
         if (tab == this.table) {
            if (e == null) {
               if (first == tab[index]) {
                  return null;
               }
            } else {
               Object oldValue = e.value;
               if (first == tab[index] && oldValue != null) {
                  e.value = null;
                  --this.count;
                  Entry head = e.next;

                  for(Entry p = first; p != e; p = p.next) {
                     head = new Entry(p.hash, p.key, p.value, head);
                  }

                  tab[index] = head;
                  this.recordModification(head);
                  return oldValue;
               }
            }
         }

         return this.sremove(key, hash);
      }
   }

   protected Object sremove(Object key, int hash) {
      Entry[] tab = this.table;
      int index = hash & tab.length - 1;
      Entry first = tab[index];

      for(Entry e = first; e != null; e = e.next) {
         if (e.hash == hash && this.eq(key, e.key)) {
            Object oldValue = e.value;
            e.value = null;
            --this.count;
            Entry head = e.next;

            for(Entry p = first; p != e; p = p.next) {
               head = new Entry(p.hash, p.key, p.value, head);
            }

            tab[index] = head;
            this.recordModification(head);
            return oldValue;
         }
      }

      return null;
   }

   public boolean containsValue(Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         Entry[] tab = this.getTableForReading();

         for(int i = 0; i < tab.length; ++i) {
            for(Entry e = tab[i]; e != null; e = e.next) {
               if (value.equals(e.value)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean contains(Object value) {
      return this.containsValue(value);
   }

   public synchronized void putAll(Map t) {
      int n = t.size();
      if (n != 0) {
         while(n >= this.threshold) {
            this.rehash();
         }

         Iterator it = t.entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            this.put(key, value);
         }

      }
   }

   public synchronized void clear() {
      Entry[] tab = this.table;

      for(int i = 0; i < tab.length; ++i) {
         for(Entry e = tab[i]; e != null; e = e.next) {
            e.value = null;
         }

         tab[i] = null;
      }

      this.count = 0;
      this.recordModification(tab);
   }

   public synchronized Object clone() {
      try {
         ConcurrentReaderHashMap t = (ConcurrentReaderHashMap)super.clone();
         t.keySet = null;
         t.entrySet = null;
         t.values = null;
         Entry[] tab = this.table;
         t.table = new Entry[tab.length];
         Entry[] ttab = t.table;

         for(int i = 0; i < tab.length; ++i) {
            Entry first = null;

            for(Entry e = tab[i]; e != null; e = e.next) {
               first = new Entry(e.hash, e.key, e.value, first);
            }

            ttab[i] = first;
         }

         return t;
      } catch (CloneNotSupportedException var7) {
         throw new InternalError();
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

   protected synchronized boolean findAndRemoveEntry(Map.Entry entry) {
      Object key = entry.getKey();
      Object v = this.get(key);
      if (v != null && v.equals(entry.getValue())) {
         this.remove(key);
         return true;
      } else {
         return false;
      }
   }

   public Enumeration keys() {
      return new KeyIterator();
   }

   public Enumeration elements() {
      return new ValueIterator();
   }

   private synchronized void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      s.writeInt(this.table.length);
      s.writeInt(this.count);

      for(int index = this.table.length - 1; index >= 0; --index) {
         for(Entry entry = this.table[index]; entry != null; entry = entry.next) {
            s.writeObject(entry.key);
            s.writeObject(entry.value);
         }
      }

   }

   private synchronized void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      int numBuckets = s.readInt();
      this.table = new Entry[numBuckets];
      int size = s.readInt();

      for(int i = 0; i < size; ++i) {
         Object key = s.readObject();
         Object value = s.readObject();
         this.put(key, value);
      }

   }

   public synchronized int capacity() {
      return this.table.length;
   }

   public float loadFactor() {
      return this.loadFactor;
   }

   protected class ValueIterator extends HashIterator {
      protected ValueIterator() {
         super();
      }

      protected Object returnValueOfNext() {
         return this.currentValue;
      }
   }

   protected class KeyIterator extends HashIterator {
      protected KeyIterator() {
         super();
      }

      protected Object returnValueOfNext() {
         return this.currentKey;
      }
   }

   protected class HashIterator implements Iterator, Enumeration {
      protected final Entry[] tab = ConcurrentReaderHashMap.this.getTableForReading();
      protected int index;
      protected Entry entry = null;
      protected Object currentKey;
      protected Object currentValue;
      protected Entry lastReturned = null;

      protected HashIterator() {
         this.index = this.tab.length - 1;
      }

      public boolean hasMoreElements() {
         return this.hasNext();
      }

      public Object nextElement() {
         return this.next();
      }

      public boolean hasNext() {
         do {
            if (this.entry != null) {
               Object v = this.entry.value;
               if (v != null) {
                  this.currentKey = this.entry.key;
                  this.currentValue = v;
                  return true;
               }

               this.entry = this.entry.next;
            }

            while(this.entry == null && this.index >= 0) {
               this.entry = this.tab[this.index--];
            }
         } while(this.entry != null);

         this.currentKey = this.currentValue = null;
         return false;
      }

      protected Object returnValueOfNext() {
         return this.entry;
      }

      public Object next() {
         if (this.currentKey == null && !this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Object result = this.returnValueOfNext();
            this.lastReturned = this.entry;
            this.currentKey = this.currentValue = null;
            this.entry = this.entry.next;
            return result;
         }
      }

      public void remove() {
         if (this.lastReturned == null) {
            throw new IllegalStateException();
         } else {
            ConcurrentReaderHashMap.this.remove(this.lastReturned.key);
            this.lastReturned = null;
         }
      }
   }

   protected static class Entry implements Map.Entry {
      protected final int hash;
      protected final Object key;
      protected final Entry next;
      protected volatile Object value;

      Entry(int hash, Object key, Object value, Entry next) {
         this.hash = hash;
         this.key = key;
         this.next = next;
         this.value = value;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         if (value == null) {
            throw new NullPointerException();
         } else {
            Object oldValue = this.value;
            this.value = value;
            return oldValue;
         }
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            return this.key.equals(e.getKey()) && this.value.equals(e.getValue());
         }
      }

      public int hashCode() {
         return this.key.hashCode() ^ this.value.hashCode();
      }

      public String toString() {
         return this.key + "=" + this.value;
      }
   }

   private class EntrySet extends AbstractSet {
      private EntrySet() {
      }

      public Iterator iterator() {
         return ConcurrentReaderHashMap.this.new HashIterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry entry = (Map.Entry)o;
            Object v = ConcurrentReaderHashMap.this.get(entry.getKey());
            return v != null && v.equals(entry.getValue());
         }
      }

      public boolean remove(Object o) {
         return !(o instanceof Map.Entry) ? false : ConcurrentReaderHashMap.this.findAndRemoveEntry((Map.Entry)o);
      }

      public int size() {
         return ConcurrentReaderHashMap.this.size();
      }

      public void clear() {
         ConcurrentReaderHashMap.this.clear();
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }

   private class Values extends AbstractCollection {
      private Values() {
      }

      public Iterator iterator() {
         return ConcurrentReaderHashMap.this.new ValueIterator();
      }

      public int size() {
         return ConcurrentReaderHashMap.this.size();
      }

      public boolean contains(Object o) {
         return ConcurrentReaderHashMap.this.containsValue(o);
      }

      public void clear() {
         ConcurrentReaderHashMap.this.clear();
      }

      // $FF: synthetic method
      Values(Object x1) {
         this();
      }
   }

   private class KeySet extends AbstractSet {
      private KeySet() {
      }

      public Iterator iterator() {
         return ConcurrentReaderHashMap.this.new KeyIterator();
      }

      public int size() {
         return ConcurrentReaderHashMap.this.size();
      }

      public boolean contains(Object o) {
         return ConcurrentReaderHashMap.this.containsKey(o);
      }

      public boolean remove(Object o) {
         return ConcurrentReaderHashMap.this.remove(o) != null;
      }

      public void clear() {
         ConcurrentReaderHashMap.this.clear();
      }

      // $FF: synthetic method
      KeySet(Object x1) {
         this();
      }
   }

   protected static class BarrierLock implements Serializable {
   }
}
