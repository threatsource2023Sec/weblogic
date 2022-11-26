package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import com.bea.core.repackaged.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public abstract class RemoteExporter extends RemotingSupport {
   private Object service;
   private Class serviceInterface;
   private Boolean registerTraceInterceptor;
   private Object[] interceptors;

   public void setService(Object service) {
      this.service = service;
   }

   public Object getService() {
      return this.service;
   }

   public void setServiceInterface(Class serviceInterface) {
      Assert.notNull(serviceInterface, (String)"'serviceInterface' must not be null");
      Assert.isTrue(serviceInterface.isInterface(), "'serviceInterface' must be an interface");
      this.serviceInterface = serviceInterface;
   }

   public Class getServiceInterface() {
      return this.serviceInterface;
   }

   public void setRegisterTraceInterceptor(boolean registerTraceInterceptor) {
      this.registerTraceInterceptor = registerTraceInterceptor;
   }

   public void setInterceptors(Object[] interceptors) {
      this.interceptors = interceptors;
   }

   protected void checkService() throws IllegalArgumentException {
      Assert.notNull(this.getService(), "Property 'service' is required");
   }

   protected void checkServiceInterface() throws IllegalArgumentException {
      Class serviceInterface = this.getServiceInterface();
      Assert.notNull(serviceInterface, (String)"Property 'serviceInterface' is required");
      Object service = this.getService();
      if (service instanceof String) {
         throw new IllegalArgumentException("Service [" + service + "] is a String rather than an actual service reference: Have you accidentally specified the service bean name as value instead of as reference?");
      } else if (!serviceInterface.isInstance(service)) {
         throw new IllegalArgumentException("Service interface [" + serviceInterface.getName() + "] needs to be implemented by service [" + service + "] of class [" + service.getClass().getName() + "]");
      }
   }

   protected Object getProxyForService() {
      ProxyFactory proxyFactory;
      label25: {
         this.checkService();
         this.checkServiceInterface();
         proxyFactory = new ProxyFactory();
         proxyFactory.addInterface(this.getServiceInterface());
         if (this.registerTraceInterceptor != null) {
            if (!this.registerTraceInterceptor) {
               break label25;
            }
         } else if (this.interceptors != null) {
            break label25;
         }

         proxyFactory.addAdvice(new RemoteInvocationTraceInterceptor(this.getExporterName()));
      }

      if (this.interceptors != null) {
         AdvisorAdapterRegistry adapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
         Object[] var3 = this.interceptors;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object interceptor = var3[var5];
            proxyFactory.addAdvisor(adapterRegistry.wrap(interceptor));
         }
      }

      proxyFactory.setTarget(this.getService());
      proxyFactory.setOpaque(true);
      return proxyFactory.getProxy(this.getBeanClassLoader());
   }

   protected String getExporterName() {
      return ClassUtils.getShortName(this.getClass());
   }
}
