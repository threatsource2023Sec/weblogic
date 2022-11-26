package com.bea.adaptive.harvester.utils.collections;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class IndexMap implements Map {
   ExtensibleList list = null;
   private static final long serialVersionUID = 1L;

   public IndexMap() {
      this.list = new ExtensibleList();
   }

   public IndexMap(int size) {
      this.list = new ExtensibleList(size);
   }

   public Object get(Object key) {
      return this.get((Integer)key);
   }

   public Object get(Integer key) {
      this.mx("OOOOOOOOOOO     key/size = " + key + " " + (this.size() - 1));
      if (key > this.size() - 1) {
         return null;
      } else {
         this.mx("OOOOOOOOOOO     Returning " + key + " as " + this.list.get(key));
         return this.list.get(key);
      }
   }

   public Object put(Integer index, Object value) {
      Object old = this.list.get(index);
      this.mx("OOOOOOOOOOO Setting index " + index + " to " + value);
      this.list.set(index, value);
      this.mx("OOOOOOOOOOO     Set index " + index + " to " + this.get(index));
      return old;
   }

   public void putAll(Map t) {
      Set entrySet = t.entrySet();
      java.util.Iterator it = entrySet.iterator();

      while(it.hasNext()) {
         Map.Entry entry = (Map.Entry)it.next();
         this.put((Integer)entry.getKey(), entry.getValue());
      }

   }

   public void putAll(IndexMap t) {
      java.util.Iterator var2 = t.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         Object value = entry.getValue();
         this.put((Integer)entry.getKey(), value);
      }

   }

   public Object remove(Object key) {
      return this.remove((Integer)key);
   }

   public Object remove(Integer key) {
      if (key > this.size() - 1) {
         return null;
      } else {
         Object old = this.list.get(key);
         this.list.set(key, (Object)null);
         return old;
      }
   }

   public void clear() {
      this.list = new ExtensibleList();
   }

   public boolean containsValue(Object value) {
      java.util.Iterator it = this.list.iterator();

      Object next;
      do {
         if (!it.hasNext()) {
            return false;
         }

         next = it.next();
      } while(next == null || !next.equals(value));

      return true;
   }

   public boolean containsKey(Object key) {
      return this.containsKey((Integer)key);
   }

   public boolean containsKey(Integer key) {
      if (key < this.list.size()) {
         return false;
      } else {
         return this.list.get(key) != null;
      }
   }

   public int size() {
      this.mx("OOOOOOOOOOO     SIZE " + this.list.size());
      return this.list.size();
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public Set keySet() {
      return new KeySet();
   }

   public Collection values() {
      return this.list;
   }

   public Set entrySet() {
      return null;
   }

   void me(String s) {
   }

   void mx(String s) {
   }

   class EntrySet implements Set {
      public boolean isEmpty() {
         return IndexMap.this.list.elementCount() > 0;
      }

      public boolean contains(Object o) {
         return this.contains((Integer)o);
      }

      public boolean containsAll(Collection c) {
         java.util.Iterator it = c.iterator();

         do {
            if (!it.hasNext()) {
               return true;
            }
         } while(this.contains(it.next()));

         return false;
      }

      public boolean contains(Integer index) {
         return index < IndexMap.this.list.size() && IndexMap.this.list.get(index) != null;
      }

      public void clear() {
         IndexMap.this.clear();
      }

      public int size() {
         return IndexMap.this.list.elementCount();
      }

      public java.util.Iterator iterator() {
         return new Iterator();
      }

      public boolean remove(Object o) {
         return this.remove((Integer)o);
      }

      public boolean remove(Integer index) {
         if (index > IndexMap.this.list.size() - 1) {
            return false;
         } else {
            Object oldValue = IndexMap.this.list.get(index);
            IndexMap.this.list.set(index, (Object)null);
            return oldValue != null;
         }
      }

      public boolean removeAll(Collection c) {
         boolean changed = false;

         for(java.util.Iterator it = c.iterator(); it.hasNext(); changed |= this.remove(it.next())) {
         }

         return changed;
      }

      public boolean retainAll(Collection c) {
         boolean changed = false;
         boolean[] dontDeleteList = new boolean[IndexMap.this.list.size()];
         java.util.Iterator it = c.iterator();

         while(it.hasNext()) {
            int index = (Integer)it.next();
            if (index < dontDeleteList.length) {
               dontDeleteList[index] = true;
            }
         }

         for(int i = 0; i < dontDeleteList.length; ++i) {
            if (!dontDeleteList[i]) {
               changed |= this.remove(i);
            }
         }

         return changed;
      }

      public boolean add(Map.Entry o) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public Object[] toArray(Object[] a) {
         synchronized(IndexMap.this) {
            int index = 0;

            for(int i = 0; i < IndexMap.this.list.size(); ++i) {
               if (IndexMap.this.list.get(i) != null) {
                  Integer val = i;
                  a[index] = val;
                  ++index;
               }
            }

            return a;
         }
      }

      public Object[] toArray() {
         return this.toArray(new Integer[IndexMap.this.list.elementCount()]);
      }

      class Iterator implements java.util.Iterator {
         int cursor = -1;

         Iterator() {
            this.setCursor();
         }

         public boolean hasNext() {
            return this.cursor >= 0;
         }

         public Map.Entry next() {
            int old = this.cursor;
            if (this.cursor >= IndexMap.this.list.size()) {
               throw new NoSuchElementException();
            } else {
               this.setCursor();
               return EntrySet.this.new Entry(old);
            }
         }

         public void remove() {
            IndexMap.this.list.remove(this.cursor);
            this.setCursor();
         }

         private int setCursor() {
            ++this.cursor;

            while(this.cursor < IndexMap.this.list.size()) {
               Object o = IndexMap.this.list.get(this.cursor);
               if (o != null) {
                  return this.cursor;
               }

               ++this.cursor;
            }

            return -1;
         }
      }

      class Entry implements Map.Entry {
         int index;

         Entry(int index) {
            this.index = index;
         }

         public Integer getKey() {
            return this.index;
         }

         public Object getValue() {
            return IndexMap.this.list.get(this.index);
         }

         public Object setValue(Object value) {
            throw new UnsupportedOperationException();
         }
      }
   }

   class KeySet implements Set {
      public boolean isEmpty() {
         return IndexMap.this.list.elementCount() > 0;
      }

      public boolean contains(Object o) {
         return this.contains((Integer)o);
      }

      public boolean containsAll(Collection c) {
         java.util.Iterator it = c.iterator();

         do {
            if (!it.hasNext()) {
               return true;
            }
         } while(this.contains(it.next()));

         return false;
      }

      public boolean contains(Integer index) {
         return index < IndexMap.this.list.size() && IndexMap.this.list.get(index) != null;
      }

      public void clear() {
         IndexMap.this.clear();
      }

      public int size() {
         return IndexMap.this.list.elementCount();
      }

      public java.util.Iterator iterator() {
         return new Iterator();
      }

      public boolean remove(Object o) {
         return this.remove((Integer)o);
      }

      public boolean remove(Integer index) {
         if (index > IndexMap.this.list.size() - 1) {
            return false;
         } else {
            Object oldValue = IndexMap.this.list.get(index);
            IndexMap.this.list.set(index, (Object)null);
            return oldValue != null;
         }
      }

      public boolean removeAll(Collection c) {
         boolean changed = false;

         for(java.util.Iterator it = c.iterator(); it.hasNext(); changed |= this.remove(it.next())) {
         }

         return changed;
      }

      public boolean retainAll(Collection c) {
         boolean changed = false;
         boolean[] dontDeleteList = new boolean[IndexMap.this.list.size()];
         java.util.Iterator it = c.iterator();

         while(it.hasNext()) {
            int index = (Integer)it.next();
            if (index < dontDeleteList.length) {
               dontDeleteList[index] = true;
            }
         }

         for(int i = 0; i < dontDeleteList.length; ++i) {
            if (!dontDeleteList[i]) {
               changed |= this.remove(i);
            }
         }

         return changed;
      }

      public boolean add(Integer o) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public Object[] toArray(Object[] a) {
         synchronized(IndexMap.this) {
            int index = 0;

            for(int i = 0; i < IndexMap.this.list.size(); ++i) {
               if (IndexMap.this.list.get(i) != null) {
                  Integer val = i;
                  a[index] = val;
                  ++index;
               }
            }

            return a;
         }
      }

      public Object[] toArray() {
         return this.toArray(new Integer[IndexMap.this.list.elementCount()]);
      }

      class Iterator implements java.util.Iterator {
         int cursor = -1;

         Iterator() {
            this.setCursor();
         }

         public boolean hasNext() {
            return this.cursor >= 0;
         }

         public Integer next() {
            int old = this.cursor;
            if (this.cursor >= IndexMap.this.list.size()) {
               throw new NoSuchElementException();
            } else {
               this.setCursor();
               return old;
            }
         }

         public void remove() {
            IndexMap.this.list.remove(this.cursor);
            this.setCursor();
         }

         private void setCursor() {
            ++this.cursor;

            while(this.cursor < IndexMap.this.list.size()) {
               Object o = IndexMap.this.list.get(this.cursor);
               if (o != null) {
                  return;
               }

               ++this.cursor;
            }

            this.cursor = -1;
         }
      }
   }
}
