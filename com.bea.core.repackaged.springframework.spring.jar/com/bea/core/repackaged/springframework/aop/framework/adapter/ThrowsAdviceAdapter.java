package com.bea.core.repackaged.springframework.aop.framework.adapter;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.ThrowsAdvice;
import java.io.Serializable;

class ThrowsAdviceAdapter implements AdvisorAdapter, Serializable {
   public boolean supportsAdvice(Advice advice) {
      return advice instanceof ThrowsAdvice;
   }

   public MethodInterceptor getInterceptor(Advisor advisor) {
      return new ThrowsAdviceInterceptor(advisor.getAdvice());
   }
}
