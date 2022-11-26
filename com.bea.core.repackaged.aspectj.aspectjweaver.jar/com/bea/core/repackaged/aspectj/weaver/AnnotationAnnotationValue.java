package com.bea.core.repackaged.aspectj.weaver;

public class AnnotationAnnotationValue extends AnnotationValue {
   private AnnotationAJ value;

   public AnnotationAnnotationValue(AnnotationAJ value) {
      super(64);
      this.value = value;
   }

   public AnnotationAJ getAnnotation() {
      return this.value;
   }

   public String stringify() {
      return this.value.stringify();
   }

   public String toString() {
      return this.value.toString();
   }
}
