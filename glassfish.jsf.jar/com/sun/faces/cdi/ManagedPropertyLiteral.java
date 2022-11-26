package com.sun.faces.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.faces.annotation.ManagedProperty;

class ManagedPropertyLiteral extends AnnotationLiteral implements ManagedProperty {
   private static final long serialVersionUID = 1L;
   private final String value;

   public ManagedPropertyLiteral() {
      this("");
   }

   public ManagedPropertyLiteral(String value) {
      this.value = value;
   }

   public String value() {
      return this.value;
   }
}
