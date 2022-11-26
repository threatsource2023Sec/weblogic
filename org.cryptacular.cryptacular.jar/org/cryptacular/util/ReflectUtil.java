package org.cryptacular.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ReflectUtil {
   private static final Map METHOD_CACHE = new HashMap();

   private ReflectUtil() {
   }

   public static Method getMethod(Class target, String name, Class... parameters) {
      String key = target.getName() + '.' + name;
      Method method = (Method)METHOD_CACHE.get(key);
      if (method != null) {
         return method;
      } else {
         try {
            method = target.getMethod(name, parameters);
            METHOD_CACHE.put(key, method);
            return method;
         } catch (NoSuchMethodException var6) {
            return null;
         }
      }
   }

   public static Object invoke(Object target, Method method, Object... parameters) {
      try {
         return method.invoke(target, parameters);
      } catch (Exception var4) {
         throw new RuntimeException("Failed invoking " + method, var4);
      }
   }
}
