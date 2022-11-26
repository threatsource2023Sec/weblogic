package org.hibernate.validator.cfg;

public class GenericConstraintDef extends ConstraintDef {
   public GenericConstraintDef(Class constraintType) {
      super(constraintType);
   }

   public GenericConstraintDef param(String key, Object value) {
      this.addParameter(key, value);
      return this;
   }
}
