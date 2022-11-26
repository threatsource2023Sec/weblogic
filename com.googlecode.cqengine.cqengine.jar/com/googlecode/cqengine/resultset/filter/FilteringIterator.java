package com.googlecode.cqengine.resultset.filter;

import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.iterator.UnmodifiableIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class FilteringIterator extends UnmodifiableIterator {
   final Iterator wrappedIterator;
   final QueryOptions queryOptions;
   private Object nextObject = null;
   private boolean nextObjectIsNull = false;

   public FilteringIterator(Iterator wrappedIterator, QueryOptions queryOptions) {
      this.wrappedIterator = wrappedIterator;
      this.queryOptions = queryOptions;
   }

   public boolean hasNext() {
      if (!this.nextObjectIsNull && this.nextObject == null) {
         while(this.wrappedIterator.hasNext()) {
            this.nextObject = this.wrappedIterator.next();
            if (this.isValid(this.nextObject, this.queryOptions)) {
               this.nextObjectIsNull = this.nextObject == null;
               return true;
            }

            this.nextObjectIsNull = false;
         }

         return false;
      } else {
         return true;
      }
   }

   public Object next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         Object objectToReturn = this.nextObject;
         this.nextObject = null;
         this.nextObjectIsNull = false;
         return objectToReturn;
      }
   }

   public abstract boolean isValid(Object var1, QueryOptions var2);
}
