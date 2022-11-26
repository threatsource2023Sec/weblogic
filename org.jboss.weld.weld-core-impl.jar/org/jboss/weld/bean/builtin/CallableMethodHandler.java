package org.jboss.weld.bean.builtin;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import org.jboss.weld.bean.proxy.MethodHandler;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.util.reflection.Reflections;

public class CallableMethodHandler implements MethodHandler, Serializable {
   private static final long serialVersionUID = -1348302663981663427L;
   private final Callable callable;

   public CallableMethodHandler(Callable callable) {
      this.callable = callable;
   }

   public Object invoke(Object self, Method proxiedMethod, Method proceed, Object[] args) throws Throwable {
      Object instance = this.callable.call();
      if (instance == null) {
         throw BeanLogger.LOG.nullInstance(this.callable);
      } else {
         Object returnValue = Reflections.invokeAndUnwrap(instance, proxiedMethod, args);
         BeanLogger.LOG.callProxiedMethod(proxiedMethod, instance, args, returnValue == null ? null : returnValue);
         return returnValue;
      }
   }
}
