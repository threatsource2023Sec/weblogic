package com.bea.core.repackaged.springframework.cache.interceptor;

import java.lang.reflect.Method;

public interface CacheOperationInvocationContext {
   BasicOperation getOperation();

   Object getTarget();

   Method getMethod();

   Object[] getArgs();
}
