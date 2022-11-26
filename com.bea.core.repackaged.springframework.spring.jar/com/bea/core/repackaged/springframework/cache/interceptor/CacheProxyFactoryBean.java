package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.framework.AbstractSingletonProxyFactoryBean;
import com.bea.core.repackaged.springframework.aop.support.DefaultPointcutAdvisor;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.SmartInitializingSingleton;
import com.bea.core.repackaged.springframework.cache.CacheManager;

public class CacheProxyFactoryBean extends AbstractSingletonProxyFactoryBean implements BeanFactoryAware, SmartInitializingSingleton {
   private final CacheInterceptor cacheInterceptor = new CacheInterceptor();
   private Pointcut pointcut;

   public CacheProxyFactoryBean() {
      this.pointcut = Pointcut.TRUE;
   }

   public void setCacheOperationSources(CacheOperationSource... cacheOperationSources) {
      this.cacheInterceptor.setCacheOperationSources(cacheOperationSources);
   }

   public void setKeyGenerator(KeyGenerator keyGenerator) {
      this.cacheInterceptor.setKeyGenerator(keyGenerator);
   }

   public void setCacheResolver(CacheResolver cacheResolver) {
      this.cacheInterceptor.setCacheResolver(cacheResolver);
   }

   public void setCacheManager(CacheManager cacheManager) {
      this.cacheInterceptor.setCacheManager(cacheManager);
   }

   public void setPointcut(Pointcut pointcut) {
      this.pointcut = pointcut;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.cacheInterceptor.setBeanFactory(beanFactory);
   }

   public void afterSingletonsInstantiated() {
      this.cacheInterceptor.afterSingletonsInstantiated();
   }

   protected Object createMainInterceptor() {
      this.cacheInterceptor.afterPropertiesSet();
      return new DefaultPointcutAdvisor(this.pointcut, this.cacheInterceptor);
   }
}
