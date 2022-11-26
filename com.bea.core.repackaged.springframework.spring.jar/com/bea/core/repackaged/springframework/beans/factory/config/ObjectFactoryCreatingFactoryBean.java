package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.ObjectFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class ObjectFactoryCreatingFactoryBean extends AbstractFactoryBean {
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
      return ObjectFactory.class;
   }

   protected ObjectFactory createInstance() {
      BeanFactory beanFactory = this.getBeanFactory();
      Assert.state(beanFactory != null, "No BeanFactory available");
      Assert.state(this.targetBeanName != null, "No target bean name specified");
      return new TargetBeanObjectFactory(beanFactory, this.targetBeanName);
   }

   private static class TargetBeanObjectFactory implements ObjectFactory, Serializable {
      private final BeanFactory beanFactory;
      private final String targetBeanName;

      public TargetBeanObjectFactory(BeanFactory beanFactory, String targetBeanName) {
         this.beanFactory = beanFactory;
         this.targetBeanName = targetBeanName;
      }

      public Object getObject() throws BeansException {
         return this.beanFactory.getBean(this.targetBeanName);
      }
   }
}
