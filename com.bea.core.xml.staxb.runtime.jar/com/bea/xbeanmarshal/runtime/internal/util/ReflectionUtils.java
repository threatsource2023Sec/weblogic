package com.bea.xbeanmarshal.runtime.internal.util;

import com.bea.xml.XmlException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ReflectionUtils {
   public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

   public static Object invokeMethod(Object target, Method method, Object[] params) throws XmlException {
      assert method != null : "null method";

      assert target == null || method.getDeclaringClass().isInstance(target) : "DECL=" + method.getDeclaringClass() + " GOT:" + target.getClass();

      assert checkParams(method, params);

      try {
         return method.invoke(target, params);
      } catch (IllegalAccessException var4) {
         throw new XmlException(var4);
      } catch (IllegalArgumentException var5) {
         throw new XmlException(var5);
      } catch (InvocationTargetException var6) {
         throw new XmlException(var6.getTargetException());
      }
   }

   private static boolean checkParams(Method method, Object[] params) {
      assert method != null;

      int expected_len = method.getParameterTypes().length;
      int actual_len = params == null ? 0 : params.length;
      if (actual_len != expected_len) {
         String msg = "Method " + method + " expects " + expected_len + " parameters -- got " + actual_len;
         throw new AssertionError(msg);
      } else {
         return true;
      }
   }

   public static Object invokeMethod(Object target, Method method) throws XmlException {
      return invokeMethod(target, method, EMPTY_OBJECT_ARRAY);
   }

   public static boolean isMethodStatic(Method m) {
      return Modifier.isStatic(m.getModifiers());
   }

   public static boolean isClassFinal(Class javaClass) {
      int modifiers = javaClass.getModifiers();
      return Modifier.isFinal(modifiers);
   }
}
