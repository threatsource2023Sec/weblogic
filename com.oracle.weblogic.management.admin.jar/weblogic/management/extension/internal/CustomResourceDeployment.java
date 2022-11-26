package weblogic.management.extension.internal;

import java.io.File;
import weblogic.application.DeploymentContext;
import weblogic.application.Module;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.CustomResourceMBean;

public class CustomResourceDeployment extends SingleModuleDeployment {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   String resourceName;

   public CustomResourceDeployment(CustomResourceMBean mbean, File f) throws DeploymentException {
      super(mbean, createModule(mbean), f);
      this.resourceName = mbean.getName();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created custom resource for file " + f);
      }

   }

   private static Module createModule(CustomResourceMBean mbean) {
      return new CustomResourceModule(mbean.getName(), mbean.getResource(), mbean.getResourceClass());
   }

   public void prepareUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      super.prepareUpdate(deploymentContext);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Prepare update for resource " + this.resourceName);
      }

   }

   public void activateUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      super.activateUpdate(deploymentContext);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Activate update for resource " + this.resourceName);
      }

   }

   public void rollbackUpdate(DeploymentContext deploymentContext) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Rollback update for resource " + this.resourceName);
      }

   }
}
