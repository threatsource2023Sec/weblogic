package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.j2objc.annotations.Weak;

@GwtCompatible(
   emulated = true
)
final class ImmutableMapKeySet extends ImmutableSet.Indexed {
   @Weak
   private final ImmutableMap map;

   ImmutableMapKeySet(ImmutableMap map) {
      this.map = map;
   }

   public int size() {
      return this.map.size();
   }

   public UnmodifiableIterator iterator() {
      return this.map.keyIterator();
   }

   public boolean contains(@Nullable Object object) {
      return this.map.containsKey(object);
   }

   Object get(int index) {
      return ((Map.Entry)this.map.entrySet().asList().get(index)).getKey();
   }

   boolean isPartialView() {
      return true;
   }

   @GwtIncompatible
   Object writeReplace() {
      return new KeySetSerializedForm(this.map);
   }

   @GwtIncompatible
   private static class KeySetSerializedForm implements Serializable {
      final ImmutableMap map;
      private static final long serialVersionUID = 0L;

      KeySetSerializedForm(ImmutableMap map) {
         this.map = map;
      }

      Object readResolve() {
         return this.map.keySet();
      }
   }
}
