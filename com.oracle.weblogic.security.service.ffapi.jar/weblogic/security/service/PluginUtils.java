package weblogic.security.service;

import weblogic.security.SecurityLogger;

public final class PluginUtils {
   public static Object createPlugin(Class assignableType, String propertyName) {
      String implClassName = System.getProperty(propertyName);
      if (implClassName != null && implClassName.length() >= 1) {
         String classType = assignableType.getName();
         Package pkg = assignableType.getPackage();
         if (pkg != null) {
            int pkgNameLen = pkg.getName().length();
            ++pkgNameLen;
            classType = assignableType.getName().substring(pkgNameLen);
         }

         try {
            Class implClass = Class.forName(implClassName);
            if (assignableType.isAssignableFrom(implClass)) {
               try {
                  return implClass.newInstance();
               } catch (Throwable var7) {
                  throw new IllegalArgumentException(SecurityLogger.getCannotInstantiateClass(classType, implClassName, propertyName, var7));
               }
            } else {
               throw new IllegalArgumentException(SecurityLogger.getClassNotAssignable(classType, implClassName, assignableType.getName(), propertyName));
            }
         } catch (Throwable var8) {
            throw new IllegalArgumentException(SecurityLogger.getCannotLoadClass(classType, implClassName, propertyName, var8));
         }
      } else {
         return null;
      }
   }
}
