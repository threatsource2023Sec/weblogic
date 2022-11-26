package com.oracle.core.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ConfigurableMethodInterceptor extends MethodInterceptor {
   void configure(Object var1);
}
