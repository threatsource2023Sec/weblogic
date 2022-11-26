package org.jboss.weld.bootstrap.events;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.jboss.weld.annotated.slim.backed.BackedAnnotatedType;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.resources.ReflectionCache;
import org.jboss.weld.util.reflection.Reflections;

public class RequiredAnnotationDiscovery implements Service {
   private final ReflectionCache cache;

   public RequiredAnnotationDiscovery(ReflectionCache cache) {
      this.cache = cache;
   }

   public boolean containsAnnotation(BackedAnnotatedType annotatedType, Class requiredAnnotation) {
      if (this.containsAnnotation((Iterable)annotatedType.getAnnotations(), requiredAnnotation, true)) {
         return true;
      } else {
         int var6;
         int var10;
         for(Class clazz = annotatedType.getJavaClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] var4 = clazz.getDeclaredFields();
            int var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               Field field = var4[var6];
               if (this.containsAnnotations((Iterable)this.cache.getAnnotations(field), requiredAnnotation)) {
                  return true;
               }
            }

            Constructor[] var14 = clazz.getDeclaredConstructors();
            var5 = var14.length;

            Annotation[][] var8;
            int var9;
            Annotation[] parameterAnnotations;
            for(var6 = 0; var6 < var5; ++var6) {
               Constructor constructor = var14[var6];
               if (this.containsAnnotations((Iterable)this.cache.getAnnotations(constructor), requiredAnnotation)) {
                  return true;
               }

               var8 = constructor.getParameterAnnotations();
               var9 = var8.length;

               for(var10 = 0; var10 < var9; ++var10) {
                  parameterAnnotations = var8[var10];
                  if (this.containsAnnotations(parameterAnnotations, requiredAnnotation)) {
                     return true;
                  }
               }
            }

            Method[] var15 = clazz.getDeclaredMethods();
            var5 = var15.length;

            for(var6 = 0; var6 < var5; ++var6) {
               Method method = var15[var6];
               if (this.containsAnnotations((Iterable)this.cache.getAnnotations(method), requiredAnnotation)) {
                  return true;
               }

               var8 = method.getParameterAnnotations();
               var9 = var8.length;

               for(var10 = 0; var10 < var9; ++var10) {
                  parameterAnnotations = var8[var10];
                  if (this.containsAnnotations(parameterAnnotations, requiredAnnotation)) {
                     return true;
                  }
               }
            }
         }

         Iterator var13 = Reflections.getInterfaceClosure(annotatedType.getJavaClass()).iterator();

         while(var13.hasNext()) {
            Class interfaceClazz = (Class)var13.next();
            Method[] var17 = interfaceClazz.getDeclaredMethods();
            var6 = var17.length;

            for(int var20 = 0; var20 < var6; ++var20) {
               Method method = var17[var20];
               if (Reflections.isDefault(method)) {
                  if (this.containsAnnotations((Iterable)this.cache.getAnnotations(method), requiredAnnotation)) {
                     return true;
                  }

                  Annotation[][] var22 = method.getParameterAnnotations();
                  var10 = var22.length;

                  for(int var23 = 0; var23 < var10; ++var23) {
                     Annotation[] parameterAnnotations = var22[var23];
                     if (this.containsAnnotations(parameterAnnotations, requiredAnnotation)) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   private boolean containsAnnotations(Annotation[] annotations, Class requiredAnnotation) {
      return this.containsAnnotation(annotations, requiredAnnotation, true);
   }

   private boolean containsAnnotation(Annotation[] annotations, Class requiredAnnotation, boolean checkMetaAnnotations) {
      Annotation[] var4 = annotations;
      int var5 = annotations.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Annotation annotation = var4[var6];
         if (this.containsAnnotation(annotation, requiredAnnotation, checkMetaAnnotations)) {
            return true;
         }
      }

      return false;
   }

   private boolean containsAnnotations(Iterable annotations, Class requiredAnnotation) {
      return this.containsAnnotation(annotations, requiredAnnotation, true);
   }

   private boolean containsAnnotation(Iterable annotations, Class requiredAnnotation, boolean checkMetaAnnotations) {
      Iterator var4 = annotations.iterator();

      Annotation annotation;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         annotation = (Annotation)var4.next();
      } while(!this.containsAnnotation(annotation, requiredAnnotation, checkMetaAnnotations));

      return true;
   }

   private boolean containsAnnotation(Annotation annotation, Class requiredAnnotation, boolean checkMetaAnnotations) {
      Class annotationType = annotation.annotationType();
      if (requiredAnnotation.equals(annotationType)) {
         return true;
      } else {
         return checkMetaAnnotations && this.containsAnnotation((Iterable)this.cache.getAnnotations(annotationType), requiredAnnotation, false);
      }
   }

   public void cleanup() {
   }
}
