package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanReference;
import com.bea.core.repackaged.springframework.beans.factory.parsing.CompositeComponentDefinition;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class AspectComponentDefinition extends CompositeComponentDefinition {
   private final BeanDefinition[] beanDefinitions;
   private final BeanReference[] beanReferences;

   public AspectComponentDefinition(String aspectName, @Nullable BeanDefinition[] beanDefinitions, @Nullable BeanReference[] beanReferences, @Nullable Object source) {
      super(aspectName, source);
      this.beanDefinitions = beanDefinitions != null ? beanDefinitions : new BeanDefinition[0];
      this.beanReferences = beanReferences != null ? beanReferences : new BeanReference[0];
   }

   public BeanDefinition[] getBeanDefinitions() {
      return this.beanDefinitions;
   }

   public BeanReference[] getBeanReferences() {
      return this.beanReferences;
   }
}
