package org.apache.openjpa.lib.conf;

import org.apache.commons.lang.StringUtils;

public class StringValue extends Value {
   private String value;

   public StringValue(String prop) {
      super(prop);
   }

   public Class getValueType() {
      return String.class;
   }

   public String get() {
      return this.value;
   }

   public void set(String value) {
      this.assertChangeable();
      String oldValue = this.value;
      this.value = value;
      if (!StringUtils.equals(value, oldValue)) {
         this.valueChanged();
      }

   }

   protected String getInternalString() {
      return this.get();
   }

   protected void setInternalString(String val) {
      this.set(val);
   }

   protected void setInternalObject(Object obj) {
      this.set((String)obj);
   }
}
