package com.bea.core.repackaged.springframework.aop.framework.adapter;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.AfterAdvice;
import com.bea.core.repackaged.springframework.aop.AfterReturningAdvice;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice, Serializable {
   private final AfterReturningAdvice advice;

   public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
      Assert.notNull(advice, (String)"Advice must not be null");
      this.advice = advice;
   }

   public Object invoke(MethodInvocation mi) throws Throwable {
      Object retVal = mi.proceed();
      this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
      return retVal;
   }
}
