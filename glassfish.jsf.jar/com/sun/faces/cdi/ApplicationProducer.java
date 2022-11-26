package com.sun.faces.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

public class ApplicationProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public ApplicationProducer() {
      super.name("application").scope(ApplicationScoped.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getContext();
      });
   }
}
