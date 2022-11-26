package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;

@Beta
@GwtIncompatible
public class TreeRangeSet extends AbstractRangeSet implements Serializable {
   @VisibleForTesting
   final NavigableMap rangesByLowerBound;
   private transient Set asRanges;
   private transient Set asDescendingSetOfRanges;
   private transient RangeSet complement;

   public static TreeRangeSet create() {
      return new TreeRangeSet(new TreeMap());
   }

   public static TreeRangeSet create(RangeSet rangeSet) {
      TreeRangeSet result = create();
      result.addAll(rangeSet);
      return result;
   }

   public static TreeRangeSet create(Iterable ranges) {
      TreeRangeSet result = create();
      result.addAll(ranges);
      return result;
   }

   private TreeRangeSet(NavigableMap rangesByLowerCut) {
      this.rangesByLowerBound = rangesByLowerCut;
   }

   public Set asRanges() {
      Set result = this.asRanges;
      return result == null ? (this.asRanges = new AsRanges(this.rangesByLowerBound.values())) : result;
   }

   public Set asDescendingSetOfRanges() {
      Set result = this.asDescendingSetOfRanges;
      return result == null ? (this.asDescendingSetOfRanges = new AsRanges(this.rangesByLowerBound.descendingMap().values())) : result;
   }

   @Nullable
   public Range rangeContaining(Comparable value) {
      Preconditions.checkNotNull(value);
      Map.Entry floorEntry = this.rangesByLowerBound.floorEntry(Cut.belowValue(value));
      return floorEntry != null && ((Range)floorEntry.getValue()).contains(value) ? (Range)floorEntry.getValue() : null;
   }

   public boolean intersects(Range range) {
      Preconditions.checkNotNull(range);
      Map.Entry ceilingEntry = this.rangesByLowerBound.ceilingEntry(range.lowerBound);
      if (ceilingEntry != null && ((Range)ceilingEntry.getValue()).isConnected(range) && !((Range)ceilingEntry.getValue()).intersection(range).isEmpty()) {
         return true;
      } else {
         Map.Entry priorEntry = this.rangesByLowerBound.lowerEntry(range.lowerBound);
         return priorEntry != null && ((Range)priorEntry.getValue()).isConnected(range) && !((Range)priorEntry.getValue()).intersection(range).isEmpty();
      }
   }

   public boolean encloses(Range range) {
      Preconditions.checkNotNull(range);
      Map.Entry floorEntry = this.rangesByLowerBound.floorEntry(range.lowerBound);
      return floorEntry != null && ((Range)floorEntry.getValue()).encloses(range);
   }

   @Nullable
   private Range rangeEnclosing(Range range) {
      Preconditions.checkNotNull(range);
      Map.Entry floorEntry = this.rangesByLowerBound.floorEntry(range.lowerBound);
      return floorEntry != null && ((Range)floorEntry.getValue()).encloses(range) ? (Range)floorEntry.getValue() : null;
   }

   public Range span() {
      Map.Entry firstEntry = this.rangesByLowerBound.firstEntry();
      Map.Entry lastEntry = this.rangesByLowerBound.lastEntry();
      if (firstEntry == null) {
         throw new NoSuchElementException();
      } else {
         return Range.create(((Range)firstEntry.getValue()).lowerBound, ((Range)lastEntry.getValue()).upperBound);
      }
   }

