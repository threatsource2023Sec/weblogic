package com.sun.faces.application.annotation;

import javax.faces.component.FacesComponent;

public class FacesComponentUsage {
   private Class target;
   private FacesComponent annotation;

   public FacesComponentUsage(Class target, FacesComponent annotation) {
      this.target = target;
      this.annotation = annotation;
   }

   public FacesComponent getAnnotation() {
      return this.annotation;
   }

   public Class getTarget() {
      return this.target;
   }
}
