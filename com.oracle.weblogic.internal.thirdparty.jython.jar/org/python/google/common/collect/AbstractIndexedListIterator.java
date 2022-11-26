package org.python.google.common.collect;

import java.util.NoSuchElementException;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
abstract class AbstractIndexedListIterator extends UnmodifiableListIterator {
   private final int size;
   private int position;

   protected abstract Object get(int var1);

   protected AbstractIndexedListIterator(int size) {
      this(size, 0);
   }

   protected AbstractIndexedListIterator(int size, int position) {
      Preconditions.checkPositionIndex(position, size);
      this.size = size;
      this.position = position;
   }

   public final boolean hasNext() {
      return this.position < this.size;
   }

   public final Object next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return this.get(this.position++);
      }
   }

   public final int nextIndex() {
      return this.position;
   }

   public final boolean hasPrevious() {
      return this.position > 0;
   }

   public final Object previous() {
      if (!this.hasPrevious()) {
         throw new NoSuchElementException();
      } else {
         return this.get(--this.position);
      }
   }

   public final int previousIndex() {
      return this.position - 1;
   }
}
