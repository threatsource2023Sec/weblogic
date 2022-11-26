package org.python.google.common.collect;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;

@GwtCompatible(
   serializable = true,
   emulated = true
)
final class RegularImmutableSet extends ImmutableSet {
   static final RegularImmutableSet EMPTY = new RegularImmutableSet(new Object[0], 0, (Object[])null, 0, 0);
   @VisibleForTesting
   final transient Object[] elements;
   @VisibleForTesting
   final transient Object[] table;
   private final transient int mask;
   private final transient int hashCode;
   private final transient int size;

   RegularImmutableSet(Object[] elements, int hashCode, Object[] table, int mask, int size) {
      this.elements = elements;
      this.table = table;
      this.mask = mask;
      this.hashCode = hashCode;
      this.size = size;
   }

   public boolean contains(@Nullable Object target) {
      Object[] table = this.table;
      if (target != null && table != null) {
         int i = Hashing.smearedHash(target);

         while(true) {
            i &= this.mask;
            Object candidate = table[i];
            if (candidate == null) {
               return false;
            }

            if (candidate.equals(target)) {
               return true;
            }

            ++i;
         }
      } else {
         return false;
      }
   }

   public int size() {
      return this.size;
   }

   public UnmodifiableIterator iterator() {
      return Iterators.forArray(this.elements, 0, this.size, 0);
   }

   int copyIntoArray(Object[] dst, int offset) {
      System.arraycopy(this.elements, 0, dst, offset, this.size);
      return offset + this.size;
   }

   ImmutableList createAsList() {
      return ImmutableList.asImmutableList(this.elements, this.size);
   }

   boolean isPartialView() {
      return false;
   }

   public int hashCode() {
      return this.hashCode;
   }

   boolean isHashCodeFast() {
      return true;
   }
}
