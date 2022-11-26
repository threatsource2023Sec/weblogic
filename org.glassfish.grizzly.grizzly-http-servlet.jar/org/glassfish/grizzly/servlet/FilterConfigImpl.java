package org.glassfish.grizzly.servlet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import org.glassfish.grizzly.http.server.util.Enumerator;

public class FilterConfigImpl implements FilterConfig {
   private WebappContext servletContext = null;
   private Filter filter = null;
   private Map initParameters = null;
   private String filterName;

   public FilterConfigImpl(WebappContext servletContext) {
      this.servletContext = servletContext;
   }

   public String getInitParameter(String name) {
      return this.initParameters == null ? null : (String)this.initParameters.get(name);
   }

   public String getFilterName() {
      return this.filterName;
   }

   public Enumeration getInitParameterNames() {
      Map map = this.initParameters;
      return map == null ? new Enumerator(new ArrayList()) : new Enumerator(map.keySet());
   }

   public ServletContext getServletContext() {
      return this.servletContext;
   }

   public Filter getFilter() {
      return this.filter;
   }

   protected void recycle() {
      if (this.filter != null) {
         this.filter.destroy();
      }

      this.filter = null;
   }

   protected void setFilter(Filter filter) {
      this.filter = filter;
   }

   protected void setFilterName(String filterName) {
      this.filterName = filterName;
   }

   protected void setInitParameters(Map initParameters) {
      if (initParameters != null && !initParameters.isEmpty()) {
         this.initParameters = initParameters;
      }

   }
}
