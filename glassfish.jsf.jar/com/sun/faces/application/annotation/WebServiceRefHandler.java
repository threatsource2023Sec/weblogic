package com.sun.faces.application.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.faces.context.FacesContext;
import javax.xml.ws.WebServiceRef;

class WebServiceRefHandler extends JndiHandler implements RuntimeAnnotationHandler {
   private Field[] fields;
   private WebServiceRef[] fieldAnnotations;
   private Method[] methods;
   private WebServiceRef[] methodAnnotations;

   public WebServiceRefHandler(Field[] fields, WebServiceRef[] fieldAnnotations, Method[] methods, WebServiceRef[] methodAnnotations) {
      this.fields = fields;
      this.fieldAnnotations = fieldAnnotations;
      this.methods = methods;
      this.methodAnnotations = methodAnnotations;
   }

   public void apply(FacesContext ctx, Object... params) {
      Object object = params[0];

      int i;
      for(i = 0; i < this.fields.length; ++i) {
         this.applyToField(ctx, this.fields[0], this.fieldAnnotations[0], object);
      }

      for(i = 0; i < this.methods.length; ++i) {
         this.applyToMethod(ctx, this.methods[i], this.methodAnnotations[i], object);
      }

   }

   private void applyToField(FacesContext facesContext, Field field, WebServiceRef ref, Object instance) {
      Object value = null;
      if (ref.name() != null && !"".equals(ref.name().trim())) {
         value = this.lookup(facesContext, "java:comp/env/" + ref.name());
      } else {
         value = this.lookup(facesContext, field.getName());
      }

      this.setField(facesContext, field, instance, value);
   }

   private void applyToMethod(FacesContext facesContext, Method method, WebServiceRef ref, Object instance) {
      if (method.getName().startsWith("set")) {
         Object value = null;
         if (ref.name() != null && !"".equals(ref.name().trim())) {
            value = this.lookup(facesContext, "java:comp/env/" + ref.name());
         }

         this.invokeMethod(facesContext, method, instance, value);
      }

   }
}
