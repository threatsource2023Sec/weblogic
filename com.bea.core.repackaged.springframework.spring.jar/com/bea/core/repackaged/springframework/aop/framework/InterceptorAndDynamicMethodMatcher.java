package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;

class InterceptorAndDynamicMethodMatcher {
   final MethodInterceptor interceptor;
   final MethodMatcher methodMatcher;

   public InterceptorAndDynamicMethodMatcher(MethodInterceptor interceptor, MethodMatcher methodMatcher) {
      this.interceptor = interceptor;
      this.methodMatcher = methodMatcher;
   }
}
