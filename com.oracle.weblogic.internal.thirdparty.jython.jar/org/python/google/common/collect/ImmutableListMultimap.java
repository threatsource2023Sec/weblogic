package org.python.google.common.collect;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.concurrent.LazyInit;
import org.python.google.j2objc.annotations.RetainedWith;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public class ImmutableListMultimap extends ImmutableMultimap implements ListMultimap {
   @LazyInit
   @RetainedWith
   private transient ImmutableListMultimap inverse;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static ImmutableListMultimap of() {
      return EmptyImmutableListMultimap.INSTANCE;
   }

   public static ImmutableListMultimap of(Object k1, Object v1) {
      Builder builder = builder();
      builder.put(k1, v1);
      return builder.build();
   }

   public static ImmutableListMultimap of(Object k1, Object v1, Object k2, Object v2) {
      Builder builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      return builder.build();
   }

   public static ImmutableListMultimap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
      Builder builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      return builder.build();
   }

   public static ImmutableListMultimap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
      Builder builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      builder.put(k4, v4);
      return builder.build();
   }

   public static ImmutableListMultimap of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4, Object k5, Object v5) {
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

   public static ImmutableListMultimap copyOf(Multimap multimap) {
      if (multimap.isEmpty()) {
         return of();
      } else {
         if (multimap instanceof ImmutableListMultimap) {
            ImmutableListMultimap kvMultimap = (ImmutableListMultimap)multimap;
            if (!kvMultimap.isPartialView()) {
               return kvMultimap;
            }
         }

         ImmutableMap.Builder builder = new ImmutableMap.Builder(multimap.asMap().size());
         int size = 0;
         Iterator var3 = multimap.asMap().entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            ImmutableList list = ImmutableList.copyOf((Collection)entry.getValue());
            if (!list.isEmpty()) {
               builder.put(entry.getKey(), list);
               size += list.size();
            }
         }

         return new ImmutableListMultimap(builder.build(), size);
      }
   }

   @Beta
   public static ImmutableListMultimap copyOf(Iterable entries) {
      return (new Builder()).putAll(entries).build();
   }

   ImmutableListMultimap(ImmutableMap map, int size) {
      super(map, size);
   }

   public ImmutableList get(@Nullable Object key) {
      ImmutableList list = (ImmutableList)this.map.get(key);
      return list == null ? ImmutableList.of() : list;
   }

   public ImmutableListMultimap inverse() {
      ImmutableListMultimap result = this.inverse;
      return result == null ? (this.inverse = this.invert()) : result;
   }

   private ImmutableListMultimap invert() {
      Builder builder = builder();
      UnmodifiableIterator var2 = this.entries().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         builder.put(entry.getValue(), entry.getKey());
      }

      ImmutableListMultimap invertedMultimap = builder.build();
      invertedMultimap.inverse = this;
      return invertedMultimap;
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public ImmutableList removeAll(Object key) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public ImmutableList replaceValues(Object key, Iterable values) {
      throw new UnsupportedOperationException();
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      Serialization.writeMultimap(this, stream);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
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

            ImmutableList.Builder valuesBuilder = ImmutableList.builder();

            for(int j = 0; j < valueCount; ++j) {
               valuesBuilder.add(stream.readObject());
            }

            builder.put(key, valuesBuilder.build());
            tmpSize += valueCount;
         }

         ImmutableMap tmpMap;
         try {
            tmpMap = builder.build();
         } catch (IllegalArgumentException var10) {
            throw (InvalidObjectException)(new InvalidObjectException(var10.getMessage())).initCause(var10);
         }

         ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, tmpMap);
         ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, tmpSize);
      }
   }

   public static final class Builder extends ImmutableMultimap.Builder {
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
      @Beta
      public Builder putAll(Iterable entries) {
         super.putAll(entries);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder putAll(Object key, Iterable values) {
         super.putAll(key, values);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder putAll(Object key, Object... values) {
         super.putAll(key, values);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder putAll(Multimap multimap) {
         super.putAll(multimap);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder orderKeysBy(Comparator keyComparator) {
         super.orderKeysBy(keyComparator);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder orderValuesBy(Comparator valueComparator) {
         super.orderValuesBy(valueComparator);
         return this;
      }

      public ImmutableListMultimap build() {
         return (ImmutableListMultimap)super.build();
      }
   }
}
