package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Method;

public class MethodLocatingFactoryBean implements FactoryBean, BeanFactoryAware {
   @Nullable
   private String targetBeanName;
   @Nullable
   private String methodName;
   @Nullable
   private Method method;

   public void setTargetBeanName(String targetBeanName) {
      this.targetBeanName = targetBeanName;
   }

   public void setMethodName(String methodName) {
      this.methodName = methodName;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (!StringUtils.hasText(this.targetBeanName)) {
         throw new IllegalArgumentException("Property 'targetBeanName' is required");
      } else if (!StringUtils.hasText(this.methodName)) {
         throw new IllegalArgumentException("Property 'methodName' is required");
      } else {
         Class beanClass = beanFactory.getType(this.targetBeanName);
         if (beanClass == null) {
            throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
         } else {
            this.method = BeanUtils.resolveSignature(this.methodName, beanClass);
            if (this.method == null) {
               throw new IllegalArgumentException("Unable to locate method [" + this.methodName + "] on bean [" + this.targetBeanName + "]");
            }
         }
      }
   }

   @Nullable
   public Method getObject() throws Exception {
      return this.method;
   }

   public Class getObjectType() {
      return Method.class;
   }

   public boolean isSingleton() {
      return true;
   }
}
