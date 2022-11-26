package org.glassfish.admin.rest.model;

public class ConstraintInfo {
   private ConstraintType type;
   private Object value;

   public ConstraintInfo(ConstraintType type, Object value) {
      this.type = type;
      this.value = value;
   }

   public ConstraintInfo(ConstraintType type) {
      this(type, (Object)null);
   }

   public ConstraintType getType() {
      return this.type;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      return "ConstraintInfo [type=" + this.getType() + ", value=" + this.getValue() + "]";
   }
}
