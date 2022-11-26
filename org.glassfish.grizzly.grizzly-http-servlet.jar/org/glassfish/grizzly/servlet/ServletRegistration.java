package org.glassfish.grizzly.servlet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletSecurityElement;
import org.glassfish.grizzly.utils.ArraySet;

public class ServletRegistration extends Registration implements javax.servlet.ServletRegistration.Dynamic, Comparable {
   protected Class servletClass;
   protected final ArraySet urlPatterns;
   protected Servlet servlet;
   protected int loadOnStartup;
   protected ExpectationHandler expectationHandler;
   protected boolean isAsyncSupported;
   private String runAs;

   protected ServletRegistration(WebappContext ctx, String name, String servletClassName) {
      super(ctx, name, servletClassName);
      this.urlPatterns = new ArraySet(String.class);
      this.loadOnStartup = -1;
      this.runAs = null;
      this.name = name;
   }

   protected ServletRegistration(WebappContext ctx, String name, Servlet servlet) {
      this(ctx, name, servlet.getClass());
      this.servlet = servlet;
   }

   protected ServletRegistration(WebappContext ctx, String name, Class servletClass) {
      this(ctx, name, servletClass.getName());
      this.servletClass = servletClass;
   }

   public Set addMapping(String... urlPatterns) {
      if (this.ctx.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else if (urlPatterns != null && urlPatterns.length != 0) {
         this.urlPatterns.addAll(urlPatterns);
         return Collections.emptySet();
      } else {
         throw new IllegalArgumentException("'urlPatterns' cannot be null or zero-length");
      }
   }

   public Collection getMappings() {
      return Collections.unmodifiableList(Arrays.asList(this.urlPatterns.getArrayCopy()));
   }

   public void setLoadOnStartup(int loadOnStartup) {
      if (this.ctx.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else {
         if (loadOnStartup < 0) {
            this.loadOnStartup = -1;
         } else {
            this.loadOnStartup = loadOnStartup;
         }

      }
   }

   public ExpectationHandler getExpectationHandler() {
      return this.expectationHandler;
   }

   public Set setServletSecurity(ServletSecurityElement constraint) {
      return Collections.emptySet();
   }

   public void setMultipartConfig(MultipartConfigElement multipartConfig) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public String getRunAsRole() {
      return this.runAs;
   }

   public void setRunAsRole(String roleName) {
      this.runAs = roleName;
   }

   public void setAsyncSupported(boolean isAsyncSupported) {
      this.isAsyncSupported = isAsyncSupported;
   }

   public void setExpectationHandler(ExpectationHandler expectationHandler) {
      this.expectationHandler = expectationHandler;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ServletRegistration");
      sb.append("{ servletName=").append(this.name);
      sb.append(", servletClass=").append(this.className);
      sb.append(", urlPatterns=").append(Arrays.toString(this.urlPatterns.getArray()));
      sb.append(", loadOnStartup=").append(this.loadOnStartup);
      sb.append(", isAsyncSupported=").append(this.isAsyncSupported);
      sb.append(" }");
      return sb.toString();
   }

   public int compareTo(ServletRegistration o) {
      if (this.loadOnStartup == o.loadOnStartup) {
         return 0;
      } else if (this.loadOnStartup < 0 && o.loadOnStartup < 0) {
         return -1;
      } else {
         return this.loadOnStartup >= 0 && o.loadOnStartup >= 0 && this.loadOnStartup < o.loadOnStartup ? -1 : 1;
      }
   }
}
