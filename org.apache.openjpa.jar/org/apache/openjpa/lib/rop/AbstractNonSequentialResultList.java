package org.apache.openjpa.lib.rop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang.ObjectUtils;

public abstract class AbstractNonSequentialResultList extends AbstractResultList {
   protected static final Object PAST_END = new Object();

   protected abstract Object getInternal(int var1);

   public boolean contains(Object o) {
      this.assertOpen();
      int i = 0;

      while(true) {
         Object obj = this.getInternal(i);
         if (obj == PAST_END) {
            return false;
         }

         if (ObjectUtils.equals(o, obj)) {
            return true;
         }

         ++i;
      }
   }

   public boolean containsAll(Collection c) {
      this.assertOpen();
      Iterator itr = c.iterator();

      do {
         if (!itr.hasNext()) {
            return true;
         }
      } while(this.contains(itr.next()));

      return false;
   }

   public Object get(int index) {
      this.assertOpen();
      Object obj = this.getInternal(index);
      if (obj == PAST_END) {
         throw new NoSuchElementException();
      } else {
         return obj;
      }
   }

   public int indexOf(Object o) {
      this.assertOpen();
      int i = 0;

      while(true) {
         Object obj = this.getInternal(i);
         if (obj == PAST_END) {
            return -1;
         }

         if (ObjectUtils.equals(o, obj)) {
            return i;
         }

         ++i;
      }
   }

   public int lastIndexOf(Object o) {
      this.assertOpen();
      int index = -1;
      int i = 0;

      while(true) {
         Object obj = this.getInternal(i);
         if (obj == PAST_END) {
            return index;
         }

         if (ObjectUtils.equals(o, obj)) {
            index = i;
         }

         ++i;
      }
   }

   public boolean isEmpty() {
      this.assertOpen();
      return this.getInternal(0) == PAST_END;
   }

   public Iterator iterator() {
      return this.listIterator();
   }

   public ListIterator listIterator() {
      return this.listIterator(0);
   }

   public ListIterator listIterator(int index) {
      return new ResultListIterator(new Itr(index), this);
   }

   public Object[] toArray() {
      this.assertOpen();
      ArrayList list = new ArrayList();
      int i = 0;

      while(true) {
         Object obj = this.getInternal(i);
         if (obj == PAST_END) {
            return list.toArray();
         }

         list.add(obj);
         ++i;
      }
   }

   public Object[] toArray(Object[] a) {
      this.assertOpen();
      ArrayList list = new ArrayList();
      int i = 0;

      while(true) {
         Object obj = this.getInternal(i);
         if (obj == PAST_END) {
            return list.toArray(a);
         }

         list.add(obj);
         ++i;
      }
   }

   private class Itr extends AbstractListIterator {
      private int _idx = 0;
      private Object _next;

      public Itr(int index) {
         this._next = AbstractNonSequentialResultList.PAST_END;
         this._idx = index;
      }

      public int nextIndex() {
         return this._idx;
      }

      public int previousIndex() {
         return this._idx - 1;
      }

      public boolean hasNext() {
         this._next = AbstractNonSequentialResultList.this.getInternal(this._idx);
         return this._next != AbstractNonSequentialResultList.PAST_END;
      }

      public boolean hasPrevious() {
         return this._idx > 0;
      }

      public Object previous() {
         if (this._idx == 0) {
            throw new NoSuchElementException();
         } else {
            return AbstractNonSequentialResultList.this.getInternal(--this._idx);
         }
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            ++this._idx;
            return this._next;
         }
      }
   }
}
