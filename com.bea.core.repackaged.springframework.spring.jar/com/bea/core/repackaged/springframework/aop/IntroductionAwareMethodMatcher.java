package com.bea.core.repackaged.springframework.aop;

import java.lang.reflect.Method;

public interface IntroductionAwareMethodMatcher extends MethodMatcher {
   boolean matches(Method var1, Class var2, boolean var3);
}
