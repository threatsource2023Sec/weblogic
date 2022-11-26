package org.python.google.common.collect;

import java.util.ListIterator;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class TransformedListIterator extends TransformedIterator implements ListIterator {
   TransformedListIterator(ListIterator backingIterator) {
      super(backingIterator);
   }

   private ListIterator backingIterator() {
      return Iterators.cast(this.backingIterator);
   }

   public final boolean hasPrevious() {
      return this.backingIterator().hasPrevious();
   }

   public final Object previous() {
      return this.transform(this.backingIterator().previous());
   }

   public final int nextIndex() {
      return this.backingIterator().nextIndex();
   }

   public final int previousIndex() {
      return this.backingIterator().previousIndex();
   }

   public void set(Object element) {
      throw new UnsupportedOperationException();
   }

   public void add(Object element) {
      throw new UnsupportedOperationException();
   }
}
