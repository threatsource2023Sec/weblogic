package org.apache.openjpa.lib.conf;

import org.apache.commons.lang.StringUtils;

public class IntValue extends Value {
   private int value;

   public IntValue(String prop) {
      super(prop);
   }

   public Class getValueType() {
      return Integer.TYPE;
   }

   public void set(int value) {
      this.assertChangeable();
      int oldValue = this.value;
      this.value = value;
      if (value != oldValue) {
         this.valueChanged();
      }

   }

   public int get() {
      return this.value;
   }

   protected String getInternalString() {
      return String.valueOf(this.value);
   }

   protected void setInternalString(String val) {
      if (StringUtils.isEmpty(val)) {
         this.set(0);
      } else {
         this.set(Integer.parseInt(val));
      }

   }

   protected void setInternalObject(Object obj) {
      if (obj == null) {
         this.set(0);
      } else {
         this.set(((Number)obj).intValue());
      }

   }
}
