package com.sun.faces.cdi;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class ExternalContextProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public ExternalContextProducer() {
      super.name("externalContext").scope(RequestScoped.class).types(ExternalContext.class).beanClass(ExternalContext.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext();
      });
   }
}
