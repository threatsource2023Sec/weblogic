package org.jboss.weld.bean.builtin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.Initialized.Literal;
import javax.enterprise.context.control.RequestContextController;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.event.FastEvent;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class RequestContextControllerBean extends AbstractStaticallyDecorableBuiltInBean {
   public RequestContextControllerBean(BeanManagerImpl beanManager) {
      super(beanManager, RequestContextController.class);
   }

   protected RequestContextController newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      return new InjectableRequestContextController(this.beanManager, this.getUnboundRequestContext());
   }

   private RequestContext getUnboundRequestContext() {
      Bean bean = this.beanManager.resolve(this.beanManager.getBeans(RequestContext.class, (Annotation[])(UnboundLiteral.INSTANCE)));
      CreationalContext ctx = this.beanManager.createCreationalContext(bean);
      return (RequestContext)this.beanManager.getReference((Bean)bean, (Type)RequestContext.class, ctx);
   }

   private static class InjectableRequestContextController implements RequestContextController {
      private final BeanManagerImpl beanManager;
      private final RequestContext requestContext;
      private final AtomicBoolean isActivator;
      private final FastEvent requestInitializedEvent;
      private final FastEvent requestBeforeDestroyedEvent;
      private final FastEvent requestDestroyedEvent;

      InjectableRequestContextController(BeanManagerImpl beanManager, RequestContext requestContext) {
         this.beanManager = beanManager;
         this.requestContext = requestContext;
         this.isActivator = new AtomicBoolean(false);
         this.requestInitializedEvent = FastEvent.of(Object.class, beanManager, Literal.REQUEST);
         this.requestBeforeDestroyedEvent = FastEvent.of(Object.class, beanManager, javax.enterprise.context.BeforeDestroyed.Literal.REQUEST);
         this.requestDestroyedEvent = FastEvent.of(Object.class, beanManager, javax.enterprise.context.Destroyed.Literal.REQUEST);
      }

      public boolean activate() {
         if (this.isRequestContextActive()) {
            return false;
         } else {
            this.requestContext.activate();
            this.requestInitializedEvent.fire(this.toString());
            this.isActivator.set(true);
            return true;
         }
      }

      public void deactivate() throws ContextNotActiveException {
         if (!this.isRequestContextActive()) {
            throw BeanManagerLogger.LOG.contextNotActive(RequestScoped.class);
         } else {
            if (this.isActivator.compareAndSet(true, false)) {
               try {
                  this.requestBeforeDestroyedEvent.fire(this.toString());
                  this.requestContext.invalidate();
                  this.requestContext.deactivate();
               } finally {
                  this.requestDestroyedEvent.fire(this.toString());
               }
            }

         }
      }

      private boolean isRequestContextActive() {
         return this.beanManager.isContextActive(RequestScoped.class);
      }
   }
}
