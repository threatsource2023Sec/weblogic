package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.primitives.Ints;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.concurrent.LazyInit;

@Beta
@GwtIncompatible
public final class ImmutableRangeSet extends AbstractRangeSet implements Serializable {
   private static final ImmutableRangeSet EMPTY = new ImmutableRangeSet(ImmutableList.of());
   private static final ImmutableRangeSet ALL = new ImmutableRangeSet(ImmutableList.of(Range.all()));
   private final transient ImmutableList ranges;
   @LazyInit
   private transient ImmutableRangeSet complement;

   public static ImmutableRangeSet of() {
      return EMPTY;
   }

   static ImmutableRangeSet all() {
      return ALL;
   }

   public static ImmutableRangeSet of(Range range) {
      Preconditions.checkNotNull(range);
      if (range.isEmpty()) {
         return of();
      } else {
         return range.equals(Range.all()) ? all() : new ImmutableRangeSet(ImmutableList.of(range));
      }
   }

   public static ImmutableRangeSet copyOf(RangeSet rangeSet) {
      Preconditions.checkNotNull(rangeSet);
      if (rangeSet.isEmpty()) {
         return of();
      } else if (rangeSet.encloses(Range.all())) {
         return all();
      } else {
         if (rangeSet instanceof ImmutableRangeSet) {
            ImmutableRangeSet immutableRangeSet = (ImmutableRangeSet)rangeSet;
            if (!immutableRangeSet.isPartialView()) {
               return immutableRangeSet;
            }
         }

         return new ImmutableRangeSet(ImmutableList.copyOf((Collection)rangeSet.asRanges()));
      }
   }

   public static ImmutableRangeSet unionOf(Iterable ranges) {
      return copyOf((RangeSet)TreeRangeSet.create(ranges));
   }

   public static ImmutableRangeSet copyOf(Iterable ranges) {
      return (new Builder()).addAll(ranges).build();
   }

   ImmutableRangeSet(ImmutableList ranges) {
      this.ranges = ranges;
   }

   private ImmutableRangeSet(ImmutableList ranges, ImmutableRangeSet complement) {
      this.ranges = ranges;
      this.complement = complement;
   }

