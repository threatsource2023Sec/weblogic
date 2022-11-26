package com.googlecode.cqengine.resultset.iterator;

import java.util.Iterator;

public class ConcatenatingIterable implements Iterable {
   private final Iterable iterables;

   public ConcatenatingIterable(Iterable iterables) {
      this.iterables = iterables;
   }

   public Iterator iterator() {
      final Iterator iterator = this.iterables.iterator();
      return new ConcatenatingIterator() {
         public Iterator getNextIterator() {
            return iterator.hasNext() ? ((Iterable)iterator.next()).iterator() : null;
         }
      };
   }
}
