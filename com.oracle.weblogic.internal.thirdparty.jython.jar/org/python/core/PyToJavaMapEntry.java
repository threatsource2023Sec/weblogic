package org.python.core;

import java.util.Map;

class PyToJavaMapEntry extends SimpleEntry {
   PyToJavaMapEntry(Map.Entry entry) {
      super(entry.getKey(), entry.getValue());
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof Map.Entry) {
         Map.Entry me = new JavaToPyMapEntry((Map.Entry)o);
         return o.equals(me);
      } else {
         return false;
      }
   }

   public Object getKey() {
      return AbstractDict.tojava(this.key);
   }

   public Object getValue() {
      return AbstractDict.tojava(this.value);
   }

   public Map.Entry getEntry() {
      return new SimpleEntry(this.key, this.value);
   }

   public int hashCode() {
      return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
   }
}
