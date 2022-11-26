package org.python.google.common.collect;

import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   serializable = true,
   emulated = true
)
class RegularImmutableList extends ImmutableList {
   static final ImmutableList EMPTY = new RegularImmutableList(new Object[0], 0);
   @VisibleForTesting
   final transient Object[] array;
   private final transient int size;

   RegularImmutableList(Object[] array, int size) {
      this.array = array;
      this.size = size;
   }

   public int size() {
      return this.size;
   }

   boolean isPartialView() {
      return false;
   }

   int copyIntoArray(Object[] dst, int dstOff) {
      System.arraycopy(this.array, 0, dst, dstOff, this.size);
      return dstOff + this.size;
   }

   public Object get(int index) {
      Preconditions.checkElementIndex(index, this.size);
      return this.array[index];
   }

   public UnmodifiableListIterator listIterator(int index) {
      return Iterators.forArray(this.array, 0, this.size, index);
   }
}
