package com.sun.faces.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.faces.model.FacesDataModel;

class FacesDataModelAnnotationLiteral extends AnnotationLiteral implements FacesDataModel {
   private static final long serialVersionUID = 1L;
   private final Class forClass;

   public FacesDataModelAnnotationLiteral(Class forClass) {
      this.forClass = forClass;
   }

   public Class forClass() {
      return this.forClass;
   }
}
