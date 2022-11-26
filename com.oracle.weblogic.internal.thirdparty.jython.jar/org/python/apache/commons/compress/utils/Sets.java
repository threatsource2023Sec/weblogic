package org.python.apache.commons.compress.utils;

import java.util.Collections;
import java.util.HashSet;

public class Sets {
   private Sets() {
   }

   public static HashSet newHashSet(Object... elements) {
      HashSet set = new HashSet(elements.length);
      Collections.addAll(set, elements);
      return set;
   }
}
