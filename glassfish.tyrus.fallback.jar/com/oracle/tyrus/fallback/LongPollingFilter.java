package com.oracle.tyrus.fallback;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LongPollingFilter implements Filter {
   private static final Logger LOGGER = Logger.getLogger(LongPollingFilter.class.getName());
   private static final String NIO_PARAM = "com.oracle.tyrus.fallback.non-blocking.enabled";
   private LongPollingConnectionMgr mgr;

   public void init(FilterConfig filterConfig) throws ServletException {
      ServletContext ctxt = filterConfig.getServletContext();
      String paramValue = ctxt.getInitParameter("com.oracle.tyrus.fallback.non-blocking.enabled");
      boolean enabled = paramValue != null && paramValue.equals("true");
      this.mgr = new LongPollingConnectionMgr(enabled);
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest)request;
      HttpServletResponse res = (HttpServletResponse)response;
      String conId = FallbackUtil.getHeaderValue(req, "tyrus-connection-id");
      if (this.mgr != null && conId != null) {
         this.mgr.processRequest(req, res, conId, filterChain);
      } else {
         filterChain.doFilter(request, response);
      }
   }

   public void destroy() {
   }
}
