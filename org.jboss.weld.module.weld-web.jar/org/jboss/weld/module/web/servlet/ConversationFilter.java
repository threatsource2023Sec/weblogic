package org.jboss.weld.module.web.servlet;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.web.logging.ServletLogger;
import org.jboss.weld.module.web.util.servlet.ServletUtils;
import org.jboss.weld.servlet.spi.HttpContextActivationFilter;

public class ConversationFilter implements Filter {
   public static final String CONVERSATION_FILTER_REGISTERED = ConversationFilter.class.getName() + ".registered";
   @Inject
   private BeanManagerImpl manager;
   private HttpContextActivationFilter contextActivationFilter;
   private ConversationContextActivator conversationContextActivator;

   public void init(FilterConfig filterConfig) throws ServletException {
      this.conversationContextActivator = new ConversationContextActivator(this.manager, false);
      filterConfig.getServletContext().setAttribute(CONVERSATION_FILTER_REGISTERED, Boolean.TRUE);
      this.contextActivationFilter = ServletUtils.getContextActivationFilter(this.manager, filterConfig.getServletContext());
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      if (request instanceof HttpServletRequest) {
         HttpServletRequest httpRequest = (HttpServletRequest)request;
         if (this.contextActivationFilter.accepts(httpRequest)) {
            this.conversationContextActivator.startConversationContext(httpRequest);
         }

         chain.doFilter(request, response);
      } else {
         throw ServletLogger.LOG.onlyHttpServletLifecycleDefined();
      }
   }

   public void destroy() {
   }
}
