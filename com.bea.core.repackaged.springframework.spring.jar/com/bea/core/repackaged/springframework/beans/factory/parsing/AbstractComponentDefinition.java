package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanReference;

public abstract class AbstractComponentDefinition implements ComponentDefinition {
   public String getDescription() {
      return this.getName();
   }

   public BeanDefinition[] getBeanDefinitions() {
      return new BeanDefinition[0];
   }

   public BeanDefinition[] getInnerBeanDefinitions() {
      return new BeanDefinition[0];
   }

   public BeanReference[] getBeanReferences() {
      return new BeanReference[0];
   }

   public String toString() {
      return this.getDescription();
   }
}
