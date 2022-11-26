package com.sun.faces.application.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;

class ResourceHandler extends JndiHandler {
   private Field[] fields;
   private Resource[] fieldAnnotations;
   private Method[] methods;
   private Resource[] methodAnnotations;

   public ResourceHandler(Field[] fields, Resource[] fieldAnnotations, Method[] methods, Resource[] methodAnnotations) {
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

   private void applyToField(FacesContext facesContext, Field field, Resource resource, Object instance) {
      Object value;
      if (resource.name() != null && !"".equals(resource.name().trim())) {
         value = this.lookup(facesContext, "java:comp/env/" + resource.name());
      } else {
         value = this.lookup(facesContext, field.getName());
      }

      this.setField(facesContext, field, instance, value);
   }

   private void applyToMethod(FacesContext facesContext, Method method, Resource resource, Object instance) {
      if (method.getName().startsWith("set")) {
         Object value = null;
         if (resource.name() != null && !"".equals(resource.name().trim())) {
            value = this.lookup(facesContext, "java:comp/env/" + resource.name());
         }

         this.invokeMethod(facesContext, method, instance, value);
      }

   }
}
