package com.bea.core.repackaged.springframework.ejb.access;

import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import javax.naming.NamingException;

public class LocalStatelessSessionProxyFactoryBean extends LocalSlsbInvokerInterceptor implements FactoryBean, BeanClassLoaderAware {
   @Nullable
   private Class businessInterface;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private Object proxy;

   public void setBusinessInterface(@Nullable Class businessInterface) {
      this.businessInterface = businessInterface;
   }

   @Nullable
   public Class getBusinessInterface() {
      return this.businessInterface;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void afterPropertiesSet() throws NamingException {
      super.afterPropertiesSet();
      if (this.businessInterface == null) {
         throw new IllegalArgumentException("businessInterface is required");
      } else {
         this.proxy = (new ProxyFactory(this.businessInterface, this)).getProxy(this.beanClassLoader);
      }
   }

   @Nullable
   public Object getObject() {
      return this.proxy;
   }

   public Class getObjectType() {
      return this.businessInterface;
   }

   public boolean isSingleton() {
      return true;
   }
}
