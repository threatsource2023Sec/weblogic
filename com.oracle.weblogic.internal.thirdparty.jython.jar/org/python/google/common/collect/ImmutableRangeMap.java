package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public class ImmutableRangeMap implements RangeMap, Serializable {
   private static final ImmutableRangeMap EMPTY = new ImmutableRangeMap(ImmutableList.of(), ImmutableList.of());
   private final transient ImmutableList ranges;
   private final transient ImmutableList values;
   private static final long serialVersionUID = 0L;

   public static ImmutableRangeMap of() {
      return EMPTY;
   }

   public static ImmutableRangeMap of(Range range, Object value) {
      return new ImmutableRangeMap(ImmutableList.of(range), ImmutableList.of(value));
   }

   public static ImmutableRangeMap copyOf(RangeMap rangeMap) {
      if (rangeMap instanceof ImmutableRangeMap) {
         return (ImmutableRangeMap)rangeMap;
      } else {
         Map map = rangeMap.asMapOfRanges();
         ImmutableList.Builder rangesBuilder = new ImmutableList.Builder(map.size());
         ImmutableList.Builder valuesBuilder = new ImmutableList.Builder(map.size());
         Iterator var4 = map.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            rangesBuilder.add(entry.getKey());
            valuesBuilder.add(entry.getValue());
         }

         return new ImmutableRangeMap(rangesBuilder.build(), valuesBuilder.build());
      }
   }

   public static Builder builder() {
      return new Builder();
   }

   ImmutableRangeMap(ImmutableList ranges, ImmutableList values) {
      this.ranges = ranges;
      this.values = values;
   }

   @Nullable
   public Object get(Comparable key) {
      int index = SortedLists.binarySearch(this.ranges, (Function)Range.lowerBoundFn(), (Comparable)Cut.belowValue(key), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
      if (index == -1) {
         return null;
      } else {
         Range range = (Range)this.ranges.get(index);
         return range.contains(key) ? this.values.get(index) : null;
      }
   }

   @Nullable
   public Map.Entry getEntry(Comparable key) {
      int index = SortedLists.binarySearch(this.ranges, (Function)Range.lowerBoundFn(), (Comparable)Cut.belowValue(key), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
      if (index == -1) {
         return null;
      } else {
         Range range = (Range)this.ranges.get(index);
         return range.contains(key) ? Maps.immutableEntry(range, this.values.get(index)) : null;
      }
   }

   public Range span() {
      if (this.ranges.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         Range firstRange = (Range)this.ranges.get(0);
         Range lastRange = (Range)this.ranges.get(this.ranges.size() - 1);
         return Range.create(firstRange.lowerBound, lastRange.upperBound);
      }
   }

   /** @deprecated */
   @Deprecated
   public void put(Range range, Object value) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void putCoalescing(Range range, Object value) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void putAll(RangeMap rangeMap) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void clear() {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void remove(Range range) {
      throw new UnsupportedOperationException();
   }

   public ImmutableMap asMapOfRanges() {
      if (this.ranges.isEmpty()) {
         return ImmutableMap.of();
      } else {
         RegularImmutableSortedSet rangeSet = new RegularImmutableSortedSet(this.ranges, Range.rangeLexOrdering());
         return new ImmutableSortedMap(rangeSet, this.values);
      }
   }

   public ImmutableMap asDescendingMapOfRanges() {
      if (this.ranges.isEmpty()) {
         return ImmutableMap.of();
      } else {
         RegularImmutableSortedSet rangeSet = new RegularImmutableSortedSet(this.ranges.reverse(), Range.rangeLexOrdering().reverse());
         return new ImmutableSortedMap(rangeSet, this.values.reverse());
      }
   }

   public ImmutableRangeMap subRangeMap(final Range range) {
      if (((Range)Preconditions.checkNotNull(range)).isEmpty()) {
         return of();
      } else if (!this.ranges.isEmpty() && !range.encloses(this.span())) {
         final int lowerIndex = SortedLists.binarySearch(this.ranges, (Function)Range.upperBoundFn(), (Comparable)range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
         int upperIndex = SortedLists.binarySearch(this.ranges, (Function)Range.lowerBoundFn(), (Comparable)range.upperBound, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
         if (lowerIndex >= upperIndex) {
            return of();
         } else {
            final int len = upperIndex - lowerIndex;
            ImmutableList subRanges = new ImmutableList() {
               public int size() {
                  return len;
               }

               public Range get(int index) {
                  Preconditions.checkElementIndex(index, len);
                  return index != 0 && index != len - 1 ? (Range)ImmutableRangeMap.this.ranges.get(index + lowerIndex) : ((Range)ImmutableRangeMap.this.ranges.get(index + lowerIndex)).intersection(range);
               }

               boolean isPartialView() {
                  return true;
               }
            };
            return new ImmutableRangeMap(subRanges, this.values.subList(lowerIndex, upperIndex)) {
               public ImmutableRangeMap subRangeMap(Range subRange) {
                  return range.isConnected(subRange) ? ImmutableRangeMap.this.subRangeMap(subRange.intersection(range)) : ImmutableRangeMap.of();
               }
            };
         }
      } else {
         return this;
      }
   }

   public int hashCode() {
      return this.asMapOfRanges().hashCode();
   }

   public boolean equals(@Nullable Object o) {
      if (o instanceof RangeMap) {
         RangeMap rangeMap = (RangeMap)o;
         return this.asMapOfRanges().equals(rangeMap.asMapOfRanges());
      } else {
         return false;
      }
   }

   public String toString() {
      return this.asMapOfRanges().toString();
   }

   Object writeReplace() {
      return new SerializedForm(this.asMapOfRanges());
   }

   private static class SerializedForm implements Serializable {
      private final ImmutableMap mapOfRanges;
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableMap mapOfRanges) {
         this.mapOfRanges = mapOfRanges;
      }

      Object readResolve() {
         return this.mapOfRanges.isEmpty() ? ImmutableRangeMap.of() : this.createRangeMap();
      }

      Object createRangeMap() {
         Builder builder = new Builder();
         UnmodifiableIterator var2 = this.mapOfRanges.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            builder.put((Range)entry.getKey(), entry.getValue());
         }

         return builder.build();
      }
   }

   public static final class Builder {
      private final List entries = Lists.newArrayList();

      @CanIgnoreReturnValue
      public Builder put(Range range, Object value) {
         Preconditions.checkNotNull(range);
         Preconditions.checkNotNull(value);
         Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", (Object)range);
         this.entries.add(Maps.immutableEntry(range, value));
         return this;
      }

      @CanIgnoreReturnValue
      public Builder putAll(RangeMap rangeMap) {
         Iterator var2 = rangeMap.asMapOfRanges().entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.put((Range)entry.getKey(), entry.getValue());
         }

         return this;
      }

      public ImmutableRangeMap build() {
         Collections.sort(this.entries, Range.rangeLexOrdering().onKeys());
         ImmutableList.Builder rangesBuilder = new ImmutableList.Builder(this.entries.size());
         ImmutableList.Builder valuesBuilder = new ImmutableList.Builder(this.entries.size());

         for(int i = 0; i < this.entries.size(); ++i) {
            Range range = (Range)((Map.Entry)this.entries.get(i)).getKey();
            if (i > 0) {
               Range prevRange = (Range)((Map.Entry)this.entries.get(i - 1)).getKey();
               if (range.isConnected(prevRange) && !range.intersection(prevRange).isEmpty()) {
                  throw new IllegalArgumentException("Overlapping ranges: range " + prevRange + " overlaps with entry " + range);
               }
            }

            rangesBuilder.add((Object)range);
            valuesBuilder.add(((Map.Entry)this.entries.get(i)).getValue());
         }

         return new ImmutableRangeMap(rangesBuilder.build(), valuesBuilder.build());
      }
   }
}
