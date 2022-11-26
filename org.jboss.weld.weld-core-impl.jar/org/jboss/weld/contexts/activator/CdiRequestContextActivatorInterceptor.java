package org.jboss.weld.contexts.activator;

import javax.annotation.Priority;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.Unbound;
import org.jboss.weld.manager.BeanManagerImpl;

@Vetoed
@Interceptor
@ActivateRequestContext
@Priority(100)
public class CdiRequestContextActivatorInterceptor extends AbstractActivateRequestContextInterceptor {
   @Inject
   public CdiRequestContextActivatorInterceptor(@Unbound RequestContext requestContext, BeanManagerImpl beanManager) {
      super(requestContext, beanManager);
   }
}
