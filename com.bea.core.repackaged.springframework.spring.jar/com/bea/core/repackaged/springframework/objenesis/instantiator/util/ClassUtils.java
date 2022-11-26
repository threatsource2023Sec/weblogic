package com.bea.core.repackaged.springframework.objenesis.instantiator.util;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;

public final class ClassUtils {
   private ClassUtils() {
   }

   public static String classNameToInternalClassName(String className) {
      return className.replace('.', '/');
   }

   public static String classNameToResource(String className) {
      return classNameToInternalClassName(className) + ".class";
   }

   public static Class getExistingClass(ClassLoader classLoader, String className) {
      try {
         return Class.forName(className, true, classLoader);
      } catch (ClassNotFoundException var3) {
         return null;
      }
   }

   public static Object newInstance(Class clazz) {
      try {
         return clazz.newInstance();
      } catch (IllegalAccessException | InstantiationException var2) {
         throw new ObjenesisException(var2);
      }
   }
}
