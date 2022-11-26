package org.python.google.common.collect;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.concurrent.LazyInit;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public abstract class ImmutableMap implements Map, Serializable {
   static final Map.Entry[] EMPTY_ENTRY_ARRAY = new Map.Entry[0];
   @LazyInit
   private transient ImmutableSet entrySet;
   @LazyInit
   private transient ImmutableSet keySet;
   @LazyInit
   private transient ImmutableCollection values;
   @LazyInit
   private transient ImmutableSetMultimap multimapView;

   public static ImmutableMap of() {
      return RegularImmutableMap.EMPTY;
   }

   public static ImmutableMap of(Object k1, Object v1) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      return RegularImmutableMap.create(1, new Object[]{k1, v1});
   }

   public static ImmutableMap of(Object k1, Object v1, Object k2, Object v2) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      CollectPreconditions.checkEntryNotNull(k2, v2);
      return RegularImmutableMap.create(2, new Object[]{k1, v1, k2, v2});
   }

   public static ImmutableMap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      CollectPreconditions.checkEntryNotNull(k2, v2);
      CollectPreconditions.checkEntryNotNull(k3, v3);
      return RegularImmutableMap.create(3, new Object[]{k1, v1, k2, v2, k3, v3});
   }

   public static ImmutableMap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      CollectPreconditions.checkEntryNotNull(k2, v2);
      CollectPreconditions.checkEntryNotNull(k3, v3);
      CollectPreconditions.checkEntryNotNull(k4, v4);
      return RegularImmutableMap.create(4, new Object[]{k1, v1, k2, v2, k3, v3, k4, v4});
   }

   public static ImmutableMap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4, Object k5, Object v5) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      CollectPreconditions.checkEntryNotNull(k2, v2);
      CollectPreconditions.checkEntryNotNull(k3, v3);
      CollectPreconditions.checkEntryNotNull(k4, v4);
      CollectPreconditions.checkEntryNotNull(k5, v5);
      return RegularImmutableMap.create(5, new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5});
   }

   static Map.Entry entryOf(Object key, Object value) {
      CollectPreconditions.checkEntryNotNull(key, value);
      return new AbstractMap.SimpleImmutableEntry(key, value);
   }

   public static Builder builder() {
      return new Builder();
   }

   static void checkNoConflict(boolean safe, String conflictDescription, Map.Entry entry1, Map.Entry entry2) {
      if (!safe) {
         throw new IllegalArgumentException("Multiple entries with same " + conflictDescription + ": " + entry1 + " and " + entry2);
      }
   }

   public static ImmutableMap copyOf(Map map) {
      if (map instanceof ImmutableMap && !(map instanceof SortedMap)) {
         ImmutableMap kvMap = (ImmutableMap)map;
         if (!kvMap.isPartialView()) {
            return kvMap;
         }
      }

      return copyOf((Iterable)map.entrySet());
   }

   @Beta
   public static ImmutableMap copyOf(Iterable entries) {
      int initialCapacity = entries instanceof Collection ? ((Collection)entries).size() : 4;
      Builder builder = new Builder(initialCapacity);
      builder.putAll(entries);
      return builder.build();
   }

   ImmutableMap() {
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final Object put(Object k, Object v) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final Object remove(Object o) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public final void putAll(Map map) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public final void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public boolean containsKey(@Nullable Object key) {
      return this.get(key) != null;
   }

   public boolean containsValue(@Nullable Object value) {
      return this.values().contains(value);
   }

   public abstract Object get(@Nullable Object var1);

   public ImmutableSet entrySet() {
      ImmutableSet result = this.entrySet;
      return result == null ? (this.entrySet = this.createEntrySet()) : result;
   }

   abstract ImmutableSet createEntrySet();

   public ImmutableSet keySet() {
      ImmutableSet result = this.keySet;
      return result == null ? (this.keySet = this.createKeySet()) : result;
   }

   abstract ImmutableSet createKeySet();

   UnmodifiableIterator keyIterator() {
      final UnmodifiableIterator entryIterator = this.entrySet().iterator();
      return new UnmodifiableIterator() {
         public boolean hasNext() {
            return entryIterator.hasNext();
         }

         public Object next() {
            return ((Map.Entry)entryIterator.next()).getKey();
         }
      };
   }

   public ImmutableCollection values() {
      ImmutableCollection result = this.values;
      return result == null ? (this.values = this.createValues()) : result;
   }

   abstract ImmutableCollection createValues();

   public ImmutableSetMultimap asMultimap() {
      if (this.isEmpty()) {
         return ImmutableSetMultimap.of();
      } else {
         ImmutableSetMultimap result = this.multimapView;
         return result == null ? (this.multimapView = new ImmutableSetMultimap(new MapViewOfValuesAsSingletonSets(), this.size(), (Comparator)null)) : result;
      }
   }

   public boolean equals(@Nullable Object object) {
      return Maps.equalsImpl(this, object);
   }

   abstract boolean isPartialView();

   public int hashCode() {
      return Sets.hashCodeImpl(this.entrySet());
   }

   boolean isHashCodeFast() {
      return false;
   }

   public String toString() {
      return Maps.toStringImpl(this);
   }

   Object writeReplace() {
      return new SerializedForm(this);
   }

   static class SerializedForm implements Serializable {
      private final Object[] keys;
      private final Object[] values;
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableMap map) {
         this.keys = new Object[map.size()];
         this.values = new Object[map.size()];
         int i = 0;

         for(UnmodifiableIterator var3 = map.entrySet().iterator(); var3.hasNext(); ++i) {
            Map.Entry entry = (Map.Entry)var3.next();
            this.keys[i] = entry.getKey();
            this.values[i] = entry.getValue();
         }

      }

      Object readResolve() {
         Builder builder = new Builder(this.keys.length);
         return this.createMap(builder);
      }

      Object createMap(Builder builder) {
         for(int i = 0; i < this.keys.length; ++i) {
            builder.put(this.keys[i], this.values[i]);
         }

         return builder.build();
      }
   }

   private final class MapViewOfValuesAsSingletonSets extends IteratorBasedImmutableMap {
      private MapViewOfValuesAsSingletonSets() {
      }

      public int size() {
         return ImmutableMap.this.size();
      }

      ImmutableSet createKeySet() {
         return ImmutableMap.this.keySet();
      }

      public boolean containsKey(@Nullable Object key) {
         return ImmutableMap.this.containsKey(key);
      }

      public ImmutableSet get(@Nullable Object key) {
         Object outerValue = ImmutableMap.this.get(key);
         return outerValue == null ? null : ImmutableSet.of(outerValue);
      }

      boolean isPartialView() {
         return ImmutableMap.this.isPartialView();
      }

      public int hashCode() {
         return ImmutableMap.this.hashCode();
      }

      boolean isHashCodeFast() {
         return ImmutableMap.this.isHashCodeFast();
      }

      UnmodifiableIterator entryIterator() {
         final Iterator backingIterator = ImmutableMap.this.entrySet().iterator();
         return new UnmodifiableIterator() {
            public boolean hasNext() {
               return backingIterator.hasNext();
            }

            public Map.Entry next() {
               final Map.Entry backingEntry = (Map.Entry)backingIterator.next();
               return new AbstractMapEntry() {
                  public Object getKey() {
                     return backingEntry.getKey();
                  }

                  public ImmutableSet getValue() {
                     return ImmutableSet.of(backingEntry.getValue());
                  }
               };
            }
         };
      }

      // $FF: synthetic method
      MapViewOfValuesAsSingletonSets(Object x1) {
         this();
      }
   }

   abstract static class IteratorBasedImmutableMap extends ImmutableMap {
      abstract UnmodifiableIterator entryIterator();

      ImmutableSet createKeySet() {
         return new ImmutableMapKeySet(this);
      }

      ImmutableSet createEntrySet() {
         class EntrySetImpl extends ImmutableMapEntrySet {
            ImmutableMap map() {
               return IteratorBasedImmutableMap.this;
            }

            public UnmodifiableIterator iterator() {
               return IteratorBasedImmutableMap.this.entryIterator();
            }
         }

         return new EntrySetImpl();
      }

      ImmutableCollection createValues() {
         return new ImmutableMapValues(this);
      }
   }

   public static class Builder {
      Comparator valueComparator;
      Object[] alternatingKeysAndValues;
      int size;
      boolean entriesUsed;

      public Builder() {
         this(4);
      }

      Builder(int initialCapacity) {
         this.alternatingKeysAndValues = new Object[2 * initialCapacity];
         this.size = 0;
         this.entriesUsed = false;
      }

      private void ensureCapacity(int minCapacity) {
         if (minCapacity * 2 > this.alternatingKeysAndValues.length) {
            this.alternatingKeysAndValues = Arrays.copyOf(this.alternatingKeysAndValues, ImmutableCollection.Builder.expandedCapacity(this.alternatingKeysAndValues.length, minCapacity * 2));
            this.entriesUsed = false;
         }

      }

      @CanIgnoreReturnValue
      public Builder put(Object key, Object value) {
         this.ensureCapacity(this.size + 1);
         CollectPreconditions.checkEntryNotNull(key, value);
         this.alternatingKeysAndValues[2 * this.size] = key;
         this.alternatingKeysAndValues[2 * this.size + 1] = value;
         ++this.size;
         return this;
      }

      @CanIgnoreReturnValue
      public Builder put(Map.Entry entry) {
         return this.put(entry.getKey(), entry.getValue());
      }

      @CanIgnoreReturnValue
      public Builder putAll(Map map) {
         return this.putAll((Iterable)map.entrySet());
      }

      @CanIgnoreReturnValue
      @Beta
      public Builder putAll(Iterable entries) {
         if (entries instanceof Collection) {
            this.ensureCapacity(this.size + ((Collection)entries).size());
         }

         Iterator var2 = entries.iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.put(entry);
         }

         return this;
      }

      @CanIgnoreReturnValue
      @Beta
      public Builder orderEntriesByValue(Comparator valueComparator) {
         Preconditions.checkState(this.valueComparator == null, "valueComparator was already set");
         this.valueComparator = (Comparator)Preconditions.checkNotNull(valueComparator, "valueComparator");
         return this;
      }

      public ImmutableMap build() {
         this.sortEntries();
         this.entriesUsed = true;
         return RegularImmutableMap.create(this.size, this.alternatingKeysAndValues);
      }

      void sortEntries() {
         if (this.valueComparator != null) {
            if (this.entriesUsed) {
               this.alternatingKeysAndValues = Arrays.copyOf(this.alternatingKeysAndValues, 2 * this.size);
            }

            Map.Entry[] entries = new Map.Entry[this.size];

            int i;
            for(i = 0; i < this.size; ++i) {
               entries[i] = new AbstractMap.SimpleImmutableEntry(this.alternatingKeysAndValues[2 * i], this.alternatingKeysAndValues[2 * i + 1]);
            }

            Arrays.sort(entries, 0, this.size, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()));

            for(i = 0; i < this.size; ++i) {
               this.alternatingKeysAndValues[2 * i] = entries[i].getKey();
               this.alternatingKeysAndValues[2 * i + 1] = entries[i].getValue();
            }
         }

      }
   }
}
