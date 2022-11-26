package com.googlecode.cqengine.resultset.common;

import com.googlecode.cqengine.resultset.ResultSet;
import java.util.Iterator;

public class IteratorCachingResultSet extends WrappedResultSet {
   Iterator cachedIterator = null;

   public IteratorCachingResultSet(ResultSet backingResultSet) {
      super(backingResultSet);
   }

   public Iterator iterator() {
      if (this.cachedIterator != null) {
         return this.cachedIterator;
      } else {
         final Iterator backingIterator = this.wrappedResultSet.iterator();
         Iterator wrappingIterator = new Iterator() {
            public boolean hasNext() {
               return backingIterator.hasNext();
            }

            public Object next() {
               IteratorCachingResultSet.this.cachedIterator = null;
               return backingIterator.next();
            }

            public void remove() {
               IteratorCachingResultSet.this.cachedIterator = null;
               backingIterator.remove();
            }
         };
         this.cachedIterator = wrappingIterator;
         return wrappingIterator;
      }
   }
}
