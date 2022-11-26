package com.bea.core.repackaged.springframework.aop.framework.adapter;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.springframework.aop.Advisor;

public interface AdvisorAdapter {
   boolean supportsAdvice(Advice var1);

   MethodInterceptor getInterceptor(Advisor var1);
}
