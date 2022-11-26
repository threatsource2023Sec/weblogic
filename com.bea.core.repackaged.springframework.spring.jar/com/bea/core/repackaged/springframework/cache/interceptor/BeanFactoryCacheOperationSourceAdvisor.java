package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class BeanFactoryCacheOperationSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {
   @Nullable
   private CacheOperationSource cacheOperationSource;
   private final CacheOperationSourcePointcut pointcut = new CacheOperationSourcePointcut() {
      @Nullable
      protected CacheOperationSource getCacheOperationSource() {
         return BeanFactoryCacheOperationSourceAdvisor.this.cacheOperationSource;
      }
   };

   public void setCacheOperationSource(CacheOperationSource cacheOperationSource) {
      this.cacheOperationSource = cacheOperationSource;
   }

   public void setClassFilter(ClassFilter classFilter) {
      this.pointcut.setClassFilter(classFilter);
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }
}
