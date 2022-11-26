package com.sun.faces.application.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

class EJBHandler extends JndiHandler implements RuntimeAnnotationHandler {
   private static final String JAVA_MODULE = "java:module/";
   private Field[] fields;
   private EJB[] fieldAnnotations;
   private Method[] methods;
   private EJB[] methodAnnotations;

   public EJBHandler(Field[] fields, EJB[] fieldAnnotations, Method[] methods, EJB[] methodAnnotations) {
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

   private void applyToField(FacesContext facesContext, Field field, EJB ejb, Object instance) {
      Object value;
      if (ejb.lookup() != null && !"".equals(ejb.lookup().trim())) {
         value = this.lookup(facesContext, ejb.lookup());
      } else if (ejb.name() != null && !"".equals(ejb.name().trim())) {
         value = this.lookup(facesContext, "java:comp/env/" + ejb.name());
      } else {
         value = this.lookup(facesContext, "java:module/" + field.getType().getSimpleName());
      }

      this.setField(facesContext, field, instance, value);
   }

   private void applyToMethod(FacesContext facesContext, Method method, EJB ejb, Object instance) {
      if (method.getName().startsWith("set")) {
         Object value = null;
         if (ejb.lookup() != null && !"".equals(ejb.lookup().trim())) {
            value = this.lookup(facesContext, ejb.lookup());
         } else if (ejb.name() != null && !"".equals(ejb.name().trim())) {
            value = this.lookup(facesContext, "java:comp/env/" + ejb.name());
         }

         this.invokeMethod(facesContext, method, instance, value);
      }

   }
}
