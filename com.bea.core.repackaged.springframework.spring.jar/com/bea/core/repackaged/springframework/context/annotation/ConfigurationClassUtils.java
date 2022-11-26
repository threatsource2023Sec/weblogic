package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.core.Conventions;
import com.bea.core.repackaged.springframework.core.annotation.Order;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.StandardAnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReaderFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class ConfigurationClassUtils {
   private static final String CONFIGURATION_CLASS_FULL = "full";
   private static final String CONFIGURATION_CLASS_LITE = "lite";
   private static final String CONFIGURATION_CLASS_ATTRIBUTE = Conventions.getQualifiedAttributeName(ConfigurationClassPostProcessor.class, "configurationClass");
   private static final String ORDER_ATTRIBUTE = Conventions.getQualifiedAttributeName(ConfigurationClassPostProcessor.class, "order");
   private static final Log logger = LogFactory.getLog(ConfigurationClassUtils.class);
   private static final Set candidateIndicators = new HashSet(8);

   public static boolean checkConfigurationClassCandidate(BeanDefinition beanDef, MetadataReaderFactory metadataReaderFactory) {
      String className = beanDef.getBeanClassName();
      if (className != null && beanDef.getFactoryMethodName() == null) {
         Object metadata;
         if (beanDef instanceof AnnotatedBeanDefinition && className.equals(((AnnotatedBeanDefinition)beanDef).getMetadata().getClassName())) {
            metadata = ((AnnotatedBeanDefinition)beanDef).getMetadata();
         } else if (beanDef instanceof AbstractBeanDefinition && ((AbstractBeanDefinition)beanDef).hasBeanClass()) {
            Class beanClass = ((AbstractBeanDefinition)beanDef).getBeanClass();
            metadata = new StandardAnnotationMetadata(beanClass, true);
         } else {
            try {
               MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(className);
               metadata = metadataReader.getAnnotationMetadata();
            } catch (IOException var5) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Could not find class file for introspecting configuration annotations: " + className, var5);
               }

               return false;
            }
         }

         if (isFullConfigurationCandidate((AnnotationMetadata)metadata)) {
            beanDef.setAttribute(CONFIGURATION_CLASS_ATTRIBUTE, "full");
         } else {
            if (!isLiteConfigurationCandidate((AnnotationMetadata)metadata)) {
               return false;
            }

            beanDef.setAttribute(CONFIGURATION_CLASS_ATTRIBUTE, "lite");
         }

         Integer order = getOrder((AnnotationMetadata)metadata);
         if (order != null) {
            beanDef.setAttribute(ORDER_ATTRIBUTE, order);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isConfigurationCandidate(AnnotationMetadata metadata) {
      return isFullConfigurationCandidate(metadata) || isLiteConfigurationCandidate(metadata);
   }

   public static boolean isFullConfigurationCandidate(AnnotationMetadata metadata) {
      return metadata.isAnnotated(Configuration.class.getName());
   }

   public static boolean isLiteConfigurationCandidate(AnnotationMetadata metadata) {
      if (metadata.isInterface()) {
         return false;
      } else {
         Iterator var1 = candidateIndicators.iterator();

         while(var1.hasNext()) {
            String indicator = (String)var1.next();
            if (metadata.isAnnotated(indicator)) {
               return true;
            }
         }

         try {
            return metadata.hasAnnotatedMethods(Bean.class.getName());
         } catch (Throwable var3) {
            if (logger.isDebugEnabled()) {
               logger.debug("Failed to introspect @Bean methods on class [" + metadata.getClassName() + "]: " + var3);
            }

            return false;
         }
      }
   }

   public static boolean isFullConfigurationClass(BeanDefinition beanDef) {
      return "full".equals(beanDef.getAttribute(CONFIGURATION_CLASS_ATTRIBUTE));
   }

   public static boolean isLiteConfigurationClass(BeanDefinition beanDef) {
      return "lite".equals(beanDef.getAttribute(CONFIGURATION_CLASS_ATTRIBUTE));
   }

   @Nullable
   public static Integer getOrder(AnnotationMetadata metadata) {
      Map orderAttributes = metadata.getAnnotationAttributes(Order.class.getName());
      return orderAttributes != null ? (Integer)orderAttributes.get("value") : null;
   }

   public static int getOrder(BeanDefinition beanDef) {
      Integer order = (Integer)beanDef.getAttribute(ORDER_ATTRIBUTE);
      return order != null ? order : Integer.MAX_VALUE;
   }

   static {
      candidateIndicators.add(Component.class.getName());
      candidateIndicators.add(ComponentScan.class.getName());
      candidateIndicators.add(Import.class.getName());
      candidateIndicators.add(ImportResource.class.getName());
   }
}
