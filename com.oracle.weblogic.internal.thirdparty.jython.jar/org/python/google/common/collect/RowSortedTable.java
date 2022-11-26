package org.python.google.common.collect;

import java.util.SortedMap;
import java.util.SortedSet;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface RowSortedTable extends Table {
   SortedSet rowKeySet();

   SortedMap rowMap();
}
