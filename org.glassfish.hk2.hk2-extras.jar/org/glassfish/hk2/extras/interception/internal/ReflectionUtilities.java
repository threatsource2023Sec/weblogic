package org.glassfish.hk2.extras.interception.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import org.glassfish.hk2.extras.interception.InterceptionBinder;

public class ReflectionUtilities {
   public static HashSet getAllBindingsFromMethod(Method m) {
      HashSet retVal = getAllBindingsFromClass(m.getDeclaringClass());
      Annotation[] allMethodAnnotations = m.getAnnotations();
      Annotation[] var3 = allMethodAnnotations;
      int var4 = allMethodAnnotations.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation aMethodAnnotation = var3[var5];
         if (isBindingAnnotation(aMethodAnnotation)) {
            getAllBinderAnnotations(aMethodAnnotation, retVal);
         }
      }

      return retVal;
   }

   public static HashSet getAllBindingsFromConstructor(Constructor m) {
      HashSet retVal = getAllBindingsFromClass(m.getDeclaringClass());
      Annotation[] allMethodAnnotations = m.getAnnotations();
      Annotation[] var3 = allMethodAnnotations;
      int var4 = allMethodAnnotations.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation aMethodAnnotation = var3[var5];
         if (isBindingAnnotation(aMethodAnnotation)) {
            getAllBinderAnnotations(aMethodAnnotation, retVal);
         }
      }

      return retVal;
   }

   public static HashSet getAllBindingsFromClass(Class c) {
      HashSet retVal = new HashSet();
      Annotation[] allClassAnnotations = c.getAnnotations();
      Annotation[] var3 = allClassAnnotations;
      int var4 = allClassAnnotations.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation aClassAnnotation = var3[var5];
         if (isBindingAnnotation(aClassAnnotation)) {
            getAllBinderAnnotations(aClassAnnotation, retVal);
         }
      }

      return retVal;
   }

   private static boolean isBindingAnnotation(Annotation a) {
      return a.annotationType().getAnnotation(InterceptionBinder.class) != null;
   }

   private static void getAllBinderAnnotations(Annotation a, HashSet retVal) {
      String aName = a.annotationType().getName();
      if (!retVal.contains(aName)) {
         retVal.add(aName);
         Annotation[] subAnnotations = a.annotationType().getAnnotations();
         Annotation[] var4 = subAnnotations;
         int var5 = subAnnotations.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Annotation subAnnotation = var4[var6];
            if (isBindingAnnotation(subAnnotation)) {
               String subName = subAnnotation.annotationType().getName();
               if (!retVal.contains(subName)) {
                  getAllBinderAnnotations(subAnnotation, retVal);
               }
            }
         }

      }
   }
}
