package org.python.google.common.collect;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Predicates;
import org.python.google.j2objc.annotations.Weak;

@GwtCompatible
final class FilteredMultimapValues extends AbstractCollection {
   @Weak
   private final FilteredMultimap multimap;

   FilteredMultimapValues(FilteredMultimap multimap) {
      this.multimap = (FilteredMultimap)Preconditions.checkNotNull(multimap);
   }

   public Iterator iterator() {
      return Maps.valueIterator(this.multimap.entries().iterator());
   }

   public boolean contains(@Nullable Object o) {
      return this.multimap.containsValue(o);
   }

   public int size() {
      return this.multimap.size();
   }

   public boolean remove(@Nullable Object o) {
      Predicate entryPredicate = this.multimap.entryPredicate();
      Iterator unfilteredItr = this.multimap.unfiltered().entries().iterator();

      Map.Entry entry;
      do {
         if (!unfilteredItr.hasNext()) {
            return false;
         }

         entry = (Map.Entry)unfilteredItr.next();
      } while(!entryPredicate.apply(entry) || !Objects.equal(entry.getValue(), o));

      unfilteredItr.remove();
      return true;
   }

   public boolean removeAll(Collection c) {
      return Iterables.removeIf(this.multimap.unfiltered().entries(), Predicates.and(this.multimap.entryPredicate(), Maps.valuePredicateOnEntries(Predicates.in(c))));
   }

   public boolean retainAll(Collection c) {
      return Iterables.removeIf(this.multimap.unfiltered().entries(), Predicates.and(this.multimap.entryPredicate(), Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(c)))));
   }

   public void clear() {
      this.multimap.clear();
   }
}
