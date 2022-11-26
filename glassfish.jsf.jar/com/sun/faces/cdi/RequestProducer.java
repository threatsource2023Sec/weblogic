package com.sun.faces.cdi;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

public class RequestProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public RequestProducer() {
      super.name("request").scope(RequestScoped.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getRequest();
      });
   }
}
