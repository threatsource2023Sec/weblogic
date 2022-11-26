package com.bea.core.repackaged.springframework.aop.framework.adapter;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.AfterReturningAdvice;
import java.io.Serializable;

class AfterReturningAdviceAdapter implements AdvisorAdapter, Serializable {
   public boolean supportsAdvice(Advice advice) {
      return advice instanceof AfterReturningAdvice;
   }

   public MethodInterceptor getInterceptor(Advisor advisor) {
      AfterReturningAdvice advice = (AfterReturningAdvice)advisor.getAdvice();
      return new AfterReturningAdviceInterceptor(advice);
   }
}
