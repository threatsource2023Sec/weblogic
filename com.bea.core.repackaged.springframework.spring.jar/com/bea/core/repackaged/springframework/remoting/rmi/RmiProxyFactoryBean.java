package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.util.Assert;

public class RmiProxyFactoryBean extends RmiClientInterceptor implements FactoryBean, BeanClassLoaderAware {
   private Object serviceProxy;

   public void afterPropertiesSet() {
      super.afterPropertiesSet();
      Class ifc = this.getServiceInterface();
      Assert.notNull(ifc, (String)"Property 'serviceInterface' is required");
      this.serviceProxy = (new ProxyFactory(ifc, this)).getProxy(this.getBeanClassLoader());
   }

   public Object getObject() {
      return this.serviceProxy;
   }

   public Class getObjectType() {
      return this.getServiceInterface();
   }

   public boolean isSingleton() {
      return true;
   }
}