   public void add(Range rangeToAdd) {
      Preconditions.checkNotNull(rangeToAdd);
      if (!rangeToAdd.isEmpty()) {
         Cut lbToAdd = rangeToAdd.lowerBound;
         Cut ubToAdd = rangeToAdd.upperBound;
         Map.Entry entryBelowLB = this.rangesByLowerBound.lowerEntry(lbToAdd);
         if (entryBelowLB != null) {
            Range rangeBelowLB = (Range)entryBelowLB.getValue();
            if (rangeBelowLB.upperBound.compareTo(lbToAdd) >= 0) {
               if (rangeBelowLB.upperBound.compareTo(ubToAdd) >= 0) {
                  ubToAdd = rangeBelowLB.upperBound;
               }

               lbToAdd = rangeBelowLB.lowerBound;
            }
         }

         Map.Entry entryBelowUB = this.rangesByLowerBound.floorEntry(ubToAdd);
         if (entryBelowUB != null) {
            Range rangeBelowUB = (Range)entryBelowUB.getValue();
            if (rangeBelowUB.upperBound.compareTo(ubToAdd) >= 0) {
               ubToAdd = rangeBelowUB.upperBound;
            }
         }

         this.rangesByLowerBound.subMap(lbToAdd, ubToAdd).clear();
         this.replaceRangeWithSameLowerBound(Range.create(lbToAdd, ubToAdd));
      }
   }

   public void remove(Range rangeToRemove) {
      Preconditions.checkNotNull(rangeToRemove);
      if (!rangeToRemove.isEmpty()) {
         Map.Entry entryBelowLB = this.rangesByLowerBound.lowerEntry(rangeToRemove.lowerBound);
         if (entryBelowLB != null) {
            Range rangeBelowLB = (Range)entryBelowLB.getValue();
            if (rangeBelowLB.upperBound.compareTo(rangeToRemove.lowerBound) >= 0) {
               if (rangeToRemove.hasUpperBound() && rangeBelowLB.upperBound.compareTo(rangeToRemove.upperBound) >= 0) {
                  this.replaceRangeWithSameLowerBound(Range.create(rangeToRemove.upperBound, rangeBelowLB.upperBound));
               }

               this.replaceRangeWithSameLowerBound(Range.create(rangeBelowLB.lowerBound, rangeToRemove.lowerBound));
            }
         }

         Map.Entry entryBelowUB = this.rangesByLowerBound.floorEntry(rangeToRemove.upperBound);
         if (entryBelowUB != null) {
            Range rangeBelowUB = (Range)entryBelowUB.getValue();
            if (rangeToRemove.hasUpperBound() && rangeBelowUB.upperBound.compareTo(rangeToRemove.upperBound) >= 0) {
               this.replaceRangeWithSameLowerBound(Range.create(rangeToRemove.upperBound, rangeBelowUB.upperBound));
            }
         }

         this.rangesByLowerBound.subMap(rangeToRemove.lowerBound, rangeToRemove.upperBound).clear();
      }
   }

   private void replaceRangeWithSameLowerBound(Range range) {
      if (range.isEmpty()) {
         this.rangesByLowerBound.remove(range.lowerBound);
      } else {
         this.rangesByLowerBound.put(range.lowerBound, range);
      }

   }

   public RangeSet complement() {
      RangeSet result = this.complement;
      return result == null ? (this.complement = new Complement()) : result;
   }

   public RangeSet subRangeSet(Range view) {
      return (RangeSet)(view.equals(Range.all()) ? this : new SubRangeSet(view));
   }

   // $FF: synthetic method
   TreeRangeSet(NavigableMap x0, Object x1) {
      this(x0);
   }

   private final class SubRangeSet extends TreeRangeSet {
      private final Range restriction;

      SubRangeSet(Range restriction) {
         super(new SubRangeSetRangesByLowerBound(Range.all(), restriction, TreeRangeSet.this.rangesByLowerBound), null);
         this.restriction = restriction;
      }

      public boolean encloses(Range range) {
         if (!this.restriction.isEmpty() && this.restriction.encloses(range)) {
            Range enclosing = TreeRangeSet.this.rangeEnclosing(range);
            return enclosing != null && !enclosing.intersection(this.restriction).isEmpty();
         } else {
            return false;
         }
      }

      @Nullable
      public Range rangeContaining(Comparable value) {
         if (!this.restriction.contains(value)) {
            return null;
         } else {
            Range result = TreeRangeSet.this.rangeContaining(value);
            return result == null ? null : result.intersection(this.restriction);
         }
      }

