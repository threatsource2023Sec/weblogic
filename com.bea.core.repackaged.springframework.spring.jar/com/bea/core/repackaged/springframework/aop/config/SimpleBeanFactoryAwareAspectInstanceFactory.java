package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.aop.aspectj.AspectInstanceFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public class SimpleBeanFactoryAwareAspectInstanceFactory implements AspectInstanceFactory, BeanFactoryAware {
   @Nullable
   private String aspectBeanName;
   @Nullable
   private BeanFactory beanFactory;

   public void setAspectBeanName(String aspectBeanName) {
      this.aspectBeanName = aspectBeanName;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
      Assert.notNull(this.aspectBeanName, (String)"'aspectBeanName' is required");
   }

   public Object getAspectInstance() {
      Assert.state(this.beanFactory != null, "No BeanFactory set");
      Assert.state(this.aspectBeanName != null, "No 'aspectBeanName' set");
      return this.beanFactory.getBean(this.aspectBeanName);
   }

   @Nullable
   public ClassLoader getAspectClassLoader() {
      return this.beanFactory instanceof ConfigurableBeanFactory ? ((ConfigurableBeanFactory)this.beanFactory).getBeanClassLoader() : ClassUtils.getDefaultClassLoader();
   }

   public int getOrder() {
      return this.beanFactory != null && this.aspectBeanName != null && this.beanFactory.isSingleton(this.aspectBeanName) && this.beanFactory.isTypeMatch(this.aspectBeanName, Ordered.class) ? ((Ordered)this.beanFactory.getBean(this.aspectBeanName)).getOrder() : Integer.MAX_VALUE;
   }
}
