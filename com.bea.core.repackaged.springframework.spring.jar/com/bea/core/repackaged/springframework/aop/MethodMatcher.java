package com.bea.core.repackaged.springframework.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
   MethodMatcher TRUE = TrueMethodMatcher.INSTANCE;

   boolean matches(Method var1, Class var2);

   boolean isRuntime();

   boolean matches(Method var1, Class var2, Object... var3);
}
