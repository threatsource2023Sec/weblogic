package org.jboss.weld.module.web.context.http;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpSession;
import org.jboss.weld.contexts.AbstractBoundContext;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.contexts.beanstore.SimpleBeanIdentifierIndexNamingScheme;
import org.jboss.weld.module.web.context.beanstore.http.EagerSessionBeanStore;
import org.jboss.weld.serialization.BeanIdentifierIndex;

public class HttpSessionDestructionContext extends AbstractBoundContext {
   private final NamingScheme namingScheme;

   public HttpSessionDestructionContext(String contextId, BeanIdentifierIndex index) {
      super(contextId, true);
      this.namingScheme = new SimpleBeanIdentifierIndexNamingScheme("WELD_S", index);
   }

   public boolean associate(HttpSession session) {
      if (this.getBeanStore() == null) {
         this.setBeanStore(new EagerSessionBeanStore(this.namingScheme, session, this.getServiceRegistry()));
         return true;
      } else {
         return false;
      }
   }

   public Class getScope() {
      return SessionScoped.class;
   }
}
