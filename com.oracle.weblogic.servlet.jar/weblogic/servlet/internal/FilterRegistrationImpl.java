package weblogic.servlet.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterRegistration;
import weblogic.management.DeploymentException;

public class FilterRegistrationImpl implements FilterRegistration.Dynamic {
   private WebAppServletContext context;
   private FilterManager filterManager;
   private FilterWrapper filterWrapper;

   FilterRegistrationImpl(WebAppServletContext context, FilterWrapper filterWrapper) {
      this.context = context;
      this.filterWrapper = filterWrapper;
      this.filterManager = context.getFilterManager();
   }

   public String getClassName() {
      return this.filterWrapper.getFilterClassName();
   }

   public String getInitParameter(String name) {
      return (String)this.filterWrapper.getInitParameters().get(name);
   }

   public Map getInitParameters() {
      Map initParameters = this.filterWrapper.getInitParameters();
      return initParameters != null && !initParameters.isEmpty() ? Collections.unmodifiableMap(initParameters) : Collections.emptyMap();
   }

   public String getName() {
      return this.filterWrapper.getFilterName();
   }

   public boolean setInitParameter(String name, String value) {
      this.checkContextStatus();
      if (name != null && value != null) {
         if (this.disallowConfigure()) {
            return true;
         } else if (this.getInitParameters().containsKey(name)) {
            return false;
         } else {
            this.filterWrapper.setInitParameter(name, value);
            return true;
         }
      } else {
         throw new IllegalArgumentException("name or value can't be null");
      }
   }

   public Set setInitParameters(Map initParameters) {
      this.checkContextStatus();
      if (initParameters == null) {
         throw new IllegalArgumentException("init parameters can't be null");
      } else if (this.disallowConfigure()) {
         return Collections.emptySet();
      } else {
         Set conflicts = new HashSet();
         Iterator var3 = initParameters.keySet().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            boolean success = this.setInitParameter(key, (String)initParameters.get(key));
            if (!success) {
               conflicts.add(key);
            }
         }

         return conflicts;
      }
   }

   public void addMappingForServletNames(EnumSet dispatcherTypes, boolean isMatchAfter, String... servletNames) {
      this.checkContextStatus();
      if (!this.disallowConfigure()) {
         if (servletNames != null && servletNames.length != 0) {
            String[] var4 = servletNames;
            int var5 = servletNames.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String servletName = var4[var6];
               if (servletName == null) {
                  throw new IllegalArgumentException("servlet name can't be null");
               }
            }

            this.filterManager.addMappingForServletNames(this.filterWrapper.getFilterName(), dispatcherTypes, isMatchAfter, servletNames);
         } else {
            throw new IllegalArgumentException("servlet names can't be null or empty");
         }
      }
   }

   public void addMappingForUrlPatterns(EnumSet dispatcherTypes, boolean isMatchAfter, String... urlPatterns) {
      this.checkContextStatus();
      if (!this.disallowConfigure()) {
         if (urlPatterns != null && urlPatterns.length != 0) {
            String[] var4 = urlPatterns;
            int var5 = urlPatterns.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String urlPattern = var4[var6];
               if (urlPattern == null) {
                  throw new IllegalArgumentException("urlPattern can't be null");
               }
            }

            try {
               this.filterManager.addMappingForUrlPatterns(this.filterWrapper.getFilterName(), dispatcherTypes, isMatchAfter, urlPatterns);
            } catch (DeploymentException var8) {
               throw new IllegalArgumentException(var8.getMessage());
            }
         } else {
            throw new IllegalArgumentException("urlPatterns can't be null");
         }
      }
   }

   public Collection getServletNameMappings() {
      return this.filterManager.getServletNameMappings(this.getName());
   }

   public Collection getUrlPatternMappings() {
      return this.filterManager.getUrlPatternMappings(this.getName());
   }

   public void setAsyncSupported(boolean isAsyncSupported) {
      this.checkContextStatus();
      if (!this.disallowConfigure()) {
         this.filterWrapper.setAsyncSupported(isAsyncSupported);
      }
   }

   protected void checkContextStatus() {
      if (this.context.isStarted()) {
         throw new IllegalStateException("ServletContext has already been initialized.");
      }
   }

   private boolean disallowConfigure() {
      return this.filterWrapper.isContextClassLoaderChanged();
   }
}
