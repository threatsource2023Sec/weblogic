package org.python.google.common.collect;

import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.concurrent.LazyInit;

@GwtCompatible(
   serializable = true,
   emulated = true
)
final class SingletonImmutableSet extends ImmutableSet {
   final transient Object element;
   @LazyInit
   private transient int cachedHashCode;

   SingletonImmutableSet(Object element) {
      this.element = Preconditions.checkNotNull(element);
   }

   SingletonImmutableSet(Object element, int hashCode) {
      this.element = element;
      this.cachedHashCode = hashCode;
   }

   public int size() {
      return 1;
   }

   public boolean contains(Object target) {
      return this.element.equals(target);
   }

   public UnmodifiableIterator iterator() {
      return Iterators.singletonIterator(this.element);
   }

   ImmutableList createAsList() {
      return ImmutableList.of(this.element);
   }

   boolean isPartialView() {
      return false;
   }

   int copyIntoArray(Object[] dst, int offset) {
      dst[offset] = this.element;
      return offset + 1;
   }

   public final int hashCode() {
      int code = this.cachedHashCode;
      if (code == 0) {
         this.cachedHashCode = code = this.element.hashCode();
      }

      return code;
   }

   boolean isHashCodeFast() {
      return this.cachedHashCode != 0;
   }

   public String toString() {
      return '[' + this.element.toString() + ']';
   }
}
