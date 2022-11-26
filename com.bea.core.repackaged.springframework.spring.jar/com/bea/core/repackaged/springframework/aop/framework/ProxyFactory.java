package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.aopalliance.intercept.Interceptor;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public class ProxyFactory extends ProxyCreatorSupport {
   public ProxyFactory() {
   }

   public ProxyFactory(Object target) {
      this.setTarget(target);
      this.setInterfaces(ClassUtils.getAllInterfaces(target));
   }

   public ProxyFactory(Class... proxyInterfaces) {
      this.setInterfaces(proxyInterfaces);
   }

   public ProxyFactory(Class proxyInterface, Interceptor interceptor) {
      this.addInterface(proxyInterface);
      this.addAdvice(interceptor);
   }

   public ProxyFactory(Class proxyInterface, TargetSource targetSource) {
      this.addInterface(proxyInterface);
      this.setTargetSource(targetSource);
   }

   public Object getProxy() {
      return this.createAopProxy().getProxy();
   }

   public Object getProxy(@Nullable ClassLoader classLoader) {
      return this.createAopProxy().getProxy(classLoader);
   }

   public static Object getProxy(Class proxyInterface, Interceptor interceptor) {
      return (new ProxyFactory(proxyInterface, interceptor)).getProxy();
   }

   public static Object getProxy(Class proxyInterface, TargetSource targetSource) {
      return (new ProxyFactory(proxyInterface, targetSource)).getProxy();
   }

   public static Object getProxy(TargetSource targetSource) {
      if (targetSource.getTargetClass() == null) {
         throw new IllegalArgumentException("Cannot create class proxy for TargetSource with null target class");
      } else {
         ProxyFactory proxyFactory = new ProxyFactory();
         proxyFactory.setTargetSource(targetSource);
         proxyFactory.setProxyTargetClass(true);
         return proxyFactory.getProxy();
      }
   }
}