   public boolean intersects(Range otherRange) {
      int ceilingIndex = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), otherRange.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
      if (ceilingIndex < this.ranges.size() && ((Range)this.ranges.get(ceilingIndex)).isConnected(otherRange) && !((Range)this.ranges.get(ceilingIndex)).intersection(otherRange).isEmpty()) {
         return true;
      } else {
         return ceilingIndex > 0 && ((Range)this.ranges.get(ceilingIndex - 1)).isConnected(otherRange) && !((Range)this.ranges.get(ceilingIndex - 1)).intersection(otherRange).isEmpty();
      }
   }

   public boolean encloses(Range otherRange) {
      int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), otherRange.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
      return index != -1 && ((Range)this.ranges.get(index)).encloses(otherRange);
   }

   public Range rangeContaining(Comparable value) {
      int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(value), Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
      if (index != -1) {
         Range range = (Range)this.ranges.get(index);
         return range.contains(value) ? range : null;
      } else {
         return null;
      }
   }

   public Range span() {
      if (this.ranges.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         return Range.create(((Range)this.ranges.get(0)).lowerBound, ((Range)this.ranges.get(this.ranges.size() - 1)).upperBound);
      }
   }

   public boolean isEmpty() {
      return this.ranges.isEmpty();
   }

   /** @deprecated */
   @Deprecated
   public void add(Range range) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void addAll(RangeSet other) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void addAll(Iterable other) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void remove(Range range) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void removeAll(RangeSet other) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void removeAll(Iterable other) {
      throw new UnsupportedOperationException();
   }

   public ImmutableSet asRanges() {
      return (ImmutableSet)(this.ranges.isEmpty() ? ImmutableSet.of() : new RegularImmutableSortedSet(this.ranges, Range.rangeLexOrdering()));
   }

   public ImmutableSet asDescendingSetOfRanges() {
      return (ImmutableSet)(this.ranges.isEmpty() ? ImmutableSet.of() : new RegularImmutableSortedSet(this.ranges.reverse(), Range.rangeLexOrdering().reverse()));
   }

   public ImmutableRangeSet complement() {
      ImmutableRangeSet result = this.complement;
      if (result != null) {
         return result;
      } else if (this.ranges.isEmpty()) {
         return this.complement = all();
      } else if (this.ranges.size() == 1 && ((Range)this.ranges.get(0)).equals(Range.all())) {
         return this.complement = of();
      } else {
         ImmutableList complementRanges = new ComplementRanges();
         result = this.complement = new ImmutableRangeSet(complementRanges, this);
         return result;
      }
   }

   public ImmutableRangeSet union(RangeSet other) {
      return unionOf(Iterables.concat(this.asRanges(), other.asRanges()));
   }

   public ImmutableRangeSet intersection(RangeSet other) {
      RangeSet copy = TreeRangeSet.create((RangeSet)this);
      copy.removeAll(other.complement());
      return copyOf((RangeSet)copy);
   }

   public ImmutableRangeSet difference(RangeSet other) {
      RangeSet copy = TreeRangeSet.create((RangeSet)this);
      copy.removeAll(other);
      return copyOf((RangeSet)copy);
   }

   private ImmutableList intersectRanges(final Range range) {
      if (!this.ranges.isEmpty() && !range.isEmpty()) {
         if (range.encloses(this.span())) {
            return this.ranges;
         } else {
            final int fromIndex;
            if (range.hasLowerBound()) {
               fromIndex = SortedLists.binarySearch(this.ranges, (Function)Range.upperBoundFn(), (Comparable)range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
            } else {
               fromIndex = 0;
            }

            int toIndex;
            if (range.hasUpperBound()) {
               toIndex = SortedLists.binarySearch(this.ranges, (Function)Range.lowerBoundFn(), (Comparable)range.upperBound, SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
            } else {
               toIndex = this.ranges.size();
            }

            final int length = toIndex - fromIndex;
            return length == 0 ? ImmutableList.of() : new ImmutableList() {
               public int size() {
                  return length;
               }

               public Range get(int index) {
                  Preconditions.checkElementIndex(index, length);
                  return index != 0 && index != length - 1 ? (Range)ImmutableRangeSet.this.ranges.get(index + fromIndex) : ((Range)ImmutableRangeSet.this.ranges.get(index + fromIndex)).intersection(range);
               }

               boolean isPartialView() {
                  return true;
               }
            };
         }
      } else {
         return ImmutableList.of();
      }
   }

   public ImmutableRangeSet subRangeSet(Range range) {
      if (!this.isEmpty()) {
         Range span = this.span();
         if (range.encloses(span)) {
            return this;
         }

         if (range.isConnected(span)) {
            return new ImmutableRangeSet(this.intersectRanges(range));
         }
      }

      return of();
   }

   public ImmutableSortedSet asSet(DiscreteDomain domain) {
      Preconditions.checkNotNull(domain);
      if (this.isEmpty()) {
         return ImmutableSortedSet.of();
      } else {
         Range span = this.span().canonical(domain);
         if (!span.hasLowerBound()) {
            throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
         } else {
            if (!span.hasUpperBound()) {
               try {
                  domain.maxValue();
               } catch (NoSuchElementException var4) {
                  throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded above");
               }
            }

            return new AsSet(domain);
         }
      }
   }

   boolean isPartialView() {
      return this.ranges.isPartialView();
   }

   public static Builder builder() {
      return new Builder();
   }

   Object writeReplace() {
      return new SerializedForm(this.ranges);
   }

   private static final class SerializedForm implements Serializable {
      private final ImmutableList ranges;

      SerializedForm(ImmutableList ranges) {
         this.ranges = ranges;
      }

      Object readResolve() {
         if (this.ranges.isEmpty()) {
            return ImmutableRangeSet.of();
         } else {
            return this.ranges.equals(ImmutableList.of(Range.all())) ? ImmutableRangeSet.all() : new ImmutableRangeSet(this.ranges);
         }
      }
   }

   public static class Builder {
      private final List ranges = Lists.newArrayList();

      @CanIgnoreReturnValue
      public Builder add(Range range) {
         Preconditions.checkArgument(!range.isEmpty(), "range must not be empty, but was %s", (Object)range);
         this.ranges.add(range);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder addAll(RangeSet ranges) {
         return this.addAll((Iterable)ranges.asRanges());
      }

      @CanIgnoreReturnValue
      public Builder addAll(Iterable ranges) {
         Iterator var2 = ranges.iterator();

         while(var2.hasNext()) {
            Range range = (Range)var2.next();
            this.add(range);
         }

         return this;
      }

      public ImmutableRangeSet build() {
         ImmutableList.Builder mergedRangesBuilder = new ImmutableList.Builder(this.ranges.size());
         Collections.sort(this.ranges, Range.rangeLexOrdering());

         Range range;
         for(PeekingIterator peekingItr = Iterators.peekingIterator(this.ranges.iterator()); peekingItr.hasNext(); mergedRangesBuilder.add((Object)range)) {
            for(range = (Range)peekingItr.next(); peekingItr.hasNext(); range = range.span((Range)peekingItr.next())) {
               Range nextRange = (Range)peekingItr.peek();
               if (!range.isConnected(nextRange)) {
                  break;
               }

               Preconditions.checkArgument(range.intersection(nextRange).isEmpty(), "Overlapping ranges not permitted but found %s overlapping %s", range, nextRange);
            }
         }

         ImmutableList mergedRanges = mergedRangesBuilder.build();
         if (mergedRanges.isEmpty()) {
            return ImmutableRangeSet.of();
         } else if (mergedRanges.size() == 1 && ((Range)Iterables.getOnlyElement(mergedRanges)).equals(Range.all())) {
            return ImmutableRangeSet.all();
         } else {
            return new ImmutableRangeSet(mergedRanges);
         }
      }
   }

   private static class AsSetSerializedForm implements Serializable {
      private final ImmutableList ranges;
      private final DiscreteDomain domain;

      AsSetSerializedForm(ImmutableList ranges, DiscreteDomain domain) {
         this.ranges = ranges;
         this.domain = domain;
      }

      Object readResolve() {
         return (new ImmutableRangeSet(this.ranges)).asSet(this.domain);
      }
   }

   private final class AsSet extends ImmutableSortedSet {
      private final DiscreteDomain domain;
      private transient Integer size;

      AsSet(DiscreteDomain domain) {
         super(Ordering.natural());
         this.domain = domain;
      }

      public int size() {
         Integer result = this.size;
         if (result == null) {
            long total = 0L;
            UnmodifiableIterator var4 = ImmutableRangeSet.this.ranges.iterator();

            while(var4.hasNext()) {
               Range range = (Range)var4.next();
               total += (long)ContiguousSet.create(range, this.domain).size();
               if (total >= 2147483647L) {
                  break;
               }
            }

            result = this.size = Ints.saturatedCast(total);
         }

         return result;
      }

      public UnmodifiableIterator iterator() {
         return new AbstractIterator() {
            final Iterator rangeItr;
            Iterator elemItr;

            {
               this.rangeItr = ImmutableRangeSet.this.ranges.iterator();
               this.elemItr = Iterators.emptyIterator();
            }

            protected Comparable computeNext() {
               while(true) {
                  if (!this.elemItr.hasNext()) {
                     if (this.rangeItr.hasNext()) {
                        this.elemItr = ContiguousSet.create((Range)this.rangeItr.next(), AsSet.this.domain).iterator();
                        continue;
                     }

                     return (Comparable)this.endOfData();
                  }

                  return (Comparable)this.elemItr.next();
               }
            }
         };
      }

      @GwtIncompatible("NavigableSet")
      public UnmodifiableIterator descendingIterator() {
         return new AbstractIterator() {
            final Iterator rangeItr;
            Iterator elemItr;

            {
               this.rangeItr = ImmutableRangeSet.this.ranges.reverse().iterator();
               this.elemItr = Iterators.emptyIterator();
            }

            protected Comparable computeNext() {
               while(true) {
                  if (!this.elemItr.hasNext()) {
                     if (this.rangeItr.hasNext()) {
                        this.elemItr = ContiguousSet.create((Range)this.rangeItr.next(), AsSet.this.domain).descendingIterator();
                        continue;
                     }

                     return (Comparable)this.endOfData();
                  }

                  return (Comparable)this.elemItr.next();
               }
            }
         };
      }

      ImmutableSortedSet subSet(Range range) {
         return ImmutableRangeSet.this.subRangeSet(range).asSet(this.domain);
      }

      ImmutableSortedSet headSetImpl(Comparable toElement, boolean inclusive) {
         return this.subSet(Range.upTo(toElement, BoundType.forBoolean(inclusive)));
      }

      ImmutableSortedSet subSetImpl(Comparable fromElement, boolean fromInclusive, Comparable toElement, boolean toInclusive) {
         return !fromInclusive && !toInclusive && Range.compareOrThrow(fromElement, toElement) == 0 ? ImmutableSortedSet.of() : this.subSet(Range.range(fromElement, BoundType.forBoolean(fromInclusive), toElement, BoundType.forBoolean(toInclusive)));
      }

      ImmutableSortedSet tailSetImpl(Comparable fromElement, boolean inclusive) {
         return this.subSet(Range.downTo(fromElement, BoundType.forBoolean(inclusive)));
      }

      public boolean contains(@Nullable Object o) {
         if (o == null) {
            return false;
         } else {
            try {
               Comparable c = (Comparable)o;
               return ImmutableRangeSet.this.contains(c);
            } catch (ClassCastException var3) {
               return false;
            }
         }
      }

      int indexOf(Object target) {
         if (this.contains(target)) {
            Comparable c = (Comparable)target;
            long total = 0L;

            Range range;
            for(UnmodifiableIterator var5 = ImmutableRangeSet.this.ranges.iterator(); var5.hasNext(); total += (long)ContiguousSet.create(range, this.domain).size()) {
               range = (Range)var5.next();
               if (range.contains(c)) {
                  return Ints.saturatedCast(total + (long)ContiguousSet.create(range, this.domain).indexOf(c));
               }
            }

            throw new AssertionError("impossible");
         } else {
            return -1;
         }
      }

      ImmutableSortedSet createDescendingSet() {
         return new DescendingImmutableSortedSet(this);
      }

      boolean isPartialView() {
         return ImmutableRangeSet.this.ranges.isPartialView();
      }

      public String toString() {
         return ImmutableRangeSet.this.ranges.toString();
      }

      Object writeReplace() {
         return new AsSetSerializedForm(ImmutableRangeSet.this.ranges, this.domain);
      }
   }

   private final class ComplementRanges extends ImmutableList {
      private final boolean positiveBoundedBelow;
      private final boolean positiveBoundedAbove;
      private final int size;

      ComplementRanges() {
         this.positiveBoundedBelow = ((Range)ImmutableRangeSet.this.ranges.get(0)).hasLowerBound();
         this.positiveBoundedAbove = ((Range)Iterables.getLast(ImmutableRangeSet.this.ranges)).hasUpperBound();
         int size = ImmutableRangeSet.this.ranges.size() - 1;
         if (this.positiveBoundedBelow) {
            ++size;
         }

         if (this.positiveBoundedAbove) {
            ++size;
         }

         this.size = size;
      }

      public int size() {
         return this.size;
      }

      public Range get(int index) {
         Preconditions.checkElementIndex(index, this.size);
         Cut lowerBound;
         if (this.positiveBoundedBelow) {
            lowerBound = index == 0 ? Cut.belowAll() : ((Range)ImmutableRangeSet.this.ranges.get(index - 1)).upperBound;
         } else {
            lowerBound = ((Range)ImmutableRangeSet.this.ranges.get(index)).upperBound;
         }

         Cut upperBound;
         if (this.positiveBoundedAbove && index == this.size - 1) {
            upperBound = Cut.aboveAll();
         } else {
            upperBound = ((Range)ImmutableRangeSet.this.ranges.get(index + (this.positiveBoundedBelow ? 0 : 1))).lowerBound;
         }

         return Range.create(lowerBound, upperBound);
      }

      boolean isPartialView() {
         return true;
      }
   }
}
