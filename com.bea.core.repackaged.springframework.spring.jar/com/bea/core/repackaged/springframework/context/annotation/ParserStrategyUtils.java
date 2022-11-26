package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.Aware;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.context.EnvironmentAware;
import com.bea.core.repackaged.springframework.context.ResourceLoaderAware;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;

abstract class ParserStrategyUtils {
   public static void invokeAwareMethods(Object parserStrategyBean, Environment environment, ResourceLoader resourceLoader, BeanDefinitionRegistry registry) {
      if (parserStrategyBean instanceof Aware) {
         if (parserStrategyBean instanceof BeanClassLoaderAware) {
            ClassLoader classLoader = registry instanceof ConfigurableBeanFactory ? ((ConfigurableBeanFactory)registry).getBeanClassLoader() : resourceLoader.getClassLoader();
            if (classLoader != null) {
               ((BeanClassLoaderAware)parserStrategyBean).setBeanClassLoader(classLoader);
            }
         }

         if (parserStrategyBean instanceof BeanFactoryAware && registry instanceof BeanFactory) {
            ((BeanFactoryAware)parserStrategyBean).setBeanFactory((BeanFactory)registry);
         }

         if (parserStrategyBean instanceof EnvironmentAware) {
            ((EnvironmentAware)parserStrategyBean).setEnvironment(environment);
         }

         if (parserStrategyBean instanceof ResourceLoaderAware) {
            ((ResourceLoaderAware)parserStrategyBean).setResourceLoader(resourceLoader);
         }
      }

   }
}
