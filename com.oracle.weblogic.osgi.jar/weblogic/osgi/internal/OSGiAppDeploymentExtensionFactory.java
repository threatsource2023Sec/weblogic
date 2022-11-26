package weblogic.osgi.internal;

import weblogic.application.AppDeploymentExtension;
import weblogic.application.AppDeploymentExtensionFactory;
import weblogic.application.ApplicationContextInternal;

public class OSGiAppDeploymentExtensionFactory implements AppDeploymentExtensionFactory {
   public AppDeploymentExtension createPreProcessorExtension(ApplicationContextInternal appCtx) {
      return new OSGiAppDeploymentExtension();
   }

   public AppDeploymentExtension createPostProcessorExtension(ApplicationContextInternal appCtx) {
      return null;
   }

   public String toString() {
      return "OSGiAppDeploymentExtensionFactory( " + System.identityHashCode(this) + ")";
   }
}
