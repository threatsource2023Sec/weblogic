package com.sun.faces.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMap extends LinkedHashMap {
   private int maxCapacity;

   public LRUMap(int maxCapacity) {
      super(maxCapacity, 1.0F, true);
      this.maxCapacity = maxCapacity;
   }

   protected boolean removeEldestEntry(Map.Entry eldest) {
      return this.size() > this.maxCapacity;
   }
}
