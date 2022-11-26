package com.bea.core.repackaged.springframework.cache.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.util.Collection;

public interface CacheAnnotationParser {
   @Nullable
   Collection parseCacheAnnotations(Class var1);

   @Nullable
   Collection parseCacheAnnotations(Method var1);
}
