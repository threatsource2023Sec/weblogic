package com.bea.core.repackaged.springframework.jmx.export.annotation;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.annotation.AnnotationBeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.EmbeddedValueResolver;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.jmx.export.metadata.InvalidMetadataException;
import com.bea.core.repackaged.springframework.jmx.export.metadata.JmxAttributeSource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class AnnotationJmxAttributeSource implements JmxAttributeSource, BeanFactoryAware {
   @Nullable
   private StringValueResolver embeddedValueResolver;

   public void setBeanFactory(BeanFactory beanFactory) {
      if (beanFactory instanceof ConfigurableBeanFactory) {
         this.embeddedValueResolver = new EmbeddedValueResolver((ConfigurableBeanFactory)beanFactory);
      }

   }

   @Nullable
   public com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedResource getManagedResource(Class beanClass) throws InvalidMetadataException {
      ManagedResource ann = (ManagedResource)AnnotationUtils.findAnnotation(beanClass, ManagedResource.class);
      if (ann == null) {
         return null;
      } else {
         Class declaringClass = AnnotationUtils.findAnnotationDeclaringClass(ManagedResource.class, beanClass);
         Class target = declaringClass != null && !declaringClass.isInterface() ? declaringClass : beanClass;
         if (!Modifier.isPublic(target.getModifiers())) {
            throw new InvalidMetadataException("@ManagedResource class '" + target.getName() + "' must be public");
         } else {
            com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedResource managedResource = new com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedResource();
            AnnotationBeanUtils.copyPropertiesToBean(ann, managedResource, this.embeddedValueResolver);
            return managedResource;
         }
      }
   }

   @Nullable
   public com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedAttribute getManagedAttribute(Method method) throws InvalidMetadataException {
      ManagedAttribute ann = (ManagedAttribute)AnnotationUtils.findAnnotation(method, ManagedAttribute.class);
      if (ann == null) {
         return null;
      } else {
         com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedAttribute managedAttribute = new com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedAttribute();
         AnnotationBeanUtils.copyPropertiesToBean(ann, managedAttribute, "defaultValue");
         if (ann.defaultValue().length() > 0) {
            managedAttribute.setDefaultValue(ann.defaultValue());
         }

         return managedAttribute;
      }
   }

   @Nullable
   public com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedMetric getManagedMetric(Method method) throws InvalidMetadataException {
      ManagedMetric ann = (ManagedMetric)AnnotationUtils.findAnnotation(method, ManagedMetric.class);
      return (com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedMetric)copyPropertiesToBean(ann, com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedMetric.class);
   }

   @Nullable
   public com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedOperation getManagedOperation(Method method) throws InvalidMetadataException {
      ManagedOperation ann = (ManagedOperation)AnnotationUtils.findAnnotation(method, ManagedOperation.class);
      return (com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedOperation)copyPropertiesToBean(ann, com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedOperation.class);
   }

   public com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedOperationParameter[] getManagedOperationParameters(Method method) throws InvalidMetadataException {
      Set anns = AnnotationUtils.getRepeatableAnnotations(method, ManagedOperationParameter.class, ManagedOperationParameters.class);
      return (com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedOperationParameter[])copyPropertiesToBeanArray(anns, com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedOperationParameter.class);
   }

   public com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedNotification[] getManagedNotifications(Class clazz) throws InvalidMetadataException {
      Set anns = AnnotationUtils.getRepeatableAnnotations(clazz, ManagedNotification.class, ManagedNotifications.class);
      return (com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedNotification[])copyPropertiesToBeanArray(anns, com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedNotification.class);
   }

   private static Object[] copyPropertiesToBeanArray(Collection anns, Class beanClass) {
      Object[] beans = (Object[])((Object[])Array.newInstance(beanClass, anns.size()));
      int i = 0;

      Annotation ann;
      for(Iterator var4 = anns.iterator(); var4.hasNext(); beans[i++] = copyPropertiesToBean(ann, beanClass)) {
         ann = (Annotation)var4.next();
      }

      return beans;
   }

   @Nullable
   private static Object copyPropertiesToBean(@Nullable Annotation ann, Class beanClass) {
      if (ann == null) {
         return null;
      } else {
         Object bean = BeanUtils.instantiateClass(beanClass);
         AnnotationBeanUtils.copyPropertiesToBean(ann, bean);
         return bean;
      }
   }
}
