package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.core.MethodClassKey;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractFallbackCacheOperationSource implements CacheOperationSource {
   private static final Collection NULL_CACHING_ATTRIBUTE = Collections.emptyList();
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final Map attributeCache = new ConcurrentHashMap(1024);

   @Nullable
   public Collection getCacheOperations(Method method, @Nullable Class targetClass) {
      if (method.getDeclaringClass() == Object.class) {
         return null;
      } else {
         Object cacheKey = this.getCacheKey(method, targetClass);
         Collection cached = (Collection)this.attributeCache.get(cacheKey);
         if (cached != null) {
            return cached != NULL_CACHING_ATTRIBUTE ? cached : null;
         } else {
            Collection cacheOps = this.computeCacheOperations(method, targetClass);
            if (cacheOps != null) {
               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("Adding cacheable method '" + method.getName() + "' with attribute: " + cacheOps);
               }

               this.attributeCache.put(cacheKey, cacheOps);
            } else {
               this.attributeCache.put(cacheKey, NULL_CACHING_ATTRIBUTE);
            }

            return cacheOps;
         }
      }
   }

   protected Object getCacheKey(Method method, @Nullable Class targetClass) {
      return new MethodClassKey(method, targetClass);
   }

   @Nullable
   private Collection computeCacheOperations(Method method, @Nullable Class targetClass) {
      if (this.allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
         return null;
      } else {
         Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
         Collection opDef = this.findCacheOperations(specificMethod);
         if (opDef != null) {
            return opDef;
         } else {
            opDef = this.findCacheOperations(specificMethod.getDeclaringClass());
            if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
               return opDef;
            } else {
               if (specificMethod != method) {
                  opDef = this.findCacheOperations(method);
                  if (opDef != null) {
                     return opDef;
                  }

                  opDef = this.findCacheOperations(method.getDeclaringClass());
                  if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
                     return opDef;
                  }
               }

               return null;
            }
         }
      }
   }

   @Nullable
   protected abstract Collection findCacheOperations(Class var1);

   @Nullable
   protected abstract Collection findCacheOperations(Method var1);

   protected boolean allowPublicMethodsOnly() {
      return false;
   }
}
