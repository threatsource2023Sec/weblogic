package org.jboss.weld.module.web;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.servlet.http.HttpServletRequest;
import org.jboss.weld.bean.builtin.AbstractStaticallyDecorableBuiltInBean;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.web.context.http.HttpRequestContextImpl;
import org.jboss.weld.module.web.logging.ServletLogger;
import org.jboss.weld.util.reflection.Reflections;

public class HttpServletRequestBean extends AbstractStaticallyDecorableBuiltInBean {
   public HttpServletRequestBean(BeanManagerImpl beanManager) {
      super(beanManager, HttpServletRequest.class);
   }

   protected HttpServletRequest newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      try {
         Context context = this.getBeanManager().getContext(RequestScoped.class);
         if (context instanceof HttpRequestContextImpl) {
            return ((HttpRequestContextImpl)Reflections.cast(context)).getHttpServletRequest();
         } else {
            throw ServletLogger.LOG.cannotInjectObjectOutsideOfServletRequest(HttpServletRequest.class.getSimpleName(), (Throwable)null);
         }
      } catch (ContextNotActiveException var4) {
         throw ServletLogger.LOG.cannotInjectObjectOutsideOfServletRequest(HttpServletRequest.class.getSimpleName(), var4);
      }
   }

   public Class getScope() {
      return RequestScoped.class;
   }
}
