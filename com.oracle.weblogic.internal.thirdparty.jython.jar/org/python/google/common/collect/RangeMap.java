package org.python.google.common.collect;

import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;

@Beta
@GwtIncompatible
public interface RangeMap {
   @Nullable
   Object get(Comparable var1);

   @Nullable
   Map.Entry getEntry(Comparable var1);

   Range span();

   void put(Range var1, Object var2);

   void putCoalescing(Range var1, Object var2);

   void putAll(RangeMap var1);

   void clear();

   void remove(Range var1);

   Map asMapOfRanges();

   Map asDescendingMapOfRanges();

   RangeMap subRangeMap(Range var1);

   boolean equals(@Nullable Object var1);

   int hashCode();

   String toString();
}
