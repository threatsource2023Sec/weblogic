package org.python.google.common.collect;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public abstract class ImmutableCollection extends AbstractCollection implements Serializable {
   private static final Object[] EMPTY_ARRAY = new Object[0];

   ImmutableCollection() {
   }

   public abstract UnmodifiableIterator iterator();

   public final Object[] toArray() {
      int size = this.size();
      if (size == 0) {
         return EMPTY_ARRAY;
      } else {
         Object[] result = new Object[size];
         this.copyIntoArray(result, 0);
         return result;
      }
   }

   @CanIgnoreReturnValue
   public final Object[] toArray(Object[] other) {
      Preconditions.checkNotNull(other);
      int size = this.size();
      if (other.length < size) {
         other = ObjectArrays.newArray(other, size);
      } else if (other.length > size) {
         other[size] = null;
      }

      this.copyIntoArray(other, 0);
      return other;
   }

   public abstract boolean contains(@Nullable Object var1);

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final boolean add(Object e) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final boolean remove(Object object) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final boolean addAll(Collection newElements) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final boolean removeAll(Collection oldElements) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final boolean retainAll(Collection elementsToKeep) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public final void clear() {
      throw new UnsupportedOperationException();
   }

   public ImmutableList asList() {
      return this.isEmpty() ? ImmutableList.of() : ImmutableList.asImmutableList(this.toArray());
   }

   abstract boolean isPartialView();

   @CanIgnoreReturnValue
   int copyIntoArray(Object[] dst, int offset) {
      Object e;
      for(UnmodifiableIterator var3 = this.iterator(); var3.hasNext(); dst[offset++] = e) {
         e = var3.next();
      }

      return offset;
   }

   Object writeReplace() {
      return new ImmutableList.SerializedForm(this.toArray());
   }

   abstract static class ArrayBasedBuilder extends Builder {
      Object[] contents;
      int size;
      boolean forceCopy;

      ArrayBasedBuilder(int initialCapacity) {
         CollectPreconditions.checkNonnegative(initialCapacity, "initialCapacity");
         this.contents = new Object[initialCapacity];
         this.size = 0;
      }

      private void getReadyToExpandTo(int minCapacity) {
         if (this.contents.length < minCapacity) {
            this.contents = Arrays.copyOf(this.contents, expandedCapacity(this.contents.length, minCapacity));
            this.forceCopy = false;
         } else if (this.forceCopy) {
            this.contents = (Object[])this.contents.clone();
            this.forceCopy = false;
         }

      }

      @CanIgnoreReturnValue
      public ArrayBasedBuilder add(Object element) {
         Preconditions.checkNotNull(element);
         this.getReadyToExpandTo(this.size + 1);
         this.contents[this.size++] = element;
         return this;
      }

      @CanIgnoreReturnValue
      public Builder add(Object... elements) {
         ObjectArrays.checkElementsNotNull(elements);
         this.getReadyToExpandTo(this.size + elements.length);
         System.arraycopy(elements, 0, this.contents, this.size, elements.length);
         this.size += elements.length;
         return this;
      }

      @CanIgnoreReturnValue
      public Builder addAll(Iterable elements) {
         if (elements instanceof Collection) {
            Collection collection = (Collection)elements;
            this.getReadyToExpandTo(this.size + collection.size());
         }

         super.addAll(elements);
         return this;
      }
   }

   public abstract static class Builder {
      static final int DEFAULT_INITIAL_CAPACITY = 4;

      static int expandedCapacity(int oldCapacity, int minCapacity) {
         if (minCapacity < 0) {
            throw new AssertionError("cannot store more than MAX_VALUE elements");
         } else {
            int newCapacity = oldCapacity + (oldCapacity >> 1) + 1;
            if (newCapacity < minCapacity) {
               newCapacity = Integer.highestOneBit(minCapacity - 1) << 1;
            }

            if (newCapacity < 0) {
               newCapacity = Integer.MAX_VALUE;
            }

            return newCapacity;
         }
      }

      Builder() {
      }

      @CanIgnoreReturnValue
      public abstract Builder add(Object var1);

      @CanIgnoreReturnValue
      public Builder add(Object... elements) {
         Object[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object element = var2[var4];
            this.add(element);
         }

         return this;
      }

      @CanIgnoreReturnValue
      public Builder addAll(Iterable elements) {
         Iterator var2 = elements.iterator();

         while(var2.hasNext()) {
            Object element = var2.next();
            this.add(element);
         }

         return this;
      }

      @CanIgnoreReturnValue
      public Builder addAll(Iterator elements) {
         while(elements.hasNext()) {
            this.add(elements.next());
         }

         return this;
      }

      public abstract ImmutableCollection build();
   }
}
