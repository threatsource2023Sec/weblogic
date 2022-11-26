package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import java.lang.reflect.Method;

public abstract class StaticMethodMatcher implements MethodMatcher {
   public final boolean isRuntime() {
      return false;
   }

   public final boolean matches(Method method, Class targetClass, Object... args) {
      throw new UnsupportedOperationException("Illegal MethodMatcher usage");
   }
}
