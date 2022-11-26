package com.sun.faces.application.resource;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MultiKeyConcurrentHashMap;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

public class ResourceCache {
   private static final Logger LOGGER;
   private MultiKeyConcurrentHashMap resourceCache;
   private long checkPeriod;

   public ResourceCache() {
      this(WebConfiguration.getInstance());
   }

   private ResourceCache(WebConfiguration config) {
      this(getCheckPeriod(config));
      if (LOGGER.isLoggable(Level.FINE)) {
         ServletContext sc = config.getServletContext();
         LOGGER.log(Level.FINE, "ResourceCache constructed for {0}.  Check period is {1} minutes.", new Object[]{getServletContextIdentifier(sc), this.checkPeriod});
      }

   }

   ResourceCache(long period) {
      this.checkPeriod = period != -1L ? period * 1000L * 60L : -1L;
      this.resourceCache = new MultiKeyConcurrentHashMap(30);
   }

   public ResourceInfo add(ResourceInfo info, List contracts) {
      Util.notNull("info", info);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "Caching ResourceInfo: {0}", info.toString());
      }

      ResourceInfoCheckPeriodProxy proxy = (ResourceInfoCheckPeriodProxy)this.resourceCache.putIfAbsent(info.name, info.libraryName, info.localePrefix, new ArrayList(contracts), new ResourceInfoCheckPeriodProxy(info, this.checkPeriod));
      return proxy != null ? proxy.getResourceInfo() : null;
   }

   public ResourceInfo get(String name, String libraryName, String localePrefix, List contracts) {
      Util.notNull("name", name);
      ResourceInfoCheckPeriodProxy proxy = (ResourceInfoCheckPeriodProxy)this.resourceCache.get(name, libraryName, localePrefix, contracts);
      if (proxy != null && proxy.needsRefreshed()) {
         this.resourceCache.remove(name, libraryName, localePrefix, contracts);
         return null;
      } else {
         return proxy != null ? proxy.getResourceInfo() : null;
      }
   }

   public void clear() {
      this.resourceCache.clear();
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "Cache Cleared");
      }

   }

   private static Long getCheckPeriod(WebConfiguration webConfig) {
      String val = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.ResourceUpdateCheckPeriod);

      try {
         return Long.parseLong(val);
      } catch (NumberFormatException var3) {
         return Long.parseLong(WebConfiguration.WebContextInitParameter.ResourceUpdateCheckPeriod.getDefaultValue());
      }
   }

   private static String getServletContextIdentifier(ServletContext context) {
      return context.getMajorVersion() == 2 && context.getMinorVersion() < 5 ? context.getServletContextName() : context.getContextPath();
   }

   static {
      LOGGER = FacesLogger.RESOURCE.getLogger();
   }

   private static final class ResourceInfoCheckPeriodProxy {
      private ResourceInfo resourceInfo;
      private Long checkTime;

      public ResourceInfoCheckPeriodProxy(ResourceInfo resourceInfo, long checkPeriod) {
         this.resourceInfo = resourceInfo;
         if (checkPeriod != -1L && !(resourceInfo.getHelper() instanceof ClasspathResourceHelper)) {
            this.checkTime = System.currentTimeMillis() + checkPeriod;
         }

      }

      private boolean needsRefreshed() {
         return this.checkTime != null && this.checkTime < System.currentTimeMillis();
      }

      private ResourceInfo getResourceInfo() {
         return this.resourceInfo;
      }
   }
}
