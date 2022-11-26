package org.apache.openjpa.lib.conf;

import org.apache.commons.lang.StringUtils;

public class DoubleValue extends Value {
   private double value;

   public DoubleValue(String prop) {
      super(prop);
   }

   public Class getValueType() {
      return Double.TYPE;
   }

   public void set(double value) {
      this.assertChangeable();
      double oldValue = this.value;
      this.value = value;
      if (oldValue != value) {
         this.valueChanged();
      }

   }

   public double get() {
      return this.value;
   }

   protected String getInternalString() {
      return String.valueOf(this.value);
   }

   protected void setInternalString(String val) {
      if (StringUtils.isEmpty(val)) {
         this.set(0.0);
      } else {
         this.set(Double.parseDouble(val));
      }

   }

   protected void setInternalObject(Object obj) {
      if (obj == null) {
         this.set(0.0);
      } else {
         this.set(((Number)obj).doubleValue());
      }

   }
}
