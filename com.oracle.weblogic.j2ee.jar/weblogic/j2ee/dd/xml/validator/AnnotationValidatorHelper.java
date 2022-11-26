package weblogic.j2ee.dd.xml.validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationValidatorHelper {
   public static Class getClass(String className, ClassLoader cl) throws ClassNotFoundException {
      return Class.forName(className, false, cl);
   }

   public static Class getClass(String className) throws ClassNotFoundException {
      return getClass(className, Thread.currentThread().getContextClassLoader());
   }

   public static List getMethods(Class clazz, String name) {
      List methodList = new ArrayList();
      Method[] var3 = clazz.getDeclaredMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];
         if (method.getName().equals(name)) {
            methodList.add(method);
         }
      }

      return methodList;
   }

   public static Field getField(Class clazz, String name) {
      try {
         return clazz.getDeclaredField(name);
      } catch (NoSuchFieldException var3) {
         return null;
      }
   }

   public static String getSetterName(String fieldName) {
      char start = fieldName.charAt(0);
      return "set" + ("" + start).toUpperCase() + fieldName.substring(1);
   }

   public static String getFieldName(String setterName) {
      char start = setterName.charAt(3);
      return ("" + start).toLowerCase() + setterName.substring(4);
   }
}
