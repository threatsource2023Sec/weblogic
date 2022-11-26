package com.bea.core.repackaged.springframework.aop.framework.adapter;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.MethodBeforeAdvice;
import java.io.Serializable;

class MethodBeforeAdviceAdapter implements AdvisorAdapter, Serializable {
   public boolean supportsAdvice(Advice advice) {
      return advice instanceof MethodBeforeAdvice;
   }

   public MethodInterceptor getInterceptor(Advisor advisor) {
      MethodBeforeAdvice advice = (MethodBeforeAdvice)advisor.getAdvice();
      return new MethodBeforeAdviceInterceptor(advice);
   }
}
