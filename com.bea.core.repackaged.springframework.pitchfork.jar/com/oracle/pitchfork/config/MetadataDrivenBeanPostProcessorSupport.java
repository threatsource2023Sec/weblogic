package com.oracle.pitchfork.config;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.core.Ordered;

public class MetadataDrivenBeanPostProcessorSupport extends InstantiationAwareBeanPostProcessorAdapter implements Ordered, BeanFactoryAware {
   protected final Log log = LogFactory.getLog(this.getClass());
   protected BeanFactory owner;
   private final String attributeName;
   protected BeanDefinitionRegistry beanDefinitionRegistry;
   private int order = Integer.MIN_VALUE;

   public MetadataDrivenBeanPostProcessorSupport(String attributeName) {
      this.attributeName = attributeName;
   }

   public void setBeanFactory(BeanFactory bf) throws BeansException {
      this.owner = bf;
      if (!(bf instanceof BeanDefinitionRegistry)) {
         throw new IllegalArgumentException("beanFactory needs to implement " + BeanDefinitionRegistry.class.getName() + " interface");
      } else {
         this.beanDefinitionRegistry = (BeanDefinitionRegistry)this.owner;
      }
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   protected Object getMetadata(String beanName) {
      return !this.beanDefinitionRegistry.containsBeanDefinition(beanName) ? null : this.beanDefinitionRegistry.getBeanDefinition(beanName).getAttribute(this.attributeName);
   }
}
