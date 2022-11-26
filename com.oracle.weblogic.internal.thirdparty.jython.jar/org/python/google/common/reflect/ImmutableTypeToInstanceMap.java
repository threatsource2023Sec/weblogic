package org.python.google.common.reflect;

import java.util.Map;
import org.python.google.common.annotations.Beta;
import org.python.google.common.collect.ForwardingMap;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public final class ImmutableTypeToInstanceMap extends ForwardingMap implements TypeToInstanceMap {
   private final ImmutableMap delegate;

   public static ImmutableTypeToInstanceMap of() {
      return new ImmutableTypeToInstanceMap(ImmutableMap.of());
   }

   public static Builder builder() {
      return new Builder();
   }

   private ImmutableTypeToInstanceMap(ImmutableMap delegate) {
      this.delegate = delegate;
   }

   public Object getInstance(TypeToken type) {
      return this.trustedGet(type.rejectTypeVariables());
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public Object putInstance(TypeToken type, Object value) {
      throw new UnsupportedOperationException();
   }

   public Object getInstance(Class type) {
      return this.trustedGet(TypeToken.of(type));
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public Object putInstance(Class type, Object value) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public Object put(TypeToken key, Object value) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public void putAll(Map map) {
      throw new UnsupportedOperationException();
   }

   protected Map delegate() {
      return this.delegate;
   }

   private Object trustedGet(TypeToken type) {
      return this.delegate.get(type);
   }

   // $FF: synthetic method
   ImmutableTypeToInstanceMap(ImmutableMap x0, Object x1) {
      this(x0);
   }

   @Beta
   public static final class Builder {
      private final ImmutableMap.Builder mapBuilder;

      private Builder() {
         this.mapBuilder = ImmutableMap.builder();
      }

      @CanIgnoreReturnValue
      public Builder put(Class key, Object value) {
         this.mapBuilder.put(TypeToken.of(key), value);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder put(TypeToken key, Object value) {
         this.mapBuilder.put(key.rejectTypeVariables(), value);
         return this;
      }

      public ImmutableTypeToInstanceMap build() {
         return new ImmutableTypeToInstanceMap(this.mapBuilder.build());
      }

      // $FF: synthetic method
      Builder(Object x0) {
         this();
      }
   }
}
