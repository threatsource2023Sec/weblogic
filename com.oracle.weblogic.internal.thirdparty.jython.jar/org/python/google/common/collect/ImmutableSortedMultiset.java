package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.concurrent.LazyInit;

@GwtIncompatible
public abstract class ImmutableSortedMultiset extends ImmutableSortedMultisetFauxverideShim implements SortedMultiset {
   @LazyInit
   transient ImmutableSortedMultiset descendingMultiset;

   public static ImmutableSortedMultiset of() {
      return RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
   }

   public static ImmutableSortedMultiset of(Comparable element) {
      RegularImmutableSortedSet elementSet = (RegularImmutableSortedSet)ImmutableSortedSet.of(element);
      long[] cumulativeCounts = new long[]{0L, 1L};
      return new RegularImmutableSortedMultiset(elementSet, cumulativeCounts, 0, 1);
   }

   public static ImmutableSortedMultiset of(Comparable e1, Comparable e2) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(e1, e2));
   }

   public static ImmutableSortedMultiset of(Comparable e1, Comparable e2, Comparable e3) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(e1, e2, e3));
   }

   public static ImmutableSortedMultiset of(Comparable e1, Comparable e2, Comparable e3, Comparable e4) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(e1, e2, e3, e4));
   }

   public static ImmutableSortedMultiset of(Comparable e1, Comparable e2, Comparable e3, Comparable e4, Comparable e5) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(e1, e2, e3, e4, e5));
   }

   public static ImmutableSortedMultiset of(Comparable e1, Comparable e2, Comparable e3, Comparable e4, Comparable e5, Comparable e6, Comparable... remaining) {
      int size = remaining.length + 6;
      List all = Lists.newArrayListWithCapacity(size);
      Collections.addAll(all, new Comparable[]{e1, e2, e3, e4, e5, e6});
      Collections.addAll(all, remaining);
      return copyOf(Ordering.natural(), (Iterable)all);
   }

   public static ImmutableSortedMultiset copyOf(Comparable[] elements) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(elements));
   }

   public static ImmutableSortedMultiset copyOf(Iterable elements) {
      Ordering naturalOrder = Ordering.natural();
      return copyOf(naturalOrder, (Iterable)elements);
   }

   public static ImmutableSortedMultiset copyOf(Iterator elements) {
      Ordering naturalOrder = Ordering.natural();
      return copyOf(naturalOrder, (Iterator)elements);
   }

   public static ImmutableSortedMultiset copyOf(Comparator comparator, Iterator elements) {
      Preconditions.checkNotNull(comparator);
      return (new Builder(comparator)).addAll(elements).build();
   }

   public static ImmutableSortedMultiset copyOf(Comparator comparator, Iterable elements) {
      if (elements instanceof ImmutableSortedMultiset) {
         ImmutableSortedMultiset multiset = (ImmutableSortedMultiset)elements;
         if (comparator.equals(multiset.comparator())) {
            if (multiset.isPartialView()) {
               return copyOfSortedEntries(comparator, multiset.entrySet().asList());
            }

            return multiset;
         }
      }

      Iterable elements = Lists.newArrayList(elements);
      TreeMultiset sortedCopy = TreeMultiset.create((Comparator)Preconditions.checkNotNull(comparator));
      Iterables.addAll(sortedCopy, elements);
      return copyOfSortedEntries(comparator, sortedCopy.entrySet());
   }

   public static ImmutableSortedMultiset copyOfSorted(SortedMultiset sortedMultiset) {
      return copyOfSortedEntries(sortedMultiset.comparator(), Lists.newArrayList((Iterable)sortedMultiset.entrySet()));
   }

   private static ImmutableSortedMultiset copyOfSortedEntries(Comparator comparator, Collection entries) {
      if (entries.isEmpty()) {
         return emptyMultiset(comparator);
      } else {
         ImmutableList.Builder elementsBuilder = new ImmutableList.Builder(entries.size());
         long[] cumulativeCounts = new long[entries.size() + 1];
         int i = 0;

         for(Iterator var5 = entries.iterator(); var5.hasNext(); ++i) {
            Multiset.Entry entry = (Multiset.Entry)var5.next();
            elementsBuilder.add(entry.getElement());
            cumulativeCounts[i + 1] = cumulativeCounts[i] + (long)entry.getCount();
         }

         return new RegularImmutableSortedMultiset(new RegularImmutableSortedSet(elementsBuilder.build(), comparator), cumulativeCounts, 0, entries.size());
      }
   }

   static ImmutableSortedMultiset emptyMultiset(Comparator comparator) {
      return (ImmutableSortedMultiset)(Ordering.natural().equals(comparator) ? RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET : new RegularImmutableSortedMultiset(comparator));
   }

   ImmutableSortedMultiset() {
   }

   public final Comparator comparator() {
      return this.elementSet().comparator();
   }

   public abstract ImmutableSortedSet elementSet();

   public ImmutableSortedMultiset descendingMultiset() {
      ImmutableSortedMultiset result = this.descendingMultiset;
      return result == null ? (this.descendingMultiset = (ImmutableSortedMultiset)(this.isEmpty() ? emptyMultiset(Ordering.from(this.comparator()).reverse()) : new DescendingImmutableSortedMultiset(this))) : result;
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final Multiset.Entry pollFirstEntry() {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final Multiset.Entry pollLastEntry() {
      throw new UnsupportedOperationException();
   }

   public abstract ImmutableSortedMultiset headMultiset(Object var1, BoundType var2);

   public ImmutableSortedMultiset subMultiset(Object lowerBound, BoundType lowerBoundType, Object upperBound, BoundType upperBoundType) {
      Preconditions.checkArgument(this.comparator().compare(lowerBound, upperBound) <= 0, "Expected lowerBound <= upperBound but %s > %s", lowerBound, upperBound);
      return this.tailMultiset(lowerBound, lowerBoundType).headMultiset(upperBound, upperBoundType);
   }

   public abstract ImmutableSortedMultiset tailMultiset(Object var1, BoundType var2);

   public static Builder orderedBy(Comparator comparator) {
      return new Builder(comparator);
   }

   public static Builder reverseOrder() {
      return new Builder(Ordering.natural().reverse());
   }

   public static Builder naturalOrder() {
      return new Builder(Ordering.natural());
   }

   Object writeReplace() {
      return new SerializedForm(this);
   }

   private static final class SerializedForm implements Serializable {
      final Comparator comparator;
      final Object[] elements;
      final int[] counts;

      SerializedForm(SortedMultiset multiset) {
         this.comparator = multiset.comparator();
         int n = multiset.entrySet().size();
         this.elements = (Object[])(new Object[n]);
         this.counts = new int[n];
         int i = 0;

         for(Iterator var4 = multiset.entrySet().iterator(); var4.hasNext(); ++i) {
            Multiset.Entry entry = (Multiset.Entry)var4.next();
            this.elements[i] = entry.getElement();
            this.counts[i] = entry.getCount();
         }

      }

      Object readResolve() {
         int n = this.elements.length;
         Builder builder = new Builder(this.comparator);

         for(int i = 0; i < n; ++i) {
            builder.addCopies(this.elements[i], this.counts[i]);
         }

         return builder.build();
      }
   }

   public static class Builder extends ImmutableMultiset.Builder {
      SortedMultiset contents;

      public Builder(Comparator comparator) {
         super(0);
         this.contents = TreeMultiset.create((Comparator)Preconditions.checkNotNull(comparator));
      }

      @CanIgnoreReturnValue
      public Builder add(Object element) {
         Preconditions.checkNotNull(element);
         this.contents.add(element);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder addCopies(Object element, int occurrences) {
         Preconditions.checkNotNull(element);
         this.contents.add(element, occurrences);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder setCount(Object element, int count) {
         Preconditions.checkNotNull(element);
         this.contents.setCount(element, count);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder add(Object... elements) {
         Collections.addAll(this.contents, elements);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder addAll(Iterable elements) {
         Iterator var2;
         if (elements instanceof Multiset) {
            var2 = ((Multiset)elements).entrySet().iterator();

            while(var2.hasNext()) {
               Multiset.Entry entry = (Multiset.Entry)var2.next();
               this.addCopies(entry.getElement(), entry.getCount());
            }
         } else {
            var2 = elements.iterator();

            while(var2.hasNext()) {
               Object e = var2.next();
               this.add(e);
            }
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

      public ImmutableSortedMultiset build() {
         return ImmutableSortedMultiset.copyOfSorted(this.contents);
      }
   }
}
