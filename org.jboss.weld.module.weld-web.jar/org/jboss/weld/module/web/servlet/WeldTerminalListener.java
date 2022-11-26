package org.jboss.weld.module.web.servlet;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.jboss.weld.Container;
import org.jboss.weld.bean.builtin.BeanManagerProxy;
import org.jboss.weld.context.http.HttpSessionContext;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.BeanManagers;
import org.jboss.weld.module.web.context.http.HttpSessionDestructionContext;

public class WeldTerminalListener implements HttpSessionListener {
   @Inject
   private volatile BeanManagerImpl beanManager;

   public WeldTerminalListener() {
   }

   public WeldTerminalListener(BeanManagerImpl beanManager) {
      this.beanManager = beanManager;
   }

   public void sessionCreated(HttpSessionEvent httpSessionEvent) {
   }

   public void sessionDestroyed(HttpSessionEvent event) {
      ServletContext ctx = event.getSession().getServletContext();
      if (this.beanManager == null) {
         synchronized(this) {
            if (this.beanManager == null) {
               String contextId = ctx.getInitParameter("WELD_CONTEXT_ID_KEY");
               if (contextId != null) {
                  List managers = new ArrayList(Container.instance(contextId).beanDeploymentArchives().values());
                  Collections.sort(managers, BeanManagers.ID_COMPARATOR);
                  this.beanManager = (BeanManagerImpl)managers.get(0);
               }
            }

            if (this.beanManager == null) {
               this.beanManager = BeanManagerProxy.unwrap(CDI.current().getBeanManager());
            }
         }
      }

      if (!this.getSessionContext().isActive()) {
         HttpSessionDestructionContext context = this.getHttpSessionDestructionContext();
         context.associate(event.getSession());
         context.activate();
      }

   }

   private HttpSessionContext getSessionContext() {
      return (HttpSessionContext)this.beanManager.instance().select(HttpSessionContext.class, new Annotation[0]).get();
   }

   private HttpSessionDestructionContext getHttpSessionDestructionContext() {
      return (HttpSessionDestructionContext)this.beanManager.instance().select(HttpSessionDestructionContext.class, new Annotation[0]).get();
   }
}
