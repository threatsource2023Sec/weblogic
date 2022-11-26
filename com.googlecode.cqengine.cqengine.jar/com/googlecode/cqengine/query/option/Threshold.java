package com.googlecode.cqengine.query.option;

public class Threshold {
   final Object key;
   final Double value;

   public Threshold(Object key, Double value) {
      this.key = key;
      this.value = value;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Threshold)) {
         return false;
      } else {
         Threshold threshold = (Threshold)o;
         return !this.key.equals(threshold.key) ? false : this.value.equals(threshold.value);
      }
   }

   public int hashCode() {
      int result = this.key.hashCode();
      result = 31 * result + this.value.hashCode();
      return result;
   }
}
