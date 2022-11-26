package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanInitializationException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import com.bea.core.repackaged.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.core.Conventions;
import com.bea.core.repackaged.springframework.core.PriorityOrdered;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** @deprecated */
@Deprecated
public class RequiredAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor, PriorityOrdered, BeanFactoryAware {
   public static final String SKIP_REQUIRED_CHECK_ATTRIBUTE = Conventions.getQualifiedAttributeName(RequiredAnnotationBeanPostProcessor.class, "skipRequiredCheck");
   private Class requiredAnnotationType = Required.class;
   private int order = 2147483646;
   @Nullable
   private ConfigurableListableBeanFactory beanFactory;
   private final Set validatedBeanNames = Collections.newSetFromMap(new ConcurrentHashMap(64));

   public void setRequiredAnnotationType(Class requiredAnnotationType) {
      Assert.notNull(requiredAnnotationType, (String)"'requiredAnnotationType' must not be null");
      this.requiredAnnotationType = requiredAnnotationType;
   }

   protected Class getRequiredAnnotationType() {
      return this.requiredAnnotationType;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (beanFactory instanceof ConfigurableListableBeanFactory) {
         this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
      }

   }

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class beanType, String beanName) {
   }

   public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) {
      if (!this.validatedBeanNames.contains(beanName)) {
         if (!this.shouldSkip(this.beanFactory, beanName)) {
            List invalidProperties = new ArrayList();
            PropertyDescriptor[] var6 = pds;
            int var7 = pds.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               PropertyDescriptor pd = var6[var8];
               if (this.isRequiredProperty(pd) && !pvs.contains(pd.getName())) {
                  invalidProperties.add(pd.getName());
               }
            }

            if (!invalidProperties.isEmpty()) {
               throw new BeanInitializationException(this.buildExceptionMessage(invalidProperties, beanName));
            }
         }

         this.validatedBeanNames.add(beanName);
      }

      return pvs;
   }

   protected boolean shouldSkip(@Nullable ConfigurableListableBeanFactory beanFactory, String beanName) {
      if (beanFactory != null && beanFactory.containsBeanDefinition(beanName)) {
         BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
         if (beanDefinition.getFactoryBeanName() != null) {
            return true;
         } else {
            Object value = beanDefinition.getAttribute(SKIP_REQUIRED_CHECK_ATTRIBUTE);
            return value != null && (Boolean.TRUE.equals(value) || Boolean.valueOf(value.toString()));
         }
      } else {
         return false;
      }
   }

   protected boolean isRequiredProperty(PropertyDescriptor propertyDescriptor) {
      Method setter = propertyDescriptor.getWriteMethod();
      return setter != null && AnnotationUtils.getAnnotation(setter, this.getRequiredAnnotationType()) != null;
   }

   private String buildExceptionMessage(List invalidProperties, String beanName) {
      int size = invalidProperties.size();
      StringBuilder sb = new StringBuilder();
      sb.append(size == 1 ? "Property" : "Properties");

      for(int i = 0; i < size; ++i) {
         String propertyName = (String)invalidProperties.get(i);
         if (i > 0) {
            if (i == size - 1) {
               sb.append(" and");
            } else {
               sb.append(",");
            }
         }

         sb.append(" '").append(propertyName).append("'");
      }

      sb.append(size == 1 ? " is" : " are");
      sb.append(" required for bean '").append(beanName).append("'");
      return sb.toString();
   }
}
