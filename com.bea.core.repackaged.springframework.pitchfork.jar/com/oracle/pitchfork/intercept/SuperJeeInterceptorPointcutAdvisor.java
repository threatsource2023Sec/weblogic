package com.oracle.pitchfork.intercept;

import com.bea.core.repackaged.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import java.lang.reflect.Method;

public class SuperJeeInterceptorPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
   private final JeeInterceptorPointcutAdvisor delegate;

   public SuperJeeInterceptorPointcutAdvisor(JeeInterceptorPointcutAdvisor delegate, JeeInterceptorInterceptor advice) {
      super(advice);
      this.delegate = delegate;
   }

   public boolean matches(Method method, Class targetClass) {
      return this.delegate.matches(method, targetClass);
   }

   public String toString() {
      return this.delegate.toString();
   }
}
