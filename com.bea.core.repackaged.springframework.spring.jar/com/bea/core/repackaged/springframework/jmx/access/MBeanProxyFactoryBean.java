package com.bea.core.repackaged.springframework.jmx.access;

import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jmx.MBeanServerNotFoundException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public class MBeanProxyFactoryBean extends MBeanClientInterceptor implements FactoryBean, BeanClassLoaderAware, InitializingBean {
   @Nullable
   private Class proxyInterface;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private Object mbeanProxy;

   public void setProxyInterface(Class proxyInterface) {
      this.proxyInterface = proxyInterface;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void afterPropertiesSet() throws MBeanServerNotFoundException, MBeanInfoRetrievalException {
      super.afterPropertiesSet();
      if (this.proxyInterface == null) {
         this.proxyInterface = this.getManagementInterface();
         if (this.proxyInterface == null) {
            throw new IllegalArgumentException("Property 'proxyInterface' or 'managementInterface' is required");
         }
      } else if (this.getManagementInterface() == null) {
         this.setManagementInterface(this.proxyInterface);
      }

      this.mbeanProxy = (new ProxyFactory(this.proxyInterface, this)).getProxy(this.beanClassLoader);
   }

   @Nullable
   public Object getObject() {
      return this.mbeanProxy;
   }

   public Class getObjectType() {
      return this.proxyInterface;
   }

   public boolean isSingleton() {
      return true;
   }
}
