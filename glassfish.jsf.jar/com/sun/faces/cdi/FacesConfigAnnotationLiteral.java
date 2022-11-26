package com.sun.faces.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.faces.annotation.FacesConfig;

public class FacesConfigAnnotationLiteral extends AnnotationLiteral implements FacesConfig {
   private static final long serialVersionUID = 1L;

   public FacesConfig.Version version() {
      return FacesConfig.Version.JSF_2_3;
   }
}
