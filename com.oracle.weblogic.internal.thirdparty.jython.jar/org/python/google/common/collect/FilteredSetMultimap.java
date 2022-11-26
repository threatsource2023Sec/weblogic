package org.python.google.common.collect;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
interface FilteredSetMultimap extends FilteredMultimap, SetMultimap {
   SetMultimap unfiltered();
}
