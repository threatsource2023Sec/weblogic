package org.jboss.weld.module.web.context.beanstore.http;

import javax.servlet.http.HttpSession;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.logging.ContextLogger;

public class EagerSessionBeanStore extends AbstractSessionBeanStore {
   private final HttpSession session;

   public EagerSessionBeanStore(NamingScheme namingScheme, HttpSession session, ServiceRegistry serviceRegistry) {
      super(namingScheme, false, serviceRegistry);
      this.session = session;
      ContextLogger.LOG.loadingBeanStoreMapFromSession(this, this.getSession(false));
   }

   protected HttpSession getSession(boolean create) {
      return this.session;
   }
}
