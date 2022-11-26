package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;

public abstract class InstantiationAwareBeanPostProcessorAdapter implements SmartInstantiationAwareBeanPostProcessor {
   @Nullable
   public Class predictBeanType(Class beanClass, String beanName) throws BeansException {
      return null;
   }

   @Nullable
   public Constructor[] determineCandidateConstructors(Class beanClass, String beanName) throws BeansException {
      return null;
   }

   public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
      return bean;
   }

   @Nullable
   public Object postProcessBeforeInstantiation(Class beanClass, String beanName) throws BeansException {
      return null;
   }

   public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
      return true;
   }

   public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
      return pvs;
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      return bean;
   }

   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      return bean;
   }
}
