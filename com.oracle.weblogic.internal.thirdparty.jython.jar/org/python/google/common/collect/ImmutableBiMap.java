package org.python.google.common.collect;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public abstract class ImmutableBiMap extends ImmutableMap implements BiMap {
   public static ImmutableBiMap of() {
      return RegularImmutableBiMap.EMPTY;
   }

   public static ImmutableBiMap of(Object k1, Object v1) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      return new RegularImmutableBiMap(new Object[]{k1, v1}, 1);
   }

   public static ImmutableBiMap of(Object k1, Object v1, Object k2, Object v2) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      CollectPreconditions.checkEntryNotNull(k2, v2);
      return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2}, 2);
   }

   public static ImmutableBiMap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      CollectPreconditions.checkEntryNotNull(k2, v2);
      CollectPreconditions.checkEntryNotNull(k3, v3);
      return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2, k3, v3}, 3);
   }

   public static ImmutableBiMap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      CollectPreconditions.checkEntryNotNull(k2, v2);
      CollectPreconditions.checkEntryNotNull(k3, v3);
      CollectPreconditions.checkEntryNotNull(k4, v4);
      return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4}, 4);
   }

   public static ImmutableBiMap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4, Object k5, Object v5) {
      CollectPreconditions.checkEntryNotNull(k1, v1);
      CollectPreconditions.checkEntryNotNull(k2, v2);
      CollectPreconditions.checkEntryNotNull(k3, v3);
      CollectPreconditions.checkEntryNotNull(k4, v4);
      CollectPreconditions.checkEntryNotNull(k5, v5);
      return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5}, 5);
   }

   public static Builder builder() {
      return new Builder();
   }

   public static ImmutableBiMap copyOf(Map map) {
      if (map instanceof ImmutableBiMap) {
         ImmutableBiMap bimap = (ImmutableBiMap)map;
         if (!bimap.isPartialView()) {
            return bimap;
         }
      }

      return copyOf((Iterable)map.entrySet());
   }

   @Beta
   public static ImmutableBiMap copyOf(Iterable entries) {
      int estimatedSize = entries instanceof Collection ? ((Collection)entries).size() : 4;
      return (new Builder(estimatedSize)).putAll(entries).build();
   }

   ImmutableBiMap() {
   }

   public abstract ImmutableBiMap inverse();

   public ImmutableSet values() {
      return this.inverse().keySet();
   }

   final ImmutableSet createValues() {
      throw new AssertionError("should never be called");
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public Object forcePut(Object key, Object value) {
      throw new UnsupportedOperationException();
   }

   Object writeReplace() {
      return new SerializedForm(this);
   }

   private static class SerializedForm extends ImmutableMap.SerializedForm {
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableBiMap bimap) {
         super(bimap);
      }

      Object readResolve() {
         Builder builder = new Builder();
         return this.createMap(builder);
      }
   }

   public static final class Builder extends ImmutableMap.Builder {
      public Builder() {
      }

      Builder(int size) {
         super(size);
      }

      @CanIgnoreReturnValue
      public Builder put(Object key, Object value) {
         super.put(key, value);
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

      @CanIgnoreReturnValue
      @Beta
      public Builder orderEntriesByValue(Comparator valueComparator) {
         super.orderEntriesByValue(valueComparator);
         return this;
      }

      public ImmutableBiMap build() {
         if (this.size == 0) {
            return ImmutableBiMap.of();
         } else {
            this.sortEntries();
            this.entriesUsed = true;
            return new RegularImmutableBiMap(this.alternatingKeysAndValues, this.size);
         }
      }
   }
}
