package com.bea.core.repackaged.springframework.aop.interceptor;

import java.lang.reflect.Method;

@FunctionalInterface
public interface AsyncUncaughtExceptionHandler {
   void handleUncaughtException(Throwable var1, Method var2, Object... var3);
}
