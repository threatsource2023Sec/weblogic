package org.jboss.weld.module.web.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletResponse;

public class WeldCrossContextFilter implements Filter {
   private static final String REQUEST_CONTEXT_KEY = "org.jboss.weld.context.http.HttpRequestContextImpl";
   private volatile WeldInitialListener listener;
   private FilterConfig config;

   public void init(FilterConfig filterConfig) throws ServletException {
      this.config = filterConfig;
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      if (this.listener == null) {
         this.listener = (WeldInitialListener)request.getServletContext().getAttribute(WeldInitialListener.class.getName());
      }

      boolean crossCtx = request.getAttribute("javax.servlet.include.request_uri") != null || request.getAttribute("javax.servlet.forward.request_uri") != null || request.getAttribute("javax.servlet.error.request_uri") != null;
      boolean activated = false;

      try {
         if (crossCtx && request.getAttribute("org.jboss.weld.context.http.HttpRequestContextImpl") == null) {
            this.listener.requestInitialized(new ServletRequestEvent(this.config.getServletContext(), request));
            activated = true;
         }

         chain.doFilter(request, response);
      } finally {
         if (activated) {
            this.listener.requestDestroyed(new ServletRequestEvent(this.config.getServletContext(), request));
         }

      }

   }

   public void destroy() {
   }
}
