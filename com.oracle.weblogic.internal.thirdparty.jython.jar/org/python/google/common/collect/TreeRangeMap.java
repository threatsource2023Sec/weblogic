package org.python.google.common.collect;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Predicates;

@Beta
@GwtIncompatible
public final class TreeRangeMap implements RangeMap {
   private final NavigableMap entriesByLowerBound = Maps.newTreeMap();
   private static final RangeMap EMPTY_SUB_RANGE_MAP = new RangeMap() {
      @Nullable
      public Object get(Comparable key) {
         return null;
      }

      @Nullable
      public Map.Entry getEntry(Comparable key) {
         return null;
      }

      public Range span() {
         throw new NoSuchElementException();
      }

      public void put(Range range, Object value) {
         Preconditions.checkNotNull(range);
         throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
      }

      public void putCoalescing(Range range, Object value) {
         Preconditions.checkNotNull(range);
         throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
      }

      public void putAll(RangeMap rangeMap) {
         if (!rangeMap.asMapOfRanges().isEmpty()) {
            throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
         }
      }

      public void clear() {
      }

      public void remove(Range range) {
         Preconditions.checkNotNull(range);
      }

      public Map asMapOfRanges() {
         return Collections.emptyMap();
      }

      public Map asDescendingMapOfRanges() {
         return Collections.emptyMap();
      }

      public RangeMap subRangeMap(Range range) {
         Preconditions.checkNotNull(range);
         return this;
      }
   };

   public static TreeRangeMap create() {
      return new TreeRangeMap();
   }

   private TreeRangeMap() {
   }

   @Nullable
   public Object get(Comparable key) {
      Map.Entry entry = this.getEntry(key);
      return entry == null ? null : entry.getValue();
   }

   @Nullable
   public Map.Entry getEntry(Comparable key) {
      Map.Entry mapEntry = this.entriesByLowerBound.floorEntry(Cut.belowValue(key));
      return mapEntry != null && ((RangeMapEntry)mapEntry.getValue()).contains(key) ? (Map.Entry)mapEntry.getValue() : null;
   }

   public void put(Range range, Object value) {
      if (!range.isEmpty()) {
         Preconditions.checkNotNull(value);
         this.remove(range);
         this.entriesByLowerBound.put(range.lowerBound, new RangeMapEntry(range, value));
      }

   }

   public void putCoalescing(Range range, Object value) {
      if (this.entriesByLowerBound.isEmpty()) {
         this.put(range, value);
      } else {
         Range coalescedRange = this.coalescedRange(range, Preconditions.checkNotNull(value));
         this.put(coalescedRange, value);
      }
   }

   private Range coalescedRange(Range range, Object value) {
      Map.Entry lowerEntry = this.entriesByLowerBound.lowerEntry(range.lowerBound);
      Range coalescedRange = coalesce(range, value, lowerEntry);
      Map.Entry higherEntry = this.entriesByLowerBound.floorEntry(range.upperBound);
      coalescedRange = coalesce(coalescedRange, value, higherEntry);
      return coalescedRange;
   }

   private static Range coalesce(Range range, Object value, @Nullable Map.Entry entry) {
      return entry != null && ((RangeMapEntry)entry.getValue()).getKey().isConnected(range) && ((RangeMapEntry)entry.getValue()).getValue().equals(value) ? range.span(((RangeMapEntry)entry.getValue()).getKey()) : range;
   }

