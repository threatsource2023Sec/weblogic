package com.bea.core.repackaged.springframework.aop;

import java.io.Serializable;
import java.lang.reflect.Method;

final class TrueMethodMatcher implements MethodMatcher, Serializable {
   public static final TrueMethodMatcher INSTANCE = new TrueMethodMatcher();

   private TrueMethodMatcher() {
   }

   public boolean isRuntime() {
      return false;
   }

   public boolean matches(Method method, Class targetClass) {
      return true;
   }

   public boolean matches(Method method, Class targetClass, Object... args) {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return "MethodMatcher.TRUE";
   }

   private Object readResolve() {
      return INSTANCE;
   }
}
