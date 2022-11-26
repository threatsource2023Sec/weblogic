package com.sun.faces.cdi;

import java.lang.reflect.Type;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

public class SessionMapProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public SessionMapProducer() {
      super.name("sessionScope").scope(RequestScoped.class).qualifiers(new SessionMapAnnotationLiteral()).types(new ParameterizedTypeImpl(Map.class, new Type[]{String.class, Object.class}), Map.class, Object.class).beanClass(Map.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
      });
   }
}
