package org.jboss.weld.injection.producer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.interceptor.InvocationContext;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.construction.api.AroundConstructCallback;
import org.jboss.weld.construction.api.ConstructionHandle;
import org.jboss.weld.contexts.CreationalContextImpl;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.interceptor.proxy.InterceptionContext;
import org.jboss.weld.interceptor.proxy.WeldInvocationContextImpl;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.logging.InterceptorLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class ConstructorInterceptionInstantiator extends ForwardingInstantiator {
   private final InterceptionModel model;
   private final SlimAnnotatedType annotatedType;

   public ConstructorInterceptionInstantiator(Instantiator delegate, InterceptionModel model, SlimAnnotatedType type) {
      super(delegate);
      this.model = model;
      this.annotatedType = type;
   }

   public Object newInstance(CreationalContext ctx, BeanManagerImpl manager) {
      if (ctx instanceof CreationalContextImpl) {
         CreationalContextImpl weldCtx = (CreationalContextImpl)Reflections.cast(ctx);
         if (!weldCtx.isConstructorInterceptionSuppressed()) {
            this.registerAroundConstructCallback(weldCtx, manager);
         }
      }

      return this.delegate().newInstance(ctx, manager);
   }

   private void registerAroundConstructCallback(CreationalContextImpl ctx, BeanManagerImpl manager) {
      final InterceptionContext interceptionContext = InterceptionContext.forConstructorInterception(this.model, ctx, manager, this.annotatedType);
      AroundConstructCallback callback = new AroundConstructCallback() {
         public Object aroundConstruct(final ConstructionHandle handle, AnnotatedConstructor constructor, Object[] parameters, Map data) {
            final AtomicReference target = new AtomicReference();
            List chain = interceptionContext.buildInterceptorMethodInvocationsForConstructorInterception();
            InvocationContext invocationContext = new WeldInvocationContextImpl(constructor.getJavaMember(), parameters, data, chain, ConstructorInterceptionInstantiator.this.model.getMemberInterceptorBindings(ConstructorInterceptionInstantiator.this.getConstructor())) {
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

            Object instance = target.get();
            if (instance == null) {
               throw InterceptorLogger.LOG.targetInstanceNotCreated(constructor);
            } else {
               return instance;
            }
         }
      };
      ctx.registerAroundConstructCallback(callback);
      ctx.setAroundConstructInterceptionContext(interceptionContext);
   }

   public String toString() {
      return "ConstructorInterceptionInstantiator wrapping " + this.delegate();
   }
}
