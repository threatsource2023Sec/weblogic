package org.glassfish.grizzly.servlet;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.glassfish.grizzly.http.server.util.Enumerator;

public class ServletConfigImpl implements ServletConfig {
   protected String name;
   protected final ConcurrentMap initParameters = new ConcurrentHashMap(16, 0.75F, 64);
   protected final WebappContext servletContextImpl;

   protected ServletConfigImpl(WebappContext servletContextImpl) {
      this.servletContextImpl = servletContextImpl;
   }

   public String getServletName() {
      return this.name;
   }

   public ServletContext getServletContext() {
      return this.servletContextImpl;
   }

   public String getInitParameter(String name) {
      return (String)this.initParameters.get(name);
   }

   protected void setInitParameters(Map parameters) {
      if (parameters != null && !parameters.isEmpty()) {
         this.initParameters.clear();
         this.initParameters.putAll(parameters);
      }

   }

   public void setServletName(String name) {
      this.name = name;
   }

   public Enumeration getInitParameterNames() {
      return new Enumerator(this.initParameters.keySet());
   }
}
