package org.python.google.common.collect;

import java.util.SortedMap;
import java.util.SortedSet;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class AbstractSortedKeySortedSetMultimap extends AbstractSortedSetMultimap {
   AbstractSortedKeySortedSetMultimap(SortedMap map) {
      super(map);
   }

   public SortedMap asMap() {
      return (SortedMap)super.asMap();
   }

   SortedMap backingMap() {
      return (SortedMap)super.backingMap();
   }

   public SortedSet keySet() {
      return (SortedSet)super.keySet();
   }
}
