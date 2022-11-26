package org.python.google.common.collect;

import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;

@GwtCompatible(
   emulated = true
)
class RegularImmutableAsList extends ImmutableAsList {
   private final ImmutableCollection delegate;
   private final ImmutableList delegateList;

   RegularImmutableAsList(ImmutableCollection delegate, ImmutableList delegateList) {
      this.delegate = delegate;
      this.delegateList = delegateList;
   }

   RegularImmutableAsList(ImmutableCollection delegate, Object[] array) {
      this(delegate, ImmutableList.asImmutableList(array));
   }

   RegularImmutableAsList(ImmutableCollection delegate, Object[] array, int size) {
      this(delegate, ImmutableList.asImmutableList(array, size));
   }

   ImmutableCollection delegateCollection() {
      return this.delegate;
   }

   ImmutableList delegateList() {
      return this.delegateList;
   }

   public UnmodifiableListIterator listIterator(int index) {
      return this.delegateList.listIterator(index);
   }

   @GwtIncompatible
   int copyIntoArray(Object[] dst, int offset) {
      return this.delegateList.copyIntoArray(dst, offset);
   }

   public Object get(int index) {
      return this.delegateList.get(index);
   }
}
