package org.python.google.common.collect;

import java.util.Iterator;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
abstract class TransformedIterator implements Iterator {
   final Iterator backingIterator;

   TransformedIterator(Iterator backingIterator) {
      this.backingIterator = (Iterator)Preconditions.checkNotNull(backingIterator);
   }

   abstract Object transform(Object var1);

   public final boolean hasNext() {
      return this.backingIterator.hasNext();
   }

   public final Object next() {
      return this.transform(this.backingIterator.next());
   }

   public final void remove() {
      this.backingIterator.remove();
   }
}
