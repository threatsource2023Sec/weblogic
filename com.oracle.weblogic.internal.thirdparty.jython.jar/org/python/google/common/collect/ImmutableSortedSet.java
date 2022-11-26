package org.python.google.common.collect;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.concurrent.LazyInit;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public abstract class ImmutableSortedSet extends ImmutableSortedSetFauxverideShim implements NavigableSet, SortedIterable {
   final transient Comparator comparator;
   @LazyInit
   @GwtIncompatible
   transient ImmutableSortedSet descendingSet;

   static RegularImmutableSortedSet emptySet(Comparator comparator) {
      return Ordering.natural().equals(comparator) ? RegularImmutableSortedSet.NATURAL_EMPTY_SET : new RegularImmutableSortedSet(ImmutableList.of(), comparator);
   }

   public static ImmutableSortedSet of() {
      return RegularImmutableSortedSet.NATURAL_EMPTY_SET;
   }

   public static ImmutableSortedSet of(Comparable element) {
      return new RegularImmutableSortedSet(ImmutableList.of(element), Ordering.natural());
   }

   public static ImmutableSortedSet of(Comparable e1, Comparable e2) {
      return construct(Ordering.natural(), 2, e1, e2);
   }

   public static ImmutableSortedSet of(Comparable e1, Comparable e2, Comparable e3) {
      return construct(Ordering.natural(), 3, e1, e2, e3);
   }

   public static ImmutableSortedSet of(Comparable e1, Comparable e2, Comparable e3, Comparable e4) {
      return construct(Ordering.natural(), 4, e1, e2, e3, e4);
   }

   public static ImmutableSortedSet of(Comparable e1, Comparable e2, Comparable e3, Comparable e4, Comparable e5) {
      return construct(Ordering.natural(), 5, e1, e2, e3, e4, e5);
   }

   public static ImmutableSortedSet of(Comparable e1, Comparable e2, Comparable e3, Comparable e4, Comparable e5, Comparable e6, Comparable... remaining) {
      Comparable[] contents = new Comparable[6 + remaining.length];
      contents[0] = e1;
      contents[1] = e2;
      contents[2] = e3;
      contents[3] = e4;
      contents[4] = e5;
      contents[5] = e6;
      System.arraycopy(remaining, 0, contents, 6, remaining.length);
      return construct(Ordering.natural(), contents.length, (Comparable[])contents);
   }

   public static ImmutableSortedSet copyOf(Comparable[] elements) {
      return construct(Ordering.natural(), elements.length, (Object[])elements.clone());
   }

   public static ImmutableSortedSet copyOf(Iterable elements) {
      Ordering naturalOrder = Ordering.natural();
      return copyOf(naturalOrder, (Iterable)elements);
   }

   public static ImmutableSortedSet copyOf(Collection elements) {
      Ordering naturalOrder = Ordering.natural();
      return copyOf(naturalOrder, (Collection)elements);
   }

   public static ImmutableSortedSet copyOf(Iterator elements) {
      Ordering naturalOrder = Ordering.natural();
      return copyOf(naturalOrder, (Iterator)elements);
   }

   public static ImmutableSortedSet copyOf(Comparator comparator, Iterator elements) {
      return (new Builder(comparator)).addAll(elements).build();
   }

   public static ImmutableSortedSet copyOf(Comparator comparator, Iterable elements) {
      Preconditions.checkNotNull(comparator);
      boolean hasSameComparator = SortedIterables.hasSameComparator(comparator, elements);
      if (hasSameComparator && elements instanceof ImmutableSortedSet) {
         ImmutableSortedSet original = (ImmutableSortedSet)elements;
         if (!original.isPartialView()) {
            return original;
         }
      }

      Object[] array = (Object[])Iterables.toArray(elements);
      return construct(comparator, array.length, array);
   }

   public static ImmutableSortedSet copyOf(Comparator comparator, Collection elements) {
      return copyOf(comparator, (Iterable)elements);
   }

   public static ImmutableSortedSet copyOfSorted(SortedSet sortedSet) {
      Comparator comparator = SortedIterables.comparator(sortedSet);
      ImmutableList list = ImmutableList.copyOf((Collection)sortedSet);
      return list.isEmpty() ? emptySet(comparator) : new RegularImmutableSortedSet(list, comparator);
   }

   static ImmutableSortedSet construct(Comparator comparator, int n, Object... contents) {
      if (n == 0) {
         return emptySet(comparator);
      } else {
         ObjectArrays.checkElementsNotNull(contents, n);
         Arrays.sort(contents, 0, n, comparator);
         int uniques = 1;

         for(int i = 1; i < n; ++i) {
            Object cur = contents[i];
            Object prev = contents[uniques - 1];
            if (comparator.compare(cur, prev) != 0) {
               contents[uniques++] = cur;
            }
         }

         Arrays.fill(contents, uniques, n, (Object)null);
         if (uniques < contents.length / 2) {
            contents = Arrays.copyOf(contents, uniques);
         }

         return new RegularImmutableSortedSet(ImmutableList.asImmutableList(contents, uniques), comparator);
      }
   }

   public static Builder orderedBy(Comparator comparator) {
      return new Builder(comparator);
   }

   public static Builder reverseOrder() {
      return new Builder(Collections.reverseOrder());
   }

   public static Builder naturalOrder() {
      return new Builder(Ordering.natural());
   }

   int unsafeCompare(Object a, Object b) {
      return unsafeCompare(this.comparator, a, b);
   }

   static int unsafeCompare(Comparator comparator, Object a, Object b) {
      return comparator.compare(a, b);
   }

   ImmutableSortedSet(Comparator comparator) {
      this.comparator = comparator;
   }

   public Comparator comparator() {
      return this.comparator;
   }

   public abstract UnmodifiableIterator iterator();

   public ImmutableSortedSet headSet(Object toElement) {
      return this.headSet(toElement, false);
   }

   @GwtIncompatible
   public ImmutableSortedSet headSet(Object toElement, boolean inclusive) {
      return this.headSetImpl(Preconditions.checkNotNull(toElement), inclusive);
   }

   public ImmutableSortedSet subSet(Object fromElement, Object toElement) {
      return this.subSet(fromElement, true, toElement, false);
   }

   @GwtIncompatible
   public ImmutableSortedSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
      Preconditions.checkNotNull(fromElement);
      Preconditions.checkNotNull(toElement);
      Preconditions.checkArgument(this.comparator.compare(fromElement, toElement) <= 0);
      return this.subSetImpl(fromElement, fromInclusive, toElement, toInclusive);
   }

   public ImmutableSortedSet tailSet(Object fromElement) {
      return this.tailSet(fromElement, true);
   }

   @GwtIncompatible
   public ImmutableSortedSet tailSet(Object fromElement, boolean inclusive) {
      return this.tailSetImpl(Preconditions.checkNotNull(fromElement), inclusive);
   }

   abstract ImmutableSortedSet headSetImpl(Object var1, boolean var2);

   abstract ImmutableSortedSet subSetImpl(Object var1, boolean var2, Object var3, boolean var4);

   abstract ImmutableSortedSet tailSetImpl(Object var1, boolean var2);

   @GwtIncompatible
   public Object lower(Object e) {
      return Iterators.getNext(this.headSet(e, false).descendingIterator(), (Object)null);
   }

   @GwtIncompatible
   public Object floor(Object e) {
      return Iterators.getNext(this.headSet(e, true).descendingIterator(), (Object)null);
   }

   @GwtIncompatible
   public Object ceiling(Object e) {
      return Iterables.getFirst(this.tailSet(e, true), (Object)null);
   }

   @GwtIncompatible
   public Object higher(Object e) {
      return Iterables.getFirst(this.tailSet(e, false), (Object)null);
   }

   public Object first() {
      return this.iterator().next();
   }

   public Object last() {
      return this.descendingIterator().next();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   @GwtIncompatible
   public final Object pollFirst() {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   @GwtIncompatible
   public final Object pollLast() {
      throw new UnsupportedOperationException();
   }

   @GwtIncompatible
   public ImmutableSortedSet descendingSet() {
      ImmutableSortedSet result = this.descendingSet;
      if (result == null) {
         result = this.descendingSet = this.createDescendingSet();
         result.descendingSet = this;
      }

      return result;
   }

   @GwtIncompatible
   abstract ImmutableSortedSet createDescendingSet();

   @GwtIncompatible
   public abstract UnmodifiableIterator descendingIterator();

   abstract int indexOf(@Nullable Object var1);

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw new InvalidObjectException("Use SerializedForm");
   }

   Object writeReplace() {
      return new SerializedForm(this.comparator, this.toArray());
   }

   private static class SerializedForm implements Serializable {
      final Comparator comparator;
      final Object[] elements;
      private static final long serialVersionUID = 0L;

      public SerializedForm(Comparator comparator, Object[] elements) {
         this.comparator = comparator;
         this.elements = elements;
      }

      Object readResolve() {
         return (new Builder(this.comparator)).add((Object[])this.elements).build();
      }
   }

   public static final class Builder extends ImmutableSet.Builder {
      private final Comparator comparator;

      public Builder(Comparator comparator) {
         this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
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

      public ImmutableSortedSet build() {
         Object[] contentsArray = (Object[])this.contents;
         ImmutableSortedSet result = ImmutableSortedSet.construct(this.comparator, this.size, contentsArray);
         this.size = result.size();
         this.forceCopy = true;
         return result;
      }
   }
}
