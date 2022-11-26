package com.bea.core.repackaged.springframework.aop.framework.autoproxy.target;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.framework.AopInfrastructureBean;
import com.bea.core.repackaged.springframework.aop.framework.autoproxy.TargetSourceCreator;
import com.bea.core.repackaged.springframework.aop.target.AbstractBeanFactoryBasedTargetSource;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.GenericBeanDefinition;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractBeanFactoryBasedTargetSourceCreator implements TargetSourceCreator, BeanFactoryAware, DisposableBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private ConfigurableBeanFactory beanFactory;
   private final Map internalBeanFactories = new HashMap();

   public final void setBeanFactory(BeanFactory beanFactory) {
      if (!(beanFactory instanceof ConfigurableBeanFactory)) {
         throw new IllegalStateException("Cannot do auto-TargetSource creation with a BeanFactory that doesn't implement ConfigurableBeanFactory: " + beanFactory.getClass());
      } else {
         this.beanFactory = (ConfigurableBeanFactory)beanFactory;
      }
   }

   protected final BeanFactory getBeanFactory() {
      return this.beanFactory;
   }

   @Nullable
   public final TargetSource getTargetSource(Class beanClass, String beanName) {
      AbstractBeanFactoryBasedTargetSource targetSource = this.createBeanFactoryBasedTargetSource(beanClass, beanName);
      if (targetSource == null) {
         return null;
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Configuring AbstractBeanFactoryBasedTargetSource: " + targetSource);
         }

         DefaultListableBeanFactory internalBeanFactory = this.getInternalBeanFactoryForBean(beanName);
         BeanDefinition bd = this.beanFactory.getMergedBeanDefinition(beanName);
         GenericBeanDefinition bdCopy = new GenericBeanDefinition(bd);
         if (this.isPrototypeBased()) {
            bdCopy.setScope("prototype");
         }

         internalBeanFactory.registerBeanDefinition(beanName, bdCopy);
         targetSource.setTargetBeanName(beanName);
         targetSource.setBeanFactory(internalBeanFactory);
         return targetSource;
      }
   }

   protected DefaultListableBeanFactory getInternalBeanFactoryForBean(String beanName) {
      synchronized(this.internalBeanFactories) {
         DefaultListableBeanFactory internalBeanFactory = (DefaultListableBeanFactory)this.internalBeanFactories.get(beanName);
         if (internalBeanFactory == null) {
            internalBeanFactory = this.buildInternalBeanFactory(this.beanFactory);
            this.internalBeanFactories.put(beanName, internalBeanFactory);
         }

         return internalBeanFactory;
      }
   }

   protected DefaultListableBeanFactory buildInternalBeanFactory(ConfigurableBeanFactory containingFactory) {
      DefaultListableBeanFactory internalBeanFactory = new DefaultListableBeanFactory(containingFactory);
      internalBeanFactory.copyConfigurationFrom(containingFactory);
      internalBeanFactory.getBeanPostProcessors().removeIf((beanPostProcessor) -> {
         return beanPostProcessor instanceof AopInfrastructureBean;
      });
      return internalBeanFactory;
   }

   public void destroy() {
      synchronized(this.internalBeanFactories) {
         Iterator var2 = this.internalBeanFactories.values().iterator();

         while(var2.hasNext()) {
            DefaultListableBeanFactory bf = (DefaultListableBeanFactory)var2.next();
            bf.destroySingletons();
         }

      }
   }

   protected boolean isPrototypeBased() {
      return true;
   }

   @Nullable
   protected abstract AbstractBeanFactoryBasedTargetSource createBeanFactoryBasedTargetSource(Class var1, String var2);
}
