package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class AbstractBeanFactoryAwareAdvisingPostProcessor extends AbstractAdvisingBeanPostProcessor implements BeanFactoryAware {
   @Nullable
   private ConfigurableListableBeanFactory beanFactory;

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory instanceof ConfigurableListableBeanFactory ? (ConfigurableListableBeanFactory)beanFactory : null;
   }

   protected ProxyFactory prepareProxyFactory(Object bean, String beanName) {
      if (this.beanFactory != null) {
         AutoProxyUtils.exposeTargetClass(this.beanFactory, beanName, bean.getClass());
      }

      ProxyFactory proxyFactory = super.prepareProxyFactory(bean, beanName);
      if (!proxyFactory.isProxyTargetClass() && this.beanFactory != null && AutoProxyUtils.shouldProxyTargetClass(this.beanFactory, beanName)) {
         proxyFactory.setProxyTargetClass(true);
      }

      return proxyFactory;
   }

   protected boolean isEligible(Object bean, String beanName) {
      return !AutoProxyUtils.isOriginalInstance(beanName, bean.getClass()) && super.isEligible(bean, beanName);
   }
}
