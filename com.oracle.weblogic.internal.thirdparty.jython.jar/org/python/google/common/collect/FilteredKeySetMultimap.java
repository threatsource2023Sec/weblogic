package org.python.google.common.collect;

import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Predicate;

@GwtCompatible
final class FilteredKeySetMultimap extends FilteredKeyMultimap implements FilteredSetMultimap {
   FilteredKeySetMultimap(SetMultimap unfiltered, Predicate keyPredicate) {
      super(unfiltered, keyPredicate);
   }

   public SetMultimap unfiltered() {
      return (SetMultimap)this.unfiltered;
   }

   public Set get(Object key) {
      return (Set)super.get(key);
   }

   public Set removeAll(Object key) {
      return (Set)super.removeAll(key);
   }

   public Set replaceValues(Object key, Iterable values) {
      return (Set)super.replaceValues(key, values);
   }

   public Set entries() {
      return (Set)super.entries();
   }

   Set createEntries() {
      return new EntrySet();
   }

   class EntrySet extends FilteredKeyMultimap.Entries implements Set {
      EntrySet() {
         super();
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }

      public boolean equals(@Nullable Object o) {
         return Sets.equalsImpl(this, o);
      }
   }
}
