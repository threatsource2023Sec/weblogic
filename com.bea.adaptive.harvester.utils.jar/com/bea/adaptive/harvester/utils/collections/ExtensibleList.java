package com.bea.adaptive.harvester.utils.collections;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ExtensibleList extends ArrayList {
   private static final long serialVersionUID = 1L;

   public ExtensibleList() {
   }

   public ExtensibleList(int size) {
      super(size);
   }

   public Object set(int index, Object value) {
      CollectionUtils.extendTo(this, index + 1);
      return super.set(index, value);
   }

   public int elementCount() {
      int count = 0;
      java.util.Iterator it = super.iterator();

      while(it.hasNext()) {
         if (it.next() != null) {
            ++count;
         }
      }

      return count;
   }

   public Object get(int i) {
      return i > super.size() - 1 ? null : super.get(i);
   }

   public boolean containsIndex(int i) {
      if (i > super.size() - 1) {
         return false;
      } else {
         return super.get(i) != null;
      }
   }

   public Object remove(int i) {
      Object old = super.get(i);
      super.set(i, (Object)null);
      return old;
   }

   public boolean remove(Object o) {
      if (o == null) {
         return false;
      } else {
         int i = super.indexOf(o);
         if (i > this.size() - 1) {
            return false;
         } else if (i > -1) {
            super.set(i, (Object)null);
            return true;
         } else {
            return false;
         }
      }
   }

   public java.util.Iterator iterator() {
      return new Iterator();
   }

   class Iterator implements java.util.Iterator {
      int cursor = -1;
      int oldCursor = -1;

      Iterator() {
         this.setCursor();
      }

      public boolean hasNext() {
         return this.cursor >= 0;
      }

      public Object next() {
         Object old = ExtensibleList.this.get(this.cursor);
         if (old == null) {
            throw new NoSuchElementException();
         } else {
            this.oldCursor = this.cursor;
            this.setCursor();
            return old;
         }
      }

      public void remove() {
         ExtensibleList.this.set(this.oldCursor, (Object)null);
      }

      private void setCursor() {
         ++this.cursor;

         while(this.cursor < ExtensibleList.this.size()) {
            Object o = ExtensibleList.this.get(this.cursor);
            if (o != null) {
               return;
            }

            ++this.cursor;
         }

         this.cursor = -1;
      }
   }
}
