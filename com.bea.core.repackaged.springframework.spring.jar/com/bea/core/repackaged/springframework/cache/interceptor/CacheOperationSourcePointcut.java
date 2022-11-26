package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.aop.support.StaticMethodMatcherPointcut;
import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.lang.reflect.Method;

abstract class CacheOperationSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {
   public boolean matches(Method method, Class targetClass) {
      if (CacheManager.class.isAssignableFrom(targetClass)) {
         return false;
      } else {
         CacheOperationSource cas = this.getCacheOperationSource();
         return cas != null && !CollectionUtils.isEmpty(cas.getCacheOperations(method, targetClass));
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof CacheOperationSourcePointcut)) {
         return false;
      } else {
         CacheOperationSourcePointcut otherPc = (CacheOperationSourcePointcut)other;
         return ObjectUtils.nullSafeEquals(this.getCacheOperationSource(), otherPc.getCacheOperationSource());
      }
   }

   public int hashCode() {
      return CacheOperationSourcePointcut.class.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.getCacheOperationSource();
   }

   @Nullable
   protected abstract CacheOperationSource getCacheOperationSource();
}
