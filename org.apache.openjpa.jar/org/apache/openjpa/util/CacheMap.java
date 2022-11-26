package org.apache.openjpa.util;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.collections.iterators.IteratorChain;
import org.apache.openjpa.lib.util.LRUMap;
import org.apache.openjpa.lib.util.SizedMap;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap;
import org.apache.openjpa.lib.util.concurrent.NullSafeConcurrentHashMap;
import org.apache.openjpa.lib.util.concurrent.SizedConcurrentHashMap;

public class CacheMap implements Map {
   protected final SizedMap cacheMap;
   protected final SizedMap softMap;
   protected final Map pinnedMap;
   private int _pinnedSize;
   private final ReentrantLock _writeLock;
   private final ReentrantLock _readLock;

   public CacheMap() {
      this(false, 1000);
   }

   public CacheMap(boolean lru, int max) {
      this(lru, max, max / 2, 0.75F);
   }

   /** @deprecated */
   public CacheMap(boolean lru, int max, int size, float load) {
      this(lru, max, size, load, 16);
   }

   public CacheMap(boolean lru, int max, int size, float load, int concurrencyLevel) {
      this._pinnedSize = 0;
      this._writeLock = new ReentrantLock();
      if (size < 0) {
         size = 500;
      }

      this.softMap = new ConcurrentReferenceHashMap(0, 2, size, load) {
         public void overflowRemoved(Object key, Object value) {
            CacheMap.this.softMapOverflowRemoved(key, value);
         }

         public void valueExpired(Object key) {
            CacheMap.this.softMapValueExpired(key);
         }
      };
      this.pinnedMap = new NullSafeConcurrentHashMap();
      if (!lru) {
         this.cacheMap = new SizedConcurrentHashMap(size, load, concurrencyLevel) {
            public void overflowRemoved(Object key, Object value) {
               CacheMap.this.cacheMapOverflowRemoved(key, value);
            }
         };
         this._readLock = null;
      } else {
         this.cacheMap = new LRUMap(size, load) {
            public void overflowRemoved(Object key, Object value) {
               CacheMap.this.cacheMapOverflowRemoved(key, value);
            }
         };
         this._readLock = this._writeLock;
      }

      if (max < 0) {
         max = Integer.MAX_VALUE;
      }

      this.cacheMap.setMaxSize(max);
   }

   protected void cacheMapOverflowRemoved(Object key, Object value) {
      if (this.softMap.size() < this.softMap.getMaxSize()) {
         this.put(this.softMap, key, value);
      } else {
         this.entryRemoved(key, value, true);
      }

   }

   protected void softMapOverflowRemoved(Object key, Object value) {
      this.entryRemoved(key, value, true);
   }

   protected void softMapValueExpired(Object key) {
      this.entryRemoved(key, (Object)null, true);
   }

   protected Object put(Map map, Object key, Object value) {
      return map.put(key, value);
   }

   protected Object remove(Map map, Object key) {
      return map.remove(key);
   }

   public void readLock() {
      if (this._readLock != null) {
         this._readLock.lock();
      }

   }

   public void readUnlock() {
      if (this._readLock != null) {
         this._readLock.unlock();
      }

   }

   public void writeLock() {
      this._writeLock.lock();
   }

   public void writeUnlock() {
      this._writeLock.unlock();
   }

   public boolean isLRU() {
      return this._readLock != null;
   }

   public void setCacheSize(int size) {
      this.writeLock();

      try {
         this.cacheMap.setMaxSize(size < 0 ? Integer.MAX_VALUE : size);
      } finally {
         this.writeUnlock();
      }

   }

   public int getCacheSize() {
      int max = this.cacheMap.getMaxSize();
      return max == Integer.MAX_VALUE ? -1 : max;
   }

   public void setSoftReferenceSize(int size) {
      this.writeLock();

      try {
         this.softMap.setMaxSize(size < 0 ? Integer.MAX_VALUE : size);
      } finally {
         this.writeUnlock();
      }

   }

