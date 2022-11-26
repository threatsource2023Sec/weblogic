package org.apache.openjpa.lib.rop;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LazyForwardResultList extends AbstractSequentialResultList implements ResultList {
   private static final int OPEN = 0;
   private static final int CLOSED = 1;
   private static final int FREED = 2;
   private ResultObjectProvider _rop = null;
   private final List _list = new ArrayList();
   private int _state = 0;
   private int _size = -1;

   public LazyForwardResultList(ResultObjectProvider rop) {
      this._rop = rop;

      try {
         this._rop.open();
      } catch (RuntimeException var3) {
         this.close();
         throw var3;
      } catch (Exception var4) {
         this.close();
         this._rop.handleCheckedException(var4);
      }

   }

   public boolean isProviderOpen() {
      return this._state == 0;
   }

   public boolean isClosed() {
      return this._state == 1;
   }

   public void close() {
      if (this._state != 1) {
         this.free();
         this._state = 1;
      }

   }

   public Object get(int index) {
      this.assertOpen();
      if (index == this._list.size()) {
         this.addNext();
      }

      return index < this._list.size() ? this._list.get(index) : super.get(index);
   }

   protected ListIterator itr(int index) {
      return (ListIterator)(this._state != 0 ? this._list.listIterator(index) : new Itr(index));
   }

   public int size() {
      this.assertOpen();
      if (this._size != -1) {
         return this._size;
      } else if (this._state != 0) {
         return this._list.size();
      } else {
         try {
            this._size = this._rop.size();
            return this._size;
         } catch (RuntimeException var2) {
            this.close();
            throw var2;
         } catch (Exception var3) {
            this.close();
            this._rop.handleCheckedException(var3);
            return -1;
         }
      }
   }

   private boolean addNext() {
      try {
         if (!this._rop.next()) {
            this.free();
            return false;
         } else {
            this._list.add(this._rop.getResultObject());
            return true;
         }
      } catch (RuntimeException var2) {
         this.close();
         throw var2;
      } catch (Exception var3) {
         this.close();
         this._rop.handleCheckedException(var3);
         return false;
      }
   }

   private void free() {
      if (this._state == 0) {
         try {
            this._rop.close();
         } catch (Exception var2) {
         }

         this._state = 2;
      }

   }

   public Object writeReplace() throws ObjectStreamException {
      if (this._state == 0) {
         Iterator itr = this.itr(this._list.size());

         while(itr.hasNext()) {
            itr.next();
         }
      }

      return this._list;
   }

   public int hashCode() {
      return System.identityHashCode(this);
   }

   public boolean equals(Object other) {
      return other == this;
   }

   private class Itr extends AbstractListIterator {
      private int _idx = 0;

      public Itr(int index) {
         this._idx = Math.min(index, LazyForwardResultList.this._list.size());

         while(this._idx < index) {
            this.next();
         }

      }

      public int nextIndex() {
         return this._idx;
      }

      public int previousIndex() {
         return this._idx - 1;
      }

      public boolean hasNext() {
         if (LazyForwardResultList.this._list.size() > this._idx) {
            return true;
         } else {
            return LazyForwardResultList.this._state != 0 ? false : LazyForwardResultList.this.addNext();
         }
      }

      public boolean hasPrevious() {
         return this._idx > 0;
      }

      public Object previous() {
         if (this._idx == 0) {
            throw new NoSuchElementException();
         } else {
            return LazyForwardResultList.this._list.get(--this._idx);
         }
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return LazyForwardResultList.this._list.get(this._idx++);
         }
      }
   }
}
