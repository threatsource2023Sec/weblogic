package org.antlr.misc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MultiMap extends LinkedHashMap {
   public void map(Object key, Object value) {
      List elementsForKey = (List)this.get(key);
      if (elementsForKey == null) {
         elementsForKey = new ArrayList();
         super.put(key, elementsForKey);
      }

      ((List)elementsForKey).add(value);
   }
}
