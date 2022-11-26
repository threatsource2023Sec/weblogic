package org.glassfish.grizzly.servlet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.DispatcherType;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import org.glassfish.grizzly.http.server.Request;

public class FilterChainFactory {
   private final WebappContext ctx;

   public FilterChainFactory(WebappContext ctx) {
      this.ctx = ctx;
   }

   public FilterChainImpl createFilterChain(ServletRequest request, Servlet servlet, DispatcherType dispatcherType) {
      return this.buildFilterChain(servlet, this.getRequestPath(request), dispatcherType);
   }

   public FilterChainImpl createFilterChain(Request request, Servlet servlet, DispatcherType dispatcherType) {
      return this.buildFilterChain(servlet, this.getRequestPath(request), dispatcherType);
   }

   private FilterChainImpl buildFilterChain(Servlet servlet, String requestPath, DispatcherType dispatcherType) {
      if (servlet == null) {
         return null;
      } else {
         FilterChainImpl filterChain = new FilterChainImpl(servlet, this.ctx);
         Map registrations = this.ctx.getFilterRegistrations();
         if (registrations.isEmpty()) {
            return filterChain;
         } else {
            List filterMaps = this.ctx.getFilterMaps();
            Iterator var7 = filterMaps.iterator();

            while(var7.hasNext()) {
               FilterMap filterMap = (FilterMap)var7.next();
               if (filterMap.getDispatcherTypes().contains(dispatcherType) && this.matchFiltersURL(filterMap, requestPath)) {
                  filterChain.addFilter((FilterRegistration)registrations.get(filterMap.getFilterName()));
               }
            }

            String servletName = servlet.getServletConfig().getServletName();
            Iterator var11 = filterMaps.iterator();

            while(var11.hasNext()) {
               FilterMap filterMap = (FilterMap)var11.next();
               if (filterMap.getDispatcherTypes().contains(dispatcherType) && this.matchFiltersServlet(filterMap, servletName)) {
                  filterChain.addFilter((FilterRegistration)registrations.get(filterMap.getFilterName()));
               }
            }

            return filterChain;
         }
      }
   }

   private String getRequestPath(ServletRequest request) {
      String requestPath = null;
      Object attribute = request.getAttribute("org.apache.catalina.core.DISPATCHER_REQUEST_PATH");
      if (attribute != null) {
         requestPath = attribute.toString();
      }

      return requestPath;
   }

   private String getRequestPath(Request request) {
      String requestPath = null;
      Object attribute = request.getAttribute("org.apache.catalina.core.DISPATCHER_REQUEST_PATH");
      if (attribute != null) {
         requestPath = attribute.toString();
      }

      return requestPath;
   }

   private boolean matchFiltersURL(FilterMap filterMap, String requestPath) {
      if (requestPath == null) {
         return false;
      } else {
         String testPath = filterMap.getURLPattern();
         if (testPath == null) {
            return false;
         } else if (testPath.equals(requestPath)) {
            return true;
         } else if (testPath.equals("/*")) {
            return true;
         } else if (testPath.endsWith("/*")) {
            if (testPath.regionMatches(0, requestPath, 0, testPath.length() - 2)) {
               if (requestPath.length() == testPath.length() - 2) {
                  return true;
               }

               if ('/' == requestPath.charAt(testPath.length() - 2)) {
                  return true;
               }
            }

            return false;
         } else {
            if (testPath.startsWith("*.")) {
               int slash = requestPath.lastIndexOf(47);
               int period = requestPath.lastIndexOf(46);
               if (slash >= 0 && period > slash && period != requestPath.length() - 1 && requestPath.length() - period == testPath.length() - 1) {
                  return testPath.regionMatches(2, requestPath, period + 1, testPath.length() - 2);
               }
            }

            return false;
         }
      }
   }

   private boolean matchFiltersServlet(FilterMap filterMap, String servletName) {
      if (servletName == null) {
         return false;
      } else {
         return servletName.equals(filterMap.getServletName()) || "*".equals(filterMap.getServletName());
      }
   }
}
