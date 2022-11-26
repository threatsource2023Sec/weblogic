package com.bea.core.repackaged.aopalliance.intercept;

@FunctionalInterface
public interface MethodInterceptor extends Interceptor {
   Object invoke(MethodInvocation var1) throws Throwable;
}
