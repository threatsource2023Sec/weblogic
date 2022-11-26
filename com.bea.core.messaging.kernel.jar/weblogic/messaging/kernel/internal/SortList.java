package weblogic.messaging.kernel.internal;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;

public final class SortList extends AbstractList {
   protected int size;
   protected SortListElement first;
   protected SortListElement last;
   private TreeMap tree = null;

   public boolean add(Object o) {
      SortListElement element = (SortListElement)o;
      if (element.getList() != null) {
         throw new IllegalArgumentException();
      } else {
         element.setNext((SortListElement)null);
         if (this.first == null) {
            this.first = element;
         } else {
            this.last.setNext(element);
         }

         element.setPrev(this.last);
         this.last = element;
         element.setList(this);
         ++this.size;
         if (this.tree != null) {
            this.tree.put(element, element);
         }

         return true;
      }
   }

   private void addAfter(Object cur, Object o) {
      SortListElement current = (SortListElement)cur;
      SortListElement element = (SortListElement)o;
      SortListElement oldNext = current.getNext();
      if (!this.contains(cur)) {
         throw new IllegalStateException();
      } else {
         current.setNext(element);
         element.setNext(oldNext);
         element.setPrev(current);
         if (oldNext != null) {
            oldNext.setPrev(element);
         } else {
            this.last = element;
         }

         element.setList(this);
         ++this.size;
         if (this.tree != null) {
            this.tree.put(element, element);
         }

      }
   }

   private void addFirst(Object o) {
      SortListElement element = (SortListElement)o;
      element.setPrev((SortListElement)null);
      if (this.first == null) {
         element.setNext((SortListElement)null);
         this.last = element;
      } else {
         element.setNext(this.first);
         this.first.setPrev(element);
      }

      this.first = element;
      element.setList(this);
      ++this.size;
      if (this.tree != null) {
         this.tree.put(element, element);
      }

   }

   public void clear() {
      while(this.first != null) {
         this.remove(this.first);
      }

      this.clearIndex();
   }

   public void clearIndex() {
      if (this.tree != null) {
         this.tree.clear();
      }

   }

   public void destroyIndex() {
      this.tree = null;
   }

   public boolean contains(Object o) {
      return ((SortListElement)o).getList() == this;
   }

