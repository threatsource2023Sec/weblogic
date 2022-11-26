package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class NamedCacheResolver extends AbstractCacheResolver {
   @Nullable
   private Collection cacheNames;

   public NamedCacheResolver() {
   }

   public NamedCacheResolver(CacheManager cacheManager, String... cacheNames) {
      super(cacheManager);
      this.cacheNames = new ArrayList(Arrays.asList(cacheNames));
   }

   public void setCacheNames(Collection cacheNames) {
      this.cacheNames = cacheNames;
   }

   protected Collection getCacheNames(CacheOperationInvocationContext context) {
      return this.cacheNames;
   }
}
