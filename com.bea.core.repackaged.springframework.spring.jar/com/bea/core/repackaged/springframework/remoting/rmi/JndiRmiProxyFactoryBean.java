package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import javax.naming.NamingException;

public class JndiRmiProxyFactoryBean extends JndiRmiClientInterceptor implements FactoryBean, BeanClassLoaderAware {
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   private Object serviceProxy;

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void afterPropertiesSet() throws NamingException {
      super.afterPropertiesSet();
      Class ifc = this.getServiceInterface();
      Assert.notNull(ifc, (String)"Property 'serviceInterface' is required");
      this.serviceProxy = (new ProxyFactory(ifc, this)).getProxy(this.beanClassLoader);
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
