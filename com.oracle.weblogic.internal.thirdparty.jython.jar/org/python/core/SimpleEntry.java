package org.python.core;

import java.util.Map;

class SimpleEntry implements Map.Entry {
   protected Object key;
   protected Object value;

   public SimpleEntry(Object key, Object value) {
      this.key = key;
      this.value = value;
   }

   public Object getKey() {
      return this.key;
   }

   public Object getValue() {
      return this.value;
   }

   public boolean equals(Object o) {
      if (!(o instanceof Map.Entry)) {
         return false;
      } else {
         Map.Entry e = (Map.Entry)o;
         return eq(this.key, e.getKey()) && eq(this.value, e.getValue());
      }
   }

   private static boolean eq(Object o1, Object o2) {
      return o1 == null ? o2 == null : o1.equals(o2);
   }

   public int hashCode() {
      return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
   }

   public String toString() {
      return this.key + "=" + this.value;
   }

   public Object setValue(Object val) {
      throw new UnsupportedOperationException("Not supported by this view");
   }
}
