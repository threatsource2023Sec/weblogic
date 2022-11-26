package weblogic.servlet.internal;

import com.oracle.injection.spi.WebModuleIntegrationService;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import javax.servlet.ServletContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.naming.Environment;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.FilterMappingBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;

public class WebAppModuleExtensionContext extends WebBaseModuleExtensionContext implements WebModuleIntegrationService {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger(WebAppModuleExtensionContext.class.getName());
   private WebAppModule webappModule;
   private War war;
   private Object beanManager = null;
   private boolean webAppDestroyed = false;

   private WebAppModuleExtensionContext(ApplicationContextInternal appCtx, ModuleContext modCtx, WebAppModule module) {
      super(appCtx, modCtx, module, (Environment)null);
      this.webappModule = module;
      this.war = this.webappModule.getWarInstance();
   }

   public static WebAppModuleExtensionContext getInstance(WebAppModule module, ModuleContext modCtx) {
      return new WebAppModuleExtensionContext(module.getApplicationContext(), modCtx, module);
   }

   public Environment getEnvironment(String componentName) {
      WebAppServletContext servletContext = (WebAppServletContext)this.getServletContext();
      return servletContext.getEnvironmentBuilder();
   }

   public Collection getEnvironments() {
      return Collections.singleton(this.getEnvironment((String)null));
   }

   protected ClassFinder getResourceFinder(String relativeURI) {
      return this.war.getResourceFinder(relativeURI);
   }

   protected ClassLoader getClassLoader() {
      return this.webappModule.getClassLoader();
   }

   public WebAppHelper getWebAppHelper() {
      return this.war;
   }

   public ServletContext getServletContext() {
      Collection contexts = this.webappModule.getAllContexts();
      if (contexts.isEmpty()) {
         debugLogger.debug("cannot find WebAppServletContext instnace from WebAppModule");
         return null;
      } else {
         return ((WebAppServletContext[])contexts.toArray(new WebAppServletContext[0]))[0];
      }
   }

   public void registerServletListener(EventListener eventListener) {
      WebAppServletContext servletContext = (WebAppServletContext)this.getServletContext();
      if (servletContext != null) {
         servletContext.getEventsManager().addEventListener(eventListener);
      }

   }

   public String[] getFilterMappingNames() {
      WebAppBean webAppBean = this.webappModule.getWebAppBean();
      if (webAppBean == null) {
         return new String[0];
      } else {
         FilterMappingBean[] filterMappingBeans = webAppBean.getFilterMappings();
         String[] filterMappingNames = new String[filterMappingBeans.length];
         int i = 0;
         FilterMappingBean[] var5 = filterMappingBeans;
         int var6 = filterMappingBeans.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            FilterMappingBean oneFilterMappingBean = var5[var7];
            filterMappingNames[i++] = oneFilterMappingBean.getFilterName();
         }

         return filterMappingNames;
      }
   }

   public GenericClassLoader getTemporaryClassLoader() {
      GenericClassLoader tempCL = super.getTemporaryClassLoader();
      if (tempCL == null) {
         tempCL = this.webappModule.getTemporaryClassLoader();
      }

      return tempCL;
   }

   public void setBeanManager(Object beanManager) {
      this.beanManager = beanManager;
   }

   public Object getBeanManager() {
      return this.beanManager;
   }

   public void setWebAppDestroyed(boolean webAppDestroyed) {
      this.webAppDestroyed = webAppDestroyed;
   }

   public boolean isWebAppDestroyed() {
      return this.webAppDestroyed;
   }
}
