package com.sun.faces.cdi;

import java.lang.reflect.Type;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

public class HeaderMapProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public HeaderMapProducer() {
      super.name("header").scope(RequestScoped.class).qualifiers(new HeaderMapAnnotationLiteral()).types(new ParameterizedTypeImpl(Map.class, new Type[]{String.class, String.class}), Map.class, Object.class).beanClass(Map.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
      });
   }
}
