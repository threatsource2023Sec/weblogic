package org.jboss.weld.interceptor.reader;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.interceptor.spi.metadata.InterceptorFactory;
import org.jboss.weld.manager.BeanManagerImpl;

public class PlainInterceptorFactory implements InterceptorFactory {
   private final InjectionTarget injectionTarget;

   public static PlainInterceptorFactory of(Class javaClass, BeanManagerImpl manager) {
      AnnotatedType type = manager.createAnnotatedType(javaClass);
      InjectionTarget it = manager.createInjectionTargetBuilder(type).setDecorationEnabled(false).setInterceptionEnabled(false).setResourceInjectionEnabled(true).setTargetClassLifecycleCallbacksEnabled(false).build();
      return new PlainInterceptorFactory(it);
   }

   public PlainInterceptorFactory(InjectionTarget injectionTarget) {
      this.injectionTarget = injectionTarget;
   }

   public Object create(CreationalContext ctx, BeanManagerImpl manager) {
      if (ctx instanceof WeldCreationalContext) {
         WeldCreationalContext weldCtx = (WeldCreationalContext)ctx;
         ctx = weldCtx.getCreationalContext((Contextual)null);
      }

      Object instance = this.injectionTarget.produce((CreationalContext)ctx);
      this.injectionTarget.inject(instance, (CreationalContext)ctx);
      return instance;
   }

   public InjectionTarget getInjectionTarget() {
      return this.injectionTarget;
   }
}
