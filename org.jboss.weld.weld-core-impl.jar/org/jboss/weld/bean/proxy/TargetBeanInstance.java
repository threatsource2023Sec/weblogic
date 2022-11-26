package org.jboss.weld.bean.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.logging.BeanLogger;

public class TargetBeanInstance extends AbstractBeanInstance implements Serializable {
   private static final long serialVersionUID = 1099995238604086450L;
   private final Object instance;
   private final Class instanceType;
   private MethodHandler interceptorsHandler;

   public TargetBeanInstance(Bean bean, Object instance) {
      this.instance = instance;
      this.instanceType = this.computeInstanceType(bean);
   }

   public TargetBeanInstance(Object instance) {
      this.instance = instance;
      this.instanceType = instance.getClass();
   }

   public TargetBeanInstance(TargetBeanInstance otherBeanInstance) {
      this.instance = otherBeanInstance.instance;
      this.instanceType = otherBeanInstance.instanceType;
      this.interceptorsHandler = otherBeanInstance.interceptorsHandler;
   }

   public Object getInstance() {
      return this.instance;
   }

   public Class getInstanceType() {
      return this.instanceType;
   }

   public MethodHandler getInterceptorsHandler() {
      return this.interceptorsHandler;
   }

   public void setInterceptorsHandler(MethodHandler interceptorsHandler) {
      this.interceptorsHandler = interceptorsHandler;
   }

   public Object invoke(Object instance, Method method, Object... arguments) throws Throwable {
      if (this.interceptorsHandler != null) {
         if (BeanLogger.LOG.isTraceEnabled()) {
            BeanLogger.LOG.invokingInterceptorChain(method.toGenericString(), instance);
         }

         return method.getDeclaringClass().isInterface() ? this.interceptorsHandler.invoke(instance, method, (Method)null, arguments) : this.interceptorsHandler.invoke(instance, method, method, arguments);
      } else {
         if (BeanLogger.LOG.isTraceEnabled()) {
            BeanLogger.LOG.invokingMethodDirectly(method.toGenericString(), instance);
         }

         return super.invoke(instance, method, arguments);
      }
   }
}
