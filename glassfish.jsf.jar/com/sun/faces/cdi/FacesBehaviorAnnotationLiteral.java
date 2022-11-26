package com.sun.faces.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.faces.component.behavior.FacesBehavior;

class FacesBehaviorAnnotationLiteral extends AnnotationLiteral implements FacesBehavior {
   private static final long serialVersionUID = -258069073667018312L;
   private String value;

   public FacesBehaviorAnnotationLiteral(String value) {
      this.value = value;
   }

   public String value() {
      return this.value;
   }

   public boolean managed() {
      return true;
   }
}
