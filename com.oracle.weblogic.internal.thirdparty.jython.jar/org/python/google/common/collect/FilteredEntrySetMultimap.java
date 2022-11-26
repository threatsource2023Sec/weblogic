package org.python.google.common.collect;

import java.util.Set;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Predicate;

@GwtCompatible
final class FilteredEntrySetMultimap extends FilteredEntryMultimap implements FilteredSetMultimap {
   FilteredEntrySetMultimap(SetMultimap unfiltered, Predicate predicate) {
      super(unfiltered, predicate);
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

   Set createEntries() {
      return Sets.filter(this.unfiltered().entries(), this.entryPredicate());
   }

   public Set entries() {
      return (Set)super.entries();
   }
}
