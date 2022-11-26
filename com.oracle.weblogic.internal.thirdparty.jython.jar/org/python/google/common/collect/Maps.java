package org.python.google.common.collect;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Converter;
import org.python.google.common.base.Equivalence;
import org.python.google.common.base.Function;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Predicates;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.j2objc.annotations.RetainedWith;
import org.python.google.j2objc.annotations.Weak;

@GwtCompatible(
   emulated = true
)
public final class Maps {
   private Maps() {
   }

   static Function keyFunction() {
      return Maps.EntryFunction.KEY;
   }

   static Function valueFunction() {
      return Maps.EntryFunction.VALUE;
   }

   static Iterator keyIterator(Iterator entryIterator) {
      return Iterators.transform(entryIterator, keyFunction());
   }

   static Iterator valueIterator(Iterator entryIterator) {
      return Iterators.transform(entryIterator, valueFunction());
   }

   @GwtCompatible(
      serializable = true
   )
   @Beta
   public static ImmutableMap immutableEnumMap(Map map) {
      if (map instanceof ImmutableEnumMap) {
         ImmutableEnumMap result = (ImmutableEnumMap)map;
         return result;
      } else if (map.isEmpty()) {
         return ImmutableMap.of();
      } else {
         Iterator var1 = map.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            Preconditions.checkNotNull(entry.getKey());
            Preconditions.checkNotNull(entry.getValue());
         }

         return ImmutableEnumMap.asImmutable(new EnumMap(map));
      }
   }

   public static HashMap newHashMap() {
      return new HashMap();
   }

   public static HashMap newHashMapWithExpectedSize(int expectedSize) {
      return new HashMap(capacity(expectedSize));
   }

   static int capacity(int expectedSize) {
      if (expectedSize < 3) {
         CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
         return expectedSize + 1;
      } else {
         return expectedSize < 1073741824 ? (int)((float)expectedSize / 0.75F + 1.0F) : Integer.MAX_VALUE;
      }
   }

   public static HashMap newHashMap(Map map) {
      return new HashMap(map);
   }

   public static LinkedHashMap newLinkedHashMap() {
      return new LinkedHashMap();
   }

   public static LinkedHashMap newLinkedHashMapWithExpectedSize(int expectedSize) {
      return new LinkedHashMap(capacity(expectedSize));
   }

   public static LinkedHashMap newLinkedHashMap(Map map) {
      return new LinkedHashMap(map);
   }

   public static ConcurrentMap newConcurrentMap() {
      return (new MapMaker()).makeMap();
   }

   public static TreeMap newTreeMap() {
      return new TreeMap();
   }

   public static TreeMap newTreeMap(SortedMap map) {
      return new TreeMap(map);
   }

   public static TreeMap newTreeMap(@Nullable Comparator comparator) {
      return new TreeMap(comparator);
   }

   public static EnumMap newEnumMap(Class type) {
      return new EnumMap((Class)Preconditions.checkNotNull(type));
   }

   public static EnumMap newEnumMap(Map map) {
      return new EnumMap(map);
   }

   public static IdentityHashMap newIdentityHashMap() {
      return new IdentityHashMap();
   }

   public static MapDifference difference(Map left, Map right) {
      if (left instanceof SortedMap) {
         SortedMap sortedLeft = (SortedMap)left;
         SortedMapDifference result = difference(sortedLeft, right);
         return result;
      } else {
         return difference(left, right, Equivalence.equals());
      }
   }

   public static MapDifference difference(Map left, Map right, Equivalence valueEquivalence) {
      Preconditions.checkNotNull(valueEquivalence);
      Map onlyOnLeft = newLinkedHashMap();
      Map onlyOnRight = new LinkedHashMap(right);
      Map onBoth = newLinkedHashMap();
      Map differences = newLinkedHashMap();
      doDifference(left, right, valueEquivalence, onlyOnLeft, onlyOnRight, onBoth, differences);
      return new MapDifferenceImpl(onlyOnLeft, onlyOnRight, onBoth, differences);
   }

   private static void doDifference(Map left, Map right, Equivalence valueEquivalence, Map onlyOnLeft, Map onlyOnRight, Map onBoth, Map differences) {
      Iterator var7 = left.entrySet().iterator();

      while(var7.hasNext()) {
         Map.Entry entry = (Map.Entry)var7.next();
         Object leftKey = entry.getKey();
         Object leftValue = entry.getValue();
         if (right.containsKey(leftKey)) {
            Object rightValue = onlyOnRight.remove(leftKey);
            if (valueEquivalence.equivalent(leftValue, rightValue)) {
               onBoth.put(leftKey, leftValue);
            } else {
               differences.put(leftKey, Maps.ValueDifferenceImpl.create(leftValue, rightValue));
            }
         } else {
            onlyOnLeft.put(leftKey, leftValue);
         }
      }

   }

   private static Map unmodifiableMap(Map map) {
      return (Map)(map instanceof SortedMap ? Collections.unmodifiableSortedMap((SortedMap)map) : Collections.unmodifiableMap(map));
   }

   public static SortedMapDifference difference(SortedMap left, Map right) {
      Preconditions.checkNotNull(left);
      Preconditions.checkNotNull(right);
      Comparator comparator = orNaturalOrder(left.comparator());
      SortedMap onlyOnLeft = newTreeMap(comparator);
      SortedMap onlyOnRight = newTreeMap(comparator);
      onlyOnRight.putAll(right);
      SortedMap onBoth = newTreeMap(comparator);
      SortedMap differences = newTreeMap(comparator);
      doDifference(left, right, Equivalence.equals(), onlyOnLeft, onlyOnRight, onBoth, differences);
      return new SortedMapDifferenceImpl(onlyOnLeft, onlyOnRight, onBoth, differences);
   }

   static Comparator orNaturalOrder(@Nullable Comparator comparator) {
      return (Comparator)(comparator != null ? comparator : Ordering.natural());
   }

   public static Map asMap(Set set, Function function) {
      return new AsMapView(set, function);
   }

   public static SortedMap asMap(SortedSet set, Function function) {
      return new SortedAsMapView(set, function);
   }

   @GwtIncompatible
   public static NavigableMap asMap(NavigableSet set, Function function) {
      return new NavigableAsMapView(set, function);
   }

   static Iterator asMapEntryIterator(Set set, final Function function) {
      return new TransformedIterator(set.iterator()) {
         Map.Entry transform(Object key) {
            return Maps.immutableEntry(key, function.apply(key));
         }
      };
   }

   private static Set removeOnlySet(final Set set) {
      return new ForwardingSet() {
         protected Set delegate() {
            return set;
         }

         public boolean add(Object element) {
            throw new UnsupportedOperationException();
         }

         public boolean addAll(Collection es) {
            throw new UnsupportedOperationException();
         }
      };
   }

   private static SortedSet removeOnlySortedSet(final SortedSet set) {
      return new ForwardingSortedSet() {
         protected SortedSet delegate() {
            return set;
         }

         public boolean add(Object element) {
            throw new UnsupportedOperationException();
         }

         public boolean addAll(Collection es) {
            throw new UnsupportedOperationException();
         }

         public SortedSet headSet(Object toElement) {
            return Maps.removeOnlySortedSet(super.headSet(toElement));
         }

         public SortedSet subSet(Object fromElement, Object toElement) {
            return Maps.removeOnlySortedSet(super.subSet(fromElement, toElement));
         }

         public SortedSet tailSet(Object fromElement) {
            return Maps.removeOnlySortedSet(super.tailSet(fromElement));
         }
      };
   }

   @GwtIncompatible
   private static NavigableSet removeOnlyNavigableSet(final NavigableSet set) {
      return new ForwardingNavigableSet() {
         protected NavigableSet delegate() {
            return set;
         }

         public boolean add(Object element) {
            throw new UnsupportedOperationException();
         }

         public boolean addAll(Collection es) {
            throw new UnsupportedOperationException();
         }

         public SortedSet headSet(Object toElement) {
            return Maps.removeOnlySortedSet(super.headSet(toElement));
         }

         public SortedSet subSet(Object fromElement, Object toElement) {
            return Maps.removeOnlySortedSet(super.subSet(fromElement, toElement));
         }

         public SortedSet tailSet(Object fromElement) {
            return Maps.removeOnlySortedSet(super.tailSet(fromElement));
         }

         public NavigableSet headSet(Object toElement, boolean inclusive) {
            return Maps.removeOnlyNavigableSet(super.headSet(toElement, inclusive));
         }

         public NavigableSet tailSet(Object fromElement, boolean inclusive) {
            return Maps.removeOnlyNavigableSet(super.tailSet(fromElement, inclusive));
         }

         public NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
            return Maps.removeOnlyNavigableSet(super.subSet(fromElement, fromInclusive, toElement, toInclusive));
         }

         public NavigableSet descendingSet() {
            return Maps.removeOnlyNavigableSet(super.descendingSet());
         }
      };
   }

   public static ImmutableMap toMap(Iterable keys, Function valueFunction) {
      return toMap(keys.iterator(), valueFunction);
   }

   public static ImmutableMap toMap(Iterator keys, Function valueFunction) {
      Preconditions.checkNotNull(valueFunction);
      Map builder = newLinkedHashMap();

      while(keys.hasNext()) {
         Object key = keys.next();
         builder.put(key, valueFunction.apply(key));
      }

      return ImmutableMap.copyOf((Map)builder);
   }

   @CanIgnoreReturnValue
   public static ImmutableMap uniqueIndex(Iterable values, Function keyFunction) {
      return uniqueIndex(values.iterator(), keyFunction);
   }

   @CanIgnoreReturnValue
   public static ImmutableMap uniqueIndex(Iterator values, Function keyFunction) {
      Preconditions.checkNotNull(keyFunction);
      ImmutableMap.Builder builder = ImmutableMap.builder();

      while(values.hasNext()) {
         Object value = values.next();
         builder.put(keyFunction.apply(value), value);
      }

      try {
         return builder.build();
      } catch (IllegalArgumentException var4) {
         throw new IllegalArgumentException(var4.getMessage() + ". To index multiple values under a key, use Multimaps.index.");
      }
   }

   @GwtIncompatible
   public static ImmutableMap fromProperties(Properties properties) {
      ImmutableMap.Builder builder = ImmutableMap.builder();
      Enumeration e = properties.propertyNames();

      while(e.hasMoreElements()) {
         String key = (String)e.nextElement();
         builder.put(key, properties.getProperty(key));
      }

      return builder.build();
   }

   @GwtCompatible(
      serializable = true
   )
   public static Map.Entry immutableEntry(@Nullable Object key, @Nullable Object value) {
      return new ImmutableEntry(key, value);
   }

   static Set unmodifiableEntrySet(Set entrySet) {
      return new UnmodifiableEntrySet(Collections.unmodifiableSet(entrySet));
   }

   static Map.Entry unmodifiableEntry(final Map.Entry entry) {
      Preconditions.checkNotNull(entry);
      return new AbstractMapEntry() {
         public Object getKey() {
            return entry.getKey();
         }

         public Object getValue() {
            return entry.getValue();
         }
      };
   }

   static UnmodifiableIterator unmodifiableEntryIterator(final Iterator entryIterator) {
      return new UnmodifiableIterator() {
         public boolean hasNext() {
            return entryIterator.hasNext();
         }

         public Map.Entry next() {
            return Maps.unmodifiableEntry((Map.Entry)entryIterator.next());
         }
      };
   }

   @Beta
   public static Converter asConverter(BiMap bimap) {
      return new BiMapConverter(bimap);
   }

   public static BiMap synchronizedBiMap(BiMap bimap) {
      return Synchronized.biMap(bimap, (Object)null);
   }

   public static BiMap unmodifiableBiMap(BiMap bimap) {
      return new UnmodifiableBiMap(bimap, (BiMap)null);
   }

   public static Map transformValues(Map fromMap, Function function) {
      return transformEntries(fromMap, asEntryTransformer(function));
   }

   public static SortedMap transformValues(SortedMap fromMap, Function function) {
      return transformEntries(fromMap, asEntryTransformer(function));
   }

   @GwtIncompatible
   public static NavigableMap transformValues(NavigableMap fromMap, Function function) {
      return transformEntries(fromMap, asEntryTransformer(function));
   }

   public static Map transformEntries(Map fromMap, EntryTransformer transformer) {
      return new TransformedEntriesMap(fromMap, transformer);
   }

   public static SortedMap transformEntries(SortedMap fromMap, EntryTransformer transformer) {
      return new TransformedEntriesSortedMap(fromMap, transformer);
   }

   @GwtIncompatible
   public static NavigableMap transformEntries(NavigableMap fromMap, EntryTransformer transformer) {
      return new TransformedEntriesNavigableMap(fromMap, transformer);
   }

   static EntryTransformer asEntryTransformer(final Function function) {
      Preconditions.checkNotNull(function);
      return new EntryTransformer() {
         public Object transformEntry(Object key, Object value) {
            return function.apply(value);
         }
      };
   }

   static Function asValueToValueFunction(final EntryTransformer transformer, final Object key) {
      Preconditions.checkNotNull(transformer);
      return new Function() {
         public Object apply(@Nullable Object v1) {
            return transformer.transformEntry(key, v1);
         }
      };
   }

   static Function asEntryToValueFunction(final EntryTransformer transformer) {
      Preconditions.checkNotNull(transformer);
      return new Function() {
         public Object apply(Map.Entry entry) {
            return transformer.transformEntry(entry.getKey(), entry.getValue());
         }
      };
   }

   static Map.Entry transformEntry(final EntryTransformer transformer, final Map.Entry entry) {
      Preconditions.checkNotNull(transformer);
      Preconditions.checkNotNull(entry);
      return new AbstractMapEntry() {
         public Object getKey() {
            return entry.getKey();
         }

         public Object getValue() {
            return transformer.transformEntry(entry.getKey(), entry.getValue());
         }
      };
   }

   static Function asEntryToEntryFunction(final EntryTransformer transformer) {
      Preconditions.checkNotNull(transformer);
      return new Function() {
         public Map.Entry apply(Map.Entry entry) {
            return Maps.transformEntry(transformer, entry);
         }
      };
   }

   static Predicate keyPredicateOnEntries(Predicate keyPredicate) {
      return Predicates.compose(keyPredicate, keyFunction());
   }

   static Predicate valuePredicateOnEntries(Predicate valuePredicate) {
      return Predicates.compose(valuePredicate, valueFunction());
   }

   public static Map filterKeys(Map unfiltered, Predicate keyPredicate) {
      Preconditions.checkNotNull(keyPredicate);
      Predicate entryPredicate = keyPredicateOnEntries(keyPredicate);
      return (Map)(unfiltered instanceof AbstractFilteredMap ? filterFiltered((AbstractFilteredMap)unfiltered, entryPredicate) : new FilteredKeyMap((Map)Preconditions.checkNotNull(unfiltered), keyPredicate, entryPredicate));
   }

   public static SortedMap filterKeys(SortedMap unfiltered, Predicate keyPredicate) {
      return filterEntries(unfiltered, keyPredicateOnEntries(keyPredicate));
   }

   @GwtIncompatible
   public static NavigableMap filterKeys(NavigableMap unfiltered, Predicate keyPredicate) {
      return filterEntries(unfiltered, keyPredicateOnEntries(keyPredicate));
   }

   public static BiMap filterKeys(BiMap unfiltered, Predicate keyPredicate) {
      Preconditions.checkNotNull(keyPredicate);
      return filterEntries(unfiltered, keyPredicateOnEntries(keyPredicate));
   }

   public static Map filterValues(Map unfiltered, Predicate valuePredicate) {
      return filterEntries(unfiltered, valuePredicateOnEntries(valuePredicate));
   }

   public static SortedMap filterValues(SortedMap unfiltered, Predicate valuePredicate) {
      return filterEntries(unfiltered, valuePredicateOnEntries(valuePredicate));
   }

   @GwtIncompatible
   public static NavigableMap filterValues(NavigableMap unfiltered, Predicate valuePredicate) {
      return filterEntries(unfiltered, valuePredicateOnEntries(valuePredicate));
   }

   public static BiMap filterValues(BiMap unfiltered, Predicate valuePredicate) {
      return filterEntries(unfiltered, valuePredicateOnEntries(valuePredicate));
   }

   public static Map filterEntries(Map unfiltered, Predicate entryPredicate) {
      Preconditions.checkNotNull(entryPredicate);
      return (Map)(unfiltered instanceof AbstractFilteredMap ? filterFiltered((AbstractFilteredMap)unfiltered, entryPredicate) : new FilteredEntryMap((Map)Preconditions.checkNotNull(unfiltered), entryPredicate));
   }

   public static SortedMap filterEntries(SortedMap unfiltered, Predicate entryPredicate) {
      Preconditions.checkNotNull(entryPredicate);
      return (SortedMap)(unfiltered instanceof FilteredEntrySortedMap ? filterFiltered((FilteredEntrySortedMap)unfiltered, entryPredicate) : new FilteredEntrySortedMap((SortedMap)Preconditions.checkNotNull(unfiltered), entryPredicate));
   }

   @GwtIncompatible
   public static NavigableMap filterEntries(NavigableMap unfiltered, Predicate entryPredicate) {
      Preconditions.checkNotNull(entryPredicate);
      return (NavigableMap)(unfiltered instanceof FilteredEntryNavigableMap ? filterFiltered((FilteredEntryNavigableMap)unfiltered, entryPredicate) : new FilteredEntryNavigableMap((NavigableMap)Preconditions.checkNotNull(unfiltered), entryPredicate));
   }

   public static BiMap filterEntries(BiMap unfiltered, Predicate entryPredicate) {
      Preconditions.checkNotNull(unfiltered);
      Preconditions.checkNotNull(entryPredicate);
      return (BiMap)(unfiltered instanceof FilteredEntryBiMap ? filterFiltered((FilteredEntryBiMap)unfiltered, entryPredicate) : new FilteredEntryBiMap(unfiltered, entryPredicate));
   }

   private static Map filterFiltered(AbstractFilteredMap map, Predicate entryPredicate) {
      return new FilteredEntryMap(map.unfiltered, Predicates.and(map.predicate, entryPredicate));
   }

   private static SortedMap filterFiltered(FilteredEntrySortedMap map, Predicate entryPredicate) {
      Predicate predicate = Predicates.and(map.predicate, entryPredicate);
      return new FilteredEntrySortedMap(map.sortedMap(), predicate);
   }

   @GwtIncompatible
   private static NavigableMap filterFiltered(FilteredEntryNavigableMap map, Predicate entryPredicate) {
      Predicate predicate = Predicates.and(map.entryPredicate, entryPredicate);
      return new FilteredEntryNavigableMap(map.unfiltered, predicate);
   }

   private static BiMap filterFiltered(FilteredEntryBiMap map, Predicate entryPredicate) {
      Predicate predicate = Predicates.and(map.predicate, entryPredicate);
      return new FilteredEntryBiMap(map.unfiltered(), predicate);
   }

   @GwtIncompatible
   public static NavigableMap unmodifiableNavigableMap(NavigableMap map) {
      Preconditions.checkNotNull(map);
      return (NavigableMap)(map instanceof UnmodifiableNavigableMap ? map : new UnmodifiableNavigableMap(map));
   }

   @Nullable
   private static Map.Entry unmodifiableOrNull(@Nullable Map.Entry entry) {
      return entry == null ? null : unmodifiableEntry(entry);
   }

   @GwtIncompatible
   public static NavigableMap synchronizedNavigableMap(NavigableMap navigableMap) {
      return Synchronized.navigableMap(navigableMap);
   }

   static Object safeGet(Map map, @Nullable Object key) {
      Preconditions.checkNotNull(map);

      try {
         return map.get(key);
      } catch (ClassCastException var3) {
         return null;
      } catch (NullPointerException var4) {
         return null;
      }
   }

   static boolean safeContainsKey(Map map, Object key) {
      Preconditions.checkNotNull(map);

      try {
         return map.containsKey(key);
      } catch (ClassCastException var3) {
         return false;
      } catch (NullPointerException var4) {
         return false;
      }
   }

   static Object safeRemove(Map map, Object key) {
      Preconditions.checkNotNull(map);

      try {
         return map.remove(key);
      } catch (ClassCastException var3) {
         return null;
      } catch (NullPointerException var4) {
         return null;
      }
   }

   static boolean containsKeyImpl(Map map, @Nullable Object key) {
      return Iterators.contains(keyIterator(map.entrySet().iterator()), key);
   }

   static boolean containsValueImpl(Map map, @Nullable Object value) {
      return Iterators.contains(valueIterator(map.entrySet().iterator()), value);
   }

   static boolean containsEntryImpl(Collection c, Object o) {
      return !(o instanceof Map.Entry) ? false : c.contains(unmodifiableEntry((Map.Entry)o));
   }

   static boolean removeEntryImpl(Collection c, Object o) {
      return !(o instanceof Map.Entry) ? false : c.remove(unmodifiableEntry((Map.Entry)o));
   }

   static boolean equalsImpl(Map map, Object object) {
      if (map == object) {
         return true;
      } else if (object instanceof Map) {
         Map o = (Map)object;
         return map.entrySet().equals(o.entrySet());
      } else {
         return false;
      }
   }

   static String toStringImpl(Map map) {
      StringBuilder sb = Collections2.newStringBuilderForCollection(map.size()).append('{');
      boolean first = true;
      Iterator var3 = map.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         if (!first) {
            sb.append(", ");
         }

         first = false;
         sb.append(entry.getKey()).append('=').append(entry.getValue());
      }

      return sb.append('}').toString();
   }

   static void putAllImpl(Map self, Map map) {
      Iterator var2 = map.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         self.put(entry.getKey(), entry.getValue());
      }

   }

   @Nullable
   static Object keyOrNull(@Nullable Map.Entry entry) {
      return entry == null ? null : entry.getKey();
   }

   @Nullable
   static Object valueOrNull(@Nullable Map.Entry entry) {
      return entry == null ? null : entry.getValue();
   }

   static ImmutableMap indexMap(Collection list) {
      ImmutableMap.Builder builder = new ImmutableMap.Builder(list.size());
      int i = 0;
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         Object e = var3.next();
         builder.put(e, i++);
      }

      return builder.build();
   }

   @Beta
   @GwtIncompatible
   public static NavigableMap subMap(NavigableMap map, Range range) {
      if (map.comparator() != null && map.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
         Preconditions.checkArgument(map.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "map is using a custom comparator which is inconsistent with the natural ordering.");
      }

      if (range.hasLowerBound() && range.hasUpperBound()) {
         return map.subMap(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED, range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED);
      } else if (range.hasLowerBound()) {
         return map.tailMap(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED);
      } else {
         return range.hasUpperBound() ? map.headMap(range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED) : (NavigableMap)Preconditions.checkNotNull(map);
      }
   }

   @GwtIncompatible
   abstract static class DescendingMap extends ForwardingMap implements NavigableMap {
      private transient Comparator comparator;
      private transient Set entrySet;
      private transient NavigableSet navigableKeySet;

      abstract NavigableMap forward();

      protected final Map delegate() {
         return this.forward();
      }

      public Comparator comparator() {
         Comparator result = this.comparator;
         if (result == null) {
            Comparator forwardCmp = this.forward().comparator();
            if (forwardCmp == null) {
               forwardCmp = Ordering.natural();
            }

            result = this.comparator = reverse((Comparator)forwardCmp);
         }

         return result;
      }

      private static Ordering reverse(Comparator forward) {
         return Ordering.from(forward).reverse();
      }

      public Object firstKey() {
         return this.forward().lastKey();
      }

      public Object lastKey() {
         return this.forward().firstKey();
      }

      public Map.Entry lowerEntry(Object key) {
         return this.forward().higherEntry(key);
      }

      public Object lowerKey(Object key) {
         return this.forward().higherKey(key);
      }

      public Map.Entry floorEntry(Object key) {
         return this.forward().ceilingEntry(key);
      }

      public Object floorKey(Object key) {
         return this.forward().ceilingKey(key);
      }

      public Map.Entry ceilingEntry(Object key) {
         return this.forward().floorEntry(key);
      }

      public Object ceilingKey(Object key) {
         return this.forward().floorKey(key);
      }

      public Map.Entry higherEntry(Object key) {
         return this.forward().lowerEntry(key);
      }

      public Object higherKey(Object key) {
         return this.forward().lowerKey(key);
      }

      public Map.Entry firstEntry() {
         return this.forward().lastEntry();
      }

      public Map.Entry lastEntry() {
         return this.forward().firstEntry();
      }

      public Map.Entry pollFirstEntry() {
         return this.forward().pollLastEntry();
      }

      public Map.Entry pollLastEntry() {
         return this.forward().pollFirstEntry();
      }

      public NavigableMap descendingMap() {
         return this.forward();
      }

      public Set entrySet() {
         Set result = this.entrySet;
         return result == null ? (this.entrySet = this.createEntrySet()) : result;
      }

      abstract Iterator entryIterator();

      Set createEntrySet() {
         class EntrySetImpl extends EntrySet {
            Map map() {
               return DescendingMap.this;
            }

            public Iterator iterator() {
               return DescendingMap.this.entryIterator();
            }
         }

         return new EntrySetImpl();
      }

      public Set keySet() {
         return this.navigableKeySet();
      }

      public NavigableSet navigableKeySet() {
         NavigableSet result = this.navigableKeySet;
         return result == null ? (this.navigableKeySet = new NavigableKeySet(this)) : result;
      }

      public NavigableSet descendingKeySet() {
         return this.forward().navigableKeySet();
      }

      public NavigableMap subMap(Object fromKey, boolean fromInclusive, Object toKey, boolean toInclusive) {
         return this.forward().subMap(toKey, toInclusive, fromKey, fromInclusive).descendingMap();
      }

      public NavigableMap headMap(Object toKey, boolean inclusive) {
         return this.forward().tailMap(toKey, inclusive).descendingMap();
      }

      public NavigableMap tailMap(Object fromKey, boolean inclusive) {
         return this.forward().headMap(fromKey, inclusive).descendingMap();
      }

      public SortedMap subMap(Object fromKey, Object toKey) {
         return this.subMap(fromKey, true, toKey, false);
      }

      public SortedMap headMap(Object toKey) {
         return this.headMap(toKey, false);
      }

      public SortedMap tailMap(Object fromKey) {
         return this.tailMap(fromKey, true);
      }

      public Collection values() {
         return new Values(this);
      }

      public String toString() {
         return this.standardToString();
      }
   }

   abstract static class EntrySet extends Sets.ImprovedAbstractSet {
      abstract Map map();

      public int size() {
         return this.map().size();
      }

      public void clear() {
         this.map().clear();
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry entry = (Map.Entry)o;
            Object key = entry.getKey();
            Object value = Maps.safeGet(this.map(), key);
            return Objects.equal(value, entry.getValue()) && (value != null || this.map().containsKey(key));
         }
      }

      public boolean isEmpty() {
         return this.map().isEmpty();
      }

      public boolean remove(Object o) {
         if (this.contains(o)) {
            Map.Entry entry = (Map.Entry)o;
            return this.map().keySet().remove(entry.getKey());
         } else {
            return false;
         }
      }

      public boolean removeAll(Collection c) {
         try {
            return super.removeAll((Collection)Preconditions.checkNotNull(c));
         } catch (UnsupportedOperationException var3) {
            return Sets.removeAllImpl(this, (Iterator)c.iterator());
         }
      }

      public boolean retainAll(Collection c) {
         try {
            return super.retainAll((Collection)Preconditions.checkNotNull(c));
         } catch (UnsupportedOperationException var7) {
            Set keys = Sets.newHashSetWithExpectedSize(c.size());
            Iterator var4 = c.iterator();

            while(var4.hasNext()) {
               Object o = var4.next();
               if (this.contains(o)) {
                  Map.Entry entry = (Map.Entry)o;
                  keys.add(entry.getKey());
               }
            }

            return this.map().keySet().retainAll(keys);
         }
      }
   }

   static class Values extends AbstractCollection {
      @Weak
      final Map map;

      Values(Map map) {
         this.map = (Map)Preconditions.checkNotNull(map);
      }

      final Map map() {
         return this.map;
      }

      public Iterator iterator() {
         return Maps.valueIterator(this.map().entrySet().iterator());
      }

      public boolean remove(Object o) {
         try {
            return super.remove(o);
         } catch (UnsupportedOperationException var5) {
            Iterator var3 = this.map().entrySet().iterator();

            Map.Entry entry;
            do {
               if (!var3.hasNext()) {
                  return false;
               }

               entry = (Map.Entry)var3.next();
            } while(!Objects.equal(o, entry.getValue()));

            this.map().remove(entry.getKey());
            return true;
         }
      }

      public boolean removeAll(Collection c) {
         try {
            return super.removeAll((Collection)Preconditions.checkNotNull(c));
         } catch (UnsupportedOperationException var6) {
            Set toRemove = Sets.newHashSet();
            Iterator var4 = this.map().entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry entry = (Map.Entry)var4.next();
               if (c.contains(entry.getValue())) {
                  toRemove.add(entry.getKey());
               }
            }

            return this.map().keySet().removeAll(toRemove);
         }
      }

      public boolean retainAll(Collection c) {
         try {
            return super.retainAll((Collection)Preconditions.checkNotNull(c));
         } catch (UnsupportedOperationException var6) {
            Set toRetain = Sets.newHashSet();
            Iterator var4 = this.map().entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry entry = (Map.Entry)var4.next();
               if (c.contains(entry.getValue())) {
                  toRetain.add(entry.getKey());
               }
            }

            return this.map().keySet().retainAll(toRetain);
         }
      }

      public int size() {
         return this.map().size();
      }

      public boolean isEmpty() {
         return this.map().isEmpty();
      }

      public boolean contains(@Nullable Object o) {
         return this.map().containsValue(o);
      }

      public void clear() {
         this.map().clear();
      }
   }

   @GwtIncompatible
   static class NavigableKeySet extends SortedKeySet implements NavigableSet {
      NavigableKeySet(NavigableMap map) {
         super(map);
      }

      NavigableMap map() {
         return (NavigableMap)this.map;
      }

      public Object lower(Object e) {
         return this.map().lowerKey(e);
      }

      public Object floor(Object e) {
         return this.map().floorKey(e);
      }

      public Object ceiling(Object e) {
         return this.map().ceilingKey(e);
      }

      public Object higher(Object e) {
         return this.map().higherKey(e);
      }

      public Object pollFirst() {
         return Maps.keyOrNull(this.map().pollFirstEntry());
      }

      public Object pollLast() {
         return Maps.keyOrNull(this.map().pollLastEntry());
      }

      public NavigableSet descendingSet() {
         return this.map().descendingKeySet();
      }

      public Iterator descendingIterator() {
         return this.descendingSet().iterator();
      }

      public NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
         return this.map().subMap(fromElement, fromInclusive, toElement, toInclusive).navigableKeySet();
      }

      public NavigableSet headSet(Object toElement, boolean inclusive) {
         return this.map().headMap(toElement, inclusive).navigableKeySet();
      }

      public NavigableSet tailSet(Object fromElement, boolean inclusive) {
         return this.map().tailMap(fromElement, inclusive).navigableKeySet();
      }

      public SortedSet subSet(Object fromElement, Object toElement) {
         return this.subSet(fromElement, true, toElement, false);
      }

      public SortedSet headSet(Object toElement) {
         return this.headSet(toElement, false);
      }

      public SortedSet tailSet(Object fromElement) {
         return this.tailSet(fromElement, true);
      }
   }

   static class SortedKeySet extends KeySet implements SortedSet {
      SortedKeySet(SortedMap map) {
         super(map);
      }

      SortedMap map() {
         return (SortedMap)super.map();
      }

      public Comparator comparator() {
         return this.map().comparator();
      }

      public SortedSet subSet(Object fromElement, Object toElement) {
         return new SortedKeySet(this.map().subMap(fromElement, toElement));
      }

      public SortedSet headSet(Object toElement) {
         return new SortedKeySet(this.map().headMap(toElement));
      }

      public SortedSet tailSet(Object fromElement) {
         return new SortedKeySet(this.map().tailMap(fromElement));
      }

      public Object first() {
         return this.map().firstKey();
      }

      public Object last() {
         return this.map().lastKey();
      }
   }

   static class KeySet extends Sets.ImprovedAbstractSet {
      @Weak
      final Map map;

      KeySet(Map map) {
         this.map = (Map)Preconditions.checkNotNull(map);
      }

      Map map() {
         return this.map;
      }

      public Iterator iterator() {
         return Maps.keyIterator(this.map().entrySet().iterator());
      }

      public int size() {
         return this.map().size();
      }

      public boolean isEmpty() {
         return this.map().isEmpty();
      }

      public boolean contains(Object o) {
         return this.map().containsKey(o);
      }

      public boolean remove(Object o) {
         if (this.contains(o)) {
            this.map().remove(o);
            return true;
         } else {
            return false;
         }
      }

      public void clear() {
         this.map().clear();
      }
   }

   abstract static class IteratorBasedAbstractMap extends AbstractMap {
      public abstract int size();

      abstract Iterator entryIterator();

      public Set entrySet() {
         return new EntrySet() {
            Map map() {
               return IteratorBasedAbstractMap.this;
            }

            public Iterator iterator() {
               return IteratorBasedAbstractMap.this.entryIterator();
            }
         };
      }

      public void clear() {
         Iterators.clear(this.entryIterator());
      }
   }

   @GwtCompatible
   abstract static class ViewCachingAbstractMap extends AbstractMap {
      private transient Set entrySet;
      private transient Set keySet;
      private transient Collection values;

      abstract Set createEntrySet();

      public Set entrySet() {
         Set result = this.entrySet;
         return result == null ? (this.entrySet = this.createEntrySet()) : result;
      }

      public Set keySet() {
         Set result = this.keySet;
         return result == null ? (this.keySet = this.createKeySet()) : result;
      }

      Set createKeySet() {
         return new KeySet(this);
      }

      public Collection values() {
         Collection result = this.values;
         return result == null ? (this.values = this.createValues()) : result;
      }

      Collection createValues() {
         return new Values(this);
      }
   }

   @GwtIncompatible
   static class UnmodifiableNavigableMap extends ForwardingSortedMap implements NavigableMap, Serializable {
      private final NavigableMap delegate;
      private transient UnmodifiableNavigableMap descendingMap;

      UnmodifiableNavigableMap(NavigableMap delegate) {
         this.delegate = delegate;
      }

      UnmodifiableNavigableMap(NavigableMap delegate, UnmodifiableNavigableMap descendingMap) {
         this.delegate = delegate;
         this.descendingMap = descendingMap;
      }

      protected SortedMap delegate() {
         return Collections.unmodifiableSortedMap(this.delegate);
      }

      public Map.Entry lowerEntry(Object key) {
         return Maps.unmodifiableOrNull(this.delegate.lowerEntry(key));
      }

      public Object lowerKey(Object key) {
         return this.delegate.lowerKey(key);
      }

      public Map.Entry floorEntry(Object key) {
         return Maps.unmodifiableOrNull(this.delegate.floorEntry(key));
      }

      public Object floorKey(Object key) {
         return this.delegate.floorKey(key);
      }

      public Map.Entry ceilingEntry(Object key) {
         return Maps.unmodifiableOrNull(this.delegate.ceilingEntry(key));
      }

      public Object ceilingKey(Object key) {
         return this.delegate.ceilingKey(key);
      }

      public Map.Entry higherEntry(Object key) {
         return Maps.unmodifiableOrNull(this.delegate.higherEntry(key));
      }

      public Object higherKey(Object key) {
         return this.delegate.higherKey(key);
      }

      public Map.Entry firstEntry() {
         return Maps.unmodifiableOrNull(this.delegate.firstEntry());
      }

      public Map.Entry lastEntry() {
         return Maps.unmodifiableOrNull(this.delegate.lastEntry());
      }

      public final Map.Entry pollFirstEntry() {
         throw new UnsupportedOperationException();
      }

      public final Map.Entry pollLastEntry() {
         throw new UnsupportedOperationException();
      }

      public NavigableMap descendingMap() {
         UnmodifiableNavigableMap result = this.descendingMap;
         return result == null ? (this.descendingMap = new UnmodifiableNavigableMap(this.delegate.descendingMap(), this)) : result;
      }

      public Set keySet() {
         return this.navigableKeySet();
      }

      public NavigableSet navigableKeySet() {
         return Sets.unmodifiableNavigableSet(this.delegate.navigableKeySet());
      }

      public NavigableSet descendingKeySet() {
         return Sets.unmodifiableNavigableSet(this.delegate.descendingKeySet());
      }

      public SortedMap subMap(Object fromKey, Object toKey) {
         return this.subMap(fromKey, true, toKey, false);
      }

      public SortedMap headMap(Object toKey) {
         return this.headMap(toKey, false);
      }

      public SortedMap tailMap(Object fromKey) {
         return this.tailMap(fromKey, true);
      }

      public NavigableMap subMap(Object fromKey, boolean fromInclusive, Object toKey, boolean toInclusive) {
         return Maps.unmodifiableNavigableMap(this.delegate.subMap(fromKey, fromInclusive, toKey, toInclusive));
      }

      public NavigableMap headMap(Object toKey, boolean inclusive) {
         return Maps.unmodifiableNavigableMap(this.delegate.headMap(toKey, inclusive));
      }

      public NavigableMap tailMap(Object fromKey, boolean inclusive) {
         return Maps.unmodifiableNavigableMap(this.delegate.tailMap(fromKey, inclusive));
      }
   }

   static final class FilteredEntryBiMap extends FilteredEntryMap implements BiMap {
      @RetainedWith
      private final BiMap inverse;

      private static Predicate inversePredicate(final Predicate forwardPredicate) {
         return new Predicate() {
            public boolean apply(Map.Entry input) {
               return forwardPredicate.apply(Maps.immutableEntry(input.getValue(), input.getKey()));
            }
         };
      }

      FilteredEntryBiMap(BiMap delegate, Predicate predicate) {
         super(delegate, predicate);
         this.inverse = new FilteredEntryBiMap(delegate.inverse(), inversePredicate(predicate), this);
      }

      private FilteredEntryBiMap(BiMap delegate, Predicate predicate, BiMap inverse) {
         super(delegate, predicate);
         this.inverse = inverse;
      }

      BiMap unfiltered() {
         return (BiMap)this.unfiltered;
      }

      public Object forcePut(@Nullable Object key, @Nullable Object value) {
         Preconditions.checkArgument(this.apply(key, value));
         return this.unfiltered().forcePut(key, value);
      }

      public BiMap inverse() {
         return this.inverse;
      }

      public Set values() {
         return this.inverse.keySet();
      }
   }

   @GwtIncompatible
   private static class FilteredEntryNavigableMap extends AbstractNavigableMap {
      private final NavigableMap unfiltered;
      private final Predicate entryPredicate;
      private final Map filteredDelegate;

      FilteredEntryNavigableMap(NavigableMap unfiltered, Predicate entryPredicate) {
         this.unfiltered = (NavigableMap)Preconditions.checkNotNull(unfiltered);
         this.entryPredicate = entryPredicate;
         this.filteredDelegate = new FilteredEntryMap(unfiltered, entryPredicate);
      }

      public Comparator comparator() {
         return this.unfiltered.comparator();
      }

      public NavigableSet navigableKeySet() {
         return new NavigableKeySet(this) {
            public boolean removeAll(Collection c) {
               return Iterators.removeIf(FilteredEntryNavigableMap.this.unfiltered.entrySet().iterator(), Predicates.and(FilteredEntryNavigableMap.this.entryPredicate, Maps.keyPredicateOnEntries(Predicates.in(c))));
            }

            public boolean retainAll(Collection c) {
               return Iterators.removeIf(FilteredEntryNavigableMap.this.unfiltered.entrySet().iterator(), Predicates.and(FilteredEntryNavigableMap.this.entryPredicate, Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(c)))));
            }
         };
      }

      public Collection values() {
         return new FilteredMapValues(this, this.unfiltered, this.entryPredicate);
      }

      Iterator entryIterator() {
         return Iterators.filter(this.unfiltered.entrySet().iterator(), this.entryPredicate);
      }

      Iterator descendingEntryIterator() {
         return Iterators.filter(this.unfiltered.descendingMap().entrySet().iterator(), this.entryPredicate);
      }

      public int size() {
         return this.filteredDelegate.size();
      }

      public boolean isEmpty() {
         return !Iterables.any(this.unfiltered.entrySet(), this.entryPredicate);
      }

      @Nullable
      public Object get(@Nullable Object key) {
         return this.filteredDelegate.get(key);
      }

      public boolean containsKey(@Nullable Object key) {
         return this.filteredDelegate.containsKey(key);
      }

      public Object put(Object key, Object value) {
         return this.filteredDelegate.put(key, value);
      }

      public Object remove(@Nullable Object key) {
         return this.filteredDelegate.remove(key);
      }

      public void putAll(Map m) {
         this.filteredDelegate.putAll(m);
      }

      public void clear() {
         this.filteredDelegate.clear();
      }

      public Set entrySet() {
         return this.filteredDelegate.entrySet();
      }

      public Map.Entry pollFirstEntry() {
         return (Map.Entry)Iterables.removeFirstMatching(this.unfiltered.entrySet(), this.entryPredicate);
      }

      public Map.Entry pollLastEntry() {
         return (Map.Entry)Iterables.removeFirstMatching(this.unfiltered.descendingMap().entrySet(), this.entryPredicate);
      }

      public NavigableMap descendingMap() {
         return Maps.filterEntries(this.unfiltered.descendingMap(), this.entryPredicate);
      }

      public NavigableMap subMap(Object fromKey, boolean fromInclusive, Object toKey, boolean toInclusive) {
         return Maps.filterEntries(this.unfiltered.subMap(fromKey, fromInclusive, toKey, toInclusive), this.entryPredicate);
      }

      public NavigableMap headMap(Object toKey, boolean inclusive) {
         return Maps.filterEntries(this.unfiltered.headMap(toKey, inclusive), this.entryPredicate);
      }

      public NavigableMap tailMap(Object fromKey, boolean inclusive) {
         return Maps.filterEntries(this.unfiltered.tailMap(fromKey, inclusive), this.entryPredicate);
      }
   }

   private static class FilteredEntrySortedMap extends FilteredEntryMap implements SortedMap {
      FilteredEntrySortedMap(SortedMap unfiltered, Predicate entryPredicate) {
         super(unfiltered, entryPredicate);
      }

      SortedMap sortedMap() {
         return (SortedMap)this.unfiltered;
      }

      public SortedSet keySet() {
         return (SortedSet)super.keySet();
      }

      SortedSet createKeySet() {
         return new SortedKeySet();
      }

      public Comparator comparator() {
         return this.sortedMap().comparator();
      }

      public Object firstKey() {
         return this.keySet().iterator().next();
      }

      public Object lastKey() {
         SortedMap headMap = this.sortedMap();

         while(true) {
            Object key = headMap.lastKey();
            if (this.apply(key, this.unfiltered.get(key))) {
               return key;
            }

            headMap = this.sortedMap().headMap(key);
         }
      }

      public SortedMap headMap(Object toKey) {
         return new FilteredEntrySortedMap(this.sortedMap().headMap(toKey), this.predicate);
      }

      public SortedMap subMap(Object fromKey, Object toKey) {
         return new FilteredEntrySortedMap(this.sortedMap().subMap(fromKey, toKey), this.predicate);
      }

      public SortedMap tailMap(Object fromKey) {
         return new FilteredEntrySortedMap(this.sortedMap().tailMap(fromKey), this.predicate);
      }

      class SortedKeySet extends FilteredEntryMap.KeySet implements SortedSet {
         SortedKeySet() {
            super();
         }

         public Comparator comparator() {
            return FilteredEntrySortedMap.this.sortedMap().comparator();
         }

         public SortedSet subSet(Object fromElement, Object toElement) {
            return (SortedSet)FilteredEntrySortedMap.this.subMap(fromElement, toElement).keySet();
         }

         public SortedSet headSet(Object toElement) {
            return (SortedSet)FilteredEntrySortedMap.this.headMap(toElement).keySet();
         }

         public SortedSet tailSet(Object fromElement) {
            return (SortedSet)FilteredEntrySortedMap.this.tailMap(fromElement).keySet();
         }

         public Object first() {
            return FilteredEntrySortedMap.this.firstKey();
         }

         public Object last() {
            return FilteredEntrySortedMap.this.lastKey();
         }
      }
   }

   static class FilteredEntryMap extends AbstractFilteredMap {
      final Set filteredEntrySet;

      FilteredEntryMap(Map unfiltered, Predicate entryPredicate) {
         super(unfiltered, entryPredicate);
         this.filteredEntrySet = Sets.filter(unfiltered.entrySet(), this.predicate);
      }

      protected Set createEntrySet() {
         return new EntrySet();
      }

      Set createKeySet() {
         return new KeySet();
      }

      class KeySet extends KeySet {
         KeySet() {
            super(FilteredEntryMap.this);
         }

         public boolean remove(Object o) {
            if (FilteredEntryMap.this.containsKey(o)) {
               FilteredEntryMap.this.unfiltered.remove(o);
               return true;
            } else {
               return false;
            }
         }

         private boolean removeIf(Predicate keyPredicate) {
            return Iterables.removeIf(FilteredEntryMap.this.unfiltered.entrySet(), Predicates.and(FilteredEntryMap.this.predicate, Maps.keyPredicateOnEntries(keyPredicate)));
         }

         public boolean removeAll(Collection c) {
            return this.removeIf(Predicates.in(c));
         }

         public boolean retainAll(Collection c) {
            return this.removeIf(Predicates.not(Predicates.in(c)));
         }

         public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
         }

         public Object[] toArray(Object[] array) {
            return Lists.newArrayList(this.iterator()).toArray(array);
         }
      }

      private class EntrySet extends ForwardingSet {
         private EntrySet() {
         }

         protected Set delegate() {
            return FilteredEntryMap.this.filteredEntrySet;
         }

         public Iterator iterator() {
            return new TransformedIterator(FilteredEntryMap.this.filteredEntrySet.iterator()) {
               Map.Entry transform(final Map.Entry entry) {
                  return new ForwardingMapEntry() {
                     protected Map.Entry delegate() {
                        return entry;
                     }

                     public Object setValue(Object newValue) {
                        Preconditions.checkArgument(FilteredEntryMap.this.apply(this.getKey(), newValue));
                        return super.setValue(newValue);
                     }
                  };
               }
            };
         }

         // $FF: synthetic method
         EntrySet(Object x1) {
            this();
         }
      }
   }

   private static class FilteredKeyMap extends AbstractFilteredMap {
      final Predicate keyPredicate;

      FilteredKeyMap(Map unfiltered, Predicate keyPredicate, Predicate entryPredicate) {
         super(unfiltered, entryPredicate);
         this.keyPredicate = keyPredicate;
      }

      protected Set createEntrySet() {
         return Sets.filter(this.unfiltered.entrySet(), this.predicate);
      }

      Set createKeySet() {
         return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
      }

      public boolean containsKey(Object key) {
         return this.unfiltered.containsKey(key) && this.keyPredicate.apply(key);
      }
   }

   private static final class FilteredMapValues extends Values {
      final Map unfiltered;
      final Predicate predicate;

      FilteredMapValues(Map filteredMap, Map unfiltered, Predicate predicate) {
         super(filteredMap);
         this.unfiltered = unfiltered;
         this.predicate = predicate;
      }

      public boolean remove(Object o) {
         return Iterables.removeFirstMatching(this.unfiltered.entrySet(), Predicates.and(this.predicate, Maps.valuePredicateOnEntries(Predicates.equalTo(o)))) != null;
      }

      private boolean removeIf(Predicate valuePredicate) {
         return Iterables.removeIf(this.unfiltered.entrySet(), Predicates.and(this.predicate, Maps.valuePredicateOnEntries(valuePredicate)));
      }

      public boolean removeAll(Collection collection) {
         return this.removeIf(Predicates.in(collection));
      }

      public boolean retainAll(Collection collection) {
         return this.removeIf(Predicates.not(Predicates.in(collection)));
      }

      public Object[] toArray() {
         return Lists.newArrayList(this.iterator()).toArray();
      }

      public Object[] toArray(Object[] array) {
         return Lists.newArrayList(this.iterator()).toArray(array);
      }
   }

   private abstract static class AbstractFilteredMap extends ViewCachingAbstractMap {
      final Map unfiltered;
      final Predicate predicate;

      AbstractFilteredMap(Map unfiltered, Predicate predicate) {
         this.unfiltered = unfiltered;
         this.predicate = predicate;
      }

      boolean apply(@Nullable Object key, @Nullable Object value) {
         return this.predicate.apply(Maps.immutableEntry(key, value));
      }

      public Object put(Object key, Object value) {
         Preconditions.checkArgument(this.apply(key, value));
         return this.unfiltered.put(key, value);
      }

      public void putAll(Map map) {
         Iterator var2 = map.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            Preconditions.checkArgument(this.apply(entry.getKey(), entry.getValue()));
         }

         this.unfiltered.putAll(map);
      }

      public boolean containsKey(Object key) {
         return this.unfiltered.containsKey(key) && this.apply(key, this.unfiltered.get(key));
      }

      public Object get(Object key) {
         Object value = this.unfiltered.get(key);
         return value != null && this.apply(key, value) ? value : null;
      }

      public boolean isEmpty() {
         return this.entrySet().isEmpty();
      }

      public Object remove(Object key) {
         return this.containsKey(key) ? this.unfiltered.remove(key) : null;
      }

      Collection createValues() {
         return new FilteredMapValues(this, this.unfiltered, this.predicate);
      }
   }

   @GwtIncompatible
   private static class TransformedEntriesNavigableMap extends TransformedEntriesSortedMap implements NavigableMap {
      TransformedEntriesNavigableMap(NavigableMap fromMap, EntryTransformer transformer) {
         super(fromMap, transformer);
      }

      public Map.Entry ceilingEntry(Object key) {
         return this.transformEntry(this.fromMap().ceilingEntry(key));
      }

      public Object ceilingKey(Object key) {
         return this.fromMap().ceilingKey(key);
      }

      public NavigableSet descendingKeySet() {
         return this.fromMap().descendingKeySet();
      }

      public NavigableMap descendingMap() {
         return Maps.transformEntries(this.fromMap().descendingMap(), this.transformer);
      }

      public Map.Entry firstEntry() {
         return this.transformEntry(this.fromMap().firstEntry());
      }

      public Map.Entry floorEntry(Object key) {
         return this.transformEntry(this.fromMap().floorEntry(key));
      }

      public Object floorKey(Object key) {
         return this.fromMap().floorKey(key);
      }

      public NavigableMap headMap(Object toKey) {
         return this.headMap(toKey, false);
      }

      public NavigableMap headMap(Object toKey, boolean inclusive) {
         return Maps.transformEntries(this.fromMap().headMap(toKey, inclusive), this.transformer);
      }

      public Map.Entry higherEntry(Object key) {
         return this.transformEntry(this.fromMap().higherEntry(key));
      }

      public Object higherKey(Object key) {
         return this.fromMap().higherKey(key);
      }

      public Map.Entry lastEntry() {
         return this.transformEntry(this.fromMap().lastEntry());
      }

      public Map.Entry lowerEntry(Object key) {
         return this.transformEntry(this.fromMap().lowerEntry(key));
      }

      public Object lowerKey(Object key) {
         return this.fromMap().lowerKey(key);
      }

      public NavigableSet navigableKeySet() {
         return this.fromMap().navigableKeySet();
      }

      public Map.Entry pollFirstEntry() {
         return this.transformEntry(this.fromMap().pollFirstEntry());
      }

      public Map.Entry pollLastEntry() {
         return this.transformEntry(this.fromMap().pollLastEntry());
      }

      public NavigableMap subMap(Object fromKey, boolean fromInclusive, Object toKey, boolean toInclusive) {
         return Maps.transformEntries(this.fromMap().subMap(fromKey, fromInclusive, toKey, toInclusive), this.transformer);
      }

      public NavigableMap subMap(Object fromKey, Object toKey) {
         return this.subMap(fromKey, true, toKey, false);
      }

      public NavigableMap tailMap(Object fromKey) {
         return this.tailMap(fromKey, true);
      }

      public NavigableMap tailMap(Object fromKey, boolean inclusive) {
         return Maps.transformEntries(this.fromMap().tailMap(fromKey, inclusive), this.transformer);
      }

      @Nullable
      private Map.Entry transformEntry(@Nullable Map.Entry entry) {
         return entry == null ? null : Maps.transformEntry(this.transformer, entry);
      }

      protected NavigableMap fromMap() {
         return (NavigableMap)super.fromMap();
      }
   }

   static class TransformedEntriesSortedMap extends TransformedEntriesMap implements SortedMap {
      protected SortedMap fromMap() {
         return (SortedMap)this.fromMap;
      }

      TransformedEntriesSortedMap(SortedMap fromMap, EntryTransformer transformer) {
         super(fromMap, transformer);
      }

      public Comparator comparator() {
         return this.fromMap().comparator();
      }

      public Object firstKey() {
         return this.fromMap().firstKey();
      }

      public SortedMap headMap(Object toKey) {
         return Maps.transformEntries(this.fromMap().headMap(toKey), this.transformer);
      }

      public Object lastKey() {
         return this.fromMap().lastKey();
      }

      public SortedMap subMap(Object fromKey, Object toKey) {
         return Maps.transformEntries(this.fromMap().subMap(fromKey, toKey), this.transformer);
      }

      public SortedMap tailMap(Object fromKey) {
         return Maps.transformEntries(this.fromMap().tailMap(fromKey), this.transformer);
      }
   }

   static class TransformedEntriesMap extends IteratorBasedAbstractMap {
      final Map fromMap;
      final EntryTransformer transformer;

      TransformedEntriesMap(Map fromMap, EntryTransformer transformer) {
         this.fromMap = (Map)Preconditions.checkNotNull(fromMap);
         this.transformer = (EntryTransformer)Preconditions.checkNotNull(transformer);
      }

      public int size() {
         return this.fromMap.size();
      }

      public boolean containsKey(Object key) {
         return this.fromMap.containsKey(key);
      }

      public Object get(Object key) {
         Object value = this.fromMap.get(key);
         return value == null && !this.fromMap.containsKey(key) ? null : this.transformer.transformEntry(key, value);
      }

      public Object remove(Object key) {
         return this.fromMap.containsKey(key) ? this.transformer.transformEntry(key, this.fromMap.remove(key)) : null;
      }

      public void clear() {
         this.fromMap.clear();
      }

      public Set keySet() {
         return this.fromMap.keySet();
      }

      Iterator entryIterator() {
         return Iterators.transform(this.fromMap.entrySet().iterator(), Maps.asEntryToEntryFunction(this.transformer));
      }

      public Collection values() {
         return new Values(this);
      }
   }

   public interface EntryTransformer {
      Object transformEntry(@Nullable Object var1, @Nullable Object var2);
   }

   private static class UnmodifiableBiMap extends ForwardingMap implements BiMap, Serializable {
      final Map unmodifiableMap;
      final BiMap delegate;
      @RetainedWith
      BiMap inverse;
      transient Set values;
      private static final long serialVersionUID = 0L;

      UnmodifiableBiMap(BiMap delegate, @Nullable BiMap inverse) {
         this.unmodifiableMap = Collections.unmodifiableMap(delegate);
         this.delegate = delegate;
         this.inverse = inverse;
      }

      protected Map delegate() {
         return this.unmodifiableMap;
      }

      public Object forcePut(Object key, Object value) {
         throw new UnsupportedOperationException();
      }

      public BiMap inverse() {
         BiMap result = this.inverse;
         return result == null ? (this.inverse = new UnmodifiableBiMap(this.delegate.inverse(), this)) : result;
      }

      public Set values() {
         Set result = this.values;
         return result == null ? (this.values = Collections.unmodifiableSet(this.delegate.values())) : result;
      }
   }

   private static final class BiMapConverter extends Converter implements Serializable {
      private final BiMap bimap;
      private static final long serialVersionUID = 0L;

      BiMapConverter(BiMap bimap) {
         this.bimap = (BiMap)Preconditions.checkNotNull(bimap);
      }

      protected Object doForward(Object a) {
         return convert(this.bimap, a);
      }

      protected Object doBackward(Object b) {
         return convert(this.bimap.inverse(), b);
      }

      private static Object convert(BiMap bimap, Object input) {
         Object output = bimap.get(input);
         Preconditions.checkArgument(output != null, "No non-null mapping present for input: %s", input);
         return output;
      }

      public boolean equals(@Nullable Object object) {
         if (object instanceof BiMapConverter) {
            BiMapConverter that = (BiMapConverter)object;
            return this.bimap.equals(that.bimap);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.bimap.hashCode();
      }

      public String toString() {
         return "Maps.asConverter(" + this.bimap + ")";
      }
   }

   static class UnmodifiableEntrySet extends UnmodifiableEntries implements Set {
      UnmodifiableEntrySet(Set entries) {
         super(entries);
      }

      public boolean equals(@Nullable Object object) {
         return Sets.equalsImpl(this, object);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   static class UnmodifiableEntries extends ForwardingCollection {
      private final Collection entries;

      UnmodifiableEntries(Collection entries) {
         this.entries = entries;
      }

      protected Collection delegate() {
         return this.entries;
      }

      public Iterator iterator() {
         return Maps.unmodifiableEntryIterator(this.entries.iterator());
      }

      public Object[] toArray() {
         return this.standardToArray();
      }

      public Object[] toArray(Object[] array) {
         return this.standardToArray(array);
      }
   }

   @GwtIncompatible
   private static final class NavigableAsMapView extends AbstractNavigableMap {
      private final NavigableSet set;
      private final Function function;

      NavigableAsMapView(NavigableSet ks, Function vFunction) {
         this.set = (NavigableSet)Preconditions.checkNotNull(ks);
         this.function = (Function)Preconditions.checkNotNull(vFunction);
      }

      public NavigableMap subMap(Object fromKey, boolean fromInclusive, Object toKey, boolean toInclusive) {
         return Maps.asMap(this.set.subSet(fromKey, fromInclusive, toKey, toInclusive), this.function);
      }

      public NavigableMap headMap(Object toKey, boolean inclusive) {
         return Maps.asMap(this.set.headSet(toKey, inclusive), this.function);
      }

      public NavigableMap tailMap(Object fromKey, boolean inclusive) {
         return Maps.asMap(this.set.tailSet(fromKey, inclusive), this.function);
      }

      public Comparator comparator() {
         return this.set.comparator();
      }

      @Nullable
      public Object get(@Nullable Object key) {
         return Collections2.safeContains(this.set, key) ? this.function.apply(key) : null;
      }

      public void clear() {
         this.set.clear();
      }

      Iterator entryIterator() {
         return Maps.asMapEntryIterator(this.set, this.function);
      }

      Iterator descendingEntryIterator() {
         return this.descendingMap().entrySet().iterator();
      }

      public NavigableSet navigableKeySet() {
         return Maps.removeOnlyNavigableSet(this.set);
      }

      public int size() {
         return this.set.size();
      }

      public NavigableMap descendingMap() {
         return Maps.asMap(this.set.descendingSet(), this.function);
      }
   }

   private static class SortedAsMapView extends AsMapView implements SortedMap {
      SortedAsMapView(SortedSet set, Function function) {
         super(set, function);
      }

      SortedSet backingSet() {
         return (SortedSet)super.backingSet();
      }

      public Comparator comparator() {
         return this.backingSet().comparator();
      }

      public Set keySet() {
         return Maps.removeOnlySortedSet(this.backingSet());
      }

      public SortedMap subMap(Object fromKey, Object toKey) {
         return Maps.asMap(this.backingSet().subSet(fromKey, toKey), this.function);
      }

      public SortedMap headMap(Object toKey) {
         return Maps.asMap(this.backingSet().headSet(toKey), this.function);
      }

      public SortedMap tailMap(Object fromKey) {
         return Maps.asMap(this.backingSet().tailSet(fromKey), this.function);
      }

      public Object firstKey() {
         return this.backingSet().first();
      }

      public Object lastKey() {
         return this.backingSet().last();
      }
   }

   private static class AsMapView extends ViewCachingAbstractMap {
      private final Set set;
      final Function function;

      Set backingSet() {
         return this.set;
      }

      AsMapView(Set set, Function function) {
         this.set = (Set)Preconditions.checkNotNull(set);
         this.function = (Function)Preconditions.checkNotNull(function);
      }

      public Set createKeySet() {
         return Maps.removeOnlySet(this.backingSet());
      }

      Collection createValues() {
         return Collections2.transform(this.set, this.function);
      }

      public int size() {
         return this.backingSet().size();
      }

      public boolean containsKey(@Nullable Object key) {
         return this.backingSet().contains(key);
      }

      public Object get(@Nullable Object key) {
         return Collections2.safeContains(this.backingSet(), key) ? this.function.apply(key) : null;
      }

      public Object remove(@Nullable Object key) {
         return this.backingSet().remove(key) ? this.function.apply(key) : null;
      }

      public void clear() {
         this.backingSet().clear();
      }

      protected Set createEntrySet() {
         class EntrySetImpl extends EntrySet {
            Map map() {
               return AsMapView.this;
            }

            public Iterator iterator() {
               return Maps.asMapEntryIterator(AsMapView.this.backingSet(), AsMapView.this.function);
            }
         }

         return new EntrySetImpl();
      }
   }

   static class SortedMapDifferenceImpl extends MapDifferenceImpl implements SortedMapDifference {
      SortedMapDifferenceImpl(SortedMap onlyOnLeft, SortedMap onlyOnRight, SortedMap onBoth, SortedMap differences) {
         super(onlyOnLeft, onlyOnRight, onBoth, differences);
      }

      public SortedMap entriesDiffering() {
         return (SortedMap)super.entriesDiffering();
      }

      public SortedMap entriesInCommon() {
         return (SortedMap)super.entriesInCommon();
      }

      public SortedMap entriesOnlyOnLeft() {
         return (SortedMap)super.entriesOnlyOnLeft();
      }

      public SortedMap entriesOnlyOnRight() {
         return (SortedMap)super.entriesOnlyOnRight();
      }
   }

   static class ValueDifferenceImpl implements MapDifference.ValueDifference {
      private final Object left;
      private final Object right;

      static MapDifference.ValueDifference create(@Nullable Object left, @Nullable Object right) {
         return new ValueDifferenceImpl(left, right);
      }

      private ValueDifferenceImpl(@Nullable Object left, @Nullable Object right) {
         this.left = left;
         this.right = right;
      }

      public Object leftValue() {
         return this.left;
      }

      public Object rightValue() {
         return this.right;
      }

      public boolean equals(@Nullable Object object) {
         if (!(object instanceof MapDifference.ValueDifference)) {
            return false;
         } else {
            MapDifference.ValueDifference that = (MapDifference.ValueDifference)object;
            return Objects.equal(this.left, that.leftValue()) && Objects.equal(this.right, that.rightValue());
         }
      }

      public int hashCode() {
         return Objects.hashCode(this.left, this.right);
      }

      public String toString() {
         return "(" + this.left + ", " + this.right + ")";
      }
   }

   static class MapDifferenceImpl implements MapDifference {
      final Map onlyOnLeft;
      final Map onlyOnRight;
      final Map onBoth;
      final Map differences;

      MapDifferenceImpl(Map onlyOnLeft, Map onlyOnRight, Map onBoth, Map differences) {
         this.onlyOnLeft = Maps.unmodifiableMap(onlyOnLeft);
         this.onlyOnRight = Maps.unmodifiableMap(onlyOnRight);
         this.onBoth = Maps.unmodifiableMap(onBoth);
         this.differences = Maps.unmodifiableMap(differences);
      }

      public boolean areEqual() {
         return this.onlyOnLeft.isEmpty() && this.onlyOnRight.isEmpty() && this.differences.isEmpty();
      }

      public Map entriesOnlyOnLeft() {
         return this.onlyOnLeft;
      }

      public Map entriesOnlyOnRight() {
         return this.onlyOnRight;
      }

      public Map entriesInCommon() {
         return this.onBoth;
      }

      public Map entriesDiffering() {
         return this.differences;
      }

      public boolean equals(Object object) {
         if (object == this) {
            return true;
         } else if (!(object instanceof MapDifference)) {
            return false;
         } else {
            MapDifference other = (MapDifference)object;
            return this.entriesOnlyOnLeft().equals(other.entriesOnlyOnLeft()) && this.entriesOnlyOnRight().equals(other.entriesOnlyOnRight()) && this.entriesInCommon().equals(other.entriesInCommon()) && this.entriesDiffering().equals(other.entriesDiffering());
         }
      }

      public int hashCode() {
         return Objects.hashCode(this.entriesOnlyOnLeft(), this.entriesOnlyOnRight(), this.entriesInCommon(), this.entriesDiffering());
      }

      public String toString() {
         if (this.areEqual()) {
            return "equal";
         } else {
            StringBuilder result = new StringBuilder("not equal");
            if (!this.onlyOnLeft.isEmpty()) {
               result.append(": only on left=").append(this.onlyOnLeft);
            }

            if (!this.onlyOnRight.isEmpty()) {
               result.append(": only on right=").append(this.onlyOnRight);
            }

            if (!this.differences.isEmpty()) {
               result.append(": value differences=").append(this.differences);
            }

            return result.toString();
         }
      }
   }

   private static enum EntryFunction implements Function {
      KEY {
         @Nullable
         public Object apply(Map.Entry entry) {
            return entry.getKey();
         }
      },
      VALUE {
         @Nullable
         public Object apply(Map.Entry entry) {
            return entry.getValue();
         }
      };

      private EntryFunction() {
      }

      // $FF: synthetic method
      EntryFunction(Object x2) {
         this();
      }
   }
}
