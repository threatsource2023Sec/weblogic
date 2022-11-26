package com.sun.faces.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMap extends LinkedHashMap {
   private static final long serialVersionUID = -7137951139094651602L;
   private int maxCapacity;

   public LRUMap(int maxCapacity) {
      super(maxCapacity, 1.0F, true);
      this.maxCapacity = maxCapacity;
   }

   protected boolean removeEldestEntry(Map.Entry eldest) {
      return this.size() > this.maxCapacity;
   }
}
