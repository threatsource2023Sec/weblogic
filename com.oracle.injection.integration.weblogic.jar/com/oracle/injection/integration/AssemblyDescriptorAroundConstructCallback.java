package com.oracle.injection.integration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.interceptor.InvocationContext;
import org.jboss.weld.construction.api.AroundConstructCallback;
import org.jboss.weld.construction.api.ConstructionHandle;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.interceptor.proxy.InterceptionContext;
import org.jboss.weld.interceptor.proxy.WeldInvocationContextImpl;

public class AssemblyDescriptorAroundConstructCallback implements AroundConstructCallback {
   private InterceptionContext interceptionContext;

   AssemblyDescriptorAroundConstructCallback(InterceptionContext interceptionCtx) {
      this.interceptionContext = interceptionCtx;
   }

   public Object aroundConstruct(final ConstructionHandle handle, AnnotatedConstructor constructor, Object[] parameters, Map data) throws Exception {
      final AtomicReference target = new AtomicReference();
      List chain = this.interceptionContext.buildInterceptorMethodInvocationsForConstructorInterception();
      InvocationContext invocationContext = new WeldInvocationContextImpl(constructor.getJavaMember(), parameters, data, chain, (Set)null) {
         protected Object interceptorChainCompleted() throws Exception {
            Object instance = handle.proceed(this.getParameters(), this.getContextData());
            target.set(instance);
            return null;
         }

         public Object getTarget() {
            return target.get();
         }
      };

      try {
         invocationContext.proceed();
      } catch (RuntimeException var9) {
         throw var9;
      } catch (Exception var10) {
         throw new WeldException(var10);
      }

      return target.get();
   }
}
