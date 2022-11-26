package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.Aware;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.EmbeddedValueResolver;
import com.bea.core.repackaged.springframework.context.ApplicationContextAware;
import com.bea.core.repackaged.springframework.context.ApplicationEventPublisherAware;
import com.bea.core.repackaged.springframework.context.ConfigurableApplicationContext;
import com.bea.core.repackaged.springframework.context.EmbeddedValueResolverAware;
import com.bea.core.repackaged.springframework.context.EnvironmentAware;
import com.bea.core.repackaged.springframework.context.MessageSourceAware;
import com.bea.core.repackaged.springframework.context.ResourceLoaderAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.security.AccessControlContext;
import java.security.AccessController;

class ApplicationContextAwareProcessor implements BeanPostProcessor {
   private final ConfigurableApplicationContext applicationContext;
   private final StringValueResolver embeddedValueResolver;

   public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
      this.applicationContext = applicationContext;
      this.embeddedValueResolver = new EmbeddedValueResolver(applicationContext.getBeanFactory());
   }

   @Nullable
   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      AccessControlContext acc = null;
      if (System.getSecurityManager() != null && (bean instanceof EnvironmentAware || bean instanceof EmbeddedValueResolverAware || bean instanceof ResourceLoaderAware || bean instanceof ApplicationEventPublisherAware || bean instanceof MessageSourceAware || bean instanceof ApplicationContextAware)) {
         acc = this.applicationContext.getBeanFactory().getAccessControlContext();
      }

      if (acc != null) {
         AccessController.doPrivileged(() -> {
            this.invokeAwareInterfaces(bean);
            return null;
         }, acc);
      } else {
         this.invokeAwareInterfaces(bean);
      }

      return bean;
   }

   private void invokeAwareInterfaces(Object bean) {
      if (bean instanceof Aware) {
         if (bean instanceof EnvironmentAware) {
            ((EnvironmentAware)bean).setEnvironment(this.applicationContext.getEnvironment());
         }

         if (bean instanceof EmbeddedValueResolverAware) {
            ((EmbeddedValueResolverAware)bean).setEmbeddedValueResolver(this.embeddedValueResolver);
         }

         if (bean instanceof ResourceLoaderAware) {
            ((ResourceLoaderAware)bean).setResourceLoader(this.applicationContext);
         }

         if (bean instanceof ApplicationEventPublisherAware) {
            ((ApplicationEventPublisherAware)bean).setApplicationEventPublisher(this.applicationContext);
         }

         if (bean instanceof MessageSourceAware) {
            ((MessageSourceAware)bean).setMessageSource(this.applicationContext);
         }

         if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware)bean).setApplicationContext(this.applicationContext);
         }
      }

   }

   public Object postProcessAfterInitialization(Object bean, String beanName) {
      return bean;
   }
}
