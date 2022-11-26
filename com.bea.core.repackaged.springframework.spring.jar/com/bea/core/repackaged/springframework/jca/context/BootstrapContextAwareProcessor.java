package com.bea.core.repackaged.springframework.jca.context;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.resource.spi.BootstrapContext;

class BootstrapContextAwareProcessor implements BeanPostProcessor {
   @Nullable
   private final BootstrapContext bootstrapContext;

   public BootstrapContextAwareProcessor(@Nullable BootstrapContext bootstrapContext) {
      this.bootstrapContext = bootstrapContext;
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      if (this.bootstrapContext != null && bean instanceof BootstrapContextAware) {
         ((BootstrapContextAware)bean).setBootstrapContext(this.bootstrapContext);
      }

      return bean;
   }

   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      return bean;
   }
}
