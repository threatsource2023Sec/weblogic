package weblogic.entitlement.util;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class OrderedHashMap extends HashMap {
   private Entry mFirst;
   private Entry mLast;
   private transient int modCount;
   private transient Set keySet;
   private transient Set entrySet;
   private transient Collection values;

   public OrderedHashMap() {
      this.modCount = 0;
      this.keySet = null;
      this.entrySet = null;
      this.values = null;
   }

   public OrderedHashMap(int initialCapacity) {
      super(initialCapacity);
      this.modCount = 0;
      this.keySet = null;
      this.entrySet = null;
      this.values = null;
   }

   public OrderedHashMap(int initialCapacity, float loadFactor) {
      super(initialCapacity, loadFactor);
      this.modCount = 0;
      this.keySet = null;
      this.entrySet = null;
      this.values = null;
   }

   public OrderedHashMap(Map t) {
      this(Math.max(2 * t.size(), 11));
      this.putAll(t);
   }

   public OrderedHashMap(OrderedHashMap t) {
      this(Math.max(2 * t.size(), 11));
      this.putAll(t);
   }

   public boolean containsValue(Object value) {
      Entry entry;
      if (value == null) {
         for(entry = this.mFirst; entry != null; entry = entry.next) {
            if (entry.value == null) {
               return true;
            }
         }
      } else {
         for(entry = this.mFirst; entry != null; entry = entry.next) {
            if (value.equals(entry.value)) {
               return true;
            }
         }
      }

      return false;
   }

   public Object get(Object key) {
      Entry entry = (Entry)super.get(key);
      return entry == null ? null : entry.value;
   }

   public Map.Entry getFirst() {
      return this.mFirst;
   }

   public Map.Entry getLast() {
      return this.mLast;
   }

   public Object put(Object key, Object value) {
      return this.put(key, value, false, false);
   }

   public Object putFirst(Object key, Object value) {
      return this.put(key, value, true, true);
   }

   public Object putLast(Object key, Object value) {
      return this.put(key, value, false, true);
   }

   public Object put(Object key, Object value, boolean putFirst, boolean moveIfExists) {
      Object oldValue = null;
      Entry entry = (Entry)super.get(key);
      if (entry == null) {
         ++this.modCount;
         entry = new Entry(key, value);
         super.put(key, entry);
         if (putFirst) {
            this.addFirstListEntry(entry);
         } else {
            this.addLastListEntry(entry);
         }
      } else {
         oldValue = entry.value;
         entry.value = value;
         if (moveIfExists) {
            if (putFirst) {
               this.moveListEntryToFirst(entry);
            } else {
               this.moveListEntryToLast(entry);
            }
         }
      }

      return oldValue;
   }

   public void putAll(Map t) {
      Iterator i = t.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry e = (Map.Entry)i.next();
         this.put(e.getKey(), e.getValue(), false, false);
      }

   }

   public Object moveToFirst(Object key) {
      Entry entry = (Entry)super.get(key);
      if (entry != null && entry != this.mFirst) {
         ++this.modCount;
         this.moveListEntryToFirst(entry);
      }

      return entry == null ? null : entry.value;
   }

   public Object moveToLast(Object key) {
      Entry entry = (Entry)super.get(key);
      if (entry != null && entry != this.mLast) {
         ++this.modCount;
         this.moveListEntryToLast(entry);
      }

      return entry == null ? null : entry.value;
   }

   public Object remove(Object key) {
      if (super.containsKey(key)) {
         ++this.modCount;
      }

      Object value = null;
      Entry entry = (Entry)super.remove(key);
      if (entry != null) {
         value = entry.value;
         this.removeListEntry(entry);
      }

      return value;
   }

   public Map.Entry removeFirst() {
      if (this.mFirst != null) {
         ++this.modCount;
         super.remove(this.mFirst.key);
         this.removeListEntry(this.mFirst);
      }

      return this.mFirst;
   }

   public Map.Entry removeLast() {
      if (this.mLast != null) {
         ++this.modCount;
         super.remove(this.mLast.key);
         this.removeListEntry(this.mLast);
      }

      return this.mLast;
   }

   public void clear() {
      ++this.modCount;
      super.clear();
      this.mFirst = this.mLast = null;
   }

   public Object clone() {
      return new OrderedHashMap(this);
   }

   public Set keySet() {
      if (this.keySet == null) {
         this.keySet = new AbstractSet() {
            public Iterator iterator() {
               return (Iterator)(OrderedHashMap.this.mFirst == null ? EmptyIterator.INSTANCE : OrderedHashMap.this.new KeyIterator(OrderedHashMap.this.mFirst));
            }

            public int size() {
               return OrderedHashMap.this.size();
            }

            public boolean contains(Object o) {
               return OrderedHashMap.this.containsKey(o);
            }

            public boolean remove(Object o) {
               int oldSize = OrderedHashMap.this.size();
               OrderedHashMap.this.remove(o);
               return OrderedHashMap.this.size() != oldSize;
            }

            public void clear() {
               OrderedHashMap.this.clear();
            }
         };
      }

      return this.keySet;
   }

   public Collection values() {
      if (this.values == null) {
         this.values = new AbstractCollection() {
            public Iterator iterator() {
               return (Iterator)(OrderedHashMap.this.mFirst == null ? EmptyIterator.INSTANCE : OrderedHashMap.this.new ValueIterator(OrderedHashMap.this.mFirst));
            }

            public int size() {
               return OrderedHashMap.this.size();
            }

            public boolean contains(Object o) {
               return OrderedHashMap.this.containsValue(o);
            }

            public void clear() {
               OrderedHashMap.this.clear();
            }
         };
      }

      return this.values;
   }

   public Set entrySet() {
      if (this.entrySet == null) {
         this.entrySet = new AbstractSet() {
            public Iterator iterator() {
               return (Iterator)(OrderedHashMap.this.mFirst == null ? EmptyIterator.INSTANCE : OrderedHashMap.this.new EntryIterator(OrderedHashMap.this.mFirst));
            }

            public boolean contains(Object o) {
               return !(o instanceof Map.Entry) ? false : OrderedHashMap.this.containsKey(((Map.Entry)o).getKey());
            }

            public boolean remove(Object o) {
               if (!(o instanceof Map.Entry)) {
                  return false;
               } else {
                  int oldSize = OrderedHashMap.this.size();
                  OrderedHashMap.this.remove(((Map.Entry)o).getKey());
                  return oldSize != OrderedHashMap.this.size();
               }
            }

            public int size() {
               return OrderedHashMap.this.size();
            }

            public void clear() {
               OrderedHashMap.this.clear();
            }
         };
      }

      return this.entrySet;
   }

   public Iterator iterator() {
      return (Iterator)(this.mFirst == null ? EmptyIterator.INSTANCE : new EntryIterator(this.mFirst));
   }

   private void moveListEntryToLast(Entry entry) {
      if (entry != this.mLast) {
         this.removeListEntry(entry);
         this.addLastListEntry(entry);
      }

   }

   private void moveListEntryToFirst(Entry entry) {
      if (entry != this.mFirst) {
         this.removeListEntry(entry);
         this.addFirstListEntry(entry);
      }

   }

   private void addLastListEntry(Entry entry) {
      if (this.mLast == null) {
         this.mFirst = this.mLast = entry;
      } else {
         this.mLast.next = entry;
         entry.prev = this.mLast;
         this.mLast = entry;
      }

   }

   private void addFirstListEntry(Entry entry) {
      if (this.mFirst == null) {
         this.mFirst = this.mLast = entry;
      } else {
         this.mFirst.prev = entry;
         entry.next = this.mFirst;
         this.mFirst = entry;
      }

   }

   private void removeListEntry(Entry entry) {
      Entry prev = entry.prev;
      Entry next = entry.next;
      if (prev == null) {
         this.mFirst = next;
      } else {
         prev.next = next;
      }

      if (next == null) {
         this.mLast = prev;
      } else {
         next.prev = prev;
      }

      entry.next = entry.prev = null;
   }

   private class Entry implements Map.Entry, Serializable {
      Entry next;
      Entry prev;
      Object key;
      Object value;

      public Entry() {
      }

      Entry(Object key, Object value) {
         this.key = key;
         this.value = value;
         this.next = this.prev = null;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         Object oldValue = this.value;
         this.value = value;
         return oldValue;
      }

      public Object getKey() {
         return this.key;
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
         int hash = 0;
         if (this.key != null) {
            hash ^= this.key.hashCode();
         }

         if (this.value != null) {
            hash ^= this.value.hashCode();
         }

         return hash;
      }

      public String toString() {
         return this.key + "=" + this.value;
      }
   }

   private class ValueIterator extends EntryIterator {
      public ValueIterator(Entry entry) {
         super(entry);
      }

      public Object next() {
         Entry entry = (Entry)super.next();
         return entry.value;
      }
   }

   private class KeyIterator extends EntryIterator {
      public KeyIterator(Entry entry) {
         super(entry);
      }

      public Object next() {
         Entry entry = (Entry)super.next();
         return entry.key;
      }
   }

   private class EntryIterator implements Iterator {
      private Entry entry = null;
      private Entry lastReturned = null;
      private int expectedModCount;

      public EntryIterator(Entry entry) {
         this.expectedModCount = OrderedHashMap.this.modCount;
         this.entry = entry;
      }

      public boolean hasNext() {
         return this.entry != null;
      }

      public Object next() {
         if (OrderedHashMap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         } else if (this.entry == null) {
            throw new NoSuchElementException();
         } else {
            this.lastReturned = this.entry;
            this.entry = this.entry.next;
            return this.lastReturned;
         }
      }

      public void remove() {
         if (this.lastReturned == null) {
            throw new IllegalStateException();
         } else if (OrderedHashMap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         } else {
            ++this.expectedModCount;
            OrderedHashMap.this.remove(this.lastReturned.key);
            if (OrderedHashMap.this.modCount != this.expectedModCount) {
               throw new ConcurrentModificationException();
            }
         }
      }
   }
}
