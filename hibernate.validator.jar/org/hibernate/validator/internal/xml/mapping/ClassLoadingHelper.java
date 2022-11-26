package org.hibernate.validator.internal.xml.mapping;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

class ClassLoadingHelper {
   private static final String PACKAGE_SEPARATOR = ".";
   private static final String ARRAY_CLASS_NAME_PREFIX = "[L";
   private static final String ARRAY_CLASS_NAME_SUFFIX = ";";
   private static final Map PRIMITIVE_NAME_TO_PRIMITIVE;
   private final ClassLoader externalClassLoader;
   private final ClassLoader threadContextClassLoader;

   ClassLoadingHelper(ClassLoader externalClassLoader, ClassLoader threadContextClassLoader) {
      this.externalClassLoader = externalClassLoader;
      this.threadContextClassLoader = threadContextClassLoader;
   }

   Class loadClass(String className, String defaultPackage) {
      if (PRIMITIVE_NAME_TO_PRIMITIVE.containsKey(className)) {
         return (Class)PRIMITIVE_NAME_TO_PRIMITIVE.get(className);
      } else {
         StringBuilder fullyQualifiedClass = new StringBuilder();
         String tmpClassName = className;
         if (isArrayClassName(className)) {
            fullyQualifiedClass.append("[L");
            tmpClassName = getArrayElementClassName(className);
         }

         if (isQualifiedClass(tmpClassName)) {
            fullyQualifiedClass.append(tmpClassName);
         } else {
            fullyQualifiedClass.append(defaultPackage);
            fullyQualifiedClass.append(".");
            fullyQualifiedClass.append(tmpClassName);
         }

         if (isArrayClassName(className)) {
            fullyQualifiedClass.append(";");
         }

         return this.loadClass(fullyQualifiedClass.toString());
      }
   }

   private Class loadClass(String className) {
      return (Class)run(LoadClass.action(className, this.externalClassLoader, this.threadContextClassLoader));
   }

   private static boolean isArrayClassName(String className) {
      return className.startsWith("[L") && className.endsWith(";");
   }

   private static String getArrayElementClassName(String className) {
      return className.substring(2, className.length() - 1);
   }

   private static boolean isQualifiedClass(String clazz) {
      return clazz.contains(".");
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   static {
      Map tmpMap = CollectionHelper.newHashMap(9);
      tmpMap.put(Boolean.TYPE.getName(), Boolean.TYPE);
      tmpMap.put(Character.TYPE.getName(), Character.TYPE);
      tmpMap.put(Double.TYPE.getName(), Double.TYPE);
      tmpMap.put(Float.TYPE.getName(), Float.TYPE);
      tmpMap.put(Long.TYPE.getName(), Long.TYPE);
      tmpMap.put(Integer.TYPE.getName(), Integer.TYPE);
      tmpMap.put(Short.TYPE.getName(), Short.TYPE);
      tmpMap.put(Byte.TYPE.getName(), Byte.TYPE);
      tmpMap.put(Void.TYPE.getName(), Void.TYPE);
      PRIMITIVE_NAME_TO_PRIMITIVE = Collections.unmodifiableMap(tmpMap);
   }
}
