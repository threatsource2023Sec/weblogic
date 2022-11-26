package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanInitializationException;
import com.bea.core.repackaged.springframework.core.PriorityOrdered;
import com.bea.core.repackaged.springframework.core.io.support.PropertiesLoaderSupport;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public abstract class PropertyResourceConfigurer extends PropertiesLoaderSupport implements BeanFactoryPostProcessor, PriorityOrdered {
   private int order = Integer.MAX_VALUE;

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      try {
         Properties mergedProps = this.mergeProperties();
         this.convertProperties(mergedProps);
         this.processProperties(beanFactory, mergedProps);
      } catch (IOException var3) {
         throw new BeanInitializationException("Could not load properties", var3);
      }
   }

   protected void convertProperties(Properties props) {
      Enumeration propertyNames = props.propertyNames();

      while(propertyNames.hasMoreElements()) {
         String propertyName = (String)propertyNames.nextElement();
         String propertyValue = props.getProperty(propertyName);
         String convertedValue = this.convertProperty(propertyName, propertyValue);
         if (!ObjectUtils.nullSafeEquals(propertyValue, convertedValue)) {
            props.setProperty(propertyName, convertedValue);
         }
      }

   }

   protected String convertProperty(String propertyName, String propertyValue) {
      return this.convertPropertyValue(propertyValue);
   }

   protected String convertPropertyValue(String originalValue) {
      return originalValue;
   }

   protected abstract void processProperties(ConfigurableListableBeanFactory var1, Properties var2) throws BeansException;
}
