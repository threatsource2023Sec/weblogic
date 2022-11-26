package org.python.google.common.collect;

import java.util.SortedMap;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface SortedMapDifference extends MapDifference {
   SortedMap entriesOnlyOnLeft();

   SortedMap entriesOnlyOnRight();

   SortedMap entriesInCommon();

   SortedMap entriesDiffering();
}
