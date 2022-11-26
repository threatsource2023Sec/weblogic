package org.jboss.weld.util.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import org.jboss.weld.exceptions.UnsupportedOperationException;
import org.jboss.weld.util.Preconditions;

public final class Iterators {
   private Iterators() {
   }

   public static boolean addAll(Collection target, Iterator iterator) {
      Preconditions.checkArgumentNotNull(target, "target");

      boolean modified;
      for(modified = false; iterator.hasNext(); modified |= target.add(iterator.next())) {
      }

      return modified;
   }

   public static Iterator concat(Iterator iterators) {
      Preconditions.checkArgumentNotNull(iterators, "iterators");
      return new CombinedIterator(iterators);
   }

   public static Iterator transform(Iterator iterator, Function function) {
      Preconditions.checkArgumentNotNull(iterator, "iterator");
      Preconditions.checkArgumentNotNull(function, "function");
      return new TransformingIterator(iterator, function);
   }

   abstract static class IndexIterator implements ListIterator {
      private int position;
      private final int size;

      IndexIterator(int size, int position) {
         this.size = size;
         this.position = position;
      }

      IndexIterator(int size) {
         this(size, 0);
      }

      public boolean hasNext() {
         return this.position < this.size;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return this.getElement(this.position++);
         }
      }

      public boolean hasPrevious() {
         return this.position > 0;
      }

      public Object previous() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            return this.getElement(--this.position);
         }
      }

      public int nextIndex() {
         return this.position;
      }

      public int previousIndex() {
         return this.position - 1;
      }

      abstract Object getElement(int var1);

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void set(Object e) {
         throw new UnsupportedOperationException();
      }

      public void add(Object e) {
         throw new UnsupportedOperationException();
      }
   }

   static class TransformingIterator implements Iterator {
      final Iterator iterator;
      final Function function;

      TransformingIterator(Iterator iterator, Function function) {
         this.iterator = iterator;
         this.function = function;
      }

      public final boolean hasNext() {
         return this.iterator.hasNext();
      }

      public final Object next() {
         return this.function.apply(this.iterator.next());
      }

      public final void remove() {
         this.iterator.remove();
      }
   }

   static class CombinedIterator implements Iterator {
      private final Iterator iterators;
      private Iterator current;
      private Iterator removeFrom;

      CombinedIterator(Iterator iterators) {
         this.iterators = iterators;
         this.current = Collections.emptyIterator();
      }

      public boolean hasNext() {
         boolean currentHasNext;
         while(!(currentHasNext = this.current.hasNext()) && this.iterators.hasNext()) {
            this.current = (Iterator)this.iterators.next();
         }

         return currentHasNext;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.removeFrom = this.current;
            return this.current.next();
         }
      }

      public void remove() {
         if (this.removeFrom == null) {
            throw new IllegalStateException();
         } else {
            this.removeFrom.remove();
            this.removeFrom = null;
         }
      }
   }
}
