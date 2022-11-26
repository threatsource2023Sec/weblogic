package org.jboss.weld.bean.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.enterprise.inject.spi.Decorator;
import javax.inject.Inject;
import org.jboss.weld.annotated.runtime.InvokableAnnotatedMethod;
import org.jboss.weld.bean.WeldDecorator;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxyMethodHandler;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.serialization.spi.helpers.SerializableContextualInstance;
import org.jboss.weld.util.reflection.Reflections;

public class DecoratorProxyMethodHandler extends TargetInstanceProxyMethodHandler {
   private static final long serialVersionUID = 4577632640130385060L;
   private final SerializableContextualInstance decoratorInstance;

   public DecoratorProxyMethodHandler(SerializableContextualInstance decoratorInstance, Object delegateInstance) {
      super(delegateInstance, delegateInstance.getClass());
      this.decoratorInstance = decoratorInstance;
   }

   protected Object doInvoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
      Decorator decorator = (Decorator)this.decoratorInstance.getContextual().get();
      if (decorator instanceof WeldDecorator) {
         WeldDecorator weldDecorator = (WeldDecorator)decorator;
         return this.doInvoke(weldDecorator, this.decoratorInstance.getInstance(), method, args);
      } else {
         throw BeanLogger.LOG.unexpectedUnwrappedCustomDecorator(decorator);
      }
   }

   private Object doInvoke(WeldDecorator weldDecorator, Object decoratorInstance, Method method, Object[] args) throws Throwable {
      if (!method.isAnnotationPresent(Inject.class)) {
         InvokableAnnotatedMethod decoratorMethod = weldDecorator.getDecoratorMethod(method);
         if (decoratorMethod != null) {
            try {
               return decoratorMethod.invokeOnInstance(decoratorInstance, args);
            } catch (InvocationTargetException var7) {
               throw var7.getCause();
            }
         }
      }

      SecurityActions.ensureAccessible(method);
      return Reflections.invokeAndUnwrap(this.getTargetInstance(), method, args);
   }
}
