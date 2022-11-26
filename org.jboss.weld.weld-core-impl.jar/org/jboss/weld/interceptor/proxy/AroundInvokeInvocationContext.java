package org.jboss.weld.interceptor.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;
import org.jboss.weld.bean.proxy.InterceptionDecorationContext;
import org.jboss.weld.util.reflection.Reflections;

abstract class AroundInvokeInvocationContext extends AbstractInvocationContext {
   final CombinedInterceptorAndDecoratorStackMethodHandler currentHandler;

   public static AroundInvokeInvocationContext create(Object instance, Method method, Method proceed, Object[] args, List chain, Set interceptorBindings, InterceptionDecorationContext.Stack stack) {
      CombinedInterceptorAndDecoratorStackMethodHandler currentHandler = stack == null ? null : stack.peek();
      return (AroundInvokeInvocationContext)(chain.size() == 1 ? new TerminalAroundInvokeInvocationContext(instance, method, proceed, args, (Map)null, interceptorBindings, currentHandler) : new NonTerminalAroundInvokeInvocationContext(instance, method, proceed, args, interceptorBindings, chain, currentHandler));
   }

   AroundInvokeInvocationContext(Object target, Method method, Method proceed, Object[] parameters, Map contextData, Set interceptorBindings, CombinedInterceptorAndDecoratorStackMethodHandler currentHandler) {
      super(target, method, proceed, parameters, contextData, interceptorBindings);
      this.currentHandler = currentHandler;
   }

   public Object proceed() throws Exception {
      InterceptionDecorationContext.Stack stack = InterceptionDecorationContext.startIfNotOnTop(this.currentHandler);

      Object var2;
      try {
         var2 = this.proceedInternal();
      } catch (InvocationTargetException var6) {
         throw Reflections.unwrapInvocationTargetException(var6);
      } finally {
         if (stack != null) {
            stack.end();
         }

      }

      return var2;
   }

   abstract Object proceedInternal() throws Exception;
}
