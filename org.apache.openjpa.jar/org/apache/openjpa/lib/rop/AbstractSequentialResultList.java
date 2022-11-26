package org.apache.openjpa.lib.rop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.apache.commons.lang.ObjectUtils;

public abstract class AbstractSequentialResultList extends AbstractResultList {
   protected abstract ListIterator itr(int var1);

   public boolean contains(Object o) {
      this.assertOpen();
      Iterator itr = this.itr(0);

      do {
         if (!itr.hasNext()) {
            return false;
         }
      } while(!ObjectUtils.equals(o, itr.next()));

      return true;
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
      return this.itr(index).next();
   }

   public int indexOf(Object o) {
      this.assertOpen();
      int index = 0;

      for(Iterator itr = this.itr(0); itr.hasNext(); ++index) {
         if (ObjectUtils.equals(o, itr.next())) {
            return index;
         }
      }

      return -1;
   }

   public int lastIndexOf(Object o) {
      this.assertOpen();
      int index = -1;
      int i = 0;

      for(Iterator itr = this.itr(0); itr.hasNext(); ++i) {
         if (ObjectUtils.equals(o, itr.next())) {
            index = i;
         }
      }

      return index;
   }

   public boolean isEmpty() {
      this.assertOpen();
      return !this.itr(0).hasNext();
   }

   public Iterator iterator() {
      return this.listIterator();
   }

   public ListIterator listIterator() {
      return this.listIterator(0);
   }

   public ListIterator listIterator(int index) {
      return new ResultListIterator(this.itr(index), this);
   }

   public Object[] toArray() {
      this.assertOpen();
      ArrayList list = new ArrayList();
      Iterator itr = this.itr(0);

      while(itr.hasNext()) {
         list.add(itr.next());
      }

      return list.toArray();
   }

   public Object[] toArray(Object[] a) {
      this.assertOpen();
      ArrayList list = new ArrayList();
      Iterator itr = this.itr(0);

      while(itr.hasNext()) {
         list.add(itr.next());
      }

      return list.toArray(a);
   }
}
