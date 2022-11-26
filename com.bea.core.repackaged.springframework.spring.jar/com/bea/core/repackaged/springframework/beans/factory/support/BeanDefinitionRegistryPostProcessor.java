package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanFactoryPostProcessor;

public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
   void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry var1) throws BeansException;
}
