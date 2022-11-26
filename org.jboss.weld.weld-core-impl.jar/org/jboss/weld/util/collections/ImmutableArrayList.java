package org.jboss.weld.util.collections;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import org.jboss.weld.util.Preconditions;

class ImmutableArrayList extends ImmutableList implements RandomAccess, Serializable {
   private static final long serialVersionUID = 1L;
   private final Object[] elements;

   ImmutableArrayList(Object[] elements) {
      this.elements = elements;
   }

   public Object get(int index) {
      if (index >= 0 && index < this.size()) {
         return this.elements[index];
      } else {
         throw this.indexOutOfBoundsException(index);
      }
   }

   public int size() {
      return this.elements.length;
   }

   public boolean contains(Object o) {
      Preconditions.checkNotNull(o);
      Object[] var2 = this.elements;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object element = var2[var4];
         if (o.equals(element)) {
            return true;
         }
      }

      return false;
   }

   public int indexOf(Object o) {
      Preconditions.checkNotNull(o);

      for(int i = 0; i < this.elements.length; ++i) {
         if (o.equals(this.elements[i])) {
            return i;
         }
      }

      return -1;
   }

   public int lastIndexOf(Object o) {
      Preconditions.checkNotNull(o);

      for(int i = this.elements.length - 1; i >= 0; --i) {
         if (o.equals(this.elements[i])) {
            return i;
         }
      }

      return -1;
   }

   public ListIterator listIterator(int index) {
      if (index >= 0 && index <= this.elements.length) {
         return new ListIteratorImpl(index);
      } else {
         throw this.indexOutOfBoundsException(index);
      }
   }

   public List subList(int fromIndex, int toIndex) {
      if (fromIndex >= 0 && fromIndex <= toIndex) {
         if (toIndex > this.elements.length) {
            throw this.indexOutOfBoundsException(toIndex);
         } else {
            return (List)(fromIndex == toIndex ? Collections.emptyList() : new ImmutableArrayList(Arrays.copyOfRange(this.elements, fromIndex, toIndex)));
         }
      } else {
         throw this.indexOutOfBoundsException(fromIndex);
      }
   }

   public int hashCode() {
      int hashCode = 1;
      Object[] var2 = this.elements;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object element = var2[var4];
         hashCode = 31 * hashCode + element.hashCode();
      }

      return hashCode;
   }

   public Object[] toArray() {
      return Arrays.copyOf(this.elements, this.size());
   }

   public Object[] toArray(Object[] array) {
      if (array.length < this.size()) {
         return (Object[])Arrays.copyOf(this.elements, this.size(), array.getClass());
      } else {
         System.arraycopy(this.elements, 0, array, 0, this.size());
         if (array.length > this.size()) {
            array[this.size()] = null;
         }

         return array;
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append('[');

      for(int i = 0; i < this.size(); ++i) {
         Object element = this.elements[i];
         sb.append(element == this ? "(this Collection)" : element);
         if (i + 1 < this.size()) {
            sb.append(',').append(' ');
         }
      }

      return sb.append(']').toString();
   }

   public void forEach(Consumer action) {
      Preconditions.checkNotNull(action);
      Object[] var2 = this.elements;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object object = var2[var4];
         action.accept(object);
      }

   }

   public Spliterator spliterator() {
      return Spliterators.spliterator(this.elements, 1296);
   }

   private class ListIteratorImpl extends Iterators.IndexIterator {
      ListIteratorImpl(int index) {
         super(ImmutableArrayList.this.size(), index);
      }

      Object getElement(int index) {
         return ImmutableArrayList.this.get(index);
      }
   }
}
