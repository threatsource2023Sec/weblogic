package com.bea.common.security.internal.utils.collections;

import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.internal.utils.UnsyncStringBuffer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ConcurrentHashMap extends AbstractMap implements Map, Cloneable, Serializable {
   private static final int DEFAULT_INITIAL_CAPACITY = 16;
   private static final int MAXIMUM_CAPACITY = 1073741824;
   private static final float DEFAULT_LOAD_FACTOR = 0.75F;
   private transient Entry[] table;
   private transient int size;
   private int threshold;
   private final float loadFactor;
   private static final Object NULL_KEY = new Object();
   private transient Set entrySet;
   private transient Set keySet;
   private transient Collection values;
   private static final long serialVersionUID = -6452706556724125778L;

   public ConcurrentHashMap(int initialCapacity, float loadFactor) {
      this.entrySet = null;
      this.keySet = null;
      this.values = null;
      if (initialCapacity < 0) {
         throw new IllegalArgumentException(ServiceLogger.getIllegalInitialCapacity(initialCapacity));
      } else {
         if (initialCapacity > 1073741824) {
            initialCapacity = 1073741824;
         }

         if (!(loadFactor <= 0.0F) && !(loadFactor > 1.0F)) {
            int capacity;
            for(capacity = 1; capacity < initialCapacity; capacity <<= 1) {
            }

            this.loadFactor = loadFactor;
            this.threshold = (int)((float)capacity * loadFactor);
            this.table = new Entry[capacity];
         } else {
            throw new IllegalArgumentException(ServiceLogger.getIllegalLoadFactor());
         }
      }
   }

   public ConcurrentHashMap(int initialCapacity) {
      this(initialCapacity, 0.75F);
   }

   public ConcurrentHashMap() {
      this(16, 0.75F);
   }

   public ConcurrentHashMap(Map m) {
      this(Math.max((int)((float)m.size() / 0.75F) + 1, 16), 0.75F);
      this.putAll(m);
   }

   private static Object maskNull(Object key) {
      return key == null ? NULL_KEY : key;
   }

   private static Object unmaskNull(Object key) {
      return key == NULL_KEY ? null : key;
   }

   private static int hash(Object x) {
      int h = x.hashCode();
      return h - (h << 7);
   }

   private static boolean eq(Object x, Object y) {
      return x == y || x.equals(y);
   }

   public final int capacity() {
      return this.table.length;
   }

   public final float loadFactor() {
      return this.loadFactor;
   }

   public final int size() {
      return this.size;
   }

   public final boolean isEmpty() {
      return this.size == 0;
   }

   public Object get(Object key) {
      Entry e = this.getEntry(key);
      return e == null ? null : e.value;
   }

   public final boolean containsKey(Object key) {
      return this.getEntry(key) != null;
   }

   protected Entry getEntry(Object key) {
      Object k = maskNull(key);
      int hash = hash(k);
      Entry[] tab = this.table;

      for(Entry e = tab[hash & tab.length - 1]; e != null; e = e.next) {
         if (e.hash == hash && eq(k, e.key)) {
            return e;
         }
      }

      return null;
   }

   public Object put(Object key, Object value) {
      Object k = maskNull(key);
      int hash = hash(k);
      synchronized(this) {
         int i = hash & this.table.length - 1;

         for(Entry e = this.table[i]; e != null; e = e.next) {
            if (e.hash == hash && eq(k, e.key)) {
               Object oldValue = e.value;
               e.value = value;
               return oldValue;
            }
         }

         this.table[i] = this.createEntry(hash, k, value, this.table[i]);
         if (this.size++ >= this.threshold) {
            this.resize(2 * this.table.length);
         }

         return null;
      }
   }

   public Object putIfAbsent(Object key, Object value) {
      Object k = maskNull(key);
      int hash = hash(k);
      synchronized(this) {
         int i = hash & this.table.length - 1;

         for(Entry e = this.table[i]; e != null; e = e.next) {
            if (e.hash == hash && eq(k, e.key)) {
               return e.value;
            }
         }

         this.table[i] = this.createEntry(hash, k, value, this.table[i]);
         if (this.size++ >= this.threshold) {
            this.resize(2 * this.table.length);
         }

         return null;
      }
   }

   private void resize(int newCapacity) {
      Entry[] oldTable = this.table;
      int oldCapacity = oldTable.length;
      if (this.size >= this.threshold && oldCapacity <= newCapacity) {
         Entry[] newTable = new Entry[newCapacity];
         int mask = newCapacity - 1;
         int i = oldCapacity;

         while(i-- > 0) {
            for(Entry e = oldTable[i]; e != null; e = e.next) {
               Entry clone = (Entry)e.clone();
               int j = clone.hash & mask;
               clone.next = newTable[j];
               newTable[j] = clone;
            }
         }

         this.table = newTable;
         this.threshold = (int)((float)newCapacity * this.loadFactor);
      }
   }

   public final synchronized void putAll(Map t) {
      int n = t.size();
      if (n != 0) {
         if (n >= this.threshold) {
            n = (int)((float)n / this.loadFactor + 1.0F);
            if (n > 1073741824) {
               n = 1073741824;
            }

            int capacity;
            for(capacity = this.table.length; capacity < n; capacity <<= 1) {
            }

            this.resize(capacity);
         }

         Iterator i = t.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry e = (Map.Entry)i.next();
            this.put(e.getKey(), e.getValue());
         }

      }
   }

   public Object remove(Object key) {
      Entry e = this.removeEntryForKey(key);
      return e == null ? e : e.value;
   }

   private Entry removeEntryForKey(Object key) {
      Object k = maskNull(key);
      int hash = hash(k);
      synchronized(this) {
         int i = hash & this.table.length - 1;
         Entry e = this.table[i];
         if (e == null) {
            return null;
         } else if (e.hash == hash && eq(k, e.key)) {
            --this.size;
            this.table[i] = e.next;
            return e;
         } else {
            Entry prev = e;

            for(e = e.next; e != null; e = e.next) {
               if (e.hash == hash && eq(k, e.key)) {
                  --this.size;
                  prev.next = e.next;
                  return e;
               }

               prev = e;
            }

            return null;
         }
      }
   }

   private Entry removeMapping(Object o) {
      if (!(o instanceof Map.Entry)) {
         return null;
      } else {
         Map.Entry entry = (Map.Entry)o;
         Object k = maskNull(entry.getKey());
         int hash = hash(k);
         synchronized(this) {
            int i = hash & this.table.length - 1;
            Entry e = this.table[i];
            if (e == null) {
               return null;
            } else if (e.hash == hash && e.equals(entry)) {
               --this.size;
               this.table[i] = e.next;
               return e;
            } else {
               Entry prev = e;

               for(e = e.next; e != null; e = e.next) {
                  if (e.hash == hash && e.equals(entry)) {
                     --this.size;
                     prev.next = e.next;
                     return e;
                  }

                  prev = e;
               }

               return null;
            }
         }
      }
   }

   public synchronized void clear() {
      this.table = new Entry[this.table.length];
      this.size = 0;
   }

   public final boolean containsValue(Object value) {
      if (value == null) {
         return this.containsNullValue();
      } else {
         Entry[] tab = this.table;

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

   private boolean containsNullValue() {
      Entry[] tab = this.table;

      for(int i = 0; i < tab.length; ++i) {
         for(Entry e = tab[i]; e != null; e = e.next) {
            if (e.value == null) {
               return true;
            }
         }
      }

      return false;
   }

   public final Object clone() {
      return new ConcurrentHashMap(this);
   }

   protected Entry createEntry(int h, Object k, Object v, Entry n) {
      return new Entry(h, k, v, n);
   }

   public final String toPrettyString() {
      return this.toPrettyString(0);
   }

   public final String toPrettyString(int indent) {
      UnsyncStringBuffer blk = new UnsyncStringBuffer();

      for(int x = 0; x < indent; ++x) {
         blk.append("  ");
      }

      UnsyncStringBuffer sb = new UnsyncStringBuffer();
      sb.append(blk.toString() + "Size: [" + this.size() + ", " + this.entrySet().size() + "] {\n");
      Set set = this.entrySet();
      Iterator i = set.iterator();
      Map.Entry entry = null;

      while(i.hasNext()) {
         entry = (Map.Entry)i.next();
         if (entry.getValue() instanceof ConcurrentHashMap) {
            sb.append(blk.toString() + "  " + entry.getKey() + "=" + ((ConcurrentHashMap)entry.getValue()).toPrettyString(indent + 1) + "\n");
         } else {
            sb.append(blk.toString() + "  " + entry.getKey() + "=" + entry.getValue() + "\n");
         }
      }

      sb.append(blk.toString() + "}\n");
      return sb.toString();
   }

   public final Set keySet() {
      Set ks = this.keySet;
      return ks != null ? ks : (this.keySet = new KeySet());
   }

   public final Collection values() {
      Collection vs = this.values;
      return vs != null ? vs : (this.values = new Values());
   }

   public final Set entrySet() {
      Set es = this.entrySet;
      return es != null ? es : (this.entrySet = new EntrySet());
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      s.writeInt(this.table.length);
      s.writeInt(this.size);
      Iterator i = this.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry e = (Map.Entry)i.next();
         s.writeObject(e.getKey());
         s.writeObject(e.getValue());
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
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

   private final class EntrySet extends AbstractSet {
      private EntrySet() {
      }

      public Iterator iterator() {
         return ConcurrentHashMap.this.new EntryIterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            Entry candidate = ConcurrentHashMap.this.getEntry(e.getKey());
            return candidate != null && candidate.equals(e);
         }
      }

      public boolean remove(Object o) {
         return ConcurrentHashMap.this.removeMapping(o) != null;
      }

      public int size() {
         return ConcurrentHashMap.this.size;
      }

      public void clear() {
         ConcurrentHashMap.this.clear();
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }

   private final class Values extends AbstractCollection {
      private Values() {
      }

      public Iterator iterator() {
         return ConcurrentHashMap.this.new ValueIterator();
      }

      public int size() {
         return ConcurrentHashMap.this.size;
      }

      public boolean contains(Object o) {
         return ConcurrentHashMap.this.containsValue(o);
      }

      public void clear() {
         ConcurrentHashMap.this.clear();
      }

      // $FF: synthetic method
      Values(Object x1) {
         this();
      }
   }

   private final class KeySet extends AbstractSet {
      private KeySet() {
      }

      public Iterator iterator() {
         return ConcurrentHashMap.this.new KeyIterator();
      }

      public int size() {
         return ConcurrentHashMap.this.size;
      }

      public boolean contains(Object o) {
         return ConcurrentHashMap.this.containsKey(o);
      }

      public boolean remove(Object o) {
         return ConcurrentHashMap.this.removeEntryForKey(o) != null;
      }

      public void clear() {
         ConcurrentHashMap.this.clear();
      }

      // $FF: synthetic method
      KeySet(Object x1) {
         this();
      }
   }

   private final class EntryIterator extends HashIterator {
      private EntryIterator() {
         super();
      }

      public Object next() {
         return this.nextEntry();
      }

      // $FF: synthetic method
      EntryIterator(Object x1) {
         this();
      }
   }

   private final class KeyIterator extends HashIterator {
      private KeyIterator() {
         super();
      }

      public Object next() {
         return this.nextEntry().getKey();
      }

      // $FF: synthetic method
      KeyIterator(Object x1) {
         this();
      }
   }

   private final class ValueIterator extends HashIterator {
      private ValueIterator() {
         super();
      }

      public Object next() {
         return this.nextEntry().value;
      }

      // $FF: synthetic method
      ValueIterator(Object x1) {
         this();
      }
   }

   private abstract class HashIterator implements Iterator {
      final Entry[] table;
      Entry next;
      int index;
      Entry current;

      HashIterator() {
         this.table = ConcurrentHashMap.this.table;
         if (ConcurrentHashMap.this.size != 0) {
            Entry[] t = this.table;
            int i = t.length - 1;

            Entry n;
            for(n = t[i]; n == null && i > 0; n = t[i]) {
               --i;
            }

            this.index = i;
            this.next = n;
         }
      }

      public final boolean hasNext() {
         return this.next != null;
      }

      final Entry nextEntry() {
         Entry e = this.next;
         if (e == null) {
            throw new NoSuchElementException();
         } else {
            Entry n = e.next;
            Entry[] t = this.table;

            int i;
            for(i = this.index; n == null && i > 0; n = t[i]) {
               --i;
            }

            this.index = i;
            this.next = n;
            return this.current = e;
         }
      }

      public final void remove() {
         if (this.current == null) {
            throw new IllegalStateException();
         } else {
            Object k = this.current.key;
            this.current = null;
            ConcurrentHashMap.this.removeEntryForKey(k);
         }
      }
   }

   protected static class Entry implements Map.Entry {
      final Object key;
      Object value;
      final int hash;
      Entry next;

      protected Entry(int h, Object k, Object v, Entry n) {
         this.value = v;
         this.next = n;
         this.key = k;
         this.hash = h;
      }

      public Object getKey() {
         return ConcurrentHashMap.unmaskNull(this.key);
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object newValue) {
         Object oldValue = this.value;
         this.value = newValue;
         return oldValue;
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            Object k1 = this.getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || k1 != null && k1.equals(k2)) {
               Object v1 = this.getValue();
               Object v2 = e.getValue();
               if (v1 == v2 || v1 != null && v1.equals(v2)) {
                  return true;
               }
            }

            return false;
         }
      }

      public int hashCode() {
         return (this.key == ConcurrentHashMap.NULL_KEY ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public String toString() {
         return this.getKey() + "=" + this.getValue();
      }

      protected Object clone() {
         return new Entry(this.hash, this.key, this.value, (Entry)null);
      }
   }
}
