package org.glassfish.grizzly.servlet;

import java.io.IOException;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.localization.LogMessages;

final class FilterChainImpl implements FilterChain, FilterChainInvoker {
   private static final Logger LOGGER = Grizzly.logger(FilterChainImpl.class);
   private final Servlet servlet;
   private final WebappContext ctx;
   private final Object lock = new Object();
   private int n;
   private FilterRegistration[] filters = new FilterRegistration[0];
   private int pos;

   public FilterChainImpl(Servlet servlet, WebappContext ctx) {
      this.servlet = servlet;
      this.ctx = ctx;
   }

   public void invokeFilterChain(ServletRequest request, ServletResponse response) throws IOException, ServletException {
      ServletRequestEvent event = new ServletRequestEvent(this.ctx, request);

      try {
         this.requestInitialized(event);
         this.pos = 0;
         this.doFilter(request, response);
      } finally {
         this.requestDestroyed(event);
      }

   }

   public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
      if (this.pos < this.n) {
         FilterRegistration registration = this.filters[this.pos++];

         try {
            Filter filter = registration.filter;
            filter.doFilter(request, response, this);
         } catch (Exception var5) {
            throw new ServletException(var5);
         }
      } else {
         try {
            if (this.servlet != null) {
               this.servlet.service(request, response);
            }

         } catch (Exception var6) {
            throw new ServletException(var6);
         }
      }
   }

   protected void addFilter(FilterRegistration filterRegistration) {
      synchronized(this.lock) {
         if (this.n == this.filters.length) {
            FilterRegistration[] newFilters = new FilterRegistration[this.n + 4];
            System.arraycopy(this.filters, 0, newFilters, 0, this.n);
            this.filters = newFilters;
         }

         this.filters[this.n++] = filterRegistration;
      }
   }

   private void requestDestroyed(ServletRequestEvent event) {
      EventListener[] listeners = this.ctx.getEventListeners();
      int i = 0;

      for(int len = listeners.length; i < len; ++i) {
         if (listeners[i] instanceof ServletRequestListener) {
            try {
               ((ServletRequestListener)listeners[i]).requestDestroyed(event);
            } catch (Throwable var6) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_DESTROYED_ERROR("requestDestroyed", "ServletRequestListener", listeners[i].getClass().getName()), var6);
               }
            }
         }
      }

   }

   private void requestInitialized(ServletRequestEvent event) {
      EventListener[] listeners = this.ctx.getEventListeners();
      int i = 0;

      for(int len = listeners.length; i < len; ++i) {
         if (listeners[i] instanceof ServletRequestListener) {
            try {
               ((ServletRequestListener)listeners[i]).requestInitialized(event);
            } catch (Throwable var6) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_INITIALIZED_ERROR("requestDestroyed", "ServletRequestListener", listeners[i].getClass().getName()), var6);
               }
            }
         }
      }

   }
}