      public void add(Range rangeToAdd) {
         Preconditions.checkArgument(this.restriction.encloses(rangeToAdd), "Cannot add range %s to subRangeSet(%s)", rangeToAdd, this.restriction);
         super.add(rangeToAdd);
      }

      public void remove(Range rangeToRemove) {
         if (rangeToRemove.isConnected(this.restriction)) {
            TreeRangeSet.this.remove(rangeToRemove.intersection(this.restriction));
         }

      }

      public boolean contains(Comparable value) {
         return this.restriction.contains(value) && TreeRangeSet.this.contains(value);
      }

      public void clear() {
         TreeRangeSet.this.remove(this.restriction);
      }

      public RangeSet subRangeSet(Range view) {
         if (view.encloses(this.restriction)) {
            return this;
         } else {
            return (RangeSet)(view.isConnected(this.restriction) ? new SubRangeSet(this.restriction.intersection(view)) : ImmutableRangeSet.of());
         }
      }
   }

   private static final class SubRangeSetRangesByLowerBound extends AbstractNavigableMap {
      private final Range lowerBoundWindow;
      private final Range restriction;
      private final NavigableMap rangesByLowerBound;
      private final NavigableMap rangesByUpperBound;

      private SubRangeSetRangesByLowerBound(Range lowerBoundWindow, Range restriction, NavigableMap rangesByLowerBound) {
         this.lowerBoundWindow = (Range)Preconditions.checkNotNull(lowerBoundWindow);
         this.restriction = (Range)Preconditions.checkNotNull(restriction);
         this.rangesByLowerBound = (NavigableMap)Preconditions.checkNotNull(rangesByLowerBound);
         this.rangesByUpperBound = new RangesByUpperBound(rangesByLowerBound);
      }

      private NavigableMap subMap(Range window) {
         return (NavigableMap)(!window.isConnected(this.lowerBoundWindow) ? ImmutableSortedMap.of() : new SubRangeSetRangesByLowerBound(this.lowerBoundWindow.intersection(window), this.restriction, this.rangesByLowerBound));
      }

      public NavigableMap subMap(Cut fromKey, boolean fromInclusive, Cut toKey, boolean toInclusive) {
         return this.subMap(Range.range(fromKey, BoundType.forBoolean(fromInclusive), toKey, BoundType.forBoolean(toInclusive)));
      }

      public NavigableMap headMap(Cut toKey, boolean inclusive) {
         return this.subMap(Range.upTo(toKey, BoundType.forBoolean(inclusive)));
      }

      public NavigableMap tailMap(Cut fromKey, boolean inclusive) {
         return this.subMap(Range.downTo(fromKey, BoundType.forBoolean(inclusive)));
      }

      public Comparator comparator() {
         return Ordering.natural();
      }

      public boolean containsKey(@Nullable Object key) {
         return this.get(key) != null;
      }

      @Nullable
      public Range get(@Nullable Object key) {
         if (key instanceof Cut) {
            try {
               Cut cut = (Cut)key;
               if (!this.lowerBoundWindow.contains(cut) || cut.compareTo(this.restriction.lowerBound) < 0 || cut.compareTo(this.restriction.upperBound) >= 0) {
                  return null;
               }

               Range candidate;
               if (cut.equals(this.restriction.lowerBound)) {
                  candidate = (Range)Maps.valueOrNull(this.rangesByLowerBound.floorEntry(cut));
                  if (candidate != null && candidate.upperBound.compareTo(this.restriction.lowerBound) > 0) {
                     return candidate.intersection(this.restriction);
                  }
               } else {
                  candidate = (Range)this.rangesByLowerBound.get(cut);
                  if (candidate != null) {
                     return candidate.intersection(this.restriction);
                  }
               }
            } catch (ClassCastException var4) {
               return null;
            }
         }

         return null;
      }

