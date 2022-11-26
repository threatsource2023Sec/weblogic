package com.sun.faces.cdi;

import java.lang.reflect.Type;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

public class RequestMapProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public RequestMapProducer() {
      super.name("requestScope").scope(RequestScoped.class).qualifiers(new RequestMapAnnotationLiteral()).types(new ParameterizedTypeImpl(Map.class, new Type[]{String.class, Object.class}), Map.class, Object.class).beanClass(Map.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
      });
   }
}
