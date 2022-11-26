package org.jboss.weld.util.collections;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

abstract class ImmutableTinyList extends ImmutableList implements RandomAccess {
   static class Singleton extends ImmutableTinyList implements Serializable {
      private static final long serialVersionUID = 1L;
      private final Object element;

      Singleton(Object element) {
         this.element = element;
      }

      public int indexOf(Object o) {
         return o != null && o.equals(this.element) ? 0 : -1;
      }

      public int lastIndexOf(Object o) {
         return this.indexOf(o);
      }

      public ListIterator listIterator(int index) {
         if (index != 0 && index != 1) {
            throw this.indexOutOfBoundsException(index);
         } else {
            return new SingletonIterator(index);
         }
      }

      public List subList(int fromIndex, int toIndex) {
         if (fromIndex >= 0 && fromIndex <= toIndex) {
            if (toIndex > this.size()) {
               throw this.indexOutOfBoundsException(toIndex);
            } else {
               return (List)(fromIndex == toIndex ? Collections.emptyList() : this);
            }
         } else {
            throw this.indexOutOfBoundsException(fromIndex);
         }
      }

      public int hashCode() {
         return 31 + this.element.hashCode();
      }

      public Object get(int index) {
         if (index == 0) {
            return this.element;
         } else {
            throw this.indexOutOfBoundsException(index);
         }
      }

      public int size() {
         return 1;
      }

      private class SingletonIterator extends Iterators.IndexIterator {
         SingletonIterator(int index) {
            super(Singleton.this.size(), index);
         }

         Object getElement(int index) {
            return Singleton.this.element;
         }
      }
   }
}
