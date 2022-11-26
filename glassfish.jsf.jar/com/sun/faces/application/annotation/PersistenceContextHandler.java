package com.sun.faces.application.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceContext;

class PersistenceContextHandler extends JndiHandler implements RuntimeAnnotationHandler {
   private Method[] methods;
   private PersistenceContext[] methodAnnotations;
   private Field[] fields;
   private PersistenceContext[] fieldAnnotations;

   public PersistenceContextHandler(Method[] methods, PersistenceContext[] methodAnnotations, Field[] fields, PersistenceContext[] fieldAnnotations) {
      this.methods = methods;
      this.methodAnnotations = methodAnnotations;
      this.fields = fields;
      this.fieldAnnotations = fieldAnnotations;
   }

   public void apply(FacesContext ctx, Object... params) {
      Object object = params[0];

      int i;
      for(i = 0; i < this.fields.length; ++i) {
         this.applyToField(ctx, this.fields[i], this.fieldAnnotations[i], object);
      }

      for(i = 0; i < this.methods.length; ++i) {
         this.applyToMethod(ctx, this.methods[i], this.methodAnnotations[i], object);
      }

   }

   private void applyToMethod(FacesContext facesContext, Method method, PersistenceContext unit, Object instance) {
      if (method.getName().startsWith("set")) {
         Object value = null;
         if (unit.name() != null && !"".equals(unit.name().trim())) {
            value = this.lookup(facesContext, "java:comp/env/" + unit.name());
         }

         this.invokeMethod(facesContext, method, instance, value);
      }

   }

   private void applyToField(FacesContext facesContext, Field field, PersistenceContext unit, Object instance) {
      Object value;
      if (unit.name() != null && !"".equals(unit.name().trim())) {
         value = this.lookup(facesContext, "java:comp/env/" + unit.name());
      } else {
         value = this.lookup(facesContext, field.getType().getSimpleName());
      }

      this.setField(facesContext, field, instance, value);
   }
}
