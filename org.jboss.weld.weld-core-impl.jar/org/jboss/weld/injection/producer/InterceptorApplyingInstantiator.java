package org.jboss.weld.injection.producer;

import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;
import org.jboss.weld.bean.proxy.ProxyObject;
import org.jboss.weld.contexts.CreationalContextImpl;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.interceptor.proxy.InterceptionContext;
import org.jboss.weld.interceptor.proxy.InterceptorMethodHandler;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptorApplyingInstantiator extends ForwardingInstantiator {
   private final InterceptionModel interceptionModel;
   private final SlimAnnotatedType annotatedType;

   public InterceptorApplyingInstantiator(Instantiator delegate, InterceptionModel model, SlimAnnotatedType type) {
      super(delegate);
      this.interceptionModel = model;
      this.annotatedType = type;
   }

   public Object newInstance(CreationalContext ctx, BeanManagerImpl manager) {
      InterceptionContext interceptionContext = null;
      if (ctx instanceof CreationalContextImpl) {
         CreationalContextImpl weldCtx = (CreationalContextImpl)Reflections.cast(ctx);
         interceptionContext = weldCtx.getAroundConstructInterceptionContext();
      }

      if (interceptionContext == null) {
         interceptionContext = InterceptionContext.forNonConstructorInterception(this.interceptionModel, ctx, manager, this.annotatedType);
      }

      Object instance = this.delegate().newInstance(ctx, manager);
      this.applyInterceptors(instance, interceptionContext);
      return instance;
   }

   protected Object applyInterceptors(Object instance, InterceptionContext interceptionContext) {
      try {
         InterceptorMethodHandler methodHandler = new InterceptorMethodHandler(interceptionContext);
         CombinedInterceptorAndDecoratorStackMethodHandler wrapperMethodHandler = (CombinedInterceptorAndDecoratorStackMethodHandler)((ProxyObject)instance).weld_getHandler();
         wrapperMethodHandler.setInterceptorMethodHandler(methodHandler);
         return instance;
      } catch (Exception var5) {
         throw new DeploymentException(var5);
      }
   }

   public String toString() {
      return "InterceptorApplyingInstantiator for " + this.delegate();
   }

   public boolean hasInterceptorSupport() {
      return true;
   }
}
