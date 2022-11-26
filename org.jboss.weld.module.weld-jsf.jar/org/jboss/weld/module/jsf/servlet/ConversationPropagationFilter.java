package org.jboss.weld.module.jsf.servlet;

import java.io.IOException;
import java.lang.annotation.Annotation;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.spi.Context;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.jboss.weld.Container;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.http.HttpConversationContext;
import org.jboss.weld.module.jsf.FacesUrlTransformer;

/** @deprecated */
@Deprecated
public class ConversationPropagationFilter implements Filter {
   private String contextId;

   public void init(FilterConfig config) throws ServletException {
      this.contextId = (String)config.getServletContext().getAttribute("WELD_CONTEXT_ID_KEY");
      if (this.contextId == null) {
         this.contextId = "STATIC_INSTANCE";
      }

   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
         response = this.wrapResponse((HttpServletResponse)response, ((HttpServletRequest)request).getContextPath());
      }

      chain.doFilter(request, response);
   }

   public void destroy() {
   }

   private ServletResponse wrapResponse(HttpServletResponse response, String requestPath) {
      return new HttpServletResponseWrapper(response) {
         public void sendRedirect(String path) throws IOException {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null) {
               ConversationContext conversationContext = (ConversationContext)ConversationPropagationFilter.instance(ConversationPropagationFilter.this.contextId).select(HttpConversationContext.class, new Annotation[0]).get();
               if (conversationContext.isActive()) {
                  Conversation conversation = conversationContext.getCurrentConversation();
                  if (!conversation.isTransient()) {
                     path = (new FacesUrlTransformer(path, context)).toRedirectViewId().toActionUrl().appendConversationIdIfNecessary(conversationContext.getParameterName(), conversation.getId()).encode();
                  }
               }
            }

            super.sendRedirect(path);
         }
      };
   }

   private static Instance instance(String id) {
      return Container.instance(id).deploymentManager().instance().select(Context.class, new Annotation[0]);
   }
}
