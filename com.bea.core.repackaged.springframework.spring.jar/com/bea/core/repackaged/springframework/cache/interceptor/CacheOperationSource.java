package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.util.Collection;

public interface CacheOperationSource {
   @Nullable
   Collection getCacheOperations(Method var1, @Nullable Class var2);
}