   public void putAll(RangeMap rangeMap) {
      Iterator var2 = rangeMap.asMapOfRanges().entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.put((Range)entry.getKey(), entry.getValue());
      }

   }

   public void clear() {
      this.entriesByLowerBound.clear();
   }

   public Range span() {
      Map.Entry firstEntry = this.entriesByLowerBound.firstEntry();
      Map.Entry lastEntry = this.entriesByLowerBound.lastEntry();
      if (firstEntry == null) {
         throw new NoSuchElementException();
      } else {
         return Range.create(((RangeMapEntry)firstEntry.getValue()).getKey().lowerBound, ((RangeMapEntry)lastEntry.getValue()).getKey().upperBound);
      }
   }

   private void putRangeMapEntry(Cut lowerBound, Cut upperBound, Object value) {
      this.entriesByLowerBound.put(lowerBound, new RangeMapEntry(lowerBound, upperBound, value));
   }

   public void remove(Range rangeToRemove) {
      if (!rangeToRemove.isEmpty()) {
         Map.Entry mapEntryBelowToTruncate = this.entriesByLowerBound.lowerEntry(rangeToRemove.lowerBound);
         if (mapEntryBelowToTruncate != null) {
            RangeMapEntry rangeMapEntry = (RangeMapEntry)mapEntryBelowToTruncate.getValue();
            if (rangeMapEntry.getUpperBound().compareTo(rangeToRemove.lowerBound) > 0) {
               if (rangeMapEntry.getUpperBound().compareTo(rangeToRemove.upperBound) > 0) {
                  this.putRangeMapEntry(rangeToRemove.upperBound, rangeMapEntry.getUpperBound(), ((RangeMapEntry)mapEntryBelowToTruncate.getValue()).getValue());
               }

               this.putRangeMapEntry(rangeMapEntry.getLowerBound(), rangeToRemove.lowerBound, ((RangeMapEntry)mapEntryBelowToTruncate.getValue()).getValue());
            }
         }

         Map.Entry mapEntryAboveToTruncate = this.entriesByLowerBound.lowerEntry(rangeToRemove.upperBound);
         if (mapEntryAboveToTruncate != null) {
            RangeMapEntry rangeMapEntry = (RangeMapEntry)mapEntryAboveToTruncate.getValue();
            if (rangeMapEntry.getUpperBound().compareTo(rangeToRemove.upperBound) > 0) {
               this.putRangeMapEntry(rangeToRemove.upperBound, rangeMapEntry.getUpperBound(), ((RangeMapEntry)mapEntryAboveToTruncate.getValue()).getValue());
            }
         }

         this.entriesByLowerBound.subMap(rangeToRemove.lowerBound, rangeToRemove.upperBound).clear();
      }
   }

   public Map asMapOfRanges() {
      return new AsMapOfRanges(this.entriesByLowerBound.values());
   }

   public Map asDescendingMapOfRanges() {
      return new AsMapOfRanges(this.entriesByLowerBound.descendingMap().values());
   }

   public RangeMap subRangeMap(Range subRange) {
      return (RangeMap)(subRange.equals(Range.all()) ? this : new SubRangeMap(subRange));
   }

   private RangeMap emptySubRangeMap() {
      return EMPTY_SUB_RANGE_MAP;
   }

   public boolean equals(@Nullable Object o) {
      if (o instanceof RangeMap) {
         RangeMap rangeMap = (RangeMap)o;
         return this.asMapOfRanges().equals(rangeMap.asMapOfRanges());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.asMapOfRanges().hashCode();
   }

   public String toString() {
      return this.entriesByLowerBound.values().toString();
   }

   private class SubRangeMap implements RangeMap {
      private final Range subRange;

      SubRangeMap(Range subRange) {
         this.subRange = subRange;
      }

      @Nullable
      public Object get(Comparable key) {
         return this.subRange.contains(key) ? TreeRangeMap.this.get(key) : null;
      }

      @Nullable
      public Map.Entry getEntry(Comparable key) {
         if (this.subRange.contains(key)) {
            Map.Entry entry = TreeRangeMap.this.getEntry(key);
            if (entry != null) {
               return Maps.immutableEntry(((Range)entry.getKey()).intersection(this.subRange), entry.getValue());
            }
         }

         return null;
      }

      public Range span() {
         Map.Entry lowerEntry = TreeRangeMap.this.entriesByLowerBound.floorEntry(this.subRange.lowerBound);
         Cut lowerBound;
         if (lowerEntry != null && ((RangeMapEntry)lowerEntry.getValue()).getUpperBound().compareTo(this.subRange.lowerBound) > 0) {
            lowerBound = this.subRange.lowerBound;
         } else {
            lowerBound = (Cut)TreeRangeMap.this.entriesByLowerBound.ceilingKey(this.subRange.lowerBound);
            if (lowerBound == null || lowerBound.compareTo(this.subRange.upperBound) >= 0) {
               throw new NoSuchElementException();
            }
         }

         Map.Entry upperEntry = TreeRangeMap.this.entriesByLowerBound.lowerEntry(this.subRange.upperBound);
         if (upperEntry == null) {
            throw new NoSuchElementException();
         } else {
            Cut upperBound;
            if (((RangeMapEntry)upperEntry.getValue()).getUpperBound().compareTo(this.subRange.upperBound) >= 0) {
               upperBound = this.subRange.upperBound;
            } else {
               upperBound = ((RangeMapEntry)upperEntry.getValue()).getUpperBound();
            }

            return Range.create(lowerBound, upperBound);
         }
      }

      public void put(Range range, Object value) {
         Preconditions.checkArgument(this.subRange.encloses(range), "Cannot put range %s into a subRangeMap(%s)", range, this.subRange);
         TreeRangeMap.this.put(range, value);
      }

      public void putCoalescing(Range range, Object value) {
         if (!TreeRangeMap.this.entriesByLowerBound.isEmpty() && !range.isEmpty() && this.subRange.encloses(range)) {
            Range coalescedRange = TreeRangeMap.this.coalescedRange(range, Preconditions.checkNotNull(value));
            this.put(coalescedRange.intersection(this.subRange), value);
         } else {
            this.put(range, value);
         }
      }

      public void putAll(RangeMap rangeMap) {
         if (!rangeMap.asMapOfRanges().isEmpty()) {
            Range span = rangeMap.span();
            Preconditions.checkArgument(this.subRange.encloses(span), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", span, this.subRange);
            TreeRangeMap.this.putAll(rangeMap);
         }
      }

      public void clear() {
         TreeRangeMap.this.remove(this.subRange);
      }

      public void remove(Range range) {
         if (range.isConnected(this.subRange)) {
            TreeRangeMap.this.remove(range.intersection(this.subRange));
         }

      }

      public RangeMap subRangeMap(Range range) {
         return !range.isConnected(this.subRange) ? TreeRangeMap.this.emptySubRangeMap() : TreeRangeMap.this.subRangeMap(range.intersection(this.subRange));
      }

      public Map asMapOfRanges() {
         return new SubRangeMapAsMap();
      }

      public Map asDescendingMapOfRanges() {
         return new SubRangeMapAsMap() {
            Iterator entryIterator() {
               if (SubRangeMap.this.subRange.isEmpty()) {
                  return Iterators.emptyIterator();
               } else {
                  final Iterator backingItr = TreeRangeMap.this.entriesByLowerBound.headMap(SubRangeMap.this.subRange.upperBound, false).descendingMap().values().iterator();
                  return new AbstractIterator() {
                     protected Map.Entry computeNext() {
                        if (backingItr.hasNext()) {
                           RangeMapEntry entry = (RangeMapEntry)backingItr.next();
                           return entry.getUpperBound().compareTo(SubRangeMap.this.subRange.lowerBound) <= 0 ? (Map.Entry)this.endOfData() : Maps.immutableEntry(entry.getKey().intersection(SubRangeMap.this.subRange), entry.getValue());
                        } else {
                           return (Map.Entry)this.endOfData();
                        }
                     }
                  };
               }
            }
         };
      }

      public boolean equals(@Nullable Object o) {
         if (o instanceof RangeMap) {
            RangeMap rangeMap = (RangeMap)o;
            return this.asMapOfRanges().equals(rangeMap.asMapOfRanges());
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.asMapOfRanges().hashCode();
      }

      public String toString() {
         return this.asMapOfRanges().toString();
      }

      class SubRangeMapAsMap extends AbstractMap {
         public boolean containsKey(Object key) {
            return this.get(key) != null;
         }

         public Object get(Object key) {
            try {
               if (key instanceof Range) {
                  Range r = (Range)key;
                  if (!SubRangeMap.this.subRange.encloses(r) || r.isEmpty()) {
                     return null;
                  }

                  RangeMapEntry candidate = null;
                  if (r.lowerBound.compareTo(SubRangeMap.this.subRange.lowerBound) == 0) {
                     Map.Entry entry = TreeRangeMap.this.entriesByLowerBound.floorEntry(r.lowerBound);
                     if (entry != null) {
                        candidate = (RangeMapEntry)entry.getValue();
                     }
                  } else {
                     candidate = (RangeMapEntry)TreeRangeMap.this.entriesByLowerBound.get(r.lowerBound);
                  }

                  if (candidate != null && candidate.getKey().isConnected(SubRangeMap.this.subRange) && candidate.getKey().intersection(SubRangeMap.this.subRange).equals(r)) {
                     return candidate.getValue();
                  }
               }

               return null;
            } catch (ClassCastException var5) {
               return null;
            }
         }

         public Object remove(Object key) {
            Object value = this.get(key);
            if (value != null) {
               Range range = (Range)key;
               TreeRangeMap.this.remove(range);
               return value;
            } else {
               return null;
            }
         }

         public void clear() {
            SubRangeMap.this.clear();
         }

         private boolean removeEntryIf(Predicate predicate) {
            List toRemove = Lists.newArrayList();
            Iterator var3 = this.entrySet().iterator();

            while(var3.hasNext()) {
               Map.Entry entry = (Map.Entry)var3.next();
               if (predicate.apply(entry)) {
                  toRemove.add(entry.getKey());
               }
            }

            var3 = toRemove.iterator();

            while(var3.hasNext()) {
               Range range = (Range)var3.next();
               TreeRangeMap.this.remove(range);
            }

            return !toRemove.isEmpty();
         }

         public Set keySet() {
            return new Maps.KeySet(this) {
               public boolean remove(@Nullable Object o) {
                  return SubRangeMapAsMap.this.remove(o) != null;
               }

               public boolean retainAll(Collection c) {
                  return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.in(c)), Maps.keyFunction()));
               }
            };
         }

         public Set entrySet() {
            return new Maps.EntrySet() {
               Map map() {
                  return SubRangeMapAsMap.this;
               }

               public Iterator iterator() {
                  return SubRangeMapAsMap.this.entryIterator();
               }

               public boolean retainAll(Collection c) {
                  return SubRangeMapAsMap.this.removeEntryIf(Predicates.not(Predicates.in(c)));
               }

               public int size() {
                  return Iterators.size(this.iterator());
               }

               public boolean isEmpty() {
                  return !this.iterator().hasNext();
               }
            };
         }

         Iterator entryIterator() {
            if (SubRangeMap.this.subRange.isEmpty()) {
               return Iterators.emptyIterator();
            } else {
               Cut cutToStart = (Cut)MoreObjects.firstNonNull(TreeRangeMap.this.entriesByLowerBound.floorKey(SubRangeMap.this.subRange.lowerBound), SubRangeMap.this.subRange.lowerBound);
               final Iterator backingItr = TreeRangeMap.this.entriesByLowerBound.tailMap(cutToStart, true).values().iterator();
               return new AbstractIterator() {
                  protected Map.Entry computeNext() {
                     while(true) {
                        if (backingItr.hasNext()) {
                           RangeMapEntry entry = (RangeMapEntry)backingItr.next();
                           if (entry.getLowerBound().compareTo(SubRangeMap.this.subRange.upperBound) >= 0) {
                              return (Map.Entry)this.endOfData();
                           }

                           if (entry.getUpperBound().compareTo(SubRangeMap.this.subRange.lowerBound) <= 0) {
                              continue;
                           }

                           return Maps.immutableEntry(entry.getKey().intersection(SubRangeMap.this.subRange), entry.getValue());
                        }

                        return (Map.Entry)this.endOfData();
                     }
                  }
               };
            }
         }

         public Collection values() {
            return new Maps.Values(this) {
               public boolean removeAll(Collection c) {
                  return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.in(c), Maps.valueFunction()));
               }

               public boolean retainAll(Collection c) {
                  return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.in(c)), Maps.valueFunction()));
               }
            };
         }
      }
   }

   private final class AsMapOfRanges extends Maps.IteratorBasedAbstractMap {
      final Iterable entryIterable;

      AsMapOfRanges(Iterable entryIterable) {
         this.entryIterable = entryIterable;
      }

      public boolean containsKey(@Nullable Object key) {
         return this.get(key) != null;
      }

      public Object get(@Nullable Object key) {
         if (key instanceof Range) {
            Range range = (Range)key;
            RangeMapEntry rangeMapEntry = (RangeMapEntry)TreeRangeMap.this.entriesByLowerBound.get(range.lowerBound);
            if (rangeMapEntry != null && rangeMapEntry.getKey().equals(range)) {
               return rangeMapEntry.getValue();
            }
         }

         return null;
      }

      public int size() {
         return TreeRangeMap.this.entriesByLowerBound.size();
      }

      Iterator entryIterator() {
         return this.entryIterable.iterator();
      }
   }

   private static final class RangeMapEntry extends AbstractMapEntry {
      private final Range range;
      private final Object value;

      RangeMapEntry(Cut lowerBound, Cut upperBound, Object value) {
         this(Range.create(lowerBound, upperBound), value);
      }

      RangeMapEntry(Range range, Object value) {
         this.range = range;
         this.value = value;
      }

      public Range getKey() {
         return this.range;
      }

      public Object getValue() {
         return this.value;
      }

      public boolean contains(Comparable value) {
         return this.range.contains(value);
      }

      Cut getLowerBound() {
         return this.range.lowerBound;
      }

      Cut getUpperBound() {
         return this.range.upperBound;
      }
   }
}
