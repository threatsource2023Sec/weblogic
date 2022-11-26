package com.bea.core.repackaged.springframework.cache.annotation;

import com.bea.core.repackaged.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheInterceptor;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheOperationSource;
import com.bea.core.repackaged.springframework.context.annotation.Bean;
import com.bea.core.repackaged.springframework.context.annotation.Configuration;
import com.bea.core.repackaged.springframework.context.annotation.Role;

@Configuration
@Role(2)
public class ProxyCachingConfiguration extends AbstractCachingConfiguration {
   @Bean(
      name = {"com.bea.core.repackaged.springframework.cache.config.internalCacheAdvisor"}
   )
   @Role(2)
   public BeanFactoryCacheOperationSourceAdvisor cacheAdvisor() {
      BeanFactoryCacheOperationSourceAdvisor advisor = new BeanFactoryCacheOperationSourceAdvisor();
      advisor.setCacheOperationSource(this.cacheOperationSource());
      advisor.setAdvice(this.cacheInterceptor());
      if (this.enableCaching != null) {
         advisor.setOrder((Integer)this.enableCaching.getNumber("order"));
      }

      return advisor;
   }

   @Bean
   @Role(2)
   public CacheOperationSource cacheOperationSource() {
      return new AnnotationCacheOperationSource();
   }

   @Bean
   @Role(2)
   public CacheInterceptor cacheInterceptor() {
      CacheInterceptor interceptor = new CacheInterceptor();
      interceptor.configure(this.errorHandler, this.keyGenerator, this.cacheResolver, this.cacheManager);
      interceptor.setCacheOperationSource(this.cacheOperationSource());
      return interceptor;
   }
}
