package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.aop.Pointcut;

public abstract class DynamicMethodMatcherPointcut extends DynamicMethodMatcher implements Pointcut {
   public ClassFilter getClassFilter() {
      return ClassFilter.TRUE;
   }

   public final MethodMatcher getMethodMatcher() {
      return this;
   }
}
