package weblogic.cacheprovider.coherence.application;

import weblogic.application.AppDeploymentExtension;
import weblogic.application.AppDeploymentExtensionFactory;
import weblogic.application.ApplicationContextInternal;

public class CoherenceApplicationDeploymentExtensionFactory implements AppDeploymentExtensionFactory {
   AppDeploymentExtension appExtn = new CoherenceAppDeploymentExtension();

   public AppDeploymentExtension createPreProcessorExtension(ApplicationContextInternal appCtx) {
      return this.appExtn;
   }

   public AppDeploymentExtension createPostProcessorExtension(ApplicationContextInternal appCtx) {
      return null;
   }
}
