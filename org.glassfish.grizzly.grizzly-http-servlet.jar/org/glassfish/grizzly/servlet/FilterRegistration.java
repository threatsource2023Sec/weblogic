package org.glassfish.grizzly.servlet;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import javax.servlet.Filter;

public class FilterRegistration extends Registration implements javax.servlet.FilterRegistration.Dynamic {
   protected Class filterClass;
   protected Filter filter;
   protected boolean isAsyncSupported;

   protected FilterRegistration(WebappContext ctx, String name, String filterClassName) {
      super(ctx, name, filterClassName);
      this.initParameters = new HashMap(4, 1.0F);
   }

   protected FilterRegistration(WebappContext ctx, String name, Class filter) {
      this(ctx, name, filter.getName());
      this.filterClass = filter;
   }

   protected FilterRegistration(WebappContext ctx, String name, Filter filter) {
      this(ctx, name, filter.getClass());
      this.filter = filter;
   }

   public void addMappingForServletNames(EnumSet dispatcherTypes, String... servletNames) {
      this.addMappingForServletNames(dispatcherTypes, true, servletNames);
   }

   public void addMappingForServletNames(EnumSet dispatcherTypes, boolean isMatchAfter, String... servletNames) {
      if (this.ctx.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else if (servletNames != null && servletNames.length != 0) {
         String[] var4 = servletNames;
         int var5 = servletNames.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String servletName = var4[var6];
            FilterMap fmap = new FilterMap();
            fmap.setFilterName(this.getName());
            fmap.setServletName(servletName);
            fmap.setDispatcherTypes(dispatcherTypes);
            this.ctx.addFilterMap(fmap, isMatchAfter);
         }

      } else {
         throw new IllegalArgumentException("'servletNames' is null or zero-length");
      }
   }

   public Collection getServletNameMappings() {
      return this.ctx.getServletNameFilterMappings(this.getName());
   }

   public void addMappingForUrlPatterns(EnumSet dispatcherTypes, String... urlPatterns) {
      this.addMappingForUrlPatterns(dispatcherTypes, true, urlPatterns);
   }

   public void addMappingForUrlPatterns(EnumSet dispatcherTypes, boolean isMatchAfter, String... urlPatterns) {
      if (this.ctx.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else if (urlPatterns != null && urlPatterns.length != 0) {
         String[] var4 = urlPatterns;
         int var5 = urlPatterns.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String urlPattern = var4[var6];
            FilterMap fmap = new FilterMap();
            fmap.setFilterName(this.getName());
            fmap.setURLPattern(urlPattern);
            fmap.setDispatcherTypes(dispatcherTypes);
            this.ctx.addFilterMap(fmap, isMatchAfter);
         }

      } else {
         throw new IllegalArgumentException("'urlPatterns' is null or zero-length");
      }
   }

   public Collection getUrlPatternMappings() {
      return this.ctx.getUrlPatternFilterMappings(this.getName());
   }

   public void setAsyncSupported(boolean isAsyncSupported) {
      this.isAsyncSupported = isAsyncSupported;
   }
}
