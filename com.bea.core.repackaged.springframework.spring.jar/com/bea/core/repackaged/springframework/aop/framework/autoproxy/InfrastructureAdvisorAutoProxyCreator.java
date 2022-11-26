package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class InfrastructureAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator {
   @Nullable
   private ConfigurableListableBeanFactory beanFactory;

   protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
      super.initBeanFactory(beanFactory);
      this.beanFactory = beanFactory;
   }

   protected boolean isEligibleAdvisorBean(String beanName) {
      return this.beanFactory != null && this.beanFactory.containsBeanDefinition(beanName) && this.beanFactory.getBeanDefinition(beanName).getRole() == 2;
   }
}
