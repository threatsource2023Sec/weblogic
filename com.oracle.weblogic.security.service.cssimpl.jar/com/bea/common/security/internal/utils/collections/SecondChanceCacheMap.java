package com.bea.common.security.internal.utils.collections;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SecondChanceCacheMap extends AbstractMap {
   private final int capacity;
   private final ConcurrentHashMap map = new ConcurrentHashMap();
   private final CircularQueue list = new CircularQueue();
   private Set entrySet;
   private static final int KEYS = 0;
   private static final int VALUES = 1;
   private static final int ENTRIES = 2;

   public SecondChanceCacheMap(int size) {
      this.capacity = size;
   }

   public int size() {
      return this.map.size();
   }

   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   public boolean containsValue(Object value) {
      return this.map.containsValue(value);
   }

   public boolean containsKey(Object key) {
      return this.map.containsKey(key);
   }

   public Object get(Object key) {
      Entry e = (Entry)this.map.get(key);
      if (e == null) {
         return null;
      } else {
         e.setSecondChance(true);
         return e.getValue();
      }
   }

   public synchronized Object put(Object key, Object value) {
      Entry e = new Entry(key, value);
      Entry oldEntry = (Entry)this.map.putIfAbsent(key, e);
      if (oldEntry != null) {
         Object oldValue = oldEntry.getValue();
         oldEntry.setValue(value);
         return oldValue;
      } else {
         this.list.add(e);
         if (this.capacity < this.size()) {
            Entry evictionEntry = this.getEntryForEviction();
            this.map.remove(evictionEntry.getKey());
            return evictionEntry.getValue();
         } else {
            return null;
         }
      }
   }

   public synchronized Object insert(Object key, Object value) {
      Entry e = new Entry(key, value);
      Entry oldEntry = (Entry)this.map.putIfAbsent(key, e);
      if (oldEntry != null) {
         Object oldValue = oldEntry.getValue();
         oldEntry.setValue(value);
         return null;
      } else {
         this.list.add(e);
         if (this.capacity < this.size()) {
            Entry evictionEntry = this.getEntryForEviction();
            Object evictionKey = evictionEntry.getKey();
            this.map.remove(evictionKey);
            return evictionKey;
         } else {
            return null;
         }
      }
   }

   protected Entry getEntryForEviction() {
      while(true) {
         Entry evictedEntry = (Entry)this.list.remove();
         if (evictedEntry.getValid()) {
            if (!evictedEntry.getSecondChance()) {
               return evictedEntry;
            }

            evictedEntry.setSecondChance(false);
            this.list.add(evictedEntry);
         }
      }
   }

   public synchronized Object remove(Object key) {
      Entry old = (Entry)this.map.remove(key);
      if (old == null) {
         return null;
      } else {
         old.setValid(false);
         if (this.list.size() > this.capacity) {
            this.cleanupInvalidObjects();
         }

         return old.getValue();
      }
   }

   private void cleanupInvalidObjects() {
      int cacheSize = this.list.size();

      for(int i = 0; i < cacheSize; ++i) {
         Entry entry = (Entry)this.list.remove();
         if (entry.getValid()) {
            this.list.add(entry);
         }
      }

   }

   public synchronized void clear() {
      this.map.clear();
      this.list.clear();
   }

   public final int getCapacity() {
      return this.capacity;
   }

   public Set entrySet() {
      if (this.entrySet == null) {
         this.entrySet = new AbstractSet() {
            public Iterator iterator() {
               return SecondChanceCacheMap.this.new SecondChanceIterator(2);
            }

            public boolean contains(Object o) {
               if (!(o instanceof Map.Entry)) {
                  return false;
               } else {
                  Map.Entry entry = (Map.Entry)o;
                  return SecondChanceCacheMap.this.map.get(entry.getKey()) != null;
               }
            }

            public boolean remove(Object o) {
               if (!(o instanceof Map.Entry)) {
                  return false;
               } else {
                  Map.Entry entry = (Map.Entry)o;
                  Object old = SecondChanceCacheMap.this.remove(entry.getKey());
                  return old != null;
               }
            }

            public int size() {
               return SecondChanceCacheMap.this.size();
            }

            public void clear() {
               SecondChanceCacheMap.this.clear();
            }
         };
      }

      return this.entrySet;
   }

   private class SecondChanceIterator implements Iterator {
      Iterator mapIterator;
      final int type;

      public SecondChanceIterator(int type) {
         this.mapIterator = SecondChanceCacheMap.this.map.values().iterator();
         this.type = type;
      }

      public boolean hasNext() {
         return this.mapIterator.hasNext();
      }

      public Object next() {
         Entry e = (Entry)this.mapIterator.next();
         return this.type == 0 ? e.getKey() : (this.type == 1 ? e.getValue() : e);
      }

      public void remove() {
         this.mapIterator.remove();
      }
   }

   protected static class Entry implements Map.Entry {
      private final Object key;
      private Object value;
      private boolean secondChance;
      private boolean valid;

      public Entry(Object key, Object value) {
         this.key = key;
         this.value = value;
         this.secondChance = false;
         this.valid = true;
      }

      public boolean getSecondChance() {
         return this.secondChance;
      }

      public void setSecondChance(boolean b) {
         this.secondChance = b;
      }

      public boolean getValid() {
         return this.valid;
      }

      public void setValid(boolean b) {
         this.valid = b;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object o) {
         Object old = this.value;
         this.value = o;
         return old;
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            boolean var10000;
            label38: {
               label27: {
                  Map.Entry e = (Map.Entry)o;
                  if (this.key == null) {
                     if (e.getKey() != null) {
                        break label27;
                     }
                  } else if (!this.key.equals(e.getKey())) {
                     break label27;
                  }

                  if (this.value == null) {
                     if (e.getValue() == null) {
                        break label38;
                     }
                  } else if (this.value.equals(e.getValue())) {
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
         return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public String toString() {
         return super.toString() + " - key: " + this.key.toString() + " value: " + this.value.toString();
      }
   }
}
