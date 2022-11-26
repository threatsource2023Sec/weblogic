package org.python.google.common.collect;

import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
public abstract class ForwardingNavigableMap extends ForwardingSortedMap implements NavigableMap {
   protected ForwardingNavigableMap() {
   }

   protected abstract NavigableMap delegate();

   public Map.Entry lowerEntry(Object key) {
      return this.delegate().lowerEntry(key);
   }

   protected Map.Entry standardLowerEntry(Object key) {
      return this.headMap(key, false).lastEntry();
   }

   public Object lowerKey(Object key) {
      return this.delegate().lowerKey(key);
   }

   protected Object standardLowerKey(Object key) {
      return Maps.keyOrNull(this.lowerEntry(key));
   }

   public Map.Entry floorEntry(Object key) {
      return this.delegate().floorEntry(key);
   }

   protected Map.Entry standardFloorEntry(Object key) {
      return this.headMap(key, true).lastEntry();
   }

   public Object floorKey(Object key) {
      return this.delegate().floorKey(key);
   }

   protected Object standardFloorKey(Object key) {
      return Maps.keyOrNull(this.floorEntry(key));
   }

   public Map.Entry ceilingEntry(Object key) {
      return this.delegate().ceilingEntry(key);
   }

   protected Map.Entry standardCeilingEntry(Object key) {
      return this.tailMap(key, true).firstEntry();
   }

   public Object ceilingKey(Object key) {
      return this.delegate().ceilingKey(key);
   }

   protected Object standardCeilingKey(Object key) {
      return Maps.keyOrNull(this.ceilingEntry(key));
   }

   public Map.Entry higherEntry(Object key) {
      return this.delegate().higherEntry(key);
   }

   protected Map.Entry standardHigherEntry(Object key) {
      return this.tailMap(key, false).firstEntry();
   }

   public Object higherKey(Object key) {
      return this.delegate().higherKey(key);
   }

   protected Object standardHigherKey(Object key) {
      return Maps.keyOrNull(this.higherEntry(key));
   }

   public Map.Entry firstEntry() {
      return this.delegate().firstEntry();
   }

   protected Map.Entry standardFirstEntry() {
      return (Map.Entry)Iterables.getFirst(this.entrySet(), (Object)null);
   }

   protected Object standardFirstKey() {
      Map.Entry entry = this.firstEntry();
      if (entry == null) {
         throw new NoSuchElementException();
      } else {
         return entry.getKey();
      }
   }

   public Map.Entry lastEntry() {
      return this.delegate().lastEntry();
   }

   protected Map.Entry standardLastEntry() {
      return (Map.Entry)Iterables.getFirst(this.descendingMap().entrySet(), (Object)null);
   }

   protected Object standardLastKey() {
      Map.Entry entry = this.lastEntry();
      if (entry == null) {
         throw new NoSuchElementException();
      } else {
         return entry.getKey();
      }
   }

   public Map.Entry pollFirstEntry() {
      return this.delegate().pollFirstEntry();
   }

   protected Map.Entry standardPollFirstEntry() {
      return (Map.Entry)Iterators.pollNext(this.entrySet().iterator());
   }

   public Map.Entry pollLastEntry() {
      return this.delegate().pollLastEntry();
   }

   protected Map.Entry standardPollLastEntry() {
      return (Map.Entry)Iterators.pollNext(this.descendingMap().entrySet().iterator());
   }

   public NavigableMap descendingMap() {
      return this.delegate().descendingMap();
   }

   public NavigableSet navigableKeySet() {
      return this.delegate().navigableKeySet();
   }

   public NavigableSet descendingKeySet() {
      return this.delegate().descendingKeySet();
   }

   @Beta
   protected NavigableSet standardDescendingKeySet() {
      return this.descendingMap().navigableKeySet();
   }

   protected SortedMap standardSubMap(Object fromKey, Object toKey) {
      return this.subMap(fromKey, true, toKey, false);
   }

   public NavigableMap subMap(Object fromKey, boolean fromInclusive, Object toKey, boolean toInclusive) {
      return this.delegate().subMap(fromKey, fromInclusive, toKey, toInclusive);
   }

   public NavigableMap headMap(Object toKey, boolean inclusive) {
      return this.delegate().headMap(toKey, inclusive);
   }

   public NavigableMap tailMap(Object fromKey, boolean inclusive) {
      return this.delegate().tailMap(fromKey, inclusive);
   }

   protected SortedMap standardHeadMap(Object toKey) {
      return this.headMap(toKey, false);
   }

   protected SortedMap standardTailMap(Object fromKey) {
      return this.tailMap(fromKey, true);
   }

   @Beta
   protected class StandardNavigableKeySet extends Maps.NavigableKeySet {
      public StandardNavigableKeySet() {
         super(ForwardingNavigableMap.this);
      }
   }

   @Beta
   protected class StandardDescendingMap extends Maps.DescendingMap {
      public StandardDescendingMap() {
      }

      NavigableMap forward() {
         return ForwardingNavigableMap.this;
      }

      protected Iterator entryIterator() {
         return new Iterator() {
            private Map.Entry toRemove = null;
            private Map.Entry nextOrNull = StandardDescendingMap.this.forward().lastEntry();

            public boolean hasNext() {
               return this.nextOrNull != null;
            }

            public Map.Entry next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  Map.Entry var1;
                  try {
                     var1 = this.nextOrNull;
                  } finally {
                     this.toRemove = this.nextOrNull;
                     this.nextOrNull = StandardDescendingMap.this.forward().lowerEntry(this.nextOrNull.getKey());
                  }

                  return var1;
               }
            }

            public void remove() {
               CollectPreconditions.checkRemove(this.toRemove != null);
               StandardDescendingMap.this.forward().remove(this.toRemove.getKey());
               this.toRemove = null;
            }
         };
      }
   }
}