   public int getSoftReferenceSize() {
      int max = this.softMap.getMaxSize();
      return max == Integer.MAX_VALUE ? -1 : max;
   }

   public Set getPinnedKeys() {
      this.readLock();

      Set var1;
      try {
         var1 = Collections.unmodifiableSet(this.pinnedMap.keySet());
      } finally {
         this.readUnlock();
      }

      return var1;
   }

   public boolean pin(Object key) {
      this.writeLock();

      boolean var2;
      try {
         if (!this.pinnedMap.containsKey(key)) {
            Object val = this.remove(this.cacheMap, key);
            if (val == null) {
               val = this.remove(this.softMap, key);
            }

            this.put(this.pinnedMap, key, val);
            boolean var3;
            if (val != null) {
               ++this._pinnedSize;
               var3 = true;
               return var3;
            }

            var3 = false;
            return var3;
         }

         var2 = this.pinnedMap.get(key) != null;
      } finally {
         this.writeUnlock();
      }

      return var2;
   }

   public boolean unpin(Object key) {
      this.writeLock();

      boolean var3;
      try {
         Object val = this.remove(this.pinnedMap, key);
         if (val == null) {
            var3 = false;
            return var3;
         }

         this.put(key, val);
         --this._pinnedSize;
         var3 = true;
      } finally {
         this.writeUnlock();
      }

      return var3;
   }

   protected void entryRemoved(Object key, Object value, boolean expired) {
   }

   protected void entryAdded(Object key, Object value) {
   }

   public Object get(Object key) {
      this.readLock();

      Object var3;
      try {
         Object val = this.pinnedMap.get(key);
         if (val == null) {
            val = this.cacheMap.get(key);
            if (val == null) {
               val = this.softMap.get(key);
               if (val != null) {
                  this.put(key, val);
               }
            }

            var3 = val;
            return var3;
         }

         var3 = val;
      } finally {
         this.readUnlock();
      }

      return var3;
   }

   public Object put(Object key, Object value) {
      this.writeLock();

      Object var4;
      try {
         Object val;
         if (!this.pinnedMap.containsKey(key)) {
            if (this.cacheMap.getMaxSize() == 0) {
               var4 = null;
               return var4;
            }

            val = this.put(this.cacheMap, key, value);
            if (val == null) {
               val = this.remove(this.softMap, key);
               if (val == null) {
                  this.entryAdded(key, value);
               } else {
                  this.entryRemoved(key, val, false);
                  this.entryAdded(key, value);
               }
            } else {
               this.entryRemoved(key, val, false);
               this.entryAdded(key, value);
            }

            var4 = val;
            return var4;
         }

         val = this.put(this.pinnedMap, key, value);
         if (val == null) {
            ++this._pinnedSize;
            this.entryAdded(key, value);
         } else {
            this.entryRemoved(key, val, false);
            this.entryAdded(key, value);
         }

         var4 = val;
      } finally {
         this.writeUnlock();
      }

      return var4;
   }

