package com.bea.core.repackaged.springframework.cache.interceptor;

import java.util.Collection;

@FunctionalInterface
public interface CacheResolver {
   Collection resolveCaches(CacheOperationInvocationContext var1);
}
