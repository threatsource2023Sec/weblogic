package com.googlecode.cqengine.resultset.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class ConcatenatingIterator extends UnmodifiableIterator {
   private Iterator currentIterator = null;

   public boolean hasNext() {
      do {
         if (this.currentIterator != null && this.currentIterator.hasNext()) {
            return true;
         }

         this.currentIterator = this.getNextIterator();
      } while(this.currentIterator != null);

      return false;
   }

   public Object next() {
      Iterator currentIterator = this.currentIterator;
      if (currentIterator == null) {
         throw new NoSuchElementException("No more elements");
      } else {
         return currentIterator.next();
      }
   }

   public abstract Iterator getNextIterator();

   public static ConcatenatingIterator concatenate(final Iterable iterators) {
      return new ConcatenatingIterator() {
         final Iterator iteratorIterator = iterators.iterator();

         public Iterator getNextIterator() {
            return this.iteratorIterator.hasNext() ? (Iterator)this.iteratorIterator.next() : null;
         }
      };
   }
}
