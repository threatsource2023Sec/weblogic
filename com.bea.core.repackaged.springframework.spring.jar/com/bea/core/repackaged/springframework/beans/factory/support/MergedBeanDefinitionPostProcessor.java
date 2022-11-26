package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;

public interface MergedBeanDefinitionPostProcessor extends BeanPostProcessor {
   void postProcessMergedBeanDefinition(RootBeanDefinition var1, Class var2, String var3);

   default void resetBeanDefinition(String beanName) {
   }
}
