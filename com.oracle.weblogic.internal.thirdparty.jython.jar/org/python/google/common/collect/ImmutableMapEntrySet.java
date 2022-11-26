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
abstract class ImmutableMapEntrySet extends ImmutableSet {
   abstract ImmutableMap map();

   public int size() {
      return this.map().size();
   }

   public boolean contains(@Nullable Object object) {
      if (!(object instanceof Map.Entry)) {
         return false;
      } else {
         Map.Entry entry = (Map.Entry)object;
         Object value = this.map().get(entry.getKey());
         return value != null && value.equals(entry.getValue());
      }
   }

   boolean isPartialView() {
      return this.map().isPartialView();
   }

   @GwtIncompatible
   boolean isHashCodeFast() {
      return this.map().isHashCodeFast();
   }

   public int hashCode() {
      return this.map().hashCode();
   }

   @GwtIncompatible
   Object writeReplace() {
      return new EntrySetSerializedForm(this.map());
   }

   @GwtIncompatible
   private static class EntrySetSerializedForm implements Serializable {
      final ImmutableMap map;
      private static final long serialVersionUID = 0L;

      EntrySetSerializedForm(ImmutableMap map) {
         this.map = map;
      }

      Object readResolve() {
         return this.map.entrySet();
      }
   }

   static final class RegularEntrySet extends ImmutableMapEntrySet {
      @Weak
      private final transient ImmutableMap map;
      private final transient Map.Entry[] entries;

      RegularEntrySet(ImmutableMap map, Map.Entry[] entries) {
         this.map = map;
         this.entries = entries;
      }

      ImmutableMap map() {
         return this.map;
      }

      public UnmodifiableIterator iterator() {
         return Iterators.forArray(this.entries);
      }

      ImmutableList createAsList() {
         return ImmutableList.asImmutableList(this.entries);
      }
   }
}