   public Object get(int index) {
      if (index >= 0 && index < this.size) {
         SortListElement ret = this.first;

         for(int inc = 1; inc < index; ++inc) {
            ret = ret.getNext();
         }

         return ret;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public Object first() {
      return this.first;
   }

   public Object last() {
      return this.last;
   }

   public boolean remove(Object o) {
      SortListElement element = (SortListElement)o;
      if (!this.contains(element)) {
         return false;
      } else {
         if (this.tree != null) {
            this.tree.remove(element);
         }

         if (element.getNext() != null) {
            element.getNext().setPrev(element.getPrev());
         } else {
            this.last = element.getPrev();
         }

         if (element.getPrev() == null) {
            this.first = element.getNext();
         } else {
            element.getPrev().setNext(element.getNext());
         }

         element.setList((SortList)null);
         element.setPrev((SortListElement)null);
         element.setNext((SortListElement)null);
         --this.size;
         return true;
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public ListIterator iterator(SortListElement element) {
      return new Itr(element);
   }

   public Iterator iterator() {
      return new Itr(this.first);
   }

   public ListIterator listIterator() {
      return new Itr(this.first);
   }

   public SortListIterator sortListIterator() {
      return new Itr(this.first);
   }

   public Object[] toArray(Object[] a) {
      Object[] array;
      if (a != null && a.length >= this.size) {
         array = a;
      } else {
         array = new Object[this.size];
      }

      int inc = 0;

      for(SortListElement elt = this.first; elt != null; ++inc) {
         array[inc] = elt;
         elt = elt.getNext();
      }

      return array;
   }

   public Object[] toArray() {
      return this.toArray((Object[])null);
   }

   public void sortAndIndex(Comparator comparator) {
      if (this.isEmpty()) {
         this.tree = new TreeMap(comparator);
      } else {
         this.tree = null;
         int i = 0;
         Object[] elements = new Object[this.size()];

         Object element;
         for(Iterator iterator = this.iterator(); iterator.hasNext(); elements[i++] = element) {
            element = iterator.next();
            iterator.remove();
         }

         if (comparator != null) {
            this.tree = new TreeMap(comparator);
         }

         Arrays.sort(elements, comparator);

         for(i = 0; i < elements.length; ++i) {
            this.add(elements[i]);
         }

      }
   }

   private TreeMap getTree() {
      return this.tree;
   }

   public void addUsingIndex(Object o) {
      SortListElement element = (SortListElement)o;
      SortedMap sm = this.tree.headMap(element);

      try {
         this.addAfter(sm.lastKey(), element);
      } catch (NoSuchElementException var5) {
         this.addFirst(element);
      }

   }

   private final class Itr implements SortListIterator {
      private SortListElement cursor;
      private SortListElement element;

      Itr(SortListElement element) {
         this.reset(element);
      }

      public void reset(SortListElement element) {
         if (element != null && !SortList.this.contains(element)) {
            throw new IllegalArgumentException("List does not contain the specified element");
         } else {
            this.cursor = element;
            this.element = null;
         }
      }

      public boolean hasNext() {
         return this.cursor != null;
      }

      public boolean hasPrevious() {
         if (this.cursor == null) {
            return SortList.this.last != null;
         } else {
            return this.cursor.getPrev() != null;
         }
      }

      public Object next() {
         if (this.cursor == null) {
            throw new NoSuchElementException();
         } else {
            this.element = this.cursor;
            this.cursor = this.cursor.getNext();
            return this.element;
         }
      }

      public Object previous() {
         if (this.cursor != null) {
            SortListElement prev = this.cursor.getPrev();
            if (prev != null) {
               this.element = prev;
               this.cursor = prev;
               return prev;
            } else {
               throw new NoSuchElementException();
            }
         } else if (SortList.this.last != null) {
            this.element = SortList.this.last;
            this.cursor = SortList.this.last;
            return this.element;
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         if (this.element == null) {
            throw new IllegalStateException();
         } else {
            if (this.element == this.cursor) {
               this.cursor = this.cursor.getNext();
            }

            SortList.this.remove(this.element);
            this.element = null;
         }
      }

      public void set(Object o) {
         if (this.element == null) {
            throw new IllegalStateException();
         } else {
            SortListElement newVal = (SortListElement)o;
            this.remove();
            this.add(newVal);
            if (this.cursor == this.element) {
               this.cursor = newVal;
            }

            this.element = newVal;
         }
      }

      public void add(Object o) {
         SortListElement temp = (SortListElement)o;
         if (temp.getList() != null) {
            throw new IllegalArgumentException();
         } else {
            if (this.cursor != null) {
               temp.setNext(this.cursor);
               temp.setPrev(this.cursor.getPrev());
               this.cursor.setPrev(temp);
            } else {
               temp.setNext((SortListElement)null);
               temp.setPrev(SortList.this.last);
               SortList.this.last = temp;
            }

            if (temp.getPrev() == null) {
               SortList.this.first = temp;
            } else {
               temp.getPrev().setNext(temp);
            }

            this.element = null;
            temp.setList(SortList.this);
            ++SortList.this.size;
            if (SortList.this.tree != null) {
               SortList.this.tree.put(temp, temp);
            }

         }
      }

      public int nextIndex() {
         if (this.cursor == null) {
            return SortList.this.size;
         } else {
            SortListElement temp = this.cursor.getPrev();

            int index;
            for(index = 0; temp != null; temp = temp.getPrev()) {
               ++index;
            }

            return index;
         }
      }

      public int previousIndex() {
         if (this.cursor == null) {
            return SortList.this.size - 1;
         } else {
            SortListElement temp = this.cursor.getPrev();

            int index;
            for(index = -1; temp != null; ++index) {
               temp = temp.getPrev();
            }

            return index;
         }
      }
   }
}
