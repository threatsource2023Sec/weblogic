package org.apache.commons.pool.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

class CursorableLinkedList implements List, Serializable {
   private static final long serialVersionUID = 8836393098519411393L;
   protected transient int _size = 0;
   protected transient Listable _head = new Listable((Listable)null, (Listable)null, (Object)null);
   protected transient int _modCount = 0;
   protected transient List _cursors = new ArrayList();

   public boolean add(Object o) {
      this.insertListable(this._head.prev(), (Listable)null, o);
      return true;
   }

   public void add(int index, Object element) {
      if (index == this._size) {
         this.add(element);
      } else {
         if (index < 0 || index > this._size) {
            throw new IndexOutOfBoundsException(index + " < 0 or " + index + " > " + this._size);
         }

         Listable succ = this.isEmpty() ? null : this.getListableAt(index);
         Listable pred = null == succ ? null : succ.prev();
         this.insertListable(pred, succ, element);
      }

   }

   public boolean addAll(Collection c) {
      if (c.isEmpty()) {
         return false;
      } else {
         Iterator it = c.iterator();

         while(it.hasNext()) {
            this.insertListable(this._head.prev(), (Listable)null, it.next());
         }

         return true;
      }
   }

   public boolean addAll(int index, Collection c) {
      if (c.isEmpty()) {
         return false;
      } else if (this._size != index && this._size != 0) {
         Listable succ = this.getListableAt(index);
         Listable pred = null == succ ? null : succ.prev();

         for(Iterator it = c.iterator(); it.hasNext(); pred = this.insertListable(pred, succ, it.next())) {
         }

         return true;
      } else {
         return this.addAll(c);
      }
   }

   public boolean addFirst(Object o) {
      this.insertListable((Listable)null, this._head.next(), o);
      return true;
   }

   public boolean addLast(Object o) {
      this.insertListable(this._head.prev(), (Listable)null, o);
      return true;
   }

   public void clear() {
      Iterator it = this.iterator();

      while(it.hasNext()) {
         it.next();
         it.remove();
      }

   }

   public boolean contains(Object o) {
      Listable elt = this._head.next();
      Listable past = null;

      while(true) {
         if (null != elt && past != this._head.prev()) {
            if ((null != o || null != elt.value()) && (o == null || !o.equals(elt.value()))) {
               past = elt;
               elt = elt.next();
               continue;
            }

            return true;
         }

         return false;
      }
   }

   public boolean containsAll(Collection c) {
      Iterator it = c.iterator();

      do {
         if (!it.hasNext()) {
            return true;
         }
      } while(this.contains(it.next()));

      return false;
   }

   public Cursor cursor() {
      return new Cursor(0);
   }

