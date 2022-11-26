package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.j2objc.annotations.Weak;

@GwtCompatible(
   emulated = true
)
public abstract class ImmutableMultimap extends AbstractMultimap implements Serializable {
   final transient ImmutableMap map;
   final transient int size;
   private static final long serialVersionUID = 0L;

   public static ImmutableMultimap of() {
      return ImmutableListMultimap.of();
   }

   public static ImmutableMultimap of(Object k1, Object v1) {
      return ImmutableListMultimap.of(k1, v1);
   }

   public static ImmutableMultimap of(Object k1, Object v1, Object k2, Object v2) {
      return ImmutableListMultimap.of(k1, v1, k2, v2);
   }

   public static ImmutableMultimap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
      return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3);
   }

   public static ImmutableMultimap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
      return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4);
   }

   public static ImmutableMultimap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4, Object k5, Object v5) {
      return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
   }

   public static Builder builder() {
      return new Builder();
   }

   public static ImmutableMultimap copyOf(Multimap multimap) {
      if (multimap instanceof ImmutableMultimap) {
         ImmutableMultimap kvMultimap = (ImmutableMultimap)multimap;
         if (!kvMultimap.isPartialView()) {
            return kvMultimap;
         }
      }

      return ImmutableListMultimap.copyOf(multimap);
   }

   @Beta
   public static ImmutableMultimap copyOf(Iterable entries) {
      return ImmutableListMultimap.copyOf(entries);
   }

   ImmutableMultimap(ImmutableMap map, int size) {
      this.map = map;
      this.size = size;
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public ImmutableCollection removeAll(Object key) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public ImmutableCollection replaceValues(Object key, Iterable values) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void clear() {
      throw new UnsupportedOperationException();
   }

   public abstract ImmutableCollection get(Object var1);

   public abstract ImmutableMultimap inverse();

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public boolean put(Object key, Object value) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public boolean putAll(Object key, Iterable values) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public boolean putAll(Multimap multimap) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public boolean remove(Object key, Object value) {
      throw new UnsupportedOperationException();
   }

   boolean isPartialView() {
      return this.map.isPartialView();
   }

   public boolean containsKey(@Nullable Object key) {
      return this.map.containsKey(key);
   }

   public boolean containsValue(@Nullable Object value) {
      return value != null && super.containsValue(value);
   }

   public int size() {
      return this.size;
   }

   public ImmutableSet keySet() {
      return this.map.keySet();
   }

   public ImmutableMap asMap() {
      return this.map;
   }

   Map createAsMap() {
      throw new AssertionError("should never be called");
   }

   public ImmutableCollection entries() {
      return (ImmutableCollection)super.entries();
   }

   ImmutableCollection createEntries() {
      return new EntryCollection(this);
   }

   UnmodifiableIterator entryIterator() {
      return new Itr() {
         Map.Entry output(Object key, Object value) {
            return Maps.immutableEntry(key, value);
         }
      };
   }

   public ImmutableMultiset keys() {
      return (ImmutableMultiset)super.keys();
   }

   ImmutableMultiset createKeys() {
      return new Keys();
   }

   public ImmutableCollection values() {
      return (ImmutableCollection)super.values();
   }

   ImmutableCollection createValues() {
      return new Values(this);
   }

   UnmodifiableIterator valueIterator() {
      return new Itr() {
         Object output(Object key, Object value) {
            return value;
         }
      };
   }

   private static final class Values extends ImmutableCollection {
      @Weak
      private final transient ImmutableMultimap multimap;
      private static final long serialVersionUID = 0L;

      Values(ImmutableMultimap multimap) {
         this.multimap = multimap;
      }

      public boolean contains(@Nullable Object object) {
         return this.multimap.containsValue(object);
      }

      public UnmodifiableIterator iterator() {
         return this.multimap.valueIterator();
      }

      @GwtIncompatible
      int copyIntoArray(Object[] dst, int offset) {
         ImmutableCollection valueCollection;
         for(UnmodifiableIterator var3 = this.multimap.map.values().iterator(); var3.hasNext(); offset = valueCollection.copyIntoArray(dst, offset)) {
            valueCollection = (ImmutableCollection)var3.next();
         }

         return offset;
      }

      public int size() {
         return this.multimap.size();
      }

      boolean isPartialView() {
         return true;
      }
   }

   class Keys extends ImmutableMultiset {
      public boolean contains(@Nullable Object object) {
         return ImmutableMultimap.this.containsKey(object);
      }

      public int count(@Nullable Object element) {
         Collection values = (Collection)ImmutableMultimap.this.map.get(element);
         return values == null ? 0 : values.size();
      }

      public ImmutableSet elementSet() {
         return ImmutableMultimap.this.keySet();
      }

      public int size() {
         return ImmutableMultimap.this.size();
      }

      Multiset.Entry getEntry(int index) {
         Map.Entry entry = (Map.Entry)ImmutableMultimap.this.map.entrySet().asList().get(index);
         return Multisets.immutableEntry(entry.getKey(), ((Collection)entry.getValue()).size());
      }

      boolean isPartialView() {
         return true;
      }
   }

   private abstract class Itr extends UnmodifiableIterator {
      final Iterator mapIterator;
      Object key;
      Iterator valueIterator;

      private Itr() {
         this.mapIterator = ImmutableMultimap.this.asMap().entrySet().iterator();
         this.key = null;
         this.valueIterator = Iterators.emptyIterator();
      }

      abstract Object output(Object var1, Object var2);

      public boolean hasNext() {
         return this.mapIterator.hasNext() || this.valueIterator.hasNext();
      }

      public Object next() {
         if (!this.valueIterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry)this.mapIterator.next();
            this.key = mapEntry.getKey();
            this.valueIterator = ((Collection)mapEntry.getValue()).iterator();
         }

         return this.output(this.key, this.valueIterator.next());
      }

      // $FF: synthetic method
      Itr(Object x1) {
         this();
      }
   }

   private static class EntryCollection extends ImmutableCollection {
      @Weak
      final ImmutableMultimap multimap;
      private static final long serialVersionUID = 0L;

      EntryCollection(ImmutableMultimap multimap) {
         this.multimap = multimap;
      }

      public UnmodifiableIterator iterator() {
         return this.multimap.entryIterator();
      }

      boolean isPartialView() {
         return this.multimap.isPartialView();
      }

      public int size() {
         return this.multimap.size();
      }

      public boolean contains(Object object) {
         if (object instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry)object;
            return this.multimap.containsEntry(entry.getKey(), entry.getValue());
         } else {
            return false;
         }
      }
   }

   @GwtIncompatible
   static class FieldSettersHolder {
      static final Serialization.FieldSetter MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
      static final Serialization.FieldSetter SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");
      static final Serialization.FieldSetter EMPTY_SET_FIELD_SETTER = Serialization.getFieldSetter(ImmutableSetMultimap.class, "emptySet");
   }

   public static class Builder {
      Multimap builderMultimap;
      Comparator keyComparator;
      Comparator valueComparator;

      public Builder() {
         this(MultimapBuilder.linkedHashKeys().arrayListValues().build());
      }

      Builder(Multimap builderMultimap) {
         this.builderMultimap = builderMultimap;
      }

      @CanIgnoreReturnValue
      public Builder put(Object key, Object value) {
         CollectPreconditions.checkEntryNotNull(key, value);
         this.builderMultimap.put(key, value);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder put(Map.Entry entry) {
         return this.put(entry.getKey(), entry.getValue());
      }

      @CanIgnoreReturnValue
      @Beta
      public Builder putAll(Iterable entries) {
         Iterator var2 = entries.iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.put(entry);
         }

         return this;
      }

      @CanIgnoreReturnValue
      public Builder putAll(Object key, Iterable values) {
         if (key == null) {
            throw new NullPointerException("null key in entry: null=" + Iterables.toString(values));
         } else {
            Collection valueList = this.builderMultimap.get(key);
            Iterator var4 = values.iterator();

            while(var4.hasNext()) {
               Object value = var4.next();
               CollectPreconditions.checkEntryNotNull(key, value);
               valueList.add(value);
            }

            return this;
         }
      }

      @CanIgnoreReturnValue
      public Builder putAll(Object key, Object... values) {
         return this.putAll(key, (Iterable)Arrays.asList(values));
      }

      @CanIgnoreReturnValue
      public Builder putAll(Multimap multimap) {
         Iterator var2 = multimap.asMap().entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.putAll(entry.getKey(), (Iterable)entry.getValue());
         }

         return this;
      }

      @CanIgnoreReturnValue
      public Builder orderKeysBy(Comparator keyComparator) {
         this.keyComparator = (Comparator)Preconditions.checkNotNull(keyComparator);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder orderValuesBy(Comparator valueComparator) {
         this.valueComparator = (Comparator)Preconditions.checkNotNull(valueComparator);
         return this;
      }

      public ImmutableMultimap build() {
         if (this.valueComparator != null) {
            Iterator var1 = this.builderMultimap.asMap().values().iterator();

            while(var1.hasNext()) {
               Collection values = (Collection)var1.next();
               List list = (List)values;
               Collections.sort(list, this.valueComparator);
            }
         }

         if (this.keyComparator != null) {
            Multimap sortedCopy = MultimapBuilder.linkedHashKeys().arrayListValues().build();
            List entries = Ordering.from(this.keyComparator).onKeys().immutableSortedCopy(this.builderMultimap.asMap().entrySet());
            Iterator var7 = entries.iterator();

            while(var7.hasNext()) {
               Map.Entry entry = (Map.Entry)var7.next();
               sortedCopy.putAll(entry.getKey(), (Iterable)entry.getValue());
            }

            this.builderMultimap = sortedCopy;
         }

         return ImmutableMultimap.copyOf(this.builderMultimap);
      }
   }
}
