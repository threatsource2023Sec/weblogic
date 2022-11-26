package org.jboss.weld.bean.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jboss.weld.logging.BeanLogger;

public class InterceptedProxyMethodHandler extends CombinedInterceptorAndDecoratorStackMethodHandler {
   private static final long serialVersionUID = -4749313040369863855L;
   private final Object instance;

   public InterceptedProxyMethodHandler(Object instance) {
      this.instance = instance;
   }

   public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
      if (BeanLogger.LOG.isTraceEnabled()) {
         BeanLogger.LOG.invokingMethodDirectly(thisMethod.toGenericString(), this.instance);
      }

      Object result = null;

      try {
         SecurityActions.ensureAccessible(thisMethod);
         result = thisMethod.invoke(this.instance, args);
         return result;
      } catch (InvocationTargetException var7) {
         throw var7.getCause();
      }
   }

   public Object invoke(InterceptionDecorationContext.Stack stack, Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
      return super.invoke(stack, this.instance, thisMethod, proceed, args);
   }
}
