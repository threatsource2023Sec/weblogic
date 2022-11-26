package weblogic.diagnostics.module;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.WLDFSystemResourceMBean;

public class WLDFDeployment extends SingleModuleDeployment implements Deployment {
   private static final WLDFModuleFactory moduleFactory = WLDFModuleFactory.getInstance();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsModule");
   private WLDFSystemResourceMBean mbean;

   WLDFDeployment(WLDFSystemResourceMBean resourceMBean, File f) throws DeploymentException {
      super(resourceMBean, createModule(resourceMBean), f);
      this.mbean = resourceMBean;
   }

   private static WLDFModule createModule(WLDFSystemResourceMBean sbean) throws ModuleException {
      try {
         boolean removedModule = destroyModule(sbean);
         if (removedModule && debugLogger.isDebugEnabled()) {
            debugLogger.debug("WLDFDeployment: Successfully removed existing module for " + sbean.getName() + ", likely present due to resource activation through RuntimeControl");
         }
      } catch (Exception var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("WLDFDeployment: Caught exception destroying existing module", var2);
         }
      }

      return moduleFactory.createModule(sbean);
   }

   public void unprepare(DeploymentContext deploymentContext) throws DeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("WLDFDeployment: unpreparing module for WLDFSystemResource " + this.mbean.getName());
      }

      super.unprepare(deploymentContext);
      WLDFModule removedModule = moduleFactory.removeModule(this.mbean);
      if (removedModule != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug("WLDFDeployment.unprepare(): Successfully removed existing module for " + this.mbean.getName());
      }

   }

   private static boolean destroyModule(WLDFSystemResourceMBean sbean) throws ModuleException {
      WLDFModule removedModule = moduleFactory.removeModule(sbean);
      if (removedModule != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("WLDFModuleFactory: Found module for " + sbean.getName() + ", destroying");
         }

         removedModule.deactivate();
         removedModule.unprepare();
         removedModule.destroy((UpdateListener.Registration)null);
      }

      return removedModule != null;
   }
}
