package org.jboss.weld.interceptor.proxy;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jboss.weld.bean.proxy.InterceptionDecorationContext;
import org.jboss.weld.bean.proxy.StackAwareMethodHandler;
import org.jboss.weld.interceptor.WeldInvocationContext;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptorMethodHandler implements StackAwareMethodHandler, Serializable {
   private static final long serialVersionUID = 1L;
   private final InterceptionContext ctx;
   private final transient ConcurrentMap cachedChains;

   public InterceptorMethodHandler(InterceptionContext ctx) {
      this.ctx = ctx;
      this.cachedChains = new ConcurrentHashMap();
   }

   public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
      return this.invoke(InterceptionDecorationContext.getStack(), self, thisMethod, proceed, args);
   }

   public Object invoke(InterceptionDecorationContext.Stack stack, Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
      SecurityActions.ensureAccessible(proceed);
      if (proceed == null) {
         if (thisMethod.getName().equals("lifecycle_mixin_$$_postConstruct")) {
            return this.executeInterception(self, (Method)null, (Method)null, (Object[])null, InterceptionType.POST_CONSTRUCT, stack);
         } else {
            return thisMethod.getName().equals("lifecycle_mixin_$$_preDestroy") ? this.executeInterception(self, (Method)null, (Method)null, (Object[])null, InterceptionType.PRE_DESTROY, stack) : null;
         }
      } else {
         return this.isInterceptorMethod(thisMethod) ? Reflections.invokeAndUnwrap(self, proceed, args) : this.executeInterception(self, thisMethod, proceed, args, InterceptionType.AROUND_INVOKE, stack);
      }
   }

   protected Object executeInterception(Object instance, Method method, Method proceed, Object[] args, InterceptionType interceptionType, InterceptionDecorationContext.Stack stack) throws Throwable {
      CachedInterceptionChain chain = this.getInterceptionChain(instance, method, interceptionType);
      if (chain.interceptorMethods.isEmpty()) {
         return proceed == null ? null : Reflections.invokeAndUnwrap(instance, proceed, args);
      } else {
         return InterceptionType.AROUND_INVOKE == interceptionType ? this.executeAroundInvoke(instance, method, proceed, args, chain, stack) : this.executeLifecycleInterception(instance, method, proceed, args, chain, stack);
      }
   }

   protected Object executeLifecycleInterception(Object instance, Method method, Method proceed, Object[] args, CachedInterceptionChain chain, InterceptionDecorationContext.Stack stack) throws Throwable {
      return (new WeldInvocationContextImpl(instance, method, proceed, args, chain.interceptorMethods, chain.interceptorBindings, stack)).proceed();
   }

   protected Object executeAroundInvoke(Object instance, Method method, Method proceed, Object[] args, CachedInterceptionChain chain, InterceptionDecorationContext.Stack stack) throws Throwable {
      WeldInvocationContext ctx = AroundInvokeInvocationContext.create(instance, method, proceed, args, chain.interceptorMethods, chain.interceptorBindings, stack);

      try {
         return ((InterceptorMethodInvocation)chain.interceptorMethods.get(0)).invoke(ctx);
      } catch (InvocationTargetException var9) {
         throw var9.getCause();
      }
   }

   private CachedInterceptionChain getInterceptionChain(Object instance, Method method, InterceptionType interceptionType) {
      if (method != null) {
         CachedInterceptionChain cachedChain = (CachedInterceptionChain)this.cachedChains.get(method);
         if (cachedChain == null) {
            cachedChain = new CachedInterceptionChain(this.ctx.buildInterceptorMethodInvocations(instance, method, interceptionType), this.ctx.getInterceptionModel().getMemberInterceptorBindings(method));
            CachedInterceptionChain old = (CachedInterceptionChain)this.cachedChains.putIfAbsent(method, cachedChain);
            if (old != null) {
               cachedChain = old;
            }
         }

         return cachedChain;
      } else {
         return new CachedInterceptionChain(this.ctx.buildInterceptorMethodInvocations(instance, (Method)null, interceptionType), this.ctx.getInterceptionModel().getClassInterceptorBindings());
      }
   }

   private boolean isInterceptorMethod(Method method) {
      return this.ctx.getInterceptionModel().getTargetClassInterceptorMetadata().isInterceptorMethod(method);
   }

   private Object readResolve() throws ObjectStreamException {
      return new InterceptorMethodHandler(this.ctx);
   }

   private static class CachedInterceptionChain {
      private final List interceptorMethods;
      private final Set interceptorBindings;

      public CachedInterceptionChain(List chain, Set interceptorBindings) {
         this.interceptorMethods = chain;
         this.interceptorBindings = interceptorBindings;
      }
   }
}
