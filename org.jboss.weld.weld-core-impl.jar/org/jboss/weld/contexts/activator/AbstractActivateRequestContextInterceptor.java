package org.jboss.weld.contexts.activator;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.Initialized.Literal;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.event.FastEvent;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractActivateRequestContextInterceptor {
   protected final BeanManagerImpl beanManager;
   protected final RequestContext requestContext;
   protected final FastEvent fastEventInit;
   protected final FastEvent fastEventBeforeDestroyed;
   protected final FastEvent fastEventDestroyed;

   public AbstractActivateRequestContextInterceptor(RequestContext requestContext, BeanManagerImpl beanManager) {
      this.beanManager = beanManager;
      this.requestContext = requestContext;
      this.fastEventInit = FastEvent.of(Object.class, beanManager, Literal.REQUEST);
      this.fastEventBeforeDestroyed = FastEvent.of(Object.class, beanManager, javax.enterprise.context.BeforeDestroyed.Literal.REQUEST);
      this.fastEventDestroyed = FastEvent.of(Object.class, beanManager, javax.enterprise.context.Destroyed.Literal.REQUEST);
   }

   @AroundInvoke
   Object invoke(InvocationContext ctx) throws Exception {
      if (this.isRequestContextActive()) {
         return ctx.proceed();
      } else {
         Object dummyPayload = new Object();

         Object var3;
         try {
            this.requestContext.activate();
            this.fastEventInit.fire(dummyPayload);
            var3 = ctx.proceed();
         } finally {
            this.requestContext.invalidate();
            this.fastEventBeforeDestroyed.fire(dummyPayload);
            this.requestContext.deactivate();
            this.fastEventDestroyed.fire(dummyPayload);
         }

         return var3;
      }
   }

   boolean isRequestContextActive() {
      return this.beanManager.isContextActive(RequestScoped.class);
   }
}
