package com.sun.faces.context;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class BaseContextMap extends AbstractMap {
   private Set entrySet;
   private Set keySet;
   private Collection values;

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public void putAll(Map t) {
      throw new UnsupportedOperationException();
   }

   public Set entrySet() {
      if (this.entrySet == null) {
         this.entrySet = new EntrySet();
      }

      return this.entrySet;
   }

   public Set keySet() {
      if (this.keySet == null) {
         this.keySet = new KeySet();
      }

      return this.keySet;
   }

   public Collection values() {
      if (this.values == null) {
         this.values = new ValueCollection();
      }

      return this.values;
   }

   public Object remove(Object key) {
      throw new UnsupportedOperationException();
   }

   protected boolean removeKey(Object key) {
      return this.remove(key) != null;
   }

   protected boolean removeValue(Object value) {
      boolean valueRemoved = false;
      if (value == null) {
         return false;
      } else {
         if (this.containsValue(value)) {
            Iterator i = this.entrySet().iterator();

            while(i.hasNext()) {
               Map.Entry e = (Map.Entry)i.next();
               if (value.equals(e.getValue())) {
                  valueRemoved = this.remove(e.getKey()) != null;
               }
            }
         }

         return valueRemoved;
      }
   }

   protected abstract Iterator getEntryIterator();

   protected abstract Iterator getKeyIterator();

   protected abstract Iterator getValueIterator();

   static class Entry implements Map.Entry {
      private final String key;
      private final Object value;

      Entry(String key, Object value) {
         this.key = key;
         this.value = value;
      }

      public String getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         throw new UnsupportedOperationException();
      }

      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public boolean equals(Object obj) {
         if (obj != null && obj instanceof Map.Entry) {
            Map.Entry input = (Map.Entry)obj;
            Object inputKey = input.getKey();
            Object inputValue = input.getValue();
            return (inputKey == this.key || inputKey != null && inputKey.equals(this.key)) && (inputValue == this.value || inputValue != null && inputValue.equals(this.value));
         } else {
            return false;
         }
      }
   }

   class ValueIterator extends BaseIterator {
      ValueIterator(Enumeration e) {
         super(e);
      }

      public void remove() {
         if (this.currentKey != null && !this.removeCalled) {
            this.removeCalled = true;
            BaseContextMap.this.removeValue(BaseContextMap.this.get(this.currentKey));
         } else {
            throw new IllegalStateException();
         }
      }

      public Object next() {
         this.nextKey();
         return BaseContextMap.this.get(this.currentKey);
      }
   }

   class KeyIterator extends BaseIterator {
      KeyIterator(Enumeration e) {
         super(e);
      }

      public void remove() {
         if (this.currentKey != null && !this.removeCalled) {
            this.removeCalled = true;
            BaseContextMap.this.removeKey(this.currentKey);
         } else {
            throw new IllegalStateException();
         }
      }

      public String next() {
         return this.nextKey();
      }
   }

   class EntryIterator extends BaseIterator {
      EntryIterator(Enumeration e) {
         super(e);
      }

      public void remove() {
         if (this.currentKey != null && !this.removeCalled) {
            this.removeCalled = true;
            BaseContextMap.this.removeKey(this.currentKey);
         } else {
            throw new IllegalStateException();
         }
      }

      public Map.Entry next() {
         this.nextKey();
         return new Entry(this.currentKey, BaseContextMap.this.get(this.currentKey));
      }
   }

   abstract class BaseIterator implements Iterator {
      protected Enumeration e;
      protected String currentKey;
      protected boolean removeCalled = false;

      BaseIterator(Enumeration e) {
         this.e = e;
      }

      public boolean hasNext() {
         return this.e.hasMoreElements();
      }

      public String nextKey() {
         this.removeCalled = false;
         this.currentKey = (String)this.e.nextElement();
         return this.currentKey;
      }
   }

   class ValueCollection extends AbstractCollection {
      public int size() {
         int size = 0;

         for(Iterator i = this.iterator(); i.hasNext(); ++size) {
            i.next();
         }

         return size;
      }

      public Iterator iterator() {
         return BaseContextMap.this.getValueIterator();
      }

      public boolean remove(Object o) {
         return BaseContextMap.this.removeValue(o);
      }
   }

   class KeySet extends BaseSet {
      KeySet() {
         super();
      }

      public Iterator iterator() {
         return BaseContextMap.this.getKeyIterator();
      }

      public boolean contains(Object o) {
         return BaseContextMap.this.containsKey(o);
      }

      public boolean remove(Object o) {
         return o instanceof String && BaseContextMap.this.removeKey(o);
      }
   }

   class EntrySet extends BaseSet {
      EntrySet() {
         super();
      }

      public Iterator iterator() {
         return BaseContextMap.this.getEntryIterator();
      }

      public boolean remove(Object o) {
         return o instanceof Map.Entry && BaseContextMap.this.removeKey(((Map.Entry)o).getKey());
      }
   }

   abstract class BaseSet extends AbstractSet {
      public int size() {
         int size = 0;

         for(Iterator i = this.iterator(); i.hasNext(); ++size) {
            i.next();
         }

         return size;
      }
   }
}
