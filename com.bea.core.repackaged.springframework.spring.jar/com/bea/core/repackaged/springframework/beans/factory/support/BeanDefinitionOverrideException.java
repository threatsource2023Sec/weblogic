package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.lang.NonNull;

public class BeanDefinitionOverrideException extends BeanDefinitionStoreException {
   private final BeanDefinition beanDefinition;
   private final BeanDefinition existingDefinition;

   public BeanDefinitionOverrideException(String beanName, BeanDefinition beanDefinition, BeanDefinition existingDefinition) {
      super(beanDefinition.getResourceDescription(), beanName, "Cannot register bean definition [" + beanDefinition + "] for bean '" + beanName + "': There is already [" + existingDefinition + "] bound.");
      this.beanDefinition = beanDefinition;
      this.existingDefinition = existingDefinition;
   }

   @NonNull
   public String getResourceDescription() {
      return String.valueOf(super.getResourceDescription());
   }

   @NonNull
   public String getBeanName() {
      return String.valueOf(super.getBeanName());
   }

   public BeanDefinition getBeanDefinition() {
      return this.beanDefinition;
   }

   public BeanDefinition getExistingDefinition() {
      return this.existingDefinition;
   }
}
