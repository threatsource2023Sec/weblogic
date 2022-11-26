package com.sun.faces.cdi;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class ViewProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public ViewProducer() {
      super.name("view").scope(RequestScoped.class).types(UIViewRoot.class).beanClass(UIViewRoot.class).create((e) -> {
         return FacesContext.getCurrentInstance().getViewRoot();
      });
   }
}
