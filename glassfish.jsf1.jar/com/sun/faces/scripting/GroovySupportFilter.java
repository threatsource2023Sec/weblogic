package com.sun.faces.scripting;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class GroovySupportFilter implements Filter {
   private boolean helperChecked;
   private GroovyHelper helper;
   private ServletContext sc;

   public void init(FilterConfig filterConfig) throws ServletException {
      this.sc = filterConfig.getServletContext();
   }

   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      if (!this.helperChecked) {
         this.helper = GroovyHelper.getCurrentInstance(this.sc);
         this.helperChecked = true;
      }

      if (this.helper != null) {
         this.helper.setClassLoader();
      }

      filterChain.doFilter(servletRequest, servletResponse);
   }

   public void destroy() {
   }
}
