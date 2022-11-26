package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;

public abstract class AbstractBeanFactoryBasedTargetSource implements TargetSource, BeanFactoryAware, Serializable {
   private static final long serialVersionUID = -4721607536018568393L;
   protected final Log logger = LogFactory.getLog(this.getClass());
   private String targetBeanName;
   private volatile Class targetClass;
   private BeanFactory beanFactory;

   public void setTargetBeanName(String targetBeanName) {
      this.targetBeanName = targetBeanName;
   }

   public String getTargetBeanName() {
      return this.targetBeanName;
   }

   public void setTargetClass(Class targetClass) {
      this.targetClass = targetClass;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (this.targetBeanName == null) {
         throw new IllegalStateException("Property 'targetBeanName' is required");
      } else {
         this.beanFactory = beanFactory;
      }
   }

   public BeanFactory getBeanFactory() {
      return this.beanFactory;
   }

   public Class getTargetClass() {
      Class targetClass = this.targetClass;
      if (targetClass != null) {
         return targetClass;
      } else {
         synchronized(this) {
            targetClass = this.targetClass;
            if (targetClass == null && this.beanFactory != null) {
               targetClass = this.beanFactory.getType(this.targetBeanName);
               if (targetClass == null) {
                  if (this.logger.isTraceEnabled()) {
                     this.logger.trace("Getting bean with name '" + this.targetBeanName + "' for type determination");
                  }

                  Object beanInstance = this.beanFactory.getBean(this.targetBeanName);
                  targetClass = beanInstance.getClass();
               }

               this.targetClass = targetClass;
            }

            return targetClass;
         }
      }
   }

   public boolean isStatic() {
      return false;
   }

   public void releaseTarget(Object target) throws Exception {
   }

   protected void copyFrom(AbstractBeanFactoryBasedTargetSource other) {
      this.targetBeanName = other.targetBeanName;
      this.targetClass = other.targetClass;
      this.beanFactory = other.beanFactory;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         AbstractBeanFactoryBasedTargetSource otherTargetSource = (AbstractBeanFactoryBasedTargetSource)other;
         return ObjectUtils.nullSafeEquals(this.beanFactory, otherTargetSource.beanFactory) && ObjectUtils.nullSafeEquals(this.targetBeanName, otherTargetSource.targetBeanName);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int hashCode = this.getClass().hashCode();
      hashCode = 13 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.beanFactory);
      hashCode = 13 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.targetBeanName);
      return hashCode;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
      sb.append(" for target bean '").append(this.targetBeanName).append("'");
      if (this.targetClass != null) {
         sb.append(" of type [").append(this.targetClass.getName()).append("]");
      }

      return sb.toString();
   }
}
