package weblogic.ejb.container.utils;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;

public final class ClassUtils {
   private static final byte[] byteArray = new byte[1];

   public static String classToJavaSourceType(Class type) {
      int arrayDim;
      for(arrayDim = 0; type.isArray(); type = type.getComponentType()) {
         ++arrayDim;
      }

      StringBuilder sb = new StringBuilder(type.getName());

      for(Class dc = type.getDeclaringClass(); dc != null; dc = dc.getDeclaringClass()) {
         sb.setCharAt(dc.getName().length(), '.');
      }

      for(int i = 0; i < arrayDim; ++i) {
         sb.append("[]");
      }

      return sb.toString();
   }

   public static Class nameToClass(String typeName, ClassLoader classLoader) throws ClassNotFoundException {
      if (typeName.equals("int")) {
         return Integer.TYPE;
      } else if (typeName.equals("float")) {
         return Float.TYPE;
      } else if (typeName.equals("double")) {
         return Double.TYPE;
      } else if (typeName.equals("char")) {
         return Character.TYPE;
      } else if (typeName.equals("boolean")) {
         return Boolean.TYPE;
      } else if (typeName.equals("byte")) {
         return Byte.TYPE;
      } else if (typeName.equals("long")) {
         return Long.TYPE;
      } else if (typeName.equals("short")) {
         return Short.TYPE;
      } else {
         return classLoader != null ? classLoader.loadClass(typeName) : Class.forName(typeName);
      }
   }

   public static boolean isPrimitiveOrImmutable(Class clazz) {
      if (isObjectPrimitive(clazz)) {
         return true;
      } else if (clazz.isPrimitive() && !clazz.equals(Void.TYPE)) {
         return true;
      } else if (clazz.equals(String.class)) {
         return true;
      } else if (clazz.equals(BigInteger.class)) {
         return true;
      } else {
         return clazz.equals(BigDecimal.class);
      }
   }

   public static Class getPrimitiveClass(Class clazz) {
      if (clazz.isPrimitive()) {
         return clazz;
      } else if (!isObjectPrimitive(clazz)) {
         return null;
      } else if (clazz.equals(Integer.class)) {
         return Integer.TYPE;
      } else if (clazz.equals(Long.class)) {
         return Long.TYPE;
      } else if (clazz.equals(Float.class)) {
         return Float.TYPE;
      } else if (clazz.equals(Double.class)) {
         return Double.TYPE;
      } else if (clazz.equals(Byte.class)) {
         return Byte.TYPE;
      } else if (clazz.equals(Short.class)) {
         return Short.TYPE;
      } else if (clazz.equals(Boolean.class)) {
         return Boolean.TYPE;
      } else {
         return clazz.equals(Character.class) ? Character.TYPE : null;
      }
   }

   public static boolean isObjectPrimitive(Class type) {
      if (type.equals(Integer.class)) {
         return true;
      } else if (type.equals(Long.class)) {
         return true;
      } else if (type.equals(Float.class)) {
         return true;
      } else if (type.equals(Double.class)) {
         return true;
      } else if (type.equals(Byte.class)) {
         return true;
      } else if (type.equals(Short.class)) {
         return true;
      } else if (type.equals(Boolean.class)) {
         return true;
      } else {
         return type.equals(Character.class);
      }
   }

