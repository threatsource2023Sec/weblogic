package com.sun.faces.cdi;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

public class FacesContextProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public FacesContextProducer() {
      super.name("facesContext").scope(RequestScoped.class).types(FacesContext.class).beanClass(FacesContext.class).create((e) -> {
         return FacesContext.getCurrentInstance();
      });
   }
}
