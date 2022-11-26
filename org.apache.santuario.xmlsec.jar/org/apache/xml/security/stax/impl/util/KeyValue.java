package org.apache.xml.security.stax.impl.util;

public class KeyValue {
   private Object key;
   private Object value;

   public KeyValue(Object key, Object value) {
      this.key = key;
      this.value = value;
   }

   public Object getKey() {
      return this.key;
   }

   public Object getValue() {
      return this.value;
   }
}
