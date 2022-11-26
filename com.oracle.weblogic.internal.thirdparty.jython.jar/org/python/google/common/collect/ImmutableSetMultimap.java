package org.python.google.common.collect;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.concurrent.LazyInit;
import org.python.google.j2objc.annotations.RetainedWith;
import org.python.google.j2objc.annotations.Weak;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public class ImmutableSetMultimap extends ImmutableMultimap implements SetMultimap {
   private final transient ImmutableSet emptySet;
   @LazyInit
   @RetainedWith
   private transient ImmutableSetMultimap inverse;
   private transient ImmutableSet entries;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static ImmutableSetMultimap of() {
      return EmptyImmutableSetMultimap.INSTANCE;
   }

   public static ImmutableSetMultimap of(Object k1, Object v1) {
      Builder builder = builder();
      builder.put(k1, v1);
      return builder.build();
   }

   public static ImmutableSetMultimap of(Object k1, Object v1, Object k2, Object v2) {
      Builder builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      return builder.build();
   }

   public static ImmutableSetMultimap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
      Builder builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      return builder.build();
   }

   public static ImmutableSetMultimap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
      Builder builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      builder.put(k4, v4);
      return builder.build();
   }

   public static ImmutableSetMultimap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4, Object k5, Object v5) {
      Builder builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      builder.put(k4, v4);
      builder.put(k5, v5);
      return builder.build();
   }

   public static Builder builder() {
      return new Builder();
   }

   public static ImmutableSetMultimap copyOf(Multimap multimap) {
      return copyOf(multimap, (Comparator)null);
   }

   private static ImmutableSetMultimap copyOf(Multimap multimap, Comparator valueComparator) {
      Preconditions.checkNotNull(multimap);
      if (multimap.isEmpty() && valueComparator == null) {
         return of();
      } else {
         if (multimap instanceof ImmutableSetMultimap) {
            ImmutableSetMultimap kvMultimap = (ImmutableSetMultimap)multimap;
            if (!kvMultimap.isPartialView()) {
               return kvMultimap;
            }
         }

         ImmutableMap.Builder builder = new ImmutableMap.Builder(multimap.asMap().size());
         int size = 0;
         Iterator var4 = multimap.asMap().entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            Object key = entry.getKey();
            Collection values = (Collection)entry.getValue();
            ImmutableSet set = valueSet(valueComparator, values);
            if (!set.isEmpty()) {
               builder.put(key, set);
               size += set.size();
            }
         }

         return new ImmutableSetMultimap(builder.build(), size, valueComparator);
      }
   }

   @Beta
   public static ImmutableSetMultimap copyOf(Iterable entries) {
      return (new Builder()).putAll(entries).build();
   }

   ImmutableSetMultimap(ImmutableMap map, int size, @Nullable Comparator valueComparator) {
      super(map, size);
      this.emptySet = emptySet(valueComparator);
   }

   public ImmutableSet get(@Nullable Object key) {
      ImmutableSet set = (ImmutableSet)this.map.get(key);
      return (ImmutableSet)MoreObjects.firstNonNull(set, this.emptySet);
   }

   public ImmutableSetMultimap inverse() {
      ImmutableSetMultimap result = this.inverse;
      return result == null ? (this.inverse = this.invert()) : result;
   }

   private ImmutableSetMultimap invert() {
      Builder builder = builder();
      UnmodifiableIterator var2 = this.entries().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         builder.put(entry.getValue(), entry.getKey());
      }

      ImmutableSetMultimap invertedMultimap = builder.build();
      invertedMultimap.inverse = this;
      return invertedMultimap;
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public ImmutableSet removeAll(Object key) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public ImmutableSet replaceValues(Object key, Iterable values) {
      throw new UnsupportedOperationException();
   }

   public ImmutableSet entries() {
      ImmutableSet result = this.entries;
      return result == null ? (this.entries = new EntrySet(this)) : result;
   }

   private static ImmutableSet valueSet(@Nullable Comparator valueComparator, Collection values) {
      return (ImmutableSet)(valueComparator == null ? ImmutableSet.copyOf(values) : ImmutableSortedSet.copyOf(valueComparator, values));
   }

   private static ImmutableSet emptySet(@Nullable Comparator valueComparator) {
      return (ImmutableSet)(valueComparator == null ? ImmutableSet.of() : ImmutableSortedSet.emptySet(valueComparator));
   }

   private static ImmutableSet.Builder valuesBuilder(@Nullable Comparator valueComparator) {
      return (ImmutableSet.Builder)(valueComparator == null ? new ImmutableSet.Builder() : new ImmutableSortedSet.Builder(valueComparator));
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeObject(this.valueComparator());
      Serialization.writeMultimap(this, stream);
   }

   @Nullable
   Comparator valueComparator() {
      return this.emptySet instanceof ImmutableSortedSet ? ((ImmutableSortedSet)this.emptySet).comparator() : null;
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      Comparator valueComparator = (Comparator)stream.readObject();
      int keyCount = stream.readInt();
      if (keyCount < 0) {
         throw new InvalidObjectException("Invalid key count " + keyCount);
      } else {
         ImmutableMap.Builder builder = ImmutableMap.builder();
         int tmpSize = 0;

         for(int i = 0; i < keyCount; ++i) {
            Object key = stream.readObject();
            int valueCount = stream.readInt();
            if (valueCount <= 0) {
               throw new InvalidObjectException("Invalid value count " + valueCount);
            }

            ImmutableSet.Builder valuesBuilder = valuesBuilder(valueComparator);

            for(int j = 0; j < valueCount; ++j) {
               valuesBuilder.add(stream.readObject());
            }

            ImmutableSet valueSet = valuesBuilder.build();
            if (valueSet.size() != valueCount) {
               throw new InvalidObjectException("Duplicate key-value pairs exist for key " + key);
            }

            builder.put(key, valueSet);
            tmpSize += valueCount;
         }

         ImmutableMap tmpMap;
         try {
            tmpMap = builder.build();
         } catch (IllegalArgumentException var11) {
            throw (InvalidObjectException)(new InvalidObjectException(var11.getMessage())).initCause(var11);
         }

         ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, tmpMap);
         ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, tmpSize);
         ImmutableMultimap.FieldSettersHolder.EMPTY_SET_FIELD_SETTER.set(this, emptySet(valueComparator));
      }
   }

   private static final class EntrySet extends ImmutableSet {
      @Weak
      private final transient ImmutableSetMultimap multimap;

      EntrySet(ImmutableSetMultimap multimap) {
         this.multimap = multimap;
      }

      public boolean contains(@Nullable Object object) {
         if (object instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry)object;
            return this.multimap.containsEntry(entry.getKey(), entry.getValue());
         } else {
            return false;
         }
      }

      public int size() {
         return this.multimap.size();
      }

      public UnmodifiableIterator iterator() {
         return this.multimap.entryIterator();
      }

      boolean isPartialView() {
         return false;
      }
   }

   public static final class Builder extends ImmutableMultimap.Builder {
      public Builder() {
         super(MultimapBuilder.linkedHashKeys().linkedHashSetValues().build());
      }

      @CanIgnoreReturnValue
      public Builder put(Object key, Object value) {
         this.builderMultimap.put(Preconditions.checkNotNull(key), Preconditions.checkNotNull(value));
         return this;
      }

      @CanIgnoreReturnValue
      public Builder put(Map.Entry entry) {
         this.builderMultimap.put(Preconditions.checkNotNull(entry.getKey()), Preconditions.checkNotNull(entry.getValue()));
         return this;
      }

      @CanIgnoreReturnValue
      @Beta
      public Builder putAll(Iterable entries) {
         super.putAll(entries);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder putAll(Object key, Iterable values) {
         Collection collection = this.builderMultimap.get(Preconditions.checkNotNull(key));
         Iterator var4 = values.iterator();

         while(var4.hasNext()) {
            Object value = var4.next();
            collection.add(Preconditions.checkNotNull(value));
         }

         return this;
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
         super.orderValuesBy(valueComparator);
         return this;
      }

      public ImmutableSetMultimap build() {
         if (this.keyComparator != null) {
            Multimap sortedCopy = MultimapBuilder.linkedHashKeys().linkedHashSetValues().build();
            List entries = Ordering.from(this.keyComparator).onKeys().immutableSortedCopy(this.builderMultimap.asMap().entrySet());
            Iterator var3 = entries.iterator();

            while(var3.hasNext()) {
               Map.Entry entry = (Map.Entry)var3.next();
               sortedCopy.putAll(entry.getKey(), (Iterable)entry.getValue());
            }

            this.builderMultimap = sortedCopy;
         }

         return ImmutableSetMultimap.copyOf(this.builderMultimap, this.valueComparator);
      }
   }
}
