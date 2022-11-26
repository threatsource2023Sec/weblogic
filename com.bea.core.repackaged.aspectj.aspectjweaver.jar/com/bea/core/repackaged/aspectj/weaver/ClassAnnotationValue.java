package com.bea.core.repackaged.aspectj.weaver;

public class ClassAnnotationValue extends AnnotationValue {
   private String signature;

   public ClassAnnotationValue(String sig) {
      super(99);
      this.signature = sig;
   }

   public String stringify() {
      return this.signature;
   }

   public String toString() {
      return this.signature;
   }
}