   public void putAll(Map map) {
      Iterator itr = map.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         this.put(entry.getKey(), entry.getValue());
      }

   }

   public Object remove(Object key) {
      this.writeLock();

      Object var3;
      try {
         Object val;
         if (this.pinnedMap.containsKey(key)) {
            val = this.put(this.pinnedMap, key, (Object)null);
            if (val != null) {
               --this._pinnedSize;
               this.entryRemoved(key, val, false);
            }

            var3 = val;
            return var3;
         }

         val = this.remove(this.cacheMap, key);
         if (val == null) {
            val = this.softMap.remove(key);
         }

         if (val != null) {
            this.entryRemoved(key, val, false);
         }

         var3 = val;
      } finally {
         this.writeUnlock();
      }

      return var3;
   }

   public void clear() {
      this.writeLock();

      try {
         this.notifyEntryRemovals(this.pinnedMap.entrySet());
         this.pinnedMap.clear();
         this._pinnedSize = 0;
         this.notifyEntryRemovals(this.cacheMap.entrySet());
         this.cacheMap.clear();
         this.notifyEntryRemovals(this.softMap.entrySet());
         this.softMap.clear();
      } finally {
         this.writeUnlock();
      }

   }

   private void notifyEntryRemovals(Set set) {
      Iterator itr = set.iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         if (entry.getValue() != null) {
            this.entryRemoved(entry.getKey(), entry.getValue(), false);
         }
      }

   }

   public int size() {
      this.readLock();

      int var1;
      try {
         var1 = this._pinnedSize + this.cacheMap.size() + this.softMap.size();
      } finally {
         this.readUnlock();
      }

      return var1;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public boolean containsKey(Object key) {
      this.readLock();

      boolean var2;
      try {
         var2 = this.pinnedMap.get(key) != null || this.cacheMap.containsKey(key) || this.softMap.containsKey(key);
      } finally {
         this.readUnlock();
      }

      return var2;
   }

   public boolean containsValue(Object val) {
      this.readLock();

      boolean var2;
      try {
         var2 = this.pinnedMap.containsValue(val) || this.cacheMap.containsValue(val) || this.softMap.containsValue(val);
      } finally {
         this.readUnlock();
      }

      return var2;
   }

   public Set keySet() {
      return new KeySet();
   }

   public Collection values() {
      return new ValueCollection();
   }

   public Set entrySet() {
      return new EntrySet();
   }

   public String toString() {
      this.readLock();

      String var1;
      try {
         var1 = "CacheMap:" + this.cacheMap.toString() + "::" + this.softMap.toString();
      } finally {
         this.readUnlock();
      }

      return var1;
   }

   private class EntryIterator implements Iterator, Predicate {
      public static final int ENTRY = 0;
      public static final int KEY = 1;
      public static final int VALUE = 2;
      private final IteratorChain _itr = new IteratorChain();
      private final int _type;

      public EntryIterator(int type) {
         this._type = type;
         this._itr.addIterator(new FilterIterator(this.getView(CacheMap.this.pinnedMap), this));
         this._itr.addIterator(this.getView(CacheMap.this.cacheMap));
         this._itr.addIterator(this.getView(CacheMap.this.softMap));
      }

      private Iterator getView(Map m) {
         if (m == null) {
            return null;
         } else {
            switch (this._type) {
               case 1:
                  return m.keySet().iterator();
               case 2:
                  return m.values().iterator();
               default:
                  return m.entrySet().iterator();
            }
         }
      }

      public boolean hasNext() {
         return this._itr.hasNext();
      }

      public Object next() {
         return this._itr.next();
      }

      public void remove() {
         this._itr.remove();
      }

      public boolean evaluate(Object obj) {
         switch (this._type) {
            case 0:
               return ((Map.Entry)obj).getValue() != null;
            case 2:
               return obj != null;
            default:
               return true;
         }
      }
   }

   private class ValueCollection extends AbstractCollection {
      private ValueCollection() {
      }

      public int size() {
         return CacheMap.this.size();
      }

      public Iterator iterator() {
         return CacheMap.this.new EntryIterator(2);
      }

      // $FF: synthetic method
      ValueCollection(Object x1) {
         this();
      }
   }

   private class KeySet extends AbstractSet {
      private KeySet() {
      }

      public int size() {
         return CacheMap.this.size();
      }

      public Iterator iterator() {
         return CacheMap.this.new EntryIterator(1);
      }

      // $FF: synthetic method
      KeySet(Object x1) {
         this();
      }
   }

   private class EntrySet extends AbstractSet {
      private EntrySet() {
      }

      public int size() {
         return CacheMap.this.size();
      }

      public boolean add(Object o) {
         Map.Entry entry = (Map.Entry)o;
         CacheMap.this.put(entry.getKey(), entry.getValue());
         return true;
      }

      public Iterator iterator() {
         return CacheMap.this.new EntryIterator(0);
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }
}
