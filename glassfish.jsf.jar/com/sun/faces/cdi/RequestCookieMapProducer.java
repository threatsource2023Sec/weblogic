package com.sun.faces.cdi;

import java.lang.reflect.Type;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

public class RequestCookieMapProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public RequestCookieMapProducer() {
      super.name("cookie").scope(RequestScoped.class).qualifiers(new RequestCookieMapAnnotationLiteral()).types(new ParameterizedTypeImpl(Map.class, new Type[]{String.class, Object.class}), Map.class, Object.class).beanClass(Map.class).create((e) -> {
         return FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
      });
   }
}
