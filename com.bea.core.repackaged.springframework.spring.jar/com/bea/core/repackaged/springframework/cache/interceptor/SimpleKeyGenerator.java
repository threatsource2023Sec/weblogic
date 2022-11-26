package com.bea.core.repackaged.springframework.cache.interceptor;

import java.lang.reflect.Method;

public class SimpleKeyGenerator implements KeyGenerator {
   public Object generate(Object target, Method method, Object... params) {
      return generateKey(params);
   }

   public static Object generateKey(Object... params) {
      if (params.length == 0) {
         return SimpleKey.EMPTY;
      } else {
         if (params.length == 1) {
            Object param = params[0];
            if (param != null && !param.getClass().isArray()) {
               return param;
            }
         }

         return new SimpleKey(params);
      }
   }
}
