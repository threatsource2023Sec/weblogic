package org.hibernate.validator.cfg;

public abstract class ConstraintDef extends AnnotationDef {
   protected ConstraintDef(Class constraintType) {
      super(constraintType);
   }

   protected ConstraintDef(ConstraintDef original) {
      super((AnnotationDef)original);
   }

   private ConstraintDef getThis() {
      return this;
   }

   public ConstraintDef message(String message) {
      this.addParameter("message", message);
      return this.getThis();
   }

   public ConstraintDef groups(Class... groups) {
      this.addParameter("groups", groups);
      return this.getThis();
   }

   public ConstraintDef payload(Class... payload) {
      this.addParameter("payload", payload);
      return this.getThis();
   }
}
