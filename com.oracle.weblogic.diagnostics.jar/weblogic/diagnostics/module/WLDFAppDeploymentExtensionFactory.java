package weblogic.diagnostics.module;

import weblogic.application.AppDeploymentExtension;
import weblogic.application.AppDeploymentExtensionFactory;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.utils.BaseAppDeploymentExtension;
import weblogic.management.DeploymentException;

public final class WLDFAppDeploymentExtensionFactory implements AppDeploymentExtensionFactory {
   private static final String DIAGNOSTICS_DD = "META-INF/weblogic-diagnostics.xml";

   public AppDeploymentExtension createPostProcessorExtension(ApplicationContextInternal appCtx) {
      return null;
   }

   public AppDeploymentExtension createPreProcessorExtension(ApplicationContextInternal appCtx) {
      return new WLDFAppDeploymentExtension();
   }

   public static class WLDFAppDeploymentExtension extends BaseAppDeploymentExtension {
      public void init(ApplicationContextInternal appCtx) throws DeploymentException {
         if (!appCtx.getStagingPath().endsWith(".xml")) {
            Module m = new WLDFModule("META-INF/weblogic-diagnostics.xml");
            Module[] oldMods = appCtx.getApplicationModules();
            if (oldMods == null) {
               oldMods = new Module[0];
            }

            Module[] newMods = new Module[oldMods.length + 1];
            newMods[0] = m;
            System.arraycopy(oldMods, 0, newMods, 1, oldMods.length);
            this.setApplicationModules(appCtx, newMods);
         }
      }
   }
}
