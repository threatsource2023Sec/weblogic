package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import javax.inject.Provider;

public class ProviderCreatingFactoryBean extends AbstractFactoryBean {
   @Nullable
   private String targetBeanName;

   public void setTargetBeanName(String targetBeanName) {
      this.targetBeanName = targetBeanName;
   }

   public void afterPropertiesSet() throws Exception {
      Assert.hasText(this.targetBeanName, "Property 'targetBeanName' is required");
      super.afterPropertiesSet();
   }

   public Class getObjectType() {
      return Provider.class;
   }

   protected Provider createInstance() {
      BeanFactory beanFactory = this.getBeanFactory();
      Assert.state(beanFactory != null, "No BeanFactory available");
      Assert.state(this.targetBeanName != null, "No target bean name specified");
      return new TargetBeanProvider(beanFactory, this.targetBeanName);
   }

   private static class TargetBeanProvider implements Provider, Serializable {
      private final BeanFactory beanFactory;
      private final String targetBeanName;

      public TargetBeanProvider(BeanFactory beanFactory, String targetBeanName) {
         this.beanFactory = beanFactory;
         this.targetBeanName = targetBeanName;
      }

      public Object get() throws BeansException {
         return this.beanFactory.getBean(this.targetBeanName);
      }
   }
}
