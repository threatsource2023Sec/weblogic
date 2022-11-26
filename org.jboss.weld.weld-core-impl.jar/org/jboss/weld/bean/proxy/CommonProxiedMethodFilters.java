package org.jboss.weld.bean.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.jboss.weld.util.reflection.Reflections;

public final class CommonProxiedMethodFilters {
   public static final ProxiedMethodFilter NON_STATIC = (m, c) -> {
      return !Modifier.isStatic(m.getModifiers());
   };
   public static final ProxiedMethodFilter NON_FINAL = (m, c) -> {
      return !Modifier.isFinal(m.getModifiers());
   };
   public static final ProxiedMethodFilter OBJECT_TO_STRING = (m, c) -> {
      return m.getDeclaringClass() != Object.class || m.getName().equals("toString");
   };
   public static final ProxiedMethodFilter NON_PRIVATE = (m, c) -> {
      return !Modifier.isPrivate(m.getModifiers());
   };
   public static final ProxiedMethodFilter NON_JDK_PACKAGE_PRIVATE = new ProxiedMethodFilter() {
      public boolean accept(Method method, Class proxySuperclass) {
         Class declaringClass = method.getDeclaringClass();
         if (declaringClass != null) {
            Package pack = declaringClass.getPackage();
            if (pack != null && pack.getName().startsWith("java")) {
               if (Reflections.isPackagePrivate(method.getModifiers())) {
                  return false;
               }

               Class[] parameterTypes = method.getParameterTypes();
               Class[] var6 = parameterTypes;
               int var7 = parameterTypes.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  Class parameterType = var6[var8];
                  if (Reflections.isPackagePrivate(parameterType.getModifiers())) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   };

   private CommonProxiedMethodFilters() {
   }
}
