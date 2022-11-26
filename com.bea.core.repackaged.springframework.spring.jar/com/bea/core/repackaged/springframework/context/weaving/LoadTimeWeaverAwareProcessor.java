package com.bea.core.repackaged.springframework.context.weaving;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;
import com.bea.core.repackaged.springframework.instrument.classloading.LoadTimeWeaver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class LoadTimeWeaverAwareProcessor implements BeanPostProcessor, BeanFactoryAware {
   @Nullable
   private LoadTimeWeaver loadTimeWeaver;
   @Nullable
   private BeanFactory beanFactory;

   public LoadTimeWeaverAwareProcessor() {
   }

   public LoadTimeWeaverAwareProcessor(@Nullable LoadTimeWeaver loadTimeWeaver) {
      this.loadTimeWeaver = loadTimeWeaver;
   }

   public LoadTimeWeaverAwareProcessor(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      if (bean instanceof LoadTimeWeaverAware) {
         LoadTimeWeaver ltw = this.loadTimeWeaver;
         if (ltw == null) {
            Assert.state(this.beanFactory != null, "BeanFactory required if no LoadTimeWeaver explicitly specified");
            ltw = (LoadTimeWeaver)this.beanFactory.getBean("loadTimeWeaver", LoadTimeWeaver.class);
         }

         ((LoadTimeWeaverAware)bean).setLoadTimeWeaver(ltw);
      }

      return bean;
   }

   public Object postProcessAfterInitialization(Object bean, String name) {
      return bean;
   }
}
