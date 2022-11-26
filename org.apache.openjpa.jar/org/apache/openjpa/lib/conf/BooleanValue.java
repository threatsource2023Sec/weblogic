package org.apache.openjpa.lib.conf;

public class BooleanValue extends Value {
   private boolean value;

   public BooleanValue(String prop) {
      super(prop);
      this.setAliasListComprehensive(true);
   }

   public Class getValueType() {
      return Boolean.TYPE;
   }

   public void set(boolean value) {
      this.assertChangeable();
      boolean oldValue = this.value;
      this.value = value;
      if (oldValue != value) {
         this.valueChanged();
      }

   }

   public boolean get() {
      return this.value;
   }

   protected String getInternalString() {
      return String.valueOf(this.value);
   }

   protected void setInternalString(String val) {
      this.set(Boolean.valueOf(val));
   }

   protected void setInternalObject(Object obj) {
      if (obj == null) {
         this.set(false);
      } else {
         this.set((Boolean)obj);
      }

   }
}