   public static String getDefaultValue(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "false";
         } else if (type == Byte.TYPE) {
            return "(byte)0";
         } else if (type == Character.TYPE) {
            return "'\\u0000'";
         } else if (type == Double.TYPE) {
            return "0.0d";
         } else if (type == Float.TYPE) {
            return "0.0f";
         } else if (type == Integer.TYPE) {
            return "0";
         } else if (type == Long.TYPE) {
            return "0L";
         } else {
            return type == Short.TYPE ? "(short)0" : "";
         }
      } else {
         return "null";
      }
   }

   public static String makeLegalName(String s) {
      StringBuilder result = new StringBuilder();
      int n = s.length();

      for(int i = 0; i < n; ++i) {
         char c = s.charAt(i);
         if (Character.isLetterOrDigit(c)) {
            if (i == 0 && Character.isDigit(c)) {
               result.append('_');
            }

            result.append(c);
         } else {
            result.append("_");
         }
      }

      return result.toString();
   }

   public static String getSQLTypeForClass(Class type) throws EJBCException {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return "java.sql.Types.BINARY";
         }

         if (type == Byte.TYPE) {
            return "java.sql.Types.INTEGER";
         }

         if (type == Character.TYPE) {
            return "java.sql.Types.CHAR";
         }

         if (type == Double.TYPE) {
            return "java.sql.Types.DOUBLE";
         }

         if (type == Float.TYPE) {
            return "java.sql.Types.FLOAT";
         }

         if (type == Integer.TYPE) {
            return "java.sql.Types.INTEGER";
         }

         if (type == Long.TYPE) {
            return "java.sql.Types.INTEGER";
         }

         if (type == Short.TYPE) {
            return "java.sql.Types.INTEGER";
         }
      } else {
         if (type == String.class) {
            return "java.sql.Types.VARCHAR";
         }

         if (type == BigDecimal.class) {
            return "java.sql.Types.NUMERIC";
         }

         if (type == Boolean.class) {
            return "java.sql.Types.BINARY";
         }

         if (type == Byte.class) {
            return "java.sql.Types.INTEGER";
         }

         if (type == Character.class) {
            return "java.sql.Types.CHAR";
         }

         if (type == Double.class) {
            return "java.sql.Types.DOUBLE";
         }

         if (type == Float.class) {
            return "java.sql.Types.FLOAT";
         }

         if (type == Integer.class) {
            return "java.sql.Types.INTEGER";
         }

         if (type == Long.class) {
            return "java.sql.Types.INTEGER";
         }

         if (type == Short.class) {
            return "java.sql.Types.INTEGER";
         }

         if (type == Date.class) {
            return "java.sql.Types.DATE";
         }

         if (type == java.sql.Date.class) {
            return "java.sql.Types.DATE";
         }

         if (type == Time.class) {
            return "java.sql.Types.TIME";
         }

         if (type == Timestamp.class) {
            return "java.sql.Types.TIMESTAMP";
         }

         if (type == byteArray.getClass()) {
            return "java.sql.Types.VARBINARY";
         }

         if (Serializable.class.isAssignableFrom(type)) {
            return "java.sql.Types.VARBINARY";
         }
      }

      throw new EJBCException("CMP20 Could not handle a SQL type in TypeUtils.getSQLTypeForClass:  type = " + type);
   }

   public static boolean isValidSQLType(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return true;
         }

         if (type == Byte.TYPE) {
            return true;
         }

         if (type == Character.TYPE) {
            return true;
         }

         if (type == Double.TYPE) {
            return true;
         }

         if (type == Float.TYPE) {
            return true;
         }

         if (type == Integer.TYPE) {
            return true;
         }

         if (type == Long.TYPE) {
            return true;
         }

         if (type == Short.TYPE) {
            return true;
         }
      } else {
         if (type == String.class) {
            return true;
         }

         if (type == Boolean.class) {
            return true;
         }

         if (type == Byte.class) {
            return true;
         }

         if (type == Character.class) {
            return true;
         }

         if (type == Double.class) {
            return true;
         }

         if (type == Float.class) {
            return true;
         }

         if (type == Integer.class) {
            return true;
         }

         if (type == Long.class) {
            return true;
         }

         if (type == Short.class) {
            return true;
         }

         if (type == Date.class) {
            return true;
         }

         if (type == java.sql.Date.class) {
            return true;
         }

         if (type == Time.class) {
            return true;
         }

         if (type == Timestamp.class) {
            return true;
         }

         if (type == BigDecimal.class) {
            return true;
         }

         if (type == byteArray.getClass()) {
            return true;
         }
      }

      return false;
   }

   public static boolean isByteArray(Class type) {
      return type.isArray() && type.getComponentType() == Byte.TYPE;
   }

   public static String setClassName(CMPBeanDescriptor bd, String field) {
      return MethodUtils.tail(bd.getGeneratedBeanClassName()) + "_" + field + "_Set";
   }

   public static String iteratorClassName(CMPBeanDescriptor bd, String field) {
      return MethodUtils.tail(bd.getGeneratedBeanClassName()) + "_" + field + "_Iterator";
   }

   public static Class getObjectClass(Class type) {
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            return Boolean.class;
         } else if (type == Byte.TYPE) {
            return Byte.class;
         } else if (type == Character.TYPE) {
            return Character.class;
         } else if (type == Double.TYPE) {
            return Double.class;
         } else if (type == Float.TYPE) {
            return Float.class;
         } else if (type == Integer.TYPE) {
            return Integer.class;
         } else if (type == Long.TYPE) {
            return Long.class;
         } else if (type == Short.TYPE) {
            return Short.class;
         } else {
            throw new AssertionError("Missing primitive in ClassUtils.getObjectClass");
         }
      } else {
         return type;
      }
   }

   public static Class getPrimitiveClass(String name) {
      if (name == null) {
         return null;
      } else if (name.equals("boolean")) {
         return Boolean.TYPE;
      } else if (name.equals("byte")) {
         return Byte.TYPE;
      } else if (name.equals("char")) {
         return Character.TYPE;
      } else if (name.equals("double")) {
         return Double.TYPE;
      } else if (name.equals("float")) {
         return Float.TYPE;
      } else if (name.equals("int")) {
         return Integer.TYPE;
      } else if (name.equals("long")) {
         return Long.TYPE;
      } else {
         return name.equals("short") ? Short.TYPE : null;
      }
   }

   public static Method getDeclaredMethod(Class clazz, String methodName, Class[] parameterTypes) throws NoSuchMethodException {
      Class tmpClass = clazz;

      while(true) {
         try {
            return tmpClass.getDeclaredMethod(methodName, parameterTypes);
         } catch (NoSuchMethodException var5) {
            if ((tmpClass = tmpClass.getSuperclass()) == null) {
               throw new NoSuchMethodException("Method " + methodName + " not found in the " + clazz);
            }
         }
      }
   }

   public static String fileNameToClass(String fileName) {
      String className = fileName.replace(File.separator.charAt(0), '.');
      return className.substring(0, fileName.indexOf(".class"));
   }

   public static String getCanonicalName(Class c) {
      if (c.getDeclaringClass() != null) {
         String dc = getCanonicalName(c.getDeclaringClass());
         return dc + "." + c.getName().substring(dc.length() + 1);
      } else {
         return c.getName();
      }
   }

   public static Method getMethodForNameAndParams(String methodName, String[] methodParams, List methods) {
      if (methods == null) {
         return null;
      } else {
         Iterator var3 = methods.iterator();

         Method method;
         boolean match;
         do {
            String tempMethodName;
            Class[] paramTypes;
            do {
               do {
                  if (!var3.hasNext()) {
                     return null;
                  }

                  method = (Method)var3.next();
                  tempMethodName = method.getName();
                  paramTypes = method.getParameterTypes();
               } while(!tempMethodName.equals(methodName));
            } while(methodParams.length != paramTypes.length);

            match = true;

            for(int i = 0; i < paramTypes.length; ++i) {
               if (!paramTypes[i].getName().equals(methodParams[i])) {
                  match = false;
                  break;
               }
            }
         } while(!match);

         return method;
      }
   }

   public static List getMethodNamesForNameAndParams(String methodName, Class[] methodParams, Method[] methods) {
      List results = new ArrayList();
      if (methods == null) {
         return results;
      } else {
         Method[] var4 = methods;
         int var5 = methods.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            String tempMethodName = method.getName();
            Class[] paramTypes = method.getParameterTypes();
            if (tempMethodName.startsWith(methodName) && methodParams.length == paramTypes.length) {
               boolean match = true;

               for(int j = 0; j < paramTypes.length; ++j) {
                  if (!paramTypes[j].getName().equals(methodParams[j].getName())) {
                     match = false;
                     break;
                  }
               }

               if (match) {
                  results.add(method.getName());
               }
            }
         }

         return results;
      }
   }
}