   public Cursor cursor(int i) {
      return new Cursor(i);
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof List)) {
         return false;
      } else {
         Iterator it = ((List)o).listIterator();
         Listable elt = this._head.next();
         Listable past = null;

         while(true) {
            if (null != elt && past != this._head.prev()) {
               if (it.hasNext()) {
                  if (null == elt.value()) {
                     if (null != it.next()) {
                        return false;
                     }
                  } else if (!elt.value().equals(it.next())) {
                     return false;
                  }

                  past = elt;
                  elt = elt.next();
                  continue;
               }

               return false;
            }

            return !it.hasNext();
         }
      }
   }

   public Object get(int index) {
      return this.getListableAt(index).value();
   }

   public Object getFirst() {
      try {
         return this._head.next().value();
      } catch (NullPointerException var2) {
         throw new NoSuchElementException();
      }
   }

   public Object getLast() {
      try {
         return this._head.prev().value();
      } catch (NullPointerException var2) {
         throw new NoSuchElementException();
      }
   }

   public int hashCode() {
      int hash = 1;
      Listable elt = this._head.next();

      for(Listable past = null; null != elt && past != this._head.prev(); elt = elt.next()) {
         hash = 31 * hash + (null == elt.value() ? 0 : elt.value().hashCode());
         past = elt;
      }

      return hash;
   }

   public int indexOf(Object o) {
      int ndx = 0;
      Listable elt;
      Listable past;
      if (null == o) {
         elt = this._head.next();

         for(past = null; null != elt && past != this._head.prev(); elt = elt.next()) {
            if (null == elt.value()) {
               return ndx;
            }

            ++ndx;
            past = elt;
         }
      } else {
         elt = this._head.next();

         for(past = null; null != elt && past != this._head.prev(); elt = elt.next()) {
            if (o.equals(elt.value())) {
               return ndx;
            }

            ++ndx;
            past = elt;
         }
      }

      return -1;
   }

   public boolean isEmpty() {
      return 0 == this._size;
   }

   public Iterator iterator() {
      return this.listIterator(0);
   }

   public int lastIndexOf(Object o) {
      int ndx = this._size - 1;
      Listable elt;
      Listable past;
      if (null == o) {
         elt = this._head.prev();

         for(past = null; null != elt && past != this._head.next(); elt = elt.prev()) {
            if (null == elt.value()) {
               return ndx;
            }

            --ndx;
            past = elt;
         }
      } else {
         elt = this._head.prev();

         for(past = null; null != elt && past != this._head.next(); elt = elt.prev()) {
            if (o.equals(elt.value())) {
               return ndx;
            }

            --ndx;
            past = elt;
         }
      }

      return -1;
   }

   public ListIterator listIterator() {
      return this.listIterator(0);
   }

   public ListIterator listIterator(int index) {
      if (index >= 0 && index <= this._size) {
         return new ListIter(index);
      } else {
         throw new IndexOutOfBoundsException(index + " < 0 or > " + this._size);
      }
   }

   public boolean remove(Object o) {
      Listable elt = this._head.next();

      for(Listable past = null; null != elt && past != this._head.prev(); elt = elt.next()) {
         if (null == o && null == elt.value()) {
            this.removeListable(elt);
            return true;
         }

         if (o != null && o.equals(elt.value())) {
            this.removeListable(elt);
            return true;
         }

         past = elt;
      }

      return false;
   }

   public Object remove(int index) {
      Listable elt = this.getListableAt(index);
      Object ret = elt.value();
      this.removeListable(elt);
      return ret;
   }

   public boolean removeAll(Collection c) {
      if (0 != c.size() && 0 != this._size) {
         boolean changed = false;
         Iterator it = this.iterator();

         while(it.hasNext()) {
            if (c.contains(it.next())) {
               it.remove();
               changed = true;
            }
         }

         return changed;
      } else {
         return false;
      }
   }

   public Object removeFirst() {
      if (this._head.next() != null) {
         Object val = this._head.next().value();
         this.removeListable(this._head.next());
         return val;
      } else {
         throw new NoSuchElementException();
      }
   }

   public Object removeLast() {
      if (this._head.prev() != null) {
         Object val = this._head.prev().value();
         this.removeListable(this._head.prev());
         return val;
      } else {
         throw new NoSuchElementException();
      }
   }

   public boolean retainAll(Collection c) {
      boolean changed = false;
      Iterator it = this.iterator();

      while(it.hasNext()) {
         if (!c.contains(it.next())) {
            it.remove();
            changed = true;
         }
      }

      return changed;
   }

   public Object set(int index, Object element) {
      Listable elt = this.getListableAt(index);
      Object val = elt.setValue(element);
      this.broadcastListableChanged(elt);
      return val;
   }

   public int size() {
      return this._size;
   }

   public Object[] toArray() {
      Object[] array = new Object[this._size];
      int i = 0;
      Listable elt = this._head.next();

      for(Listable past = null; null != elt && past != this._head.prev(); elt = elt.next()) {
         array[i++] = elt.value();
         past = elt;
      }

      return array;
   }

   public Object[] toArray(Object[] a) {
      if (a.length < this._size) {
         a = (Object[])((Object[])Array.newInstance(a.getClass().getComponentType(), this._size));
      }

      int i = 0;
      Listable elt = this._head.next();

      for(Listable past = null; null != elt && past != this._head.prev(); elt = elt.next()) {
         a[i++] = elt.value();
         past = elt;
      }

      if (a.length > this._size) {
         a[this._size] = null;
      }

      return a;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[");
      Listable elt = this._head.next();

      for(Listable past = null; null != elt && past != this._head.prev(); elt = elt.next()) {
         if (this._head.next() != elt) {
            buf.append(", ");
         }

         buf.append(elt.value());
         past = elt;
      }

      buf.append("]");
      return buf.toString();
   }

   public List subList(int i, int j) {
      if (i >= 0 && j <= this._size && i <= j) {
         return (List)(i == 0 && j == this._size ? this : new CursorableSubList(this, i, j));
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   protected Listable insertListable(Listable before, Listable after, Object value) {
      ++this._modCount;
      ++this._size;
      Listable elt = new Listable(before, after, value);
      if (null != before) {
         before.setNext(elt);
      } else {
         this._head.setNext(elt);
      }

      if (null != after) {
         after.setPrev(elt);
      } else {
         this._head.setPrev(elt);
      }

      this.broadcastListableInserted(elt);
      return elt;
   }

   protected void removeListable(Listable elt) {
      ++this._modCount;
      --this._size;
      if (this._head.next() == elt) {
         this._head.setNext(elt.next());
      }

      if (null != elt.next()) {
         elt.next().setPrev(elt.prev());
      }

      if (this._head.prev() == elt) {
         this._head.setPrev(elt.prev());
      }

      if (null != elt.prev()) {
         elt.prev().setNext(elt.next());
      }

      this.broadcastListableRemoved(elt);
   }

   protected Listable getListableAt(int index) {
      if (index >= 0 && index < this._size) {
         Listable elt;
         int i;
         if (index <= this._size / 2) {
            elt = this._head.next();

            for(i = 0; i < index; ++i) {
               elt = elt.next();
            }

            return elt;
         } else {
            elt = this._head.prev();

            for(i = this._size - 1; i > index; --i) {
               elt = elt.prev();
            }

            return elt;
         }
      } else {
         throw new IndexOutOfBoundsException(index + " < 0 or " + index + " >= " + this._size);
      }
   }

   protected void registerCursor(Cursor cur) {
      Iterator it = this._cursors.iterator();

      while(it.hasNext()) {
         WeakReference ref = (WeakReference)it.next();
         if (ref.get() == null) {
            it.remove();
         }
      }

      this._cursors.add(new WeakReference(cur));
   }

   protected void unregisterCursor(Cursor cur) {
      Iterator it = this._cursors.iterator();

      while(it.hasNext()) {
         WeakReference ref = (WeakReference)it.next();
         Cursor cursor = (Cursor)ref.get();
         if (cursor == null) {
            it.remove();
         } else if (cursor == cur) {
            ref.clear();
            it.remove();
            break;
         }
      }

   }

   protected void invalidateCursors() {
      for(Iterator it = this._cursors.iterator(); it.hasNext(); it.remove()) {
         WeakReference ref = (WeakReference)it.next();
         Cursor cursor = (Cursor)ref.get();
         if (cursor != null) {
            cursor.invalidate();
            ref.clear();
         }
      }

   }

   protected void broadcastListableChanged(Listable elt) {
      Iterator it = this._cursors.iterator();

      while(it.hasNext()) {
         WeakReference ref = (WeakReference)it.next();
         Cursor cursor = (Cursor)ref.get();
         if (cursor == null) {
            it.remove();
         } else {
            cursor.listableChanged(elt);
         }
      }

   }

   protected void broadcastListableRemoved(Listable elt) {
      Iterator it = this._cursors.iterator();

      while(it.hasNext()) {
         WeakReference ref = (WeakReference)it.next();
         Cursor cursor = (Cursor)ref.get();
         if (cursor == null) {
            it.remove();
         } else {
            cursor.listableRemoved(elt);
         }
      }

   }

   protected void broadcastListableInserted(Listable elt) {
      Iterator it = this._cursors.iterator();

      while(it.hasNext()) {
         WeakReference ref = (WeakReference)it.next();
         Cursor cursor = (Cursor)ref.get();
         if (cursor == null) {
            it.remove();
         } else {
            cursor.listableInserted(elt);
         }
      }

   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();
      out.writeInt(this._size);

      for(Listable cur = this._head.next(); cur != null; cur = cur.next()) {
         out.writeObject(cur.value());
      }

   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this._size = 0;
      this._modCount = 0;
      this._cursors = new ArrayList();
      this._head = new Listable((Listable)null, (Listable)null, (Object)null);
      int size = in.readInt();

      for(int i = 0; i < size; ++i) {
         this.add(in.readObject());
      }

   }

   public class Cursor extends ListIter implements ListIterator {
      boolean _valid = false;

      Cursor(int index) {
         super(index);
         this._valid = true;
         CursorableLinkedList.this.registerCursor(this);
      }

      public int previousIndex() {
         throw new UnsupportedOperationException();
      }

      public int nextIndex() {
         throw new UnsupportedOperationException();
      }

      public void add(Object o) {
         this.checkForComod();
         Listable elt = CursorableLinkedList.this.insertListable(this._cur.prev(), this._cur.next(), o);
         this._cur.setPrev(elt);
         this._cur.setNext(elt.next());
         this._lastReturned = null;
         ++this._nextIndex;
         ++this._expectedModCount;
      }

      protected void listableRemoved(Listable elt) {
         if (null == CursorableLinkedList.this._head.prev()) {
            this._cur.setNext((Listable)null);
         } else if (this._cur.next() == elt) {
            this._cur.setNext(elt.next());
         }

         if (null == CursorableLinkedList.this._head.next()) {
            this._cur.setPrev((Listable)null);
         } else if (this._cur.prev() == elt) {
            this._cur.setPrev(elt.prev());
         }

         if (this._lastReturned == elt) {
            this._lastReturned = null;
         }

      }

      protected void listableInserted(Listable elt) {
         if (null == this._cur.next() && null == this._cur.prev()) {
            this._cur.setNext(elt);
         } else if (this._cur.prev() == elt.prev()) {
            this._cur.setNext(elt);
         }

         if (this._cur.next() == elt.next()) {
            this._cur.setPrev(elt);
         }

         if (this._lastReturned == elt) {
            this._lastReturned = null;
         }

      }

      protected void listableChanged(Listable elt) {
         if (this._lastReturned == elt) {
            this._lastReturned = null;
         }

      }

      protected void checkForComod() {
         if (!this._valid) {
            throw new ConcurrentModificationException();
         }
      }

      protected void invalidate() {
         this._valid = false;
      }

      public void close() {
         if (this._valid) {
            this._valid = false;
            CursorableLinkedList.this.unregisterCursor(this);
         }

      }
   }

   class ListIter implements ListIterator {
      Listable _cur = null;
      Listable _lastReturned = null;
      int _expectedModCount;
      int _nextIndex;

      ListIter(int index) {
         this._expectedModCount = CursorableLinkedList.this._modCount;
         this._nextIndex = 0;
         if (index == 0) {
            this._cur = new Listable((Listable)null, CursorableLinkedList.this._head.next(), (Object)null);
            this._nextIndex = 0;
         } else if (index == CursorableLinkedList.this._size) {
            this._cur = new Listable(CursorableLinkedList.this._head.prev(), (Listable)null, (Object)null);
            this._nextIndex = CursorableLinkedList.this._size;
         } else {
            Listable temp = CursorableLinkedList.this.getListableAt(index);
            this._cur = new Listable(temp.prev(), temp, (Object)null);
            this._nextIndex = index;
         }

      }

      public Object previous() {
         this.checkForComod();
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            Object ret = this._cur.prev().value();
            this._lastReturned = this._cur.prev();
            this._cur.setNext(this._cur.prev());
            this._cur.setPrev(this._cur.prev().prev());
            --this._nextIndex;
            return ret;
         }
      }

      public boolean hasNext() {
         this.checkForComod();
         return null != this._cur.next() && this._cur.prev() != CursorableLinkedList.this._head.prev();
      }

      public Object next() {
         this.checkForComod();
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Object ret = this._cur.next().value();
            this._lastReturned = this._cur.next();
            this._cur.setPrev(this._cur.next());
            this._cur.setNext(this._cur.next().next());
            ++this._nextIndex;
            return ret;
         }
      }

      public int previousIndex() {
         this.checkForComod();
         return !this.hasPrevious() ? -1 : this._nextIndex - 1;
      }

      public boolean hasPrevious() {
         this.checkForComod();
         return null != this._cur.prev() && this._cur.next() != CursorableLinkedList.this._head.next();
      }

      public void set(Object o) {
         this.checkForComod();

         try {
            this._lastReturned.setValue(o);
         } catch (NullPointerException var3) {
            throw new IllegalStateException();
         }
      }

      public int nextIndex() {
         this.checkForComod();
         return !this.hasNext() ? CursorableLinkedList.this.size() : this._nextIndex;
      }

      public void remove() {
         this.checkForComod();
         if (null == this._lastReturned) {
            throw new IllegalStateException();
         } else {
            this._cur.setNext(this._lastReturned == CursorableLinkedList.this._head.prev() ? null : this._lastReturned.next());
            this._cur.setPrev(this._lastReturned == CursorableLinkedList.this._head.next() ? null : this._lastReturned.prev());
            CursorableLinkedList.this.removeListable(this._lastReturned);
            this._lastReturned = null;
            --this._nextIndex;
            ++this._expectedModCount;
         }
      }

      public void add(Object o) {
         this.checkForComod();
         this._cur.setPrev(CursorableLinkedList.this.insertListable(this._cur.prev(), this._cur.next(), o));
         this._lastReturned = null;
         ++this._nextIndex;
         ++this._expectedModCount;
      }

      protected void checkForComod() {
         if (this._expectedModCount != CursorableLinkedList.this._modCount) {
            throw new ConcurrentModificationException();
         }
      }
   }

   static class Listable implements Serializable {
      private Listable _prev = null;
      private Listable _next = null;
      private Object _val = null;

      Listable(Listable prev, Listable next, Object val) {
         this._prev = prev;
         this._next = next;
         this._val = val;
      }

      Listable next() {
         return this._next;
      }

      Listable prev() {
         return this._prev;
      }

      Object value() {
         return this._val;
      }

      void setNext(Listable next) {
         this._next = next;
      }

      void setPrev(Listable prev) {
         this._prev = prev;
      }

      Object setValue(Object val) {
         Object temp = this._val;
         this._val = val;
         return temp;
      }
   }
}
