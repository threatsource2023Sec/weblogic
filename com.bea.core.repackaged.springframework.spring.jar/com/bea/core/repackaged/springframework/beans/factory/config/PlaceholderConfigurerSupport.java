package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringValueResolver;

public abstract class PlaceholderConfigurerSupport extends PropertyResourceConfigurer implements BeanNameAware, BeanFactoryAware {
   public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
   public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";
   public static final String DEFAULT_VALUE_SEPARATOR = ":";
   protected String placeholderPrefix = "${";
   protected String placeholderSuffix = "}";
   @Nullable
   protected String valueSeparator = ":";
   protected boolean trimValues = false;
   @Nullable
   protected String nullValue;
   protected boolean ignoreUnresolvablePlaceholders = false;
   @Nullable
   private String beanName;
   @Nullable
   private BeanFactory beanFactory;

   public void setPlaceholderPrefix(String placeholderPrefix) {
      this.placeholderPrefix = placeholderPrefix;
   }

   public void setPlaceholderSuffix(String placeholderSuffix) {
      this.placeholderSuffix = placeholderSuffix;
   }

   public void setValueSeparator(@Nullable String valueSeparator) {
      this.valueSeparator = valueSeparator;
   }

   public void setTrimValues(boolean trimValues) {
      this.trimValues = trimValues;
   }

   public void setNullValue(String nullValue) {
      this.nullValue = nullValue;
   }

   public void setIgnoreUnresolvablePlaceholders(boolean ignoreUnresolvablePlaceholders) {
      this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
   }

   public void setBeanName(String beanName) {
      this.beanName = beanName;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess, StringValueResolver valueResolver) {
      BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);
      String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
      String[] var5 = beanNames;
      int var6 = beanNames.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String curName = var5[var7];
         if (!curName.equals(this.beanName) || !beanFactoryToProcess.equals(this.beanFactory)) {
            BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(curName);

            try {
               visitor.visitBeanDefinition(bd);
            } catch (Exception var11) {
               throw new BeanDefinitionStoreException(bd.getResourceDescription(), curName, var11.getMessage(), var11);
            }
         }
      }

      beanFactoryToProcess.resolveAliases(valueResolver);
      beanFactoryToProcess.addEmbeddedValueResolver(valueResolver);
   }
}
