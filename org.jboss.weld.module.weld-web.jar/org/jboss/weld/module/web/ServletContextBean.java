package org.jboss.weld.module.web;

import java.security.AccessController;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.servlet.ServletContext;
import org.jboss.weld.bean.builtin.AbstractStaticallyDecorableBuiltInBean;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.web.logging.ServletLogger;
import org.jboss.weld.module.web.servlet.ServletContextService;
import org.jboss.weld.security.GetContextClassLoaderAction;

public class ServletContextBean extends AbstractStaticallyDecorableBuiltInBean {
   private final ServletContextService servletContexts;

   public ServletContextBean(BeanManagerImpl beanManager) {
      super(beanManager, ServletContext.class);
      this.servletContexts = (ServletContextService)beanManager.getServices().get(ServletContextService.class);
   }

   protected ServletContext newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      ServletContext ctx = this.servletContexts.getCurrentServletContext();
      if (ctx == null) {
         ClassLoader cl = (ClassLoader)AccessController.doPrivileged(GetContextClassLoaderAction.INSTANCE);
         throw ServletLogger.LOG.cannotInjectServletContext(cl, this.servletContexts);
      } else {
         return ctx;
      }
   }

   public Class getScope() {
      return RequestScoped.class;
   }
}
