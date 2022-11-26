package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.primitives.Primitives;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
public final class ImmutableClassToInstanceMap extends ForwardingMap implements ClassToInstanceMap, Serializable {
   private static final ImmutableClassToInstanceMap EMPTY = new ImmutableClassToInstanceMap(ImmutableMap.of());
   private final ImmutableMap delegate;

   public static ImmutableClassToInstanceMap of() {
      return EMPTY;
   }

   public static ImmutableClassToInstanceMap of(Class type, Object value) {
      ImmutableMap map = ImmutableMap.of(type, value);
      return new ImmutableClassToInstanceMap(map);
   }

   public static Builder builder() {
      return new Builder();
   }

   public static ImmutableClassToInstanceMap copyOf(Map map) {
      if (map instanceof ImmutableClassToInstanceMap) {
         ImmutableClassToInstanceMap cast = (ImmutableClassToInstanceMap)map;
         return cast;
      } else {
         return (new Builder()).putAll(map).build();
      }
   }

   private ImmutableClassToInstanceMap(ImmutableMap delegate) {
      this.delegate = delegate;
   }

   protected Map delegate() {
      return this.delegate;
   }

   @Nullable
   public Object getInstance(Class type) {
      return this.delegate.get(Preconditions.checkNotNull(type));
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public Object putInstance(Class type, Object value) {
      throw new UnsupportedOperationException();
   }

   Object readResolve() {
      return this.isEmpty() ? of() : this;
   }

   // $FF: synthetic method
   ImmutableClassToInstanceMap(ImmutableMap x0, Object x1) {
      this(x0);
   }

   public static final class Builder {
      private final ImmutableMap.Builder mapBuilder = ImmutableMap.builder();

      @CanIgnoreReturnValue
      public Builder put(Class key, Object value) {
         this.mapBuilder.put(key, value);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder putAll(Map map) {
         Iterator var2 = map.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            Class type = (Class)entry.getKey();
            Object value = entry.getValue();
            this.mapBuilder.put(type, cast(type, value));
         }

         return this;
      }

      private static Object cast(Class type, Object value) {
         return Primitives.wrap(type).cast(value);
      }

      public ImmutableClassToInstanceMap build() {
         ImmutableMap map = this.mapBuilder.build();
         return map.isEmpty() ? ImmutableClassToInstanceMap.of() : new ImmutableClassToInstanceMap(map);
      }
   }
}
