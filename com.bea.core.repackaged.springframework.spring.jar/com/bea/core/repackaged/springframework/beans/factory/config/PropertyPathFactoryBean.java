package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeanWrapper;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.PropertyAccessorFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class PropertyPathFactoryBean implements FactoryBean, BeanNameAware, BeanFactoryAware {
   private static final Log logger = LogFactory.getLog(PropertyPathFactoryBean.class);
   @Nullable
   private BeanWrapper targetBeanWrapper;
   @Nullable
   private String targetBeanName;
   @Nullable
   private String propertyPath;
   @Nullable
   private Class resultType;
   @Nullable
   private String beanName;
   @Nullable
   private BeanFactory beanFactory;

   public void setTargetObject(Object targetObject) {
      this.targetBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(targetObject);
   }

   public void setTargetBeanName(String targetBeanName) {
      this.targetBeanName = StringUtils.trimAllWhitespace(targetBeanName);
   }

   public void setPropertyPath(String propertyPath) {
      this.propertyPath = StringUtils.trimAllWhitespace(propertyPath);
   }

   public void setResultType(Class resultType) {
      this.resultType = resultType;
   }

   public void setBeanName(String beanName) {
      this.beanName = StringUtils.trimAllWhitespace(BeanFactoryUtils.originalBeanName(beanName));
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
      if (this.targetBeanWrapper != null && this.targetBeanName != null) {
         throw new IllegalArgumentException("Specify either 'targetObject' or 'targetBeanName', not both");
      } else {
         if (this.targetBeanWrapper == null && this.targetBeanName == null) {
            if (this.propertyPath != null) {
               throw new IllegalArgumentException("Specify 'targetObject' or 'targetBeanName' in combination with 'propertyPath'");
            }

            int dotIndex = this.beanName != null ? this.beanName.indexOf(46) : -1;
            if (dotIndex == -1) {
               throw new IllegalArgumentException("Neither 'targetObject' nor 'targetBeanName' specified, and PropertyPathFactoryBean bean name '" + this.beanName + "' does not follow 'beanName.property' syntax");
            }

            this.targetBeanName = this.beanName.substring(0, dotIndex);
            this.propertyPath = this.beanName.substring(dotIndex + 1);
         } else if (this.propertyPath == null) {
            throw new IllegalArgumentException("'propertyPath' is required");
         }

         if (this.targetBeanWrapper == null && this.beanFactory.isSingleton(this.targetBeanName)) {
            Object bean = this.beanFactory.getBean(this.targetBeanName);
            this.targetBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
            this.resultType = this.targetBeanWrapper.getPropertyType(this.propertyPath);
         }

      }
   }

   @Nullable
   public Object getObject() throws BeansException {
      BeanWrapper target = this.targetBeanWrapper;
      if (target != null) {
         if (logger.isWarnEnabled() && this.targetBeanName != null && this.beanFactory instanceof ConfigurableBeanFactory && ((ConfigurableBeanFactory)this.beanFactory).isCurrentlyInCreation(this.targetBeanName)) {
            logger.warn("Target bean '" + this.targetBeanName + "' is still in creation due to a circular reference - obtained value for property '" + this.propertyPath + "' may be outdated!");
         }
      } else {
         Assert.state(this.beanFactory != null, "No BeanFactory available");
         Assert.state(this.targetBeanName != null, "No target bean name specified");
         Object bean = this.beanFactory.getBean(this.targetBeanName);
         target = PropertyAccessorFactory.forBeanPropertyAccess(bean);
      }

      Assert.state(this.propertyPath != null, "No property path specified");
      return target.getPropertyValue(this.propertyPath);
   }

   public Class getObjectType() {
      return this.resultType;
   }

   public boolean isSingleton() {
      return false;
   }
}
