package org.python.google.common.collect;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public interface SortedSetMultimap extends SetMultimap {
   SortedSet get(@Nullable Object var1);

   @CanIgnoreReturnValue
   SortedSet removeAll(@Nullable Object var1);

   @CanIgnoreReturnValue
   SortedSet replaceValues(Object var1, Iterable var2);

   Map asMap();

   Comparator valueComparator();
}
