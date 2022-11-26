package com.bea.core.repackaged.springframework.aop.framework.adapter;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.BeforeAdvice;
import com.bea.core.repackaged.springframework.aop.MethodBeforeAdvice;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice, Serializable {
   private final MethodBeforeAdvice advice;

   public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
      Assert.notNull(advice, (String)"Advice must not be null");
      this.advice = advice;
   }

   public Object invoke(MethodInvocation mi) throws Throwable {
      this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
      return mi.proceed();
   }
}
