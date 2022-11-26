package com.bea.core.repackaged.springframework.scripting.config;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.scripting.support.ScriptFactoryPostProcessor;

public abstract class LangNamespaceUtils {
   private static final String SCRIPT_FACTORY_POST_PROCESSOR_BEAN_NAME = "com.bea.core.repackaged.springframework.scripting.config.scriptFactoryPostProcessor";

   public static BeanDefinition registerScriptFactoryPostProcessorIfNecessary(BeanDefinitionRegistry registry) {
      Object beanDefinition;
      if (registry.containsBeanDefinition("com.bea.core.repackaged.springframework.scripting.config.scriptFactoryPostProcessor")) {
         beanDefinition = registry.getBeanDefinition("com.bea.core.repackaged.springframework.scripting.config.scriptFactoryPostProcessor");
      } else {
         beanDefinition = new RootBeanDefinition(ScriptFactoryPostProcessor.class);
         registry.registerBeanDefinition("com.bea.core.repackaged.springframework.scripting.config.scriptFactoryPostProcessor", (BeanDefinition)beanDefinition);
      }

      return (BeanDefinition)beanDefinition;
   }
}
