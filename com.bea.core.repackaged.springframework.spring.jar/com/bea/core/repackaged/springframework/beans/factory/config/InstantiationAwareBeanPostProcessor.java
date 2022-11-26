package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.PropertyDescriptor;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
   @Nullable
   default Object postProcessBeforeInstantiation(Class beanClass, String beanName) throws BeansException {
      return null;
   }

   default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
      return true;
   }

   @Nullable
   default PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
      return null;
   }

   /** @deprecated */
   @Deprecated
   @Nullable
   default PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
      return pvs;
   }
}
