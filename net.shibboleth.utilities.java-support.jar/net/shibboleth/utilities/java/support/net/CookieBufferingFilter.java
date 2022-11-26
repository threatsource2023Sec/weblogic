package net.shibboleth.utilities.java.support.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;

public class CookieBufferingFilter implements Filter {
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
         chain.doFilter(request, new CookieBufferingHttpServletResponseProxy((HttpServletResponse)response));
      }
   }

   private class CookieBufferingHttpServletResponseProxy extends HttpServletResponseWrapper {
      @Nonnull
      @NonnullElements
      private Map cookieMap = new HashMap();

      public CookieBufferingHttpServletResponseProxy(@Nonnull HttpServletResponse response) {
         super(response);
      }

      public void addCookie(Cookie cookie) {
         this.cookieMap.put(cookie.getName(), cookie);
      }

      @Nonnull
      @NonnullElements
      @Live
      protected Map getCookies() {
         return this.cookieMap;
      }

      public ServletOutputStream getOutputStream() throws IOException {
         this.dumpCookies();
         return super.getOutputStream();
      }

      public PrintWriter getWriter() throws IOException {
         this.dumpCookies();
         return super.getWriter();
      }

      public void sendError(int sc, String msg) throws IOException {
         this.dumpCookies();
         super.sendError(sc, msg);
      }

      public void sendError(int sc) throws IOException {
         this.dumpCookies();
         super.sendError(sc);
      }

      public void sendRedirect(String location) throws IOException {
         this.dumpCookies();
         super.sendRedirect(location);
      }

      protected void dumpCookies() {
         Iterator i$ = this.cookieMap.values().iterator();

         while(i$.hasNext()) {
            Cookie cookie = (Cookie)i$.next();
            ((HttpServletResponse)this.getResponse()).addCookie(cookie);
         }

         this.cookieMap.clear();
      }
   }
}
