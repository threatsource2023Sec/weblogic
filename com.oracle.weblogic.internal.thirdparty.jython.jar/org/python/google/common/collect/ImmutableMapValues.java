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
final class ImmutableMapValues extends ImmutableCollection {
   @Weak
   private final ImmutableMap map;

   ImmutableMapValues(ImmutableMap map) {
      this.map = map;
   }

   public int size() {
      return this.map.size();
   }

   public UnmodifiableIterator iterator() {
      return new UnmodifiableIterator() {
         final UnmodifiableIterator entryItr;

         {
            this.entryItr = ImmutableMapValues.this.map.entrySet().iterator();
         }

         public boolean hasNext() {
            return this.entryItr.hasNext();
         }

         public Object next() {
            return ((Map.Entry)this.entryItr.next()).getValue();
         }
      };
   }

   public boolean contains(@Nullable Object object) {
      return object != null && Iterators.contains(this.iterator(), object);
   }

   boolean isPartialView() {
      return true;
   }

   public ImmutableList asList() {
      final ImmutableList entryList = this.map.entrySet().asList();
      return new ImmutableList() {
         public Object get(int index) {
            return ((Map.Entry)entryList.get(index)).getValue();
         }

         boolean isPartialView() {
            return true;
         }

         public int size() {
            return entryList.size();
         }
      };
   }

   @GwtIncompatible
   Object writeReplace() {
      return new SerializedForm(this.map);
   }

   @GwtIncompatible
   private static class SerializedForm implements Serializable {
      final ImmutableMap map;
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableMap map) {
         this.map = map;
      }

      Object readResolve() {
         return this.map.values();
      }
   }
}
