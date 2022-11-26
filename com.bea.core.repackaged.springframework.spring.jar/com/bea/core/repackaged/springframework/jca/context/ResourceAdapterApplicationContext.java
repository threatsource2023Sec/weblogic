package com.bea.core.repackaged.springframework.jca.context;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.context.support.GenericApplicationContext;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.work.WorkManager;

public class ResourceAdapterApplicationContext extends GenericApplicationContext {
   private final BootstrapContext bootstrapContext;

   public ResourceAdapterApplicationContext(BootstrapContext bootstrapContext) {
      Assert.notNull(bootstrapContext, (String)"BootstrapContext must not be null");
      this.bootstrapContext = bootstrapContext;
   }

   protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      beanFactory.addBeanPostProcessor(new BootstrapContextAwareProcessor(this.bootstrapContext));
      beanFactory.ignoreDependencyInterface(BootstrapContextAware.class);
      beanFactory.registerResolvableDependency(BootstrapContext.class, this.bootstrapContext);
      BootstrapContext var10002 = this.bootstrapContext;
      beanFactory.registerResolvableDependency(WorkManager.class, var10002::getWorkManager);
   }
}
