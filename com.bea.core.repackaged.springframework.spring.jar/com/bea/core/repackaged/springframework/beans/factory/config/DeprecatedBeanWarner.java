package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class DeprecatedBeanWarner implements BeanFactoryPostProcessor {
   protected transient Log logger = LogFactory.getLog(this.getClass());

   public void setLoggerName(String loggerName) {
      this.logger = LogFactory.getLog(loggerName);
   }

   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      if (this.isLogEnabled()) {
         String[] beanNames = beanFactory.getBeanDefinitionNames();
         String[] var3 = beanNames;
         int var4 = beanNames.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String beanName = var3[var5];
            String nameToLookup = beanName;
            if (beanFactory.isFactoryBean(beanName)) {
               nameToLookup = "&" + beanName;
            }

            Class beanType = beanFactory.getType(nameToLookup);
            if (beanType != null) {
               Class userClass = ClassUtils.getUserClass(beanType);
               if (userClass.isAnnotationPresent(Deprecated.class)) {
                  BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                  this.logDeprecatedBean(beanName, beanType, beanDefinition);
               }
            }
         }
      }

   }

   protected void logDeprecatedBean(String beanName, Class beanType, BeanDefinition beanDefinition) {
      StringBuilder builder = new StringBuilder();
      builder.append(beanType);
      builder.append(" ['");
      builder.append(beanName);
      builder.append('\'');
      String resourceDescription = beanDefinition.getResourceDescription();
      if (StringUtils.hasLength(resourceDescription)) {
         builder.append(" in ");
         builder.append(resourceDescription);
      }

      builder.append("] has been deprecated");
      this.writeToLog(builder.toString());
   }

   protected void writeToLog(String message) {
      this.logger.warn(message);
   }

   protected boolean isLogEnabled() {
      return this.logger.isWarnEnabled();
   }
}
