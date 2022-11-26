package com.sun.faces.cdi;

import java.lang.reflect.Type;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

public class RequestParameterMapProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public RequestParameterMapProducer() {
      super.name("param").scope(RequestScoped.class).qualifiers(new RequestParameterMapAnnotationLiteral()).types(new ParameterizedTypeImpl(Map.class, new Type[]{String.class, String.class}), Map.class, Object.class).beanClass(Map.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      });
   }
}
