package com.googlecode.cqengine.resultset.filter;

import com.googlecode.cqengine.resultset.iterator.UnmodifiableIterator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MaterializedDeduplicatedIterator extends UnmodifiableIterator {
   final Iterator backingIterator;
   final Set materializedSet = new HashSet();
   Object nextObject = null;

   public MaterializedDeduplicatedIterator(Iterator backingIterator) {
      this.backingIterator = backingIterator;
   }

   public boolean hasNext() {
      if (this.nextObject != null) {
         return true;
      } else {
         Object next;
         do {
            if (!this.backingIterator.hasNext()) {
               this.nextObject = null;
               this.materializedSet.clear();
               return false;
            }

            next = this.backingIterator.next();
            if (next == null) {
               throw new IllegalStateException("Unexpectedly received null from the backing iterator's iterator.next()");
            }
         } while(!this.materializedSet.add(next));

         this.nextObject = next;
         return true;
      }
   }

   public Object next() {
      Object next = this.nextObject;
      if (next == null) {
         throw new IllegalStateException("Detected an attempt to call iterator.next() without calling iterator.hasNext() immediately beforehand");
      } else {
         this.nextObject = null;
         return next;
      }
   }
}
