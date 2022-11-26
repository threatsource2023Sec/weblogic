package org.python.google.common.collect;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   serializable = true,
   emulated = true
)
final class ImmutableEnumMap extends ImmutableMap.IteratorBasedImmutableMap {
   private final transient EnumMap delegate;

   static ImmutableMap asImmutable(EnumMap map) {
      switch (map.size()) {
         case 0:
            return ImmutableMap.of();
         case 1:
            Map.Entry entry = (Map.Entry)Iterables.getOnlyElement(map.entrySet());
            return ImmutableMap.of(entry.getKey(), entry.getValue());
         default:
            return new ImmutableEnumMap(map);
      }
   }

   private ImmutableEnumMap(EnumMap delegate) {
      this.delegate = delegate;
      Preconditions.checkArgument(!delegate.isEmpty());
   }

   UnmodifiableIterator keyIterator() {
      return Iterators.unmodifiableIterator(this.delegate.keySet().iterator());
   }

   public int size() {
      return this.delegate.size();
   }

   public boolean containsKey(@Nullable Object key) {
      return this.delegate.containsKey(key);
   }

   public Object get(Object key) {
      return this.delegate.get(key);
   }

   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else {
         if (object instanceof ImmutableEnumMap) {
            object = ((ImmutableEnumMap)object).delegate;
         }

         return this.delegate.equals(object);
      }
   }

   UnmodifiableIterator entryIterator() {
      return Maps.unmodifiableEntryIterator(this.delegate.entrySet().iterator());
   }

   boolean isPartialView() {
      return false;
   }

   Object writeReplace() {
      return new EnumSerializedForm(this.delegate);
   }

   // $FF: synthetic method
   ImmutableEnumMap(EnumMap x0, Object x1) {
      this(x0);
   }

   private static class EnumSerializedForm implements Serializable {
      final EnumMap delegate;
      private static final long serialVersionUID = 0L;

      EnumSerializedForm(EnumMap delegate) {
         this.delegate = delegate;
      }

      Object readResolve() {
         return new ImmutableEnumMap(this.delegate);
      }
   }
}
