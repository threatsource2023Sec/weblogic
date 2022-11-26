package com.bea.core.repackaged.springframework.aop.framework.autoproxy.target;

import com.bea.core.repackaged.springframework.aop.target.AbstractBeanFactoryBasedTargetSource;
import com.bea.core.repackaged.springframework.aop.target.LazyInitTargetSource;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class LazyInitTargetSourceCreator extends AbstractBeanFactoryBasedTargetSourceCreator {
   protected boolean isPrototypeBased() {
      return false;
   }

   @Nullable
   protected AbstractBeanFactoryBasedTargetSource createBeanFactoryBasedTargetSource(Class beanClass, String beanName) {
      if (this.getBeanFactory() instanceof ConfigurableListableBeanFactory) {
         BeanDefinition definition = ((ConfigurableListableBeanFactory)this.getBeanFactory()).getBeanDefinition(beanName);
         if (definition.isLazyInit()) {
            return new LazyInitTargetSource();
         }
      }

      return null;
   }
}
