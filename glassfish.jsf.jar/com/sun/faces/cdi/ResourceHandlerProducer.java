package com.sun.faces.cdi;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

public class ResourceHandlerProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public ResourceHandlerProducer() {
      super.name("resource").scope(RequestScoped.class).beanClassAndType(ResourceHandler.class).create((e) -> {
         return FacesContext.getCurrentInstance().getApplication().getResourceHandler();
      });
   }
}
