package com.bea.core.repackaged.springframework.aop.framework.adapter;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.springframework.aop.Advisor;

public interface AdvisorAdapterRegistry {
   Advisor wrap(Object var1) throws UnknownAdviceTypeException;

   MethodInterceptor[] getInterceptors(Advisor var1) throws UnknownAdviceTypeException;

   void registerAdvisorAdapter(AdvisorAdapter var1);
}
