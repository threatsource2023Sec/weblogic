package weblogic.servlet.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import weblogic.management.DeploymentException;
import weblogic.utils.classloaders.ChangeAwareClassLoader;

public class FilterWrapper {
   private static final String HEAD_FILTER = "weblogic.servlet.Filter.Head";
   private final WebAppServletContext context;
   private String filterName;
   private String filterClassName;
   private Map filterParams;
   private Filter filter;
   private ClassLoader contextLoader;
   private boolean reloadable;
   private boolean headFilter;
   private boolean filterInitializedByUser;
   private boolean asyncSupported;

   FilterWrapper(String filterName, String filterClassName, Map initArgs, WebAppServletContext context) {
      this(filterName, filterClassName, (Class)null, (Filter)null, true, initArgs, context);
   }

   FilterWrapper(String filterName, String filterClassName, boolean asyncSupported, Map filterParams, WebAppServletContext context) {
      this(filterName, filterClassName, (Class)null, (Filter)null, asyncSupported, filterParams, context);
   }

   FilterWrapper(String filterName, String filterClassName, Class filterClass, Filter filter, WebAppServletContext context) {
      this(filterName, filterClassName, filterClass, filter, true, (Map)null, context);
   }

   private FilterWrapper(String filterName, String filterClassName, Class filterClass, Filter filter, boolean asyncSupported, Map filterParams, WebAppServletContext ctx) {
      this.reloadable = false;
      this.headFilter = false;
      this.filterInitializedByUser = false;
      this.filterName = filterName;
      this.setFilter(filterClassName, filterClass, filter, filter != null);
      this.filterParams = filterParams;
      this.asyncSupported = asyncSupported;
      this.context = ctx;
      this.contextLoader = ctx.getServletClassLoader();
      this.reloadable = this.contextLoader instanceof ChangeAwareClassLoader;
      this.headFilter = filterParams != null && "true".equalsIgnoreCase((String)filterParams.get("weblogic.servlet.Filter.Head"));
   }

   String getFilterName() {
      return this.filterName;
   }

   String getFilterClassName() {
      return this.filterClassName;
   }

   Filter getFilter() {
      return this.filter;
   }

   void setFilter(String filterClassName, Class filterClass, Filter filter, boolean filterInitializedByUser) {
      if (filterClassName != null) {
         this.filterClassName = filterClassName;
      } else if (filterClass != null) {
         this.filterClassName = filterClass.getName();
      } else if (filter != null) {
         if (this.filter != null) {
            Thread thread = Thread.currentThread();
            ClassLoader currentContextClassLoader = thread.getContextClassLoader();

            try {
               thread.setContextClassLoader(this.contextLoader);
               this.context.getFilterManager().destroyFilter(this.filterName);
            } finally {
               thread.setContextClassLoader(currentContextClassLoader);
            }
         }

         this.filter = filter;
         this.filterClassName = filter.getClass().getName();
      }

      this.filterInitializedByUser = filterInitializedByUser;
   }

   Filter getFilter(boolean checkForReload) throws ServletException {
      try {
         if (checkForReload && this.reloadable) {
            this.checkForReload();
         }
      } catch (DeploymentException var3) {
         throw new ServletException(var3);
      }

      return this.filter;
   }

   boolean isContextClassLoaderChanged() {
      return this.context.getServletClassLoader() != this.contextLoader;
   }

   boolean getAsyncSupported() {
      return this.asyncSupported;
   }

   void setAsyncSupported(boolean asyncSupported) {
      this.asyncSupported = asyncSupported;
   }

   boolean isHeadFilter() {
      return this.headFilter;
   }

   boolean isConfigurable() {
      return this.filterClassName == null || this.isContextClassLoaderChanged();
   }

   Map getInitParameters() {
      return this.filterParams;
   }

   void setHeadFilter(boolean head) {
      this.headFilter = head;
   }

   void setInitParameter(String name, String value) {
      if (this.filterParams == null || this.filterParams == Collections.EMPTY_MAP) {
         this.filterParams = new HashMap();
      }

      this.filterParams.put(name, value);
   }

   private void reloadFilter(boolean reloadServletClassLoader) throws DeploymentException {
      Thread thread = Thread.currentThread();
      ClassLoader oldClassLoader = null;

      try {
         if (Thread.currentThread().getContextClassLoader() != this.contextLoader) {
            oldClassLoader = thread.getContextClassLoader();
            thread.setContextClassLoader(this.contextLoader);
         }

         if (!this.filterInitializedByUser) {
            this.context.getFilterManager().destroyFilter(this.filterName);
         }
      } finally {
         if (oldClassLoader != null) {
            thread.setContextClassLoader(oldClassLoader);
            oldClassLoader = null;
         }

      }

      if (reloadServletClassLoader) {
         this.context.reloadServletClassLoader();
      }

      this.contextLoader = this.context.getServletClassLoader();
      this.reloadable = this.contextLoader instanceof ChangeAwareClassLoader;
      if (!this.filterInitializedByUser) {
         this.filter = null;
      }

      try {
         oldClassLoader = this.context.pushEnvironment(thread);
         this.context.getFilterManager().loadFilter(this);
      } finally {
         WebAppServletContext.popEnvironment(thread, oldClassLoader);
      }

   }

   private void checkForReload() throws DeploymentException {
      if (this.isContextClassLoaderChanged()) {
         synchronized(this) {
            if (this.isContextClassLoaderChanged()) {
               this.reloadFilter(false);
               return;
            }
         }
      }

      long reloadInterval = (long)this.context.getConfigManager().getServletReloadCheckSecs();
      if (reloadInterval >= 0L && this.checkReloadTimeout(reloadInterval)) {
         synchronized(this) {
            if (this.isContextClassLoaderChanged()) {
               this.reloadFilter(false);
               return;
            }

            if (this.checkReloadTimeout(reloadInterval) && this.needToReload()) {
               this.reloadFilter(true);
            }
         }
      }

   }

   private boolean checkReloadTimeout(long reloadInterval) {
      ChangeAwareClassLoader cacl = (ChangeAwareClassLoader)this.context.getServletClassLoader();
      long currentTime = System.currentTimeMillis();
      return currentTime - reloadInterval * 1000L > cacl.getLastChecked();
   }

   private boolean needToReload() {
      ChangeAwareClassLoader cacl = (ChangeAwareClassLoader)this.context.getServletClassLoader();
      return !cacl.upToDate();
   }
}
