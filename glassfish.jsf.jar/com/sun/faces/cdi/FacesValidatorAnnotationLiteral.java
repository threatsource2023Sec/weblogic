package com.sun.faces.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.faces.validator.FacesValidator;

class FacesValidatorAnnotationLiteral extends AnnotationLiteral implements FacesValidator {
   private static final long serialVersionUID = -6266044469152347882L;
   private String value;

   public FacesValidatorAnnotationLiteral(String value) {
      this.value = value;
   }

   public String value() {
      return this.value;
   }

   public boolean isDefault() {
      return false;
   }

   public boolean managed() {
      return true;
   }
}
