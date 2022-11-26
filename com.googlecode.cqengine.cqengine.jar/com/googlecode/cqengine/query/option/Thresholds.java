package com.googlecode.cqengine.query.option;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Thresholds {
   final Map thresholds = new LinkedHashMap();

   public Thresholds(Collection thresholds) {
      Iterator var2 = thresholds.iterator();

      while(var2.hasNext()) {
         Threshold threshold = (Threshold)var2.next();
         this.thresholds.put(threshold.key, threshold.value);
      }

   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Thresholds)) {
         return false;
      } else {
         Thresholds that = (Thresholds)o;
         return this.thresholds.equals(that.thresholds);
      }
   }

   public int hashCode() {
      return this.thresholds.hashCode();
   }

   public Double getThreshold(Object key) {
      return (Double)this.thresholds.get(key);
   }

   public String toString() {
      return this.thresholds.toString();
   }

   public static Double getThreshold(QueryOptions queryOptions, Object key) {
      Thresholds thresholds = (Thresholds)queryOptions.get(Thresholds.class);
      return thresholds == null ? null : thresholds.getThreshold(key);
   }
}
