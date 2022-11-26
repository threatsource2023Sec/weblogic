package org.python.google.common.collect;

import java.util.Map;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   emulated = true
)
abstract class HashMultimapGwtSerializationDependencies extends AbstractSetMultimap {
   HashMultimapGwtSerializationDependencies(Map map) {
      super(map);
   }
}
