package org.python.google.common.collect;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.primitives.Ints;
import org.python.google.errorprone.annotations.concurrent.LazyInit;

@GwtCompatible(
   serializable = true
)
class RegularImmutableMultiset extends ImmutableMultiset {
   static final RegularImmutableMultiset EMPTY = new RegularImmutableMultiset(ObjectCountHashMap.create());
   private final transient ObjectCountHashMap contents;
   private final transient int size;
   @LazyInit
   private transient ImmutableSet elementSet;

   RegularImmutableMultiset(ObjectCountHashMap contents) {
      this.contents = contents;
      long size = 0L;

      for(int i = 0; i < contents.size(); ++i) {
         size += (long)contents.getValue(i);
      }

      this.size = Ints.saturatedCast(size);
   }

   boolean isPartialView() {
      return false;
   }

   public int count(@Nullable Object element) {
      return this.contents.get(element);
   }

   public int size() {
      return this.size;
   }

   public ImmutableSet elementSet() {
      ImmutableSet result = this.elementSet;
      return result == null ? (this.elementSet = new ElementSet()) : result;
   }

   Multiset.Entry getEntry(int index) {
      return this.contents.getEntry(index);
   }

   private final class ElementSet extends ImmutableSet.Indexed {
      private ElementSet() {
      }

      Object get(int index) {
         return RegularImmutableMultiset.this.contents.getKey(index);
      }

      public boolean contains(@Nullable Object object) {
         return RegularImmutableMultiset.this.contains(object);
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return RegularImmutableMultiset.this.contents.size();
      }

      // $FF: synthetic method
      ElementSet(Object x1) {
         this();
      }
   }
}
