package com.sun.faces.cdi;

import java.lang.reflect.Type;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

public class RequestParameterValuesMapProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public RequestParameterValuesMapProducer() {
      super.name("paramValues").scope(RequestScoped.class).qualifiers(new RequestParameterValuesMapAnnotationLiteral()).types(new ParameterizedTypeImpl(Map.class, new Type[]{String.class, String[].class}), Map.class, Object.class).beanClass(Map.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
      });
   }
}
