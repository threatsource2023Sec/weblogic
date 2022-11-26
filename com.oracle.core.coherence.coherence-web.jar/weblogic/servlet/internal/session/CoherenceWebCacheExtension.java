package weblogic.servlet.internal.session;

import java.util.Arrays;
import java.util.List;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.cacheprovider.coherence.CoherenceClusterContainer;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;
import weblogic.j2ee.descriptor.wl.CoherenceClusterRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.WebAppComponentRuntimeMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.CacheExtension;

class CoherenceWebCacheExtension implements CoherenceClusterContainer, CacheExtension {
   private ClassLoader classLoader;
   private WeblogicWebAppBean wlWebAppBean;
   private boolean registered = false;
   private Module module;

   CoherenceWebCacheExtension(Module module, ClassLoader classLoader, WeblogicWebAppBean wlWebAppBean) {
      this.module = module;
      this.classLoader = classLoader;
      this.wlWebAppBean = wlWebAppBean;
   }

   private ClassLoader getClassLoader() {
      return this.classLoader;
   }

   private ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return this.module.getComponentRuntimeMBeans();
   }

   private WeblogicWebAppBean getWlWebAppBean() {
      return this.wlWebAppBean;
   }

   public void setupCaches() throws ModuleException {
      if (!this.registered) {
         try {
            ClassLoader loader = this.getClassLoader();
            CoherenceClusterManager mgr = CoherenceClusterManager.getInstance();
            this.registered = mgr.startUp(loader, this);
         } catch (ModuleException var3) {
            throw var3;
         } catch (Exception var4) {
            throw new ModuleException(var4.getMessage(), var4);
         }
      }
   }

   public void releaseCaches() {
      if (this.registered) {
         try {
            ClassLoader loader = this.getClassLoader();
            CoherenceClusterManager mgr = CoherenceClusterManager.getInstance();
            if (mgr.tearDown(loader, this)) {
               this.registered = false;
            }
         } catch (Exception var3) {
            HTTPLogger.logError(this.toString(), "Failed to cleanly shutdown Coherence: " + var3);
         }

      }
   }

   public CoherenceClusterRefBean getCoherenceClusterRefBean() {
      WeblogicWebAppBean wlWebAppBean = this.getWlWebAppBean();
      return wlWebAppBean != null ? wlWebAppBean.getCoherenceClusterRef() : null;
   }

   public void registerRuntimeMBeans(CoherenceClusterManager mgr, ClassLoader loader) throws ManagementException {
      WebAppComponentRuntimeMBean[] mbeans = this.getRuntimeMBeans();
      mgr.registerWebAppComponentRuntimeMBean(loader, mbeans);
   }

   public void unRegisterRuntimeMBeans(CoherenceClusterManager mgr, ClassLoader loader) throws ManagementException {
      WebAppComponentRuntimeMBean[] mbeans = this.getRuntimeMBeans();
      mgr.unRegisterWebAppComponentRuntimeMBean(loader, mbeans);
   }

   private WebAppComponentRuntimeMBean[] getRuntimeMBeans() {
      ComponentRuntimeMBean[] compMbeans = this.getComponentRuntimeMBeans();
      List l = Arrays.asList(compMbeans);
      WebAppComponentRuntimeMBean[] mbeans = new WebAppComponentRuntimeMBean[l.size()];
      l.toArray(mbeans);
      return mbeans;
   }
}
