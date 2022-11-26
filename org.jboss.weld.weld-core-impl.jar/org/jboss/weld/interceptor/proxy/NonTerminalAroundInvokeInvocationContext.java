package org.jboss.weld.interceptor.proxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;
import org.jboss.weld.interceptor.WeldInvocationContext;

class NonTerminalAroundInvokeInvocationContext extends AroundInvokeInvocationContext {
   private final int position;
   private final List chain;

   public NonTerminalAroundInvokeInvocationContext(Object target, Method method, Method proceed, Object[] parameters, Set interceptorBindings, List chain, CombinedInterceptorAndDecoratorStackMethodHandler currentHandler) {
      this(target, method, proceed, parameters, newContextData(interceptorBindings), interceptorBindings, 0, chain, currentHandler);
   }

   public NonTerminalAroundInvokeInvocationContext(NonTerminalAroundInvokeInvocationContext ctx) {
      this(ctx.getTarget(), ctx.getMethod(), ctx.getProceed(), ctx.getParameters(), ctx.contextData, ctx.getInterceptorBindings(), ctx.position + 1, ctx.chain, ctx.currentHandler);
   }

   private NonTerminalAroundInvokeInvocationContext(Object target, Method method, Method proceed, Object[] parameters, Map contextData, Set interceptorBindings, int position, List chain, CombinedInterceptorAndDecoratorStackMethodHandler currentHandler) {
      super(target, method, proceed, parameters, contextData, interceptorBindings, currentHandler);
      this.position = position;
      this.chain = chain;
   }

   public Object proceedInternal() throws Exception {
      WeldInvocationContext ctx = this.createNextContext();
      return ((InterceptorMethodInvocation)this.chain.get(this.position + 1)).invoke(ctx);
   }

   private WeldInvocationContext createNextContext() {
      return (WeldInvocationContext)(this.position + 2 == this.chain.size() ? new TerminalAroundInvokeInvocationContext(this) : new NonTerminalAroundInvokeInvocationContext(this));
   }

   public String toString() {
      return "NonTerminalAroundInvokeInvocationContext [method=" + this.method + ", interceptor=" + this.chain.get(this.position) + ']';
   }
}
