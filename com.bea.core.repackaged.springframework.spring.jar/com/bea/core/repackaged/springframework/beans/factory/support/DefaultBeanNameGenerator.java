package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;

public class DefaultBeanNameGenerator implements BeanNameGenerator {
   public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
      return BeanDefinitionReaderUtils.generateBeanName(definition, registry);
   }
}
