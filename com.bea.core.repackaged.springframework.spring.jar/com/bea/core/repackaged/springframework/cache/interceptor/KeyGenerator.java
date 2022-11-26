package com.bea.core.repackaged.springframework.cache.interceptor;

import java.lang.reflect.Method;

@FunctionalInterface
public interface KeyGenerator {
   Object generate(Object var1, Method var2, Object... var3);
}
