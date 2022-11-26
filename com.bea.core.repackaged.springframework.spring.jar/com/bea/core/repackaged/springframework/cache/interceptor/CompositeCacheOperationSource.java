package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class CompositeCacheOperationSource implements CacheOperationSource, Serializable {
   private final CacheOperationSource[] cacheOperationSources;

   public CompositeCacheOperationSource(CacheOperationSource... cacheOperationSources) {
      Assert.notEmpty((Object[])cacheOperationSources, (String)"CacheOperationSource array must not be empty");
      this.cacheOperationSources = cacheOperationSources;
   }

   public final CacheOperationSource[] getCacheOperationSources() {
      return this.cacheOperationSources;
   }

   @Nullable
   public Collection getCacheOperations(Method method, @Nullable Class targetClass) {
      Collection ops = null;
      CacheOperationSource[] var4 = this.cacheOperationSources;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         CacheOperationSource source = var4[var6];
         Collection cacheOperations = source.getCacheOperations(method, targetClass);
         if (cacheOperations != null) {
            if (ops == null) {
               ops = new ArrayList();
            }

            ops.addAll(cacheOperations);
         }
      }

      return ops;
   }
}
