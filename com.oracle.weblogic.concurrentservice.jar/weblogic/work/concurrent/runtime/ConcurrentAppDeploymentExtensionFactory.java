package weblogic.work.concurrent.runtime;

import weblogic.application.AppDeploymentExtension;
import weblogic.application.AppDeploymentExtensionFactory;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.BaseAppDeploymentExtension;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;

public class ConcurrentAppDeploymentExtensionFactory implements AppDeploymentExtensionFactory {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");

   public AppDeploymentExtension createPreProcessorExtension(ApplicationContextInternal appCtx) {
      return new ConcurrentAppDeploymentPreExtension();
   }

   public AppDeploymentExtension createPostProcessorExtension(ApplicationContextInternal appCtx) {
      return new ConcurrentAppDeploymentPostExtension();
   }

   static class ConcurrentAppDeploymentPostExtension extends BaseAppDeploymentExtension {
      public String getName() {
         return this.getClass().getName();
      }

      public void activate(ApplicationContextInternal appCtx) throws DeploymentException {
         if (ConcurrentAppDeploymentExtensionFactory.debugLogger.isDebugEnabled()) {
            ConcurrentAppDeploymentExtensionFactory.debugLogger.debug("-- ConcurrentAppDeploymentPostExtension -- calling activate on - " + appCtx.getApplicationId());
         }

         ConcurrentManagedObjectCollection collection = appCtx.getConcurrentManagedObjectCollection();
         AppDeploymentMBean deploymentMBean = appCtx.getAppDeploymentMBean();
         String moduleType = deploymentMBean == null ? null : deploymentMBean.getModuleType();
         collection.activate(appCtx.getEnvContext(), appCtx.getAppClassLoader(), appCtx.getRootContext(), moduleType);
      }
   }

   static class ConcurrentAppDeploymentPreExtension extends BaseAppDeploymentExtension {
      public String getName() {
         return this.getClass().getName();
      }

      public void prepare(ApplicationContextInternal appCtx) throws DeploymentException {
         if (ConcurrentAppDeploymentExtensionFactory.debugLogger.isDebugEnabled()) {
            ConcurrentAppDeploymentExtensionFactory.debugLogger.debug("-- ConcurrentAppDeploymentPreExtension -- calling prepare on - " + appCtx.getApplicationId());
         }

         ConcurrentManagedObjectCollection collection = appCtx.getConcurrentManagedObjectCollection();
         collection.setApplicationRuntime(appCtx.getRuntime());
         collection.initialize(appCtx.getWLApplicationDD(), appCtx.getWorkManagerCollection());
      }

      public void unprepare(ApplicationContextInternal appCtx) throws DeploymentException {
         if (ConcurrentAppDeploymentExtensionFactory.debugLogger.isDebugEnabled()) {
            ConcurrentAppDeploymentExtensionFactory.debugLogger.debug("-- ConcurrentAppDeploymentPreExtension -- calling unprepare on - " + appCtx.getApplicationId());
         }

         ConcurrentManagedObjectCollection collection = appCtx.getConcurrentManagedObjectCollection();
         collection.terminate();
      }

      public void deactivate(ApplicationContextInternal appCtx) throws DeploymentException {
         if (ConcurrentAppDeploymentExtensionFactory.debugLogger.isDebugEnabled()) {
            ConcurrentAppDeploymentExtensionFactory.debugLogger.debug("-- ConcurrentAppDeploymentPreExtension -- calling deactivate on - " + appCtx.getApplicationId());
         }

         ConcurrentManagedObjectCollection collection = appCtx.getConcurrentManagedObjectCollection();
         collection.deactive(appCtx.getEnvContext());
      }
   }
}
