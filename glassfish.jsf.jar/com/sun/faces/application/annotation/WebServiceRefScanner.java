package com.sun.faces.application.annotation;

import com.sun.faces.util.Util;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.xml.ws.WebServiceRef;

class WebServiceRefScanner implements Scanner {
   public Class getAnnotation() {
      return WebServiceRef.class;
   }

   public RuntimeAnnotationHandler scan(Class clazz) {
      Util.notNull("clazz", clazz);
      WebServiceRefHandler handler = null;
      ArrayList classAnnotations = new ArrayList();
      WebServiceRef classAnnotation = (WebServiceRef)clazz.getAnnotation(WebServiceRef.class);
      if (classAnnotation != null) {
         classAnnotations.add(classAnnotation);
      }

      ArrayList fieldAnnotations = new ArrayList();
      ArrayList fields = new ArrayList();
      Field[] var7 = clazz.getDeclaredFields();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Field field = var7[var9];
         WebServiceRef fieldAnnotation = (WebServiceRef)field.getAnnotation(WebServiceRef.class);
         if (fieldAnnotation != null) {
            fieldAnnotations.add(fieldAnnotation);
            fields.add(field);
         }
      }

      ArrayList methodAnnotations = new ArrayList();
      ArrayList methods = new ArrayList();
      Method[] var16 = clazz.getDeclaredMethods();
      int var17 = var16.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         Method method = var16[var18];
         WebServiceRef methodAnnotation = (WebServiceRef)method.getAnnotation(WebServiceRef.class);
         if (methodAnnotation != null) {
            methodAnnotations.add(methodAnnotation);
            methods.add(method);
         }
      }

      if (!classAnnotations.isEmpty() || !fieldAnnotations.isEmpty()) {
         handler = new WebServiceRefHandler((Field[])fields.toArray(new Field[0]), (WebServiceRef[])((WebServiceRef[])fieldAnnotations.toArray(new WebServiceRef[0])), (Method[])methods.toArray(new Method[0]), (WebServiceRef[])((WebServiceRef[])methodAnnotations.toArray(new WebServiceRef[0])));
      }

      return handler;
   }
}
