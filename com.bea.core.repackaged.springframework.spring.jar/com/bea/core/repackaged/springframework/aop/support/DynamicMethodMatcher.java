package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import java.lang.reflect.Method;

public abstract class DynamicMethodMatcher implements MethodMatcher {
   public final boolean isRuntime() {
      return true;
   }

   public boolean matches(Method method, Class targetClass) {
      return true;
   }
}
