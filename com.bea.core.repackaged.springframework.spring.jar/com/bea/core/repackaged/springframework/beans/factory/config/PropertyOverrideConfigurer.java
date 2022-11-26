package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.PropertyValue;
import com.bea.core.repackaged.springframework.beans.factory.BeanInitializationException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PropertyOverrideConfigurer extends PropertyResourceConfigurer {
   public static final String DEFAULT_BEAN_NAME_SEPARATOR = ".";
   private String beanNameSeparator = ".";
   private boolean ignoreInvalidKeys = false;
   private final Set beanNames = Collections.newSetFromMap(new ConcurrentHashMap(16));

   public void setBeanNameSeparator(String beanNameSeparator) {
      this.beanNameSeparator = beanNameSeparator;
   }

   public void setIgnoreInvalidKeys(boolean ignoreInvalidKeys) {
      this.ignoreInvalidKeys = ignoreInvalidKeys;
   }

   protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
      Enumeration names = props.propertyNames();

      while(names.hasMoreElements()) {
         String key = (String)names.nextElement();

         try {
            this.processKey(beanFactory, key, props.getProperty(key));
         } catch (BeansException var7) {
            String msg = "Could not process key '" + key + "' in PropertyOverrideConfigurer";
            if (!this.ignoreInvalidKeys) {
               throw new BeanInitializationException(msg, var7);
            }

            if (this.logger.isDebugEnabled()) {
               this.logger.debug(msg, var7);
            }
         }
      }

   }

   protected void processKey(ConfigurableListableBeanFactory factory, String key, String value) throws BeansException {
      int separatorIndex = key.indexOf(this.beanNameSeparator);
      if (separatorIndex == -1) {
         throw new BeanInitializationException("Invalid key '" + key + "': expected 'beanName" + this.beanNameSeparator + "property'");
      } else {
         String beanName = key.substring(0, separatorIndex);
         String beanProperty = key.substring(separatorIndex + 1);
         this.beanNames.add(beanName);
         this.applyPropertyValue(factory, beanName, beanProperty, value);
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Property '" + key + "' set to value [" + value + "]");
         }

      }
   }

   protected void applyPropertyValue(ConfigurableListableBeanFactory factory, String beanName, String property, String value) {
      BeanDefinition bd = factory.getBeanDefinition(beanName);

      BeanDefinition bdToUse;
      for(bdToUse = bd; bd != null; bd = bd.getOriginatingBeanDefinition()) {
         bdToUse = bd;
      }

      PropertyValue pv = new PropertyValue(property, value);
      pv.setOptional(this.ignoreInvalidKeys);
      bdToUse.getPropertyValues().addPropertyValue(pv);
   }

   public boolean hasPropertyOverridesFor(String beanName) {
      return this.beanNames.contains(beanName);
   }
}
