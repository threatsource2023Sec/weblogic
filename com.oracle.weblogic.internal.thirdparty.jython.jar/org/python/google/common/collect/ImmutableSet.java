package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.concurrent.LazyInit;
import org.python.google.j2objc.annotations.RetainedWith;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public abstract class ImmutableSet extends ImmutableCollection implements Set {
   static final int MAX_TABLE_SIZE = 1073741824;
   private static final double DESIRED_LOAD_FACTOR = 0.7;
   private static final int CUTOFF = 751619276;
   @LazyInit
   @RetainedWith
   private transient ImmutableList asList;

   public static ImmutableSet of() {
      return RegularImmutableSet.EMPTY;
   }

   public static ImmutableSet of(Object element) {
      return new SingletonImmutableSet(element);
   }

   public static ImmutableSet of(Object e1, Object e2) {
      return construct(2, e1, e2);
   }

   public static ImmutableSet of(Object e1, Object e2, Object e3) {
      return construct(3, e1, e2, e3);
   }

   public static ImmutableSet of(Object e1, Object e2, Object e3, Object e4) {
      return construct(4, e1, e2, e3, e4);
   }

   public static ImmutableSet of(Object e1, Object e2, Object e3, Object e4, Object e5) {
      return construct(5, e1, e2, e3, e4, e5);
   }

   @SafeVarargs
   public static ImmutableSet of(Object e1, Object e2, Object e3, Object e4, Object e5, Object e6, Object... others) {
      int paramCount = true;
      Object[] elements = new Object[6 + others.length];
      elements[0] = e1;
      elements[1] = e2;
      elements[2] = e3;
      elements[3] = e4;
      elements[4] = e5;
      elements[5] = e6;
      System.arraycopy(others, 0, elements, 6, others.length);
      return construct(elements.length, elements);
   }

   private static ImmutableSet construct(int n, Object... elements) {
      switch (n) {
         case 0:
            return of();
         case 1:
            Object elem = elements[0];
            return of(elem);
         default:
            int tableSize = chooseTableSize(n);
            Object[] table = new Object[tableSize];
            int mask = tableSize - 1;
            int hashCode = 0;
            int uniques = 0;
            int i = 0;

            for(; i < n; ++i) {
               Object element = ObjectArrays.checkElementNotNull(elements[i], i);
               int hash = element.hashCode();
               int j = Hashing.smear(hash);

               while(true) {
                  int index = j & mask;
                  Object value = table[index];
                  if (value == null) {
                     elements[uniques++] = element;
                     table[index] = element;
                     hashCode += hash;
                     break;
                  }

                  if (value.equals(element)) {
                     break;
                  }

                  ++j;
               }
            }

            Arrays.fill(elements, uniques, n, (Object)null);
            if (uniques == 1) {
               Object element = elements[0];
               return new SingletonImmutableSet(element, hashCode);
            } else if (chooseTableSize(uniques) < tableSize / 2) {
               return construct(uniques, elements);
            } else {
               Object[] uniqueElements = uniques < elements.length / 2 ? Arrays.copyOf(elements, uniques) : elements;
               return new RegularImmutableSet(uniqueElements, hashCode, table, mask, uniques);
            }
      }
   }

   @VisibleForTesting
   static int chooseTableSize(int setSize) {
      if (setSize >= 751619276) {
         Preconditions.checkArgument(setSize < 1073741824, "collection too large");
         return 1073741824;
      } else {
         int tableSize;
         for(tableSize = Integer.highestOneBit(setSize - 1) << 1; (double)tableSize * 0.7 < (double)setSize; tableSize <<= 1) {
         }

         return tableSize;
      }
   }

   public static ImmutableSet copyOf(Collection elements) {
      if (elements instanceof ImmutableSet && !(elements instanceof SortedSet)) {
         ImmutableSet set = (ImmutableSet)elements;
         if (!set.isPartialView()) {
            return set;
         }
      }

      Object[] array = elements.toArray();
      return construct(array.length, array);
   }

   public static ImmutableSet copyOf(Iterable elements) {
      return elements instanceof Collection ? copyOf((Collection)elements) : copyOf(elements.iterator());
   }

   public static ImmutableSet copyOf(Iterator elements) {
      if (!elements.hasNext()) {
         return of();
      } else {
         Object first = elements.next();
         return !elements.hasNext() ? of(first) : (new Builder()).add(first).addAll(elements).build();
      }
   }

   public static ImmutableSet copyOf(Object[] elements) {
      switch (elements.length) {
         case 0:
            return of();
         case 1:
            return of(elements[0]);
         default:
            return construct(elements.length, (Object[])elements.clone());
      }
   }

   ImmutableSet() {
   }

   boolean isHashCodeFast() {
      return false;
   }

   public boolean equals(@Nullable Object object) {
      if (object == this) {
         return true;
      } else {
         return object instanceof ImmutableSet && this.isHashCodeFast() && ((ImmutableSet)object).isHashCodeFast() && this.hashCode() != object.hashCode() ? false : Sets.equalsImpl(this, object);
      }
   }

   public int hashCode() {
      return Sets.hashCodeImpl(this);
   }

   public abstract UnmodifiableIterator iterator();

   public ImmutableList asList() {
      ImmutableList result = this.asList;
      return result == null ? (this.asList = this.createAsList()) : result;
   }

   ImmutableList createAsList() {
      return ImmutableList.asImmutableList(this.toArray());
   }

   Object writeReplace() {
      return new SerializedForm(this.toArray());
   }

   public static Builder builder() {
      return new Builder();
   }

   public static class Builder extends ImmutableCollection.ArrayBasedBuilder {
      public Builder() {
         this(4);
      }

      Builder(int capacity) {
         super(capacity);
      }

      @CanIgnoreReturnValue
      public Builder add(Object element) {
         super.add(element);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder add(Object... elements) {
         super.add(elements);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder addAll(Iterable elements) {
         super.addAll(elements);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder addAll(Iterator elements) {
         super.addAll(elements);
         return this;
      }

      public ImmutableSet build() {
         ImmutableSet result = ImmutableSet.construct(this.size, this.contents);
         this.size = result.size();
         this.forceCopy = true;
         return result;
      }
   }

   private static class SerializedForm implements Serializable {
      final Object[] elements;
      private static final long serialVersionUID = 0L;

      SerializedForm(Object[] elements) {
         this.elements = elements;
      }

      Object readResolve() {
         return ImmutableSet.copyOf(this.elements);
      }
   }

   abstract static class Indexed extends ImmutableSet {
      abstract Object get(int var1);

      public UnmodifiableIterator iterator() {
         return this.asList().iterator();
      }

      ImmutableList createAsList() {
         return new ImmutableList() {
            public Object get(int index) {
               return Indexed.this.get(index);
            }

            boolean isPartialView() {
               return Indexed.this.isPartialView();
            }

            public int size() {
               return Indexed.this.size();
            }
         };
      }
   }
}
