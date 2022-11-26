package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

public class CustomAutowireConfigurer implements BeanFactoryPostProcessor, BeanClassLoaderAware, Ordered {
   private int order = Integer.MAX_VALUE;
   @Nullable
   private Set customQualifierTypes;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
      this.beanClassLoader = beanClassLoader;
   }

   public void setCustomQualifierTypes(Set customQualifierTypes) {
      this.customQualifierTypes = customQualifierTypes;
   }

   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      if (this.customQualifierTypes != null) {
         if (!(beanFactory instanceof DefaultListableBeanFactory)) {
            throw new IllegalStateException("CustomAutowireConfigurer needs to operate on a DefaultListableBeanFactory");
         }

         DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory)beanFactory;
         if (!(dlbf.getAutowireCandidateResolver() instanceof QualifierAnnotationAutowireCandidateResolver)) {
            dlbf.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());
         }

         QualifierAnnotationAutowireCandidateResolver resolver = (QualifierAnnotationAutowireCandidateResolver)dlbf.getAutowireCandidateResolver();
         Iterator var4 = this.customQualifierTypes.iterator();

         while(var4.hasNext()) {
            Object value = var4.next();
            Class customType = null;
            if (value instanceof Class) {
               customType = (Class)value;
            } else {
               if (!(value instanceof String)) {
                  throw new IllegalArgumentException("Invalid value [" + value + "] for custom qualifier type: needs to be Class or String.");
               }

               String className = (String)value;
               customType = ClassUtils.resolveClassName(className, this.beanClassLoader);
            }

            if (!Annotation.class.isAssignableFrom(customType)) {
               throw new IllegalArgumentException("Qualifier type [" + customType.getName() + "] needs to be annotation type");
            }

            resolver.addQualifierType(customType);
         }
      }

   }
}
