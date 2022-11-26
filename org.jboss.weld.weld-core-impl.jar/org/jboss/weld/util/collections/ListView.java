package org.jboss.weld.util.collections;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class ListView extends AbstractList {
   protected abstract List getDelegate();

   public Object get(int index) {
      return this.toView(this.getDelegate().get(index));
   }

   public int size() {
      return this.getDelegate().size();
   }

   public boolean add(Object element) {
      return this.getDelegate().add(this.createSource(element));
   }

   public Object set(int index, Object element) {
      return this.toView(this.getDelegate().set(index, this.createSource(element)));
   }

   public void add(int index, Object element) {
      this.getDelegate().add(index, this.createSource(element));
   }

   public Object remove(int index) {
      return this.toView(this.getDelegate().remove(index));
   }

   public void clear() {
      this.getDelegate().clear();
   }

   public Iterator iterator() {
      return this.listIterator();
   }

   public ListIterator listIterator() {
      return new ListViewIterator(this.getDelegate().listIterator());
   }

   public ListIterator listIterator(int index) {
      return new ListViewIterator(this.getDelegate().listIterator(index));
   }

   protected abstract Object toView(Object var1);

   protected abstract Object createSource(Object var1);

   protected class ListViewIterator implements ListIterator {
      protected final ListIterator delegate;

      public ListViewIterator(ListIterator delegate) {
         this.delegate = delegate;
      }

      public boolean hasNext() {
         return this.delegate.hasNext();
      }

      public Object next() {
         return ListView.this.toView(this.delegate.next());
      }

      public boolean hasPrevious() {
         return this.delegate.hasPrevious();
      }

      public Object previous() {
         return ListView.this.toView(this.delegate.previous());
      }

      public int nextIndex() {
         return this.delegate.nextIndex();
      }

      public int previousIndex() {
         return this.delegate.previousIndex();
      }

      public void remove() {
         this.delegate.remove();
      }

      public void set(Object e) {
         this.delegate.set(ListView.this.createSource(e));
      }

      public void add(Object e) {
         this.delegate.add(ListView.this.createSource(e));
      }
   }
}
