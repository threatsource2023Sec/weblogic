package org.python.google.common.collect;

import java.util.List;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Predicate;

@GwtCompatible
final class FilteredKeyListMultimap extends FilteredKeyMultimap implements ListMultimap {
   FilteredKeyListMultimap(ListMultimap unfiltered, Predicate keyPredicate) {
      super(unfiltered, keyPredicate);
   }

   public ListMultimap unfiltered() {
      return (ListMultimap)super.unfiltered();
   }

   public List get(Object key) {
      return (List)super.get(key);
   }

   public List removeAll(@Nullable Object key) {
      return (List)super.removeAll(key);
   }

   public List replaceValues(Object key, Iterable values) {
      return (List)super.replaceValues(key, values);
   }
}
