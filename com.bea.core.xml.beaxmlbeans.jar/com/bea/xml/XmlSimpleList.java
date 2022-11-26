package com.bea.xml;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class XmlSimpleList implements List, Serializable {
   private static final long serialVersionUID = 1L;
   private List underlying;

   public XmlSimpleList(List list) {
      this.underlying = list;
   }

   public int size() {
      return this.underlying.size();
   }

   public boolean isEmpty() {
      return this.underlying.isEmpty();
   }

   public boolean contains(Object o) {
      return this.underlying.contains(o);
   }

   public boolean containsAll(Collection coll) {
      return this.underlying.containsAll(coll);
   }

   public Object[] toArray() {
      return this.underlying.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.underlying.toArray(a);
   }

   public boolean add(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection coll) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection coll) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection coll) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public Object get(int index) {
      return this.underlying.get(index);
   }

   public Object set(int index, Object element) {
      throw new UnsupportedOperationException();
   }

   public void add(int index, Object element) {
      throw new UnsupportedOperationException();
   }

   public Object remove(int index) {
      throw new UnsupportedOperationException();
   }

   public int indexOf(Object o) {
      return this.underlying.indexOf(o);
   }

   public int lastIndexOf(Object o) {
      return this.underlying.lastIndexOf(o);
   }

   public boolean addAll(int index, Collection c) {
      throw new UnsupportedOperationException();
   }

   public List subList(int from, int to) {
      return new XmlSimpleList(this.underlying.subList(from, to));
   }

   public Iterator iterator() {
      return new Iterator() {
         Iterator i;

         {
            this.i = XmlSimpleList.this.underlying.iterator();
         }

         public boolean hasNext() {
            return this.i.hasNext();
         }

         public Object next() {
            return this.i.next();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public ListIterator listIterator() {
      return this.listIterator(0);
   }

   public ListIterator listIterator(final int index) {
      return new ListIterator() {
         ListIterator i;

         {
            this.i = XmlSimpleList.this.underlying.listIterator(index);
         }

         public boolean hasNext() {
            return this.i.hasNext();
         }

         public Object next() {
            return this.i.next();
         }

         public boolean hasPrevious() {
            return this.i.hasPrevious();
         }

         public Object previous() {
            return this.i.previous();
         }

         public int nextIndex() {
            return this.i.nextIndex();
         }

         public int previousIndex() {
            return this.i.previousIndex();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }

         public void set(Object o) {
            throw new UnsupportedOperationException();
         }

         public void add(Object o) {
            throw new UnsupportedOperationException();
         }
      };
   }

   private String stringValue(Object o) {
      return o instanceof SimpleValue ? ((SimpleValue)o).stringValue() : o.toString();
   }

   public String toString() {
      int size = this.underlying.size();
      if (size == 0) {
         return "";
      } else {
         String first = this.stringValue(this.underlying.get(0));
         if (size == 1) {
            return first;
         } else {
            StringBuffer result = new StringBuffer(first);

            for(int i = 1; i < size; ++i) {
               result.append(' ');
               result.append(this.stringValue(this.underlying.get(i)));
            }

            return result.toString();
         }
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof XmlSimpleList)) {
         return false;
      } else {
         XmlSimpleList xmlSimpleList = (XmlSimpleList)o;
         List underlying2 = xmlSimpleList.underlying;
         int size = this.underlying.size();
         if (size != underlying2.size()) {
            return false;
         } else {
            int i = 0;

            while(true) {
               if (i >= size) {
                  return true;
               }

               Object item = this.underlying.get(i);
               Object item2 = underlying2.get(i);
               if (item == null) {
                  if (item2 != null) {
                     break;
                  }
               } else if (!item.equals(item2)) {
                  break;
               }

               ++i;
            }

            return false;
         }
      }
   }

   public int hashCode() {
      int size = this.underlying.size();
      int hash = 0;

      for(int i = 0; i < size; ++i) {
         Object item = this.underlying.get(i);
         hash *= 19;
         hash += item.hashCode();
      }

      return hash;
   }
}
