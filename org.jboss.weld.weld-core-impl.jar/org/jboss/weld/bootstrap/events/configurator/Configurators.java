package org.jboss.weld.bootstrap.events.configurator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.inject.spi.Annotated;
import javax.inject.Qualifier;
import org.jboss.weld.util.collections.ImmutableSet;

class Configurators {
   private Configurators() {
   }

   static Set getQualifiers(Annotated annotated) {
      return (Set)annotated.getAnnotations().stream().filter((a) -> {
         return a.annotationType().isAnnotationPresent(Qualifier.class);
      }).collect(ImmutableSet.collector());
   }

   static Set getQualifiers(AnnotatedElement annotatedElement) {
      Set qualifiers = new HashSet();
      Annotation[] annotations = annotatedElement.getDeclaredAnnotations();
      Annotation[] var3 = annotations;
      int var4 = annotations.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var3[var5];
         if (annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
            qualifiers.add(annotation);
         }
      }

      return qualifiers;
   }

   @SafeVarargs
   static Set getAnnotatedParameters(Method method, Class... annotationClasses) {
      if (method.getParameterCount() == 0) {
         return Collections.emptySet();
      } else {
         Set annotatedParameters = new HashSet();
         Parameter[] parameters = method.getParameters();
         Parameter[] var4 = parameters;
         int var5 = parameters.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Parameter parameter = var4[var6];
            Class[] var8 = annotationClasses;
            int var9 = annotationClasses.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Class annotationClass = var8[var10];
               if (parameter.isAnnotationPresent(annotationClass)) {
                  annotatedParameters.add(parameter);
                  break;
               }
            }
         }

         return annotatedParameters;
      }
   }
}
