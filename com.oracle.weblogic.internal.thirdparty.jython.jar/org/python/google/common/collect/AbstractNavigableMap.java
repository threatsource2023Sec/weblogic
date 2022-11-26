package org.python.google.common.collect;

import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
abstract class AbstractNavigableMap extends Maps.IteratorBasedAbstractMap implements NavigableMap {
   @Nullable
   public abstract Object get(@Nullable Object var1);

   @Nullable
   public Map.Entry firstEntry() {
      return (Map.Entry)Iterators.getNext(this.entryIterator(), (Object)null);
   }

   @Nullable
   public Map.Entry lastEntry() {
      return (Map.Entry)Iterators.getNext(this.descendingEntryIterator(), (Object)null);
   }

   @Nullable
   public Map.Entry pollFirstEntry() {
      return (Map.Entry)Iterators.pollNext(this.entryIterator());
   }

   @Nullable
   public Map.Entry pollLastEntry() {
      return (Map.Entry)Iterators.pollNext(this.descendingEntryIterator());
   }

   public Object firstKey() {
      Map.Entry entry = this.firstEntry();
      if (entry == null) {
         throw new NoSuchElementException();
      } else {
         return entry.getKey();
      }
   }

   public Object lastKey() {
      Map.Entry entry = this.lastEntry();
      if (entry == null) {
         throw new NoSuchElementException();
      } else {
         return entry.getKey();
      }
   }

   @Nullable
   public Map.Entry lowerEntry(Object key) {
      return this.headMap(key, false).lastEntry();
   }

   @Nullable
   public Map.Entry floorEntry(Object key) {
      return this.headMap(key, true).lastEntry();
   }

   @Nullable
   public Map.Entry ceilingEntry(Object key) {
      return this.tailMap(key, true).firstEntry();
   }

   @Nullable
   public Map.Entry higherEntry(Object key) {
      return this.tailMap(key, false).firstEntry();
   }

   public Object lowerKey(Object key) {
      return Maps.keyOrNull(this.lowerEntry(key));
   }

   public Object floorKey(Object key) {
      return Maps.keyOrNull(this.floorEntry(key));
   }

   public Object ceilingKey(Object key) {
      return Maps.keyOrNull(this.ceilingEntry(key));
   }

   public Object higherKey(Object key) {
      return Maps.keyOrNull(this.higherEntry(key));
   }

   abstract Iterator descendingEntryIterator();

   public SortedMap subMap(Object fromKey, Object toKey) {
      return this.subMap(fromKey, true, toKey, false);
   }

   public SortedMap headMap(Object toKey) {
      return this.headMap(toKey, false);
   }

   public SortedMap tailMap(Object fromKey) {
      return this.tailMap(fromKey, true);
   }

   public NavigableSet navigableKeySet() {
      return new Maps.NavigableKeySet(this);
   }

   public Set keySet() {
      return this.navigableKeySet();
   }

   public NavigableSet descendingKeySet() {
      return this.descendingMap().navigableKeySet();
   }

   public NavigableMap descendingMap() {
      return new DescendingMap();
   }

   private final class DescendingMap extends Maps.DescendingMap {
      private DescendingMap() {
      }

      NavigableMap forward() {
         return AbstractNavigableMap.this;
      }

      Iterator entryIterator() {
         return AbstractNavigableMap.this.descendingEntryIterator();
      }

      // $FF: synthetic method
      DescendingMap(Object x1) {
         this();
      }
   }
}
