package com.bea.core.repackaged.springframework.aop.framework.adapter;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;

public class AdvisorAdapterRegistrationManager implements BeanPostProcessor {
   private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();

   public void setAdvisorAdapterRegistry(AdvisorAdapterRegistry advisorAdapterRegistry) {
      this.advisorAdapterRegistry = advisorAdapterRegistry;
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      return bean;
   }

   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      if (bean instanceof AdvisorAdapter) {
         this.advisorAdapterRegistry.registerAdvisorAdapter((AdvisorAdapter)bean);
      }

      return bean;
   }
}