      Iterator entryIterator() {
         if (this.restriction.isEmpty()) {
            return Iterators.emptyIterator();
         } else if (this.lowerBoundWindow.upperBound.isLessThan(this.restriction.lowerBound)) {
            return Iterators.emptyIterator();
         } else {
            final Iterator completeRangeItr;
            if (this.lowerBoundWindow.lowerBound.isLessThan(this.restriction.lowerBound)) {
               completeRangeItr = this.rangesByUpperBound.tailMap(this.restriction.lowerBound, false).values().iterator();
            } else {
               completeRangeItr = this.rangesByLowerBound.tailMap(this.lowerBoundWindow.lowerBound.endpoint(), this.lowerBoundWindow.lowerBoundType() == BoundType.CLOSED).values().iterator();
            }

            final Cut upperBoundOnLowerBounds = (Cut)Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound));
            return new AbstractIterator() {
               protected Map.Entry computeNext() {
                  if (!completeRangeItr.hasNext()) {
                     return (Map.Entry)this.endOfData();
                  } else {
                     Range nextRange = (Range)completeRangeItr.next();
                     if (upperBoundOnLowerBounds.isLessThan(nextRange.lowerBound)) {
                        return (Map.Entry)this.endOfData();
                     } else {
                        nextRange = nextRange.intersection(SubRangeSetRangesByLowerBound.this.restriction);
                        return Maps.immutableEntry(nextRange.lowerBound, nextRange);
                     }
                  }
               }
            };
         }
      }

      Iterator descendingEntryIterator() {
         if (this.restriction.isEmpty()) {
            return Iterators.emptyIterator();
         } else {
            Cut upperBoundOnLowerBounds = (Cut)Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound));
            final Iterator completeRangeItr = this.rangesByLowerBound.headMap(upperBoundOnLowerBounds.endpoint(), upperBoundOnLowerBounds.typeAsUpperBound() == BoundType.CLOSED).descendingMap().values().iterator();
            return new AbstractIterator() {
               protected Map.Entry computeNext() {
                  if (!completeRangeItr.hasNext()) {
                     return (Map.Entry)this.endOfData();
                  } else {
                     Range nextRange = (Range)completeRangeItr.next();
                     if (SubRangeSetRangesByLowerBound.this.restriction.lowerBound.compareTo(nextRange.upperBound) >= 0) {
                        return (Map.Entry)this.endOfData();
                     } else {
                        nextRange = nextRange.intersection(SubRangeSetRangesByLowerBound.this.restriction);
                        return SubRangeSetRangesByLowerBound.this.lowerBoundWindow.contains(nextRange.lowerBound) ? Maps.immutableEntry(nextRange.lowerBound, nextRange) : (Map.Entry)this.endOfData();
                     }
                  }
               }
            };
         }
      }

      public int size() {
         return Iterators.size(this.entryIterator());
      }

      // $FF: synthetic method
      SubRangeSetRangesByLowerBound(Range x0, Range x1, NavigableMap x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private final class Complement extends TreeRangeSet {
      Complement() {
         super(new ComplementRangesByLowerBound(TreeRangeSet.this.rangesByLowerBound), null);
      }

      public void add(Range rangeToAdd) {
         TreeRangeSet.this.remove(rangeToAdd);
      }

      public void remove(Range rangeToRemove) {
         TreeRangeSet.this.add(rangeToRemove);
      }

      public boolean contains(Comparable value) {
         return !TreeRangeSet.this.contains(value);
      }

      public RangeSet complement() {
         return TreeRangeSet.this;
      }
   }

   private static final class ComplementRangesByLowerBound extends AbstractNavigableMap {
      private final NavigableMap positiveRangesByLowerBound;
      private final NavigableMap positiveRangesByUpperBound;
      private final Range complementLowerBoundWindow;

      ComplementRangesByLowerBound(NavigableMap positiveRangesByLowerBound) {
         this(positiveRangesByLowerBound, Range.all());
      }

      private ComplementRangesByLowerBound(NavigableMap positiveRangesByLowerBound, Range window) {
         this.positiveRangesByLowerBound = positiveRangesByLowerBound;
         this.positiveRangesByUpperBound = new RangesByUpperBound(positiveRangesByLowerBound);
         this.complementLowerBoundWindow = window;
      }

      private NavigableMap subMap(Range subWindow) {
         if (!this.complementLowerBoundWindow.isConnected(subWindow)) {
            return ImmutableSortedMap.of();
         } else {
            subWindow = subWindow.intersection(this.complementLowerBoundWindow);
            return new ComplementRangesByLowerBound(this.positiveRangesByLowerBound, subWindow);
         }
      }

      public NavigableMap subMap(Cut fromKey, boolean fromInclusive, Cut toKey, boolean toInclusive) {
         return this.subMap(Range.range(fromKey, BoundType.forBoolean(fromInclusive), toKey, BoundType.forBoolean(toInclusive)));
      }

      public NavigableMap headMap(Cut toKey, boolean inclusive) {
         return this.subMap(Range.upTo(toKey, BoundType.forBoolean(inclusive)));
      }

      public NavigableMap tailMap(Cut fromKey, boolean inclusive) {
         return this.subMap(Range.downTo(fromKey, BoundType.forBoolean(inclusive)));
      }

      public Comparator comparator() {
         return Ordering.natural();
      }

      Iterator entryIterator() {
         Collection positiveRanges;
         if (this.complementLowerBoundWindow.hasLowerBound()) {
            positiveRanges = this.positiveRangesByUpperBound.tailMap(this.complementLowerBoundWindow.lowerEndpoint(), this.complementLowerBoundWindow.lowerBoundType() == BoundType.CLOSED).values();
         } else {
            positiveRanges = this.positiveRangesByUpperBound.values();
         }

         final PeekingIterator positiveItr = Iterators.peekingIterator(positiveRanges.iterator());
         final Cut firstComplementRangeLowerBound;
         if (this.complementLowerBoundWindow.contains(Cut.belowAll()) && (!positiveItr.hasNext() || ((Range)positiveItr.peek()).lowerBound != Cut.belowAll())) {
            firstComplementRangeLowerBound = Cut.belowAll();
         } else {
            if (!positiveItr.hasNext()) {
               return Iterators.emptyIterator();
            }

            firstComplementRangeLowerBound = ((Range)positiveItr.next()).upperBound;
         }

         return new AbstractIterator() {
            Cut nextComplementRangeLowerBound = firstComplementRangeLowerBound;

            protected Map.Entry computeNext() {
               if (!ComplementRangesByLowerBound.this.complementLowerBoundWindow.upperBound.isLessThan(this.nextComplementRangeLowerBound) && this.nextComplementRangeLowerBound != Cut.aboveAll()) {
                  Range negativeRange;
                  if (positiveItr.hasNext()) {
                     Range positiveRange = (Range)positiveItr.next();
                     negativeRange = Range.create(this.nextComplementRangeLowerBound, positiveRange.lowerBound);
                     this.nextComplementRangeLowerBound = positiveRange.upperBound;
                  } else {
                     negativeRange = Range.create(this.nextComplementRangeLowerBound, Cut.aboveAll());
                     this.nextComplementRangeLowerBound = Cut.aboveAll();
                  }

                  return Maps.immutableEntry(negativeRange.lowerBound, negativeRange);
               } else {
                  return (Map.Entry)this.endOfData();
               }
            }
         };
      }

      Iterator descendingEntryIterator() {
         Cut startingPoint = this.complementLowerBoundWindow.hasUpperBound() ? (Cut)this.complementLowerBoundWindow.upperEndpoint() : Cut.aboveAll();
         boolean inclusive = this.complementLowerBoundWindow.hasUpperBound() && this.complementLowerBoundWindow.upperBoundType() == BoundType.CLOSED;
         final PeekingIterator positiveItr = Iterators.peekingIterator(this.positiveRangesByUpperBound.headMap(startingPoint, inclusive).descendingMap().values().iterator());
         Cut cut;
         if (positiveItr.hasNext()) {
            cut = ((Range)positiveItr.peek()).upperBound == Cut.aboveAll() ? ((Range)positiveItr.next()).lowerBound : (Cut)this.positiveRangesByLowerBound.higherKey(((Range)positiveItr.peek()).upperBound);
         } else {
            if (!this.complementLowerBoundWindow.contains(Cut.belowAll()) || this.positiveRangesByLowerBound.containsKey(Cut.belowAll())) {
               return Iterators.emptyIterator();
            }

            cut = (Cut)this.positiveRangesByLowerBound.higherKey(Cut.belowAll());
         }

         final Cut firstComplementRangeUpperBound = (Cut)MoreObjects.firstNonNull(cut, Cut.aboveAll());
         return new AbstractIterator() {
            Cut nextComplementRangeUpperBound = firstComplementRangeUpperBound;

            protected Map.Entry computeNext() {
               if (this.nextComplementRangeUpperBound == Cut.belowAll()) {
                  return (Map.Entry)this.endOfData();
               } else {
                  Range negativeRange;
                  if (positiveItr.hasNext()) {
                     negativeRange = (Range)positiveItr.next();
                     Range negativeRangex = Range.create(negativeRange.upperBound, this.nextComplementRangeUpperBound);
                     this.nextComplementRangeUpperBound = negativeRange.lowerBound;
                     if (ComplementRangesByLowerBound.this.complementLowerBoundWindow.lowerBound.isLessThan(negativeRangex.lowerBound)) {
                        return Maps.immutableEntry(negativeRangex.lowerBound, negativeRangex);
                     }
                  } else if (ComplementRangesByLowerBound.this.complementLowerBoundWindow.lowerBound.isLessThan(Cut.belowAll())) {
                     negativeRange = Range.create(Cut.belowAll(), this.nextComplementRangeUpperBound);
                     this.nextComplementRangeUpperBound = Cut.belowAll();
                     return Maps.immutableEntry(Cut.belowAll(), negativeRange);
                  }

                  return (Map.Entry)this.endOfData();
               }
            }
         };
      }

      public int size() {
         return Iterators.size(this.entryIterator());
      }

      @Nullable
      public Range get(Object key) {
         if (key instanceof Cut) {
            try {
               Cut cut = (Cut)key;
               Map.Entry firstEntry = this.tailMap(cut, true).firstEntry();
               if (firstEntry != null && ((Cut)firstEntry.getKey()).equals(cut)) {
                  return (Range)firstEntry.getValue();
               }
            } catch (ClassCastException var4) {
               return null;
            }
         }

         return null;
      }

      public boolean containsKey(Object key) {
         return this.get(key) != null;
      }
   }

   @VisibleForTesting
   static final class RangesByUpperBound extends AbstractNavigableMap {
      private final NavigableMap rangesByLowerBound;
      private final Range upperBoundWindow;

      RangesByUpperBound(NavigableMap rangesByLowerBound) {
         this.rangesByLowerBound = rangesByLowerBound;
         this.upperBoundWindow = Range.all();
      }

      private RangesByUpperBound(NavigableMap rangesByLowerBound, Range upperBoundWindow) {
         this.rangesByLowerBound = rangesByLowerBound;
         this.upperBoundWindow = upperBoundWindow;
      }

      private NavigableMap subMap(Range window) {
         return (NavigableMap)(window.isConnected(this.upperBoundWindow) ? new RangesByUpperBound(this.rangesByLowerBound, window.intersection(this.upperBoundWindow)) : ImmutableSortedMap.of());
      }

      public NavigableMap subMap(Cut fromKey, boolean fromInclusive, Cut toKey, boolean toInclusive) {
         return this.subMap(Range.range(fromKey, BoundType.forBoolean(fromInclusive), toKey, BoundType.forBoolean(toInclusive)));
      }

      public NavigableMap headMap(Cut toKey, boolean inclusive) {
         return this.subMap(Range.upTo(toKey, BoundType.forBoolean(inclusive)));
      }

      public NavigableMap tailMap(Cut fromKey, boolean inclusive) {
         return this.subMap(Range.downTo(fromKey, BoundType.forBoolean(inclusive)));
      }

      public Comparator comparator() {
         return Ordering.natural();
      }

      public boolean containsKey(@Nullable Object key) {
         return this.get(key) != null;
      }

      public Range get(@Nullable Object key) {
         if (key instanceof Cut) {
            try {
               Cut cut = (Cut)key;
               if (!this.upperBoundWindow.contains(cut)) {
                  return null;
               }

               Map.Entry candidate = this.rangesByLowerBound.lowerEntry(cut);
               if (candidate != null && ((Range)candidate.getValue()).upperBound.equals(cut)) {
                  return (Range)candidate.getValue();
               }
            } catch (ClassCastException var4) {
               return null;
            }
         }

         return null;
      }

      Iterator entryIterator() {
         final Iterator backingItr;
         if (!this.upperBoundWindow.hasLowerBound()) {
            backingItr = this.rangesByLowerBound.values().iterator();
         } else {
            Map.Entry lowerEntry = this.rangesByLowerBound.lowerEntry(this.upperBoundWindow.lowerEndpoint());
            if (lowerEntry == null) {
               backingItr = this.rangesByLowerBound.values().iterator();
            } else if (this.upperBoundWindow.lowerBound.isLessThan(((Range)lowerEntry.getValue()).upperBound)) {
               backingItr = this.rangesByLowerBound.tailMap(lowerEntry.getKey(), true).values().iterator();
            } else {
               backingItr = this.rangesByLowerBound.tailMap(this.upperBoundWindow.lowerEndpoint(), true).values().iterator();
            }
         }

         return new AbstractIterator() {
            protected Map.Entry computeNext() {
               if (!backingItr.hasNext()) {
                  return (Map.Entry)this.endOfData();
               } else {
                  Range range = (Range)backingItr.next();
                  return RangesByUpperBound.this.upperBoundWindow.upperBound.isLessThan(range.upperBound) ? (Map.Entry)this.endOfData() : Maps.immutableEntry(range.upperBound, range);
               }
            }
         };
      }

      Iterator descendingEntryIterator() {
         Collection candidates;
         if (this.upperBoundWindow.hasUpperBound()) {
            candidates = this.rangesByLowerBound.headMap(this.upperBoundWindow.upperEndpoint(), false).descendingMap().values();
         } else {
            candidates = this.rangesByLowerBound.descendingMap().values();
         }

         final PeekingIterator backingItr = Iterators.peekingIterator(candidates.iterator());
         if (backingItr.hasNext() && this.upperBoundWindow.upperBound.isLessThan(((Range)backingItr.peek()).upperBound)) {
            backingItr.next();
         }

         return new AbstractIterator() {
            protected Map.Entry computeNext() {
               if (!backingItr.hasNext()) {
                  return (Map.Entry)this.endOfData();
               } else {
                  Range range = (Range)backingItr.next();
                  return RangesByUpperBound.this.upperBoundWindow.lowerBound.isLessThan(range.upperBound) ? Maps.immutableEntry(range.upperBound, range) : (Map.Entry)this.endOfData();
               }
            }
         };
      }

      public int size() {
         return this.upperBoundWindow.equals(Range.all()) ? this.rangesByLowerBound.size() : Iterators.size(this.entryIterator());
      }

      public boolean isEmpty() {
         return this.upperBoundWindow.equals(Range.all()) ? this.rangesByLowerBound.isEmpty() : !this.entryIterator().hasNext();
      }
   }

   final class AsRanges extends ForwardingCollection implements Set {
      final Collection delegate;

      AsRanges(Collection delegate) {
         this.delegate = delegate;
      }

      protected Collection delegate() {
         return this.delegate;
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }

      public boolean equals(@Nullable Object o) {
         return Sets.equalsImpl(this, o);
      }
   }
}
