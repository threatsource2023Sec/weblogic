package org.python.google.common.collect;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public final class ImmutableSortedMap extends ImmutableSortedMapFauxverideShim implements NavigableMap {
   private static final Comparator NATURAL_ORDER = Ordering.natural();
   private static final ImmutableSortedMap NATURAL_EMPTY_MAP = new ImmutableSortedMap(ImmutableSortedSet.emptySet(Ordering.natural()), ImmutableList.of());
   private final transient RegularImmutableSortedSet keySet;
   private final transient ImmutableList valueList;
   private transient ImmutableSortedMap descendingMap;
   private static final long serialVersionUID = 0L;

   static ImmutableSortedMap emptyMap(Comparator comparator) {
      return Ordering.natural().equals(comparator) ? of() : new ImmutableSortedMap(ImmutableSortedSet.emptySet(comparator), ImmutableList.of());
   }

   public static ImmutableSortedMap of() {
      return NATURAL_EMPTY_MAP;
   }

   public static ImmutableSortedMap of(Comparable k1, Object v1) {
      return of(Ordering.natural(), k1, v1);
   }

   private static ImmutableSortedMap of(Comparator comparator, Object k1, Object v1) {
      return new ImmutableSortedMap(new RegularImmutableSortedSet(ImmutableList.of(k1), (Comparator)Preconditions.checkNotNull(comparator)), ImmutableList.of(v1));
   }

   private static ImmutableSortedMap ofEntries(Map.Entry... entries) {
      return fromEntries(Ordering.natural(), false, entries, entries.length);
   }

   public static ImmutableSortedMap of(Comparable k1, Object v1, Comparable k2, Object v2) {
      return ofEntries(entryOf(k1, v1), entryOf(k2, v2));
   }

   public static ImmutableSortedMap of(Comparable k1, Object v1, Comparable k2, Object v2, Comparable k3, Object v3) {
      return ofEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3));
   }

   public static ImmutableSortedMap of(Comparable k1, Object v1, Comparable k2, Object v2, Comparable k3, Object v3, Comparable k4, Object v4) {
      return ofEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4));
   }

   public static ImmutableSortedMap of(Comparable k1, Object v1, Comparable k2, Object v2, Comparable k3, Object v3, Comparable k4, Object v4, Comparable k5, Object v5) {
      return ofEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5));
   }

   public static ImmutableSortedMap copyOf(Map map) {
      Ordering naturalOrder = (Ordering)NATURAL_ORDER;
      return copyOfInternal(map, naturalOrder);
   }

   public static ImmutableSortedMap copyOf(Map map, Comparator comparator) {
      return copyOfInternal(map, (Comparator)Preconditions.checkNotNull(comparator));
   }

   @Beta
   public static ImmutableSortedMap copyOf(Iterable entries) {
      Ordering naturalOrder = (Ordering)NATURAL_ORDER;
      return copyOf((Iterable)entries, naturalOrder);
   }

   @Beta
   public static ImmutableSortedMap copyOf(Iterable entries, Comparator comparator) {
      return fromEntries((Comparator)Preconditions.checkNotNull(comparator), false, entries);
   }

   public static ImmutableSortedMap copyOfSorted(SortedMap map) {
      Comparator comparator = map.comparator();
      if (comparator == null) {
         comparator = NATURAL_ORDER;
      }

      if (map instanceof ImmutableSortedMap) {
         ImmutableSortedMap kvMap = (ImmutableSortedMap)map;
         if (!kvMap.isPartialView()) {
            return kvMap;
         }
      }

      return fromEntries(comparator, true, map.entrySet());
   }

   private static ImmutableSortedMap copyOfInternal(Map map, Comparator comparator) {
      boolean sameComparator = false;
      if (map instanceof SortedMap) {
         SortedMap sortedMap = (SortedMap)map;
         Comparator comparator2 = sortedMap.comparator();
         sameComparator = comparator2 == null ? comparator == NATURAL_ORDER : comparator.equals(comparator2);
      }

      if (sameComparator && map instanceof ImmutableSortedMap) {
         ImmutableSortedMap kvMap = (ImmutableSortedMap)map;
         if (!kvMap.isPartialView()) {
            return kvMap;
         }
      }

      return fromEntries(comparator, sameComparator, map.entrySet());
   }

   private static ImmutableSortedMap fromEntries(Comparator comparator, boolean sameComparator, Iterable entries) {
      Map.Entry[] entryArray = (Map.Entry[])((Map.Entry[])Iterables.toArray(entries, (Object[])EMPTY_ENTRY_ARRAY));
      return fromEntries(comparator, sameComparator, entryArray, entryArray.length);
   }

   private static ImmutableSortedMap fromEntries(final Comparator comparator, boolean sameComparator, Map.Entry[] entryArray, int size) {
      switch (size) {
         case 0:
            return emptyMap(comparator);
         case 1:
            return of(comparator, entryArray[0].getKey(), entryArray[0].getValue());
         default:
            Object[] keys = new Object[size];
            Object[] values = new Object[size];
            Object key;
            if (sameComparator) {
               for(int i = 0; i < size; ++i) {
                  Object key = entryArray[i].getKey();
                  key = entryArray[i].getValue();
                  CollectPreconditions.checkEntryNotNull(key, key);
                  keys[i] = key;
                  values[i] = key;
               }
            } else {
               Arrays.sort(entryArray, 0, size, new Comparator() {
                  public int compare(Map.Entry e1, Map.Entry e2) {
                     return comparator.compare(e1.getKey(), e2.getKey());
                  }
               });
               Object prevKey = entryArray[0].getKey();
               keys[0] = prevKey;
               values[0] = entryArray[0].getValue();

               for(int i = 1; i < size; ++i) {
                  key = entryArray[i].getKey();
                  Object value = entryArray[i].getValue();
                  CollectPreconditions.checkEntryNotNull(key, value);
                  keys[i] = key;
                  values[i] = value;
                  checkNoConflict(comparator.compare(prevKey, key) != 0, "key", entryArray[i - 1], entryArray[i]);
                  prevKey = key;
               }
            }

            return new ImmutableSortedMap(new RegularImmutableSortedSet(ImmutableList.asImmutableList(keys), comparator), ImmutableList.asImmutableList(values));
      }
   }

   public static Builder naturalOrder() {
      return new Builder(Ordering.natural());
   }

   public static Builder orderedBy(Comparator comparator) {
      return new Builder(comparator);
   }

   public static Builder reverseOrder() {
      return new Builder(Ordering.natural().reverse());
   }

   ImmutableSortedMap(RegularImmutableSortedSet keySet, ImmutableList valueList) {
      this(keySet, valueList, (ImmutableSortedMap)null);
   }

   ImmutableSortedMap(RegularImmutableSortedSet keySet, ImmutableList valueList, ImmutableSortedMap descendingMap) {
      this.keySet = keySet;
      this.valueList = valueList;
      this.descendingMap = descendingMap;
   }

   public int size() {
      return this.valueList.size();
   }

   public Object get(@Nullable Object key) {
      int index = this.keySet.indexOf(key);
      return index == -1 ? null : this.valueList.get(index);
   }

   boolean isPartialView() {
      return this.keySet.isPartialView() || this.valueList.isPartialView();
   }

   public ImmutableSet entrySet() {
      return super.entrySet();
   }

   ImmutableSet createEntrySet() {
      class EntrySet extends ImmutableMapEntrySet {
         public UnmodifiableIterator iterator() {
            return this.asList().iterator();
         }

         ImmutableList createAsList() {
            return new ImmutableList() {
               public Map.Entry get(int index) {
                  return new AbstractMap.SimpleImmutableEntry(ImmutableSortedMap.this.keySet.asList().get(index), ImmutableSortedMap.this.valueList.get(index));
               }

               boolean isPartialView() {
                  return true;
               }

               public int size() {
                  return ImmutableSortedMap.this.size();
               }
            };
         }

         ImmutableMap map() {
            return ImmutableSortedMap.this;
         }
      }

      return (ImmutableSet)(this.isEmpty() ? ImmutableSet.of() : new EntrySet());
   }

   public ImmutableSortedSet keySet() {
      return this.keySet;
   }

   ImmutableSet createKeySet() {
      throw new AssertionError("should never be called");
   }

   public ImmutableCollection values() {
      return this.valueList;
   }

   ImmutableCollection createValues() {
      throw new AssertionError("should never be called");
   }

   public Comparator comparator() {
      return this.keySet().comparator();
   }

   public Object firstKey() {
      return this.keySet().first();
   }

   public Object lastKey() {
      return this.keySet().last();
   }

   private ImmutableSortedMap getSubMap(int fromIndex, int toIndex) {
      if (fromIndex == 0 && toIndex == this.size()) {
         return this;
      } else {
         return fromIndex == toIndex ? emptyMap(this.comparator()) : new ImmutableSortedMap(this.keySet.getSubSet(fromIndex, toIndex), this.valueList.subList(fromIndex, toIndex));
      }
   }

   public ImmutableSortedMap headMap(Object toKey) {
      return this.headMap(toKey, false);
   }

   public ImmutableSortedMap headMap(Object toKey, boolean inclusive) {
      return this.getSubMap(0, this.keySet.headIndex(Preconditions.checkNotNull(toKey), inclusive));
   }

   public ImmutableSortedMap subMap(Object fromKey, Object toKey) {
      return this.subMap(fromKey, true, toKey, false);
   }

   public ImmutableSortedMap subMap(Object fromKey, boolean fromInclusive, Object toKey, boolean toInclusive) {
      Preconditions.checkNotNull(fromKey);
      Preconditions.checkNotNull(toKey);
      Preconditions.checkArgument(this.comparator().compare(fromKey, toKey) <= 0, "expected fromKey <= toKey but %s > %s", fromKey, toKey);
      return this.headMap(toKey, toInclusive).tailMap(fromKey, fromInclusive);
   }

   public ImmutableSortedMap tailMap(Object fromKey) {
      return this.tailMap(fromKey, true);
   }

   public ImmutableSortedMap tailMap(Object fromKey, boolean inclusive) {
      return this.getSubMap(this.keySet.tailIndex(Preconditions.checkNotNull(fromKey), inclusive), this.size());
   }

   public Map.Entry lowerEntry(Object key) {
      return this.headMap(key, false).lastEntry();
   }

   public Object lowerKey(Object key) {
      return Maps.keyOrNull(this.lowerEntry(key));
   }

   public Map.Entry floorEntry(Object key) {
      return this.headMap(key, true).lastEntry();
   }

   public Object floorKey(Object key) {
      return Maps.keyOrNull(this.floorEntry(key));
   }

   public Map.Entry ceilingEntry(Object key) {
      return this.tailMap(key, true).firstEntry();
   }

   public Object ceilingKey(Object key) {
      return Maps.keyOrNull(this.ceilingEntry(key));
   }

   public Map.Entry higherEntry(Object key) {
      return this.tailMap(key, false).firstEntry();
   }

   public Object higherKey(Object key) {
      return Maps.keyOrNull(this.higherEntry(key));
   }

   public Map.Entry firstEntry() {
      return this.isEmpty() ? null : (Map.Entry)this.entrySet().asList().get(0);
   }

   public Map.Entry lastEntry() {
      return this.isEmpty() ? null : (Map.Entry)this.entrySet().asList().get(this.size() - 1);
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final Map.Entry pollFirstEntry() {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final Map.Entry pollLastEntry() {
      throw new UnsupportedOperationException();
   }

   public ImmutableSortedMap descendingMap() {
      ImmutableSortedMap result = this.descendingMap;
      if (result == null) {
         return this.isEmpty() ? emptyMap(Ordering.from(this.comparator()).reverse()) : new ImmutableSortedMap((RegularImmutableSortedSet)this.keySet.descendingSet(), this.valueList.reverse(), this);
      } else {
         return result;
      }
   }

   public ImmutableSortedSet navigableKeySet() {
      return this.keySet;
   }

   public ImmutableSortedSet descendingKeySet() {
      return this.keySet.descendingSet();
   }

   Object writeReplace() {
      return new SerializedForm(this);
   }

   private static class SerializedForm extends ImmutableMap.SerializedForm {
      private final Comparator comparator;
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableSortedMap sortedMap) {
         super(sortedMap);
         this.comparator = sortedMap.comparator();
      }

      Object readResolve() {
         Builder builder = new Builder(this.comparator);
         return this.createMap(builder);
      }
   }

   public static class Builder extends ImmutableMap.Builder {
      private transient Object[] keys;
      private transient Object[] values;
      private final Comparator comparator;

      public Builder(Comparator comparator) {
         this(comparator, 4);
      }

      private Builder(Comparator comparator, int initialCapacity) {
         this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
         this.keys = new Object[initialCapacity];
         this.values = new Object[initialCapacity];
      }

      private void ensureCapacity(int minCapacity) {
         if (minCapacity > this.keys.length) {
            int newCapacity = ImmutableCollection.Builder.expandedCapacity(this.keys.length, minCapacity);
            this.keys = Arrays.copyOf(this.keys, newCapacity);
            this.values = Arrays.copyOf(this.values, newCapacity);
         }

      }

      @CanIgnoreReturnValue
      public Builder put(Object key, Object value) {
         this.ensureCapacity(this.size + 1);
         CollectPreconditions.checkEntryNotNull(key, value);
         this.keys[this.size] = key;
         this.values[this.size] = value;
         ++this.size;
         return this;
      }

      @CanIgnoreReturnValue
      public Builder put(Map.Entry entry) {
         super.put(entry);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder putAll(Map map) {
         super.putAll(map);
         return this;
      }

      @CanIgnoreReturnValue
      @Beta
      public Builder putAll(Iterable entries) {
         super.putAll(entries);
         return this;
      }

      /** @deprecated */
      @Deprecated
      @CanIgnoreReturnValue
      @Beta
      public Builder orderEntriesByValue(Comparator valueComparator) {
         throw new UnsupportedOperationException("Not available on ImmutableSortedMap.Builder");
      }

      public ImmutableSortedMap build() {
         switch (this.size) {
            case 0:
               return ImmutableSortedMap.emptyMap(this.comparator);
            case 1:
               return ImmutableSortedMap.of(this.comparator, this.keys[0], this.values[0]);
            default:
               Object[] sortedKeys = Arrays.copyOf(this.keys, this.size);
               Arrays.sort((Object[])sortedKeys, this.comparator);
               Object[] sortedValues = new Object[this.size];

               for(int i = 0; i < this.size; ++i) {
                  if (i > 0 && this.comparator.compare(sortedKeys[i - 1], sortedKeys[i]) == 0) {
                     throw new IllegalArgumentException("keys required to be distinct but compared as equal: " + sortedKeys[i - 1] + " and " + sortedKeys[i]);
                  }

                  int index = Arrays.binarySearch((Object[])sortedKeys, this.keys[i], this.comparator);
                  sortedValues[index] = this.values[i];
               }

               return new ImmutableSortedMap(new RegularImmutableSortedSet(ImmutableList.asImmutableList(sortedKeys), this.comparator), ImmutableList.asImmutableList(sortedValues));
         }
      }
   }
}
