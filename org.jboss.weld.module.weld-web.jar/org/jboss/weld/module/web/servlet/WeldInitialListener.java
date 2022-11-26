package org.jboss.weld.module.web.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import org.jboss.weld.Container;
import org.jboss.weld.bean.builtin.BeanManagerProxy;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.BeanManagers;
import org.jboss.weld.module.web.logging.ServletLogger;
import org.jboss.weld.module.web.util.servlet.ServletUtils;
import org.jboss.weld.servlet.api.helpers.AbstractServletListener;
import org.jboss.weld.servlet.spi.HttpContextActivationFilter;

public class WeldInitialListener extends AbstractServletListener {
   private static final String CONTEXT_IGNORE_GUARD_PARAMETER = "org.jboss.weld.context.ignore.guard";
   @Inject
   private BeanManagerImpl beanManager;
   private HttpContextLifecycle lifecycle;

   public WeldInitialListener() {
   }

   public WeldInitialListener(BeanManagerImpl beanManager) {
      this.beanManager = beanManager;
   }

   public void contextInitialized(ServletContextEvent sce) {
      ServletContext ctx = sce.getServletContext();
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

      HttpContextActivationFilter filter = ServletUtils.getContextActivationFilter(this.beanManager, ctx);
      boolean ignoreForwards = this.getBooleanInitParameter(ctx, "org.jboss.weld.context.ignore.forward", false);
      boolean ignoreIncludes = this.getBooleanInitParameter(ctx, "org.jboss.weld.context.ignore.include", false);
      boolean nestedInvocationGuard = this.getBooleanInitParameter(ctx, "org.jboss.weld.context.ignore.guard", true);
      boolean lazyConversationContext = this.getBooleanInitParameter(ctx, "org.jboss.weld.context.conversation.lazy", true);
      this.lifecycle = new HttpContextLifecycle(this.beanManager, filter, ignoreForwards, ignoreIncludes, lazyConversationContext, nestedInvocationGuard);
      if (Boolean.valueOf(ctx.getInitParameter(ConversationFilter.CONVERSATION_FILTER_REGISTERED))) {
         this.lifecycle.setConversationActivationEnabled(false);
      }

      this.lifecycle.contextInitialized(ctx);
      ctx.setAttribute(WeldInitialListener.class.getName(), this);
   }

   private boolean getBooleanInitParameter(ServletContext ctx, String parameterName, boolean defaultValue) {
      String value = ctx.getInitParameter(parameterName);
      return value == null ? defaultValue : Boolean.valueOf(value);
   }

   public void contextDestroyed(ServletContextEvent sce) {
      this.lifecycle.contextDestroyed(sce.getServletContext());
   }

   public void sessionCreated(HttpSessionEvent event) {
      this.lifecycle.sessionCreated(event.getSession());
   }

   public void sessionDestroyed(HttpSessionEvent event) {
      this.lifecycle.sessionDestroyed(event.getSession());
   }

   public void requestDestroyed(ServletRequestEvent event) {
      if (event.getServletRequest() instanceof HttpServletRequest) {
         this.lifecycle.requestDestroyed((HttpServletRequest)event.getServletRequest());
      } else {
         throw ServletLogger.LOG.onlyHttpServletLifecycleDefined();
      }
   }

   public void requestInitialized(ServletRequestEvent event) {
      if (!this.lifecycle.isConversationActivationSet()) {
         Object value = event.getServletContext().getAttribute(ConversationFilter.CONVERSATION_FILTER_REGISTERED);
         if (Boolean.TRUE.equals(value)) {
            this.lifecycle.setConversationActivationEnabled(false);
         } else {
            this.lifecycle.setConversationActivationEnabled(true);
         }
      }

      if (event.getServletRequest() instanceof HttpServletRequest) {
         this.lifecycle.requestInitialized((HttpServletRequest)event.getServletRequest(), event.getServletContext());
      } else {
         throw ServletLogger.LOG.onlyHttpServletLifecycleDefined();
      }
   }
}
