package com.sun.faces.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.faces.convert.FacesConverter;

class FacesConverterAnnotationLiteral extends AnnotationLiteral implements FacesConverter {
   private static final long serialVersionUID = -6822858461634741174L;
   private String value;
   private Class forClass;

   public FacesConverterAnnotationLiteral(String value, Class forClass) {
      this.value = value;
      this.forClass = forClass;
   }

   public String value() {
      return this.value;
   }

   public Class forClass() {
      return this.forClass;
   }

   public boolean managed() {
      return true;
   }
}
