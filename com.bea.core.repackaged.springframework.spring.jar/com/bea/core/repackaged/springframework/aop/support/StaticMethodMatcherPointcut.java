package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.aop.Pointcut;

public abstract class StaticMethodMatcherPointcut extends StaticMethodMatcher implements Pointcut {
   private ClassFilter classFilter;

   public StaticMethodMatcherPointcut() {
      this.classFilter = ClassFilter.TRUE;
   }

   public void setClassFilter(ClassFilter classFilter) {
      this.classFilter = classFilter;
   }

   public ClassFilter getClassFilter() {
      return this.classFilter;
   }

   public final MethodMatcher getMethodMatcher() {
      return this;
   }
}
