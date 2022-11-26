package org.glassfish.hk2.configuration.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.Dynamicity;
import org.glassfish.hk2.utilities.reflection.TypeChecker;

public class BeanUtilities {
   private static final String GET = "get";
   private static final String IS = "is";
   private static final String EMPTY = "";

   private static String firstUpper(String s) {
      if (s != null && s.length() > 0) {
         char firstChar = Character.toUpperCase(s.charAt(0));
         return firstChar + s.substring(1);
      } else {
         return s;
      }
   }

   public static Object getBeanPropertyValue(Type requiredType, String attribute, BeanInfo beanInfo) {
      Object bean;
      if ("$bean".equals(attribute)) {
         bean = beanInfo.getBean();
         if (bean == null) {
            return null;
         } else if (TypeChecker.isRawTypeSafe(requiredType, bean.getClass())) {
            return bean;
         } else {
            Object metadata = beanInfo.getMetadata();
            return metadata != null && TypeChecker.isRawTypeSafe(requiredType, metadata.getClass()) ? metadata : bean;
         }
      } else if ("$type".equals(attribute)) {
         return beanInfo.getTypeName();
      } else if ("$instance".equals(attribute)) {
         return beanInfo.getInstanceName();
      } else {
         bean = beanInfo.getBean();
         if (bean instanceof Map) {
            Map beanLikeMap = (Map)bean;
            return beanLikeMap.get(attribute);
         } else {
            attribute = firstUpper(attribute);
            String methodName = "get" + attribute;
            Class beanClass = bean.getClass();
            Method m = null;

            try {
               m = beanClass.getMethod(methodName);
            } catch (NoSuchMethodException var13) {
               methodName = "is" + attribute;

               try {
                  m = beanClass.getMethod(methodName);
               } catch (NoSuchMethodException var12) {
                  throw new IllegalArgumentException("The bean " + bean + " has no getter for attribute " + attribute);
               }
            }

            m.setAccessible(true);

            try {
               return m.invoke(bean);
            } catch (InvocationTargetException var9) {
               Throwable th = var9.getTargetException();
               throw new IllegalStateException(th);
            } catch (IllegalAccessException var10) {
               throw new IllegalStateException(var10);
            } catch (IllegalArgumentException var11) {
               throw new IllegalStateException(var11);
            }
         }
      }
   }

   public static boolean isEmpty(String s) {
      return s == null ? true : "".equals(s);
   }

   public static String getParameterNameFromField(Field f, boolean onlyDynamic) {
      Configured c = (Configured)f.getAnnotation(Configured.class);
      if (c == null) {
         return null;
      } else if (onlyDynamic && !Dynamicity.FULLY_DYNAMIC.equals(c.dynamicity())) {
         return null;
      } else {
         String key = c.value();
         if (isEmpty(key)) {
            key = f.getName();
         }

         return key;
      }
   }

   public static String getParameterNameFromMethod(Method m, int paramIndex) {
      Annotation[] annotations = m.getParameterAnnotations()[paramIndex];
      Annotation[] var3 = annotations;
      int var4 = annotations.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var3[var5];
         if (Configured.class.equals(annotation.annotationType())) {
            Configured configured = (Configured)annotation;
            if (!Dynamicity.FULLY_DYNAMIC.equals(configured.dynamicity())) {
               return null;
            }

            String retVal = ((Configured)annotation).value();
            if (isEmpty(retVal)) {
               return null;
            }

            return retVal;
         }
      }

      return null;
   }

   public static boolean hasDynamicParameter(Method m) {
      Annotation[][] var1 = m.getParameterAnnotations();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Annotation[] annotations = var1[var3];
         Annotation[] var5 = annotations;
         int var6 = annotations.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Annotation annotation = var5[var7];
            if (Configured.class.equals(annotation.annotationType())) {
               Configured configured = (Configured)annotation;
               if (Dynamicity.FULLY_DYNAMIC.equals(configured.dynamicity())) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
