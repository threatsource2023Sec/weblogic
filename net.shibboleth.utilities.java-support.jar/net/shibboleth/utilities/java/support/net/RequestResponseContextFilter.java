package net.shibboleth.utilities.java.support.net;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestResponseContextFilter implements Filter {
   public void init(FilterConfig filterConfig) throws ServletException {
   }

   public void destroy() {
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      if (!(request instanceof HttpServletRequest)) {
         throw new ServletException("Request is not an instance of HttpServletRequest");
      } else if (!(response instanceof HttpServletResponse)) {
         throw new ServletException("Response is not an instance of HttpServletResponse");
      } else {
         try {
            HttpServletRequestResponseContext.loadCurrent((HttpServletRequest)request, (HttpServletResponse)response);
            chain.doFilter(request, response);
         } finally {
            HttpServletRequestResponseContext.clearCurrent();
         }

      }
   }
}
