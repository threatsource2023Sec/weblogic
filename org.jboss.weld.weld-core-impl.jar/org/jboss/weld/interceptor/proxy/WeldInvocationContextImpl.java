package org.jboss.weld.interceptor.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.interceptor.InvocationContext;
import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;
import org.jboss.weld.bean.proxy.InterceptionDecorationContext;
import org.jboss.weld.interceptor.WeldInvocationContext;
import org.jboss.weld.logging.InterceptorLogger;
import org.jboss.weld.util.ForwardingInvocationContext;
import org.jboss.weld.util.Preconditions;

public class WeldInvocationContextImpl extends ForwardingInvocationContext implements WeldInvocationContext {
   private int position;
   private final List chain;
   private final CombinedInterceptorAndDecoratorStackMethodHandler currentHandler;
   private final InvocationContext delegate;
   private final Set interceptorBindings;

   public WeldInvocationContextImpl(Constructor constructor, Object[] parameters, Map contextData, List chain, Set interceptorBindings) {
      this(new SimpleInvocationContext(constructor, parameters, contextData, interceptorBindings), chain, interceptorBindings, (CombinedInterceptorAndDecoratorStackMethodHandler)null);
   }

   public WeldInvocationContextImpl(Object target, Method targetMethod, Method proceed, Object[] parameters, List chain, Set interceptorBindings, InterceptionDecorationContext.Stack stack) {
      this(new SimpleInvocationContext(target, targetMethod, proceed, parameters, interceptorBindings), chain, interceptorBindings, stack == null ? null : stack.peek());
   }

   public WeldInvocationContextImpl(InvocationContext delegate, List chain, Set interceptorBindings, CombinedInterceptorAndDecoratorStackMethodHandler currentHandler) {
      this.delegate = delegate;
      this.chain = chain;
      this.currentHandler = currentHandler;
      if (interceptorBindings == null) {
         this.interceptorBindings = Collections.emptySet();
      } else {
         this.interceptorBindings = interceptorBindings;
      }

      this.getContextData().put("org.jboss.weld.interceptor.bindings", interceptorBindings);
   }

   protected InvocationContext delegate() {
      return this.delegate;
   }

   public boolean hasNextInterceptor() {
      return this.position < this.chain.size();
   }

   protected Object invokeNext() throws Exception {
      int oldCurrentPosition = this.position;

      try {
         InterceptorMethodInvocation nextInterceptorMethodInvocation = (InterceptorMethodInvocation)this.chain.get(this.position++);
         InterceptorLogger.LOG.invokingNextInterceptorInChain(nextInterceptorMethodInvocation);
         Object var3;
         if (nextInterceptorMethodInvocation.expectsInvocationContext()) {
            var3 = nextInterceptorMethodInvocation.invoke(this);
            return var3;
         } else {
            nextInterceptorMethodInvocation.invoke((InvocationContext)null);

            while(this.hasNextInterceptor()) {
               nextInterceptorMethodInvocation = (InterceptorMethodInvocation)this.chain.get(this.position++);
               nextInterceptorMethodInvocation.invoke((InvocationContext)null);
            }

            var3 = null;
            return var3;
         }
      } finally {
         this.position = oldCurrentPosition;
      }
   }

   protected Object interceptorChainCompleted() throws Exception {
      return this.delegate().proceed();
   }

   public Object proceed() throws Exception {
      InterceptionDecorationContext.Stack stack = null;
      if (this.currentHandler != null && this.position != 0) {
         stack = InterceptionDecorationContext.startIfNotOnTop(this.currentHandler);
      }

      Object var2;
      try {
         if (this.hasNextInterceptor()) {
            var2 = this.invokeNext();
            return var2;
         }

         var2 = this.interceptorChainCompleted();
      } catch (InvocationTargetException var7) {
         Throwable cause = var7.getCause();
         if (cause instanceof Error) {
            throw (Error)cause;
         }

         if (cause instanceof Exception) {
            throw (Exception)cause;
         }

         throw new RuntimeException(cause);
      } finally {
         if (stack != null) {
            stack.end();
         }

      }

      return var2;
   }

   public Set getInterceptorBindingsByType(Class annotationType) {
      Preconditions.checkArgumentNotNull(annotationType, "annotationType");
      Set result = new HashSet();
      Iterator var3 = this.interceptorBindings.iterator();

      while(var3.hasNext()) {
         Annotation interceptorBinding = (Annotation)var3.next();
         if (interceptorBinding.annotationType().equals(annotationType)) {
            result.add(interceptorBinding);
         }
      }

      return result;
   }

   public Set getInterceptorBindings() {
      return this.interceptorBindings;
   }
}
