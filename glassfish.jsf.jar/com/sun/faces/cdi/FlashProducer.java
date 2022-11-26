package com.sun.faces.cdi;

import java.lang.reflect.Type;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class FlashProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public FlashProducer() {
      super.name("flash").beanClass(Flash.class).types(Flash.class, new ParameterizedTypeImpl(Map.class, new Type[]{Dummy.class, Dummy.class}), Object.class).scope(RequestScoped.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getFlash();
      });
   }

   private static class Dummy {
   }
}
