package com.sun.faces.cdi;

import java.lang.reflect.Type;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;

public class FlowMapProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public FlowMapProducer() {
      super.name("flowScope").scope(FlowScoped.class).qualifiers(new FlowMapAnnotationLiteral()).types(new ParameterizedTypeImpl(Map.class, new Type[]{Object.class, Object.class}), Map.class, Object.class).beanClass(Map.class).create((e) -> {
         return FacesContext.getCurrentInstance().getApplication().getFlowHandler().getCurrentFlowScope();
      });
   }
}
