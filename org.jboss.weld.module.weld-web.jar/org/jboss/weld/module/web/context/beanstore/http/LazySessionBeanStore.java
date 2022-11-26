package org.jboss.weld.module.web.context.beanstore.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.module.web.servlet.SessionHolder;

public class LazySessionBeanStore extends AbstractSessionBeanStore {
   private final HttpServletRequest request;

   public LazySessionBeanStore(HttpServletRequest request, NamingScheme namingScheme, ServiceRegistry serviceRegistry) {
      this(request, namingScheme, true, serviceRegistry);
   }

   public LazySessionBeanStore(HttpServletRequest request, NamingScheme namingScheme, boolean attributeLazyFetchingEnabled, ServiceRegistry serviceRegistry) {
      super(namingScheme, attributeLazyFetchingEnabled, serviceRegistry);
      this.request = request;
      ContextLogger.LOG.loadingBeanStoreMapFromSession(this, this.getSession(false));
   }

   protected HttpSession getSessionIfExists() {
      return SessionHolder.getSessionIfExists();
   }

   protected HttpSession getSession(boolean create) {
      try {
         return SessionHolder.getSession(this.request, create);
      } catch (IllegalStateException var3) {
         this.detach();
         return null;
      }
   }
}
