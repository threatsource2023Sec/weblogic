package com.googlecode.cqengine.index.support;

public class KeyStatistics {
   final Object key;
   final Integer count;

   public KeyStatistics(Object key, Integer count) {
      this.key = key;
      this.count = count;
   }

   public Object getKey() {
      return this.key;
   }

   public Integer getCount() {
      return this.count;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof KeyStatistics)) {
         return false;
      } else {
         KeyStatistics that = (KeyStatistics)o;
         return this.count != that.count ? false : this.key.equals(that.key);
      }
   }

   public int hashCode() {
      int result = this.key.hashCode();
      result = 31 * result + this.count;
      return result;
   }

   public String toString() {
      return "KeyStatistics{key=" + this.key + ", count=" + this.count + '}';
   }
}
