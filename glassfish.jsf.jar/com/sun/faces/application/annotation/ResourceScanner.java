package com.sun.faces.application.annotation;

import com.sun.faces.util.Util;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.annotation.Resource;

class ResourceScanner implements Scanner {
   public Class getAnnotation() {
      return Resource.class;
   }

   public RuntimeAnnotationHandler scan(Class clazz) {
      Util.notNull("clazz", clazz);
      ResourceHandler handler = null;
      ArrayList fieldAnnotations = new ArrayList();
      ArrayList fields = new ArrayList();
      Field[] var5 = clazz.getDeclaredFields();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Field field = var5[var7];
         Resource fieldAnnotation = (Resource)field.getAnnotation(Resource.class);
         if (fieldAnnotation != null) {
            fieldAnnotations.add(fieldAnnotation);
            fields.add(field);
         }
      }

      ArrayList methodAnnotations = new ArrayList();
      ArrayList methods = new ArrayList();
      Method[] var14 = clazz.getDeclaredMethods();
      int var15 = var14.length;

      for(int var16 = 0; var16 < var15; ++var16) {
         Method method = var14[var16];
         Resource methodAnnotation = (Resource)method.getAnnotation(Resource.class);
         if (methodAnnotation != null) {
            methodAnnotations.add(methodAnnotation);
            methods.add(method);
         }
      }

      if (!fieldAnnotations.isEmpty() || !methodAnnotations.isEmpty()) {
         handler = new ResourceHandler((Field[])fields.toArray(new Field[0]), (Resource[])((Resource[])fieldAnnotations.toArray(new Resource[0])), (Method[])methods.toArray(new Method[0]), (Resource[])((Resource[])methodAnnotations.toArray(new Resource[0])));
      }

      return handler;
   }
}
