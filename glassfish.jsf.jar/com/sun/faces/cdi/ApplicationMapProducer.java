package com.sun.faces.cdi;

import java.lang.reflect.Type;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

public class ApplicationMapProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public ApplicationMapProducer() {
      super.name("applicationScope").scope(ApplicationScoped.class).qualifiers(new ApplicationMapAnnotationLiteral()).types(new ParameterizedTypeImpl(Map.class, new Type[]{String.class, Object.class}), Map.class, Object.class).beanClass(Map.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
      });
   }
}
