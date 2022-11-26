package org.python.google.common.reflect;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ForwardingMap;
import org.python.google.common.collect.ForwardingMapEntry;
import org.python.google.common.collect.ForwardingSet;
import org.python.google.common.collect.Iterators;
import org.python.google.common.collect.Maps;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public final class MutableTypeToInstanceMap extends ForwardingMap implements TypeToInstanceMap {
   private final Map backingMap = Maps.newHashMap();

   @Nullable
   public Object getInstance(Class type) {
      return this.trustedGet(TypeToken.of(type));
   }

   @Nullable
   @CanIgnoreReturnValue
   public Object putInstance(Class type, @Nullable Object value) {
      return this.trustedPut(TypeToken.of(type), value);
   }

   @Nullable
   public Object getInstance(TypeToken type) {
      return this.trustedGet(type.rejectTypeVariables());
   }

   @Nullable
   @CanIgnoreReturnValue
   public Object putInstance(TypeToken type, @Nullable Object value) {
      return this.trustedPut(type.rejectTypeVariables(), value);
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public Object put(TypeToken key, Object value) {
      throw new UnsupportedOperationException("Please use putInstance() instead.");
   }

   /** @deprecated */
   @Deprecated
   public void putAll(Map map) {
      throw new UnsupportedOperationException("Please use putInstance() instead.");
   }

   public Set entrySet() {
      return MutableTypeToInstanceMap.UnmodifiableEntry.transformEntries(super.entrySet());
   }

   protected Map delegate() {
      return this.backingMap;
   }

   @Nullable
   private Object trustedPut(TypeToken type, @Nullable Object value) {
      return this.backingMap.put(type, value);
   }

   @Nullable
   private Object trustedGet(TypeToken type) {
      return this.backingMap.get(type);
   }

   private static final class UnmodifiableEntry extends ForwardingMapEntry {
      private final Map.Entry delegate;

      static Set transformEntries(final Set entries) {
         return new ForwardingSet() {
            protected Set delegate() {
               return entries;
            }

            public Iterator iterator() {
               return MutableTypeToInstanceMap.UnmodifiableEntry.transformEntries(super.iterator());
            }

            public Object[] toArray() {
               return this.standardToArray();
            }

            public Object[] toArray(Object[] array) {
               return this.standardToArray(array);
            }
         };
      }

      private static Iterator transformEntries(Iterator entries) {
         return Iterators.transform(entries, new Function() {
            public Map.Entry apply(Map.Entry entry) {
               return new UnmodifiableEntry(entry);
            }
         });
      }

      private UnmodifiableEntry(Map.Entry delegate) {
         this.delegate = (Map.Entry)Preconditions.checkNotNull(delegate);
      }

      protected Map.Entry delegate() {
         return this.delegate;
      }

      public Object setValue(Object value) {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      UnmodifiableEntry(Map.Entry x0, Object x1) {
         this(x0);
      }
   }
}
