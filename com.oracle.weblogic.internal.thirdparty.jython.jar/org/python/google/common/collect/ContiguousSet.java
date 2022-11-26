package org.python.google.common.collect;

import java.util.NoSuchElementException;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   emulated = true
)
public abstract class ContiguousSet extends ImmutableSortedSet {
   final DiscreteDomain domain;

   public static ContiguousSet create(Range range, DiscreteDomain domain) {
      Preconditions.checkNotNull(range);
      Preconditions.checkNotNull(domain);
      Range effectiveRange = range;

      try {
         if (!range.hasLowerBound()) {
            effectiveRange = effectiveRange.intersection(Range.atLeast(domain.minValue()));
         }

         if (!range.hasUpperBound()) {
            effectiveRange = effectiveRange.intersection(Range.atMost(domain.maxValue()));
         }
      } catch (NoSuchElementException var4) {
         throw new IllegalArgumentException(var4);
      }

      boolean empty = effectiveRange.isEmpty() || Range.compareOrThrow(range.lowerBound.leastValueAbove(domain), range.upperBound.greatestValueBelow(domain)) > 0;
      return (ContiguousSet)(empty ? new EmptyContiguousSet(domain) : new RegularContiguousSet(effectiveRange, domain));
   }

   ContiguousSet(DiscreteDomain domain) {
      super(Ordering.natural());
      this.domain = domain;
   }

   public ContiguousSet headSet(Comparable toElement) {
      return this.headSetImpl((Comparable)Preconditions.checkNotNull(toElement), false);
   }

   @GwtIncompatible
   public ContiguousSet headSet(Comparable toElement, boolean inclusive) {
      return this.headSetImpl((Comparable)Preconditions.checkNotNull(toElement), inclusive);
   }

   public ContiguousSet subSet(Comparable fromElement, Comparable toElement) {
      Preconditions.checkNotNull(fromElement);
      Preconditions.checkNotNull(toElement);
      Preconditions.checkArgument(this.comparator().compare(fromElement, toElement) <= 0);
      return this.subSetImpl(fromElement, true, toElement, false);
   }

   @GwtIncompatible
   public ContiguousSet subSet(Comparable fromElement, boolean fromInclusive, Comparable toElement, boolean toInclusive) {
      Preconditions.checkNotNull(fromElement);
      Preconditions.checkNotNull(toElement);
      Preconditions.checkArgument(this.comparator().compare(fromElement, toElement) <= 0);
      return this.subSetImpl(fromElement, fromInclusive, toElement, toInclusive);
   }

   public ContiguousSet tailSet(Comparable fromElement) {
      return this.tailSetImpl((Comparable)Preconditions.checkNotNull(fromElement), true);
   }

   @GwtIncompatible
   public ContiguousSet tailSet(Comparable fromElement, boolean inclusive) {
      return this.tailSetImpl((Comparable)Preconditions.checkNotNull(fromElement), inclusive);
   }

   abstract ContiguousSet headSetImpl(Comparable var1, boolean var2);

   abstract ContiguousSet subSetImpl(Comparable var1, boolean var2, Comparable var3, boolean var4);

   abstract ContiguousSet tailSetImpl(Comparable var1, boolean var2);

   public abstract ContiguousSet intersection(ContiguousSet var1);

   public abstract Range range();

   public abstract Range range(BoundType var1, BoundType var2);

   @GwtIncompatible
   ImmutableSortedSet createDescendingSet() {
      return new DescendingImmutableSortedSet(this);
   }

   public String toString() {
      return this.range().toString();
   }

   /** @deprecated */
   @Deprecated
   public static ImmutableSortedSet.Builder builder() {
      throw new UnsupportedOperationException();
   }
}
