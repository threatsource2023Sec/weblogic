package org.python.apache.commons.compress.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class Iterators {
   public static boolean addAll(Collection collection, Iterator iterator) {
      Objects.requireNonNull(collection);
      Objects.requireNonNull(iterator);

      boolean wasModified;
      for(wasModified = false; iterator.hasNext(); wasModified |= collection.add(iterator.next())) {
      }

      return wasModified;
   }

   private Iterators() {
   }
}
