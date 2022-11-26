package org.jboss.weld.util.collections;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import org.jboss.weld.exceptions.UnsupportedOperationException;

abstract class AbstractImmutableList extends AbstractList {
   public boolean add(Object e) {
      throw new UnsupportedOperationException();
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

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(int index, Collection c) {
      throw new UnsupportedOperationException();
   }

   protected void removeRange(int fromIndex, int toIndex) {
      throw new UnsupportedOperationException();
   }

   public boolean isEmpty() {
      return false;
   }

   public void replaceAll(UnaryOperator operator) {
      throw new UnsupportedOperationException();
   }

   public void sort(Comparator c) {
      throw new UnsupportedOperationException();
   }

   public boolean removeIf(Predicate filter) {
      throw new UnsupportedOperationException();
   }

   public Iterator iterator() {
      return this.listIterator(0);
   }

   public ListIterator listIterator() {
      return this.listIterator(0);
   }

   public Spliterator spliterator() {
      return Spliterators.spliterator(this, 1296);
   }

   protected IndexOutOfBoundsException indexOutOfBoundsException(int index) {
      return new IndexOutOfBoundsException("Index: " + String.valueOf(index) + ", Size: " + this.size());
   }
}
