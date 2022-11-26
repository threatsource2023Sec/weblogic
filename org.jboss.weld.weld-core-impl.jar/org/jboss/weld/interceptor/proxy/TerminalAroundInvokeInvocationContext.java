package org.jboss.weld.interceptor.proxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;

class TerminalAroundInvokeInvocationContext extends AroundInvokeInvocationContext {
   public TerminalAroundInvokeInvocationContext(Object target, Method method, Method proceed, Object[] parameters, Map contextData, Set interceptorBindings, CombinedInterceptorAndDecoratorStackMethodHandler currentHandler) {
      super(target, method, proceed, parameters, contextData == null ? null : new HashMap(contextData), interceptorBindings, currentHandler);
   }

   public TerminalAroundInvokeInvocationContext(NonTerminalAroundInvokeInvocationContext ctx) {
      super(ctx.getTarget(), ctx.getMethod(), ctx.getProceed(), ctx.getParameters(), ctx.contextData, ctx.getInterceptorBindings(), ctx.currentHandler);
   }

   public Object proceedInternal() throws Exception {
      return this.getProceed().invoke(this.getTarget(), this.getParameters());
   }

   public String toString() {
      return "TerminalAroundInvokeInvocationContext [method=" + this.method + ']';
   }
}
