package org.jboss.weld.module.ejb;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.Initialized.Literal;
import javax.interceptor.InvocationContext;
import org.jboss.weld.context.ejb.EjbRequestContext;
import org.jboss.weld.event.ContextEvent;
import org.jboss.weld.event.FastEvent;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.LazyValueHolder;

public abstract class AbstractEJBRequestScopeActivationInterceptor implements Serializable {
   private static final long serialVersionUID = 7327757031821596782L;
   private final LazyValueHolder requestInitializedEvent = new LazyValueHolder.Serializable() {
      private static final long serialVersionUID = 1L;

      protected FastEvent computeValue() {
         return FastEvent.of(Object.class, AbstractEJBRequestScopeActivationInterceptor.this.getBeanManager(), AbstractEJBRequestScopeActivationInterceptor.this.getBeanManager().getGlobalLenientObserverNotifier(), new Annotation[]{Literal.REQUEST});
      }
   };
   private final LazyValueHolder requestBeforeDestroyedEvent = new LazyValueHolder.Serializable() {
      private static final long serialVersionUID = 1L;

      protected FastEvent computeValue() {
         return FastEvent.of(Object.class, AbstractEJBRequestScopeActivationInterceptor.this.getBeanManager(), AbstractEJBRequestScopeActivationInterceptor.this.getBeanManager().getGlobalLenientObserverNotifier(), new Annotation[]{javax.enterprise.context.BeforeDestroyed.Literal.REQUEST});
      }
   };
   private final LazyValueHolder requestDestroyedEvent = new LazyValueHolder.Serializable() {
      private static final long serialVersionUID = 1L;

      protected FastEvent computeValue() {
         return FastEvent.of(Object.class, AbstractEJBRequestScopeActivationInterceptor.this.getBeanManager(), AbstractEJBRequestScopeActivationInterceptor.this.getBeanManager().getGlobalLenientObserverNotifier(), new Annotation[]{javax.enterprise.context.Destroyed.Literal.REQUEST});
      }
   };

   public Object aroundInvoke(InvocationContext invocation) throws Exception {
      if (this.isRequestContextActive()) {
         return invocation.proceed();
      } else {
         EjbRequestContext requestContext = this.getEjbRequestContext();

         Object var3;
         try {
            requestContext.associate(invocation);
            requestContext.activate();

            try {
               ((FastEvent)this.requestInitializedEvent.get()).fire(ContextEvent.REQUEST_INITIALIZED_EJB);
               var3 = invocation.proceed();
            } finally {
               ((FastEvent)this.requestBeforeDestroyedEvent.get()).fire(ContextEvent.REQUEST_BEFORE_DESTROYED_EJB);
               requestContext.invalidate();
               requestContext.deactivate();
            }
         } finally {
            requestContext.dissociate(invocation);
            ((FastEvent)this.requestDestroyedEvent.get()).fire(ContextEvent.REQUEST_DESTROYED_EJB);
         }

         return var3;
      }
   }

   protected boolean isRequestContextActive() {
      return this.getBeanManager().isContextActive(RequestScoped.class);
   }

   protected EjbRequestContext getEjbRequestContext() {
      return (EjbRequestContext)this.getBeanManager().instance().select(EjbRequestContext.class, new Annotation[0]).get();
   }

   protected abstract BeanManagerImpl getBeanManager();
}
