package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.springframework.aop.SpringProxy;
import java.io.Serializable;
import java.lang.reflect.Proxy;

public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {
   public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
      if (!config.isOptimize() && !config.isProxyTargetClass() && !this.hasNoUserSuppliedProxyInterfaces(config)) {
         return new JdkDynamicAopProxy(config);
      } else {
         Class targetClass = config.getTargetClass();
         if (targetClass == null) {
            throw new AopConfigException("TargetSource cannot determine target class: Either an interface or a target is required for proxy creation.");
         } else {
            return (AopProxy)(!targetClass.isInterface() && !Proxy.isProxyClass(targetClass) ? new ObjenesisCglibAopProxy(config) : new JdkDynamicAopProxy(config));
         }
      }
   }

   private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
      Class[] ifcs = config.getProxiedInterfaces();
      return ifcs.length == 0 || ifcs.length == 1 && SpringProxy.class.isAssignableFrom(ifcs[0]);
   }
}
