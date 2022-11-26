package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBeanNotInitializedException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class MethodInvokingFactoryBean extends MethodInvokingBean implements FactoryBean {
   private boolean singleton = true;
   private boolean initialized = false;
   @Nullable
   private Object singletonObject;

   public void setSingleton(boolean singleton) {
      this.singleton = singleton;
   }

   public void afterPropertiesSet() throws Exception {
      this.prepare();
      if (this.singleton) {
         this.initialized = true;
         this.singletonObject = this.invokeWithTargetException();
      }

   }

   @Nullable
   public Object getObject() throws Exception {
      if (this.singleton) {
         if (!this.initialized) {
            throw new FactoryBeanNotInitializedException();
         } else {
            return this.singletonObject;
         }
      } else {
         return this.invokeWithTargetException();
      }
   }

   public Class getObjectType() {
      return !this.isPrepared() ? null : this.getPreparedMethod().getReturnType();
   }

   public boolean isSingleton() {
      return this.singleton;
   }
}
