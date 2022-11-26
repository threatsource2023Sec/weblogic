package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.context.event.ApplicationEventMulticaster;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ApplicationListenerDetector implements DestructionAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor {
   private static final Log logger = LogFactory.getLog(ApplicationListenerDetector.class);
   private final transient AbstractApplicationContext applicationContext;
   private final transient Map singletonNames = new ConcurrentHashMap(256);

   public ApplicationListenerDetector(AbstractApplicationContext applicationContext) {
      this.applicationContext = applicationContext;
   }

   public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class beanType, String beanName) {
      this.singletonNames.put(beanName, beanDefinition.isSingleton());
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) {
      return bean;
   }

   public Object postProcessAfterInitialization(Object bean, String beanName) {
      if (bean instanceof ApplicationListener) {
         Boolean flag = (Boolean)this.singletonNames.get(beanName);
         if (Boolean.TRUE.equals(flag)) {
            this.applicationContext.addApplicationListener((ApplicationListener)bean);
         } else if (Boolean.FALSE.equals(flag)) {
            if (logger.isWarnEnabled() && !this.applicationContext.containsBean(beanName)) {
               logger.warn("Inner bean '" + beanName + "' implements ApplicationListener interface but is not reachable for event multicasting by its containing ApplicationContext because it does not have singleton scope. Only top-level listener beans are allowed to be of non-singleton scope.");
            }

            this.singletonNames.remove(beanName);
         }
      }

      return bean;
   }

   public void postProcessBeforeDestruction(Object bean, String beanName) {
      if (bean instanceof ApplicationListener) {
         try {
            ApplicationEventMulticaster multicaster = this.applicationContext.getApplicationEventMulticaster();
            multicaster.removeApplicationListener((ApplicationListener)bean);
            multicaster.removeApplicationListenerBean(beanName);
         } catch (IllegalStateException var4) {
         }
      }

   }

   public boolean requiresDestruction(Object bean) {
      return bean instanceof ApplicationListener;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof ApplicationListenerDetector && this.applicationContext == ((ApplicationListenerDetector)other).applicationContext;
   }

   public int hashCode() {
      return ObjectUtils.nullSafeHashCode((Object)this.applicationContext);
   }
}
