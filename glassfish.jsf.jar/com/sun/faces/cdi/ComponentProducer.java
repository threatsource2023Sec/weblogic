package com.sun.faces.cdi;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ComponentProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public ComponentProducer() {
      super.name("component").beanClassAndType(UIComponent.class).create((e) -> {
         return UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());
      });
   }
}
