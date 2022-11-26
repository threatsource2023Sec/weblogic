package com.googlecode.cqengine.index.support;

public class KeyValueMaterialized implements KeyValue {
   final Object key;
   final Object value;

   public KeyValueMaterialized(Object key, Object value) {
      this.key = key;
      this.value = value;
   }

   public Object getKey() {
      return this.key;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      return this.key + "=" + this.value;
   }
}
