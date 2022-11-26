package org.python.google.common.collect;

import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Predicate;

@GwtCompatible
interface FilteredMultimap extends Multimap {
   Multimap unfiltered();

   Predicate entryPredicate();
}
