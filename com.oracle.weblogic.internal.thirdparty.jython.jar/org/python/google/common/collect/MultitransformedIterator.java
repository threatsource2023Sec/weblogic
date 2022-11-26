package org.python.google.common.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
abstract class MultitransformedIterator implements Iterator {
   final Iterator backingIterator;
   Iterator current = Iterators.emptyIterator();
   private Iterator removeFrom;

   MultitransformedIterator(Iterator backingIterator) {
      this.backingIterator = (Iterator)Preconditions.checkNotNull(backingIterator);
   }

   abstract Iterator transform(Object var1);

   public boolean hasNext() {
      Preconditions.checkNotNull(this.current);
      if (this.current.hasNext()) {
         return true;
      } else {
         do {
            if (!this.backingIterator.hasNext()) {
               return false;
            }

            Preconditions.checkNotNull(this.current = this.transform(this.backingIterator.next()));
         } while(!this.current.hasNext());

         return true;
      }
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
      CollectPreconditions.checkRemove(this.removeFrom != null);
      this.removeFrom.remove();
      this.removeFrom = null;
   }
}
