package weblogic.management.deploy.internal;

import java.util.Iterator;
import java.util.Map;
import weblogic.application.utils.XMLWriter;
import weblogic.deploy.internal.diagnostics.ImageProvider;

public class AppRuntimeStateImageProvider extends ImageProvider {
   public void writeDiagnosticImage(XMLWriter writer) {
      AppRuntimeStateManager manager = AppRuntimeStateManager.getManager();
      Iterator iter = manager.getAppStates().iterator();

      while(iter.hasNext() && !this.timedOut) {
         ApplicationRuntimeState state = (ApplicationRuntimeState)((Map.Entry)iter.next()).getValue();
         writer.addElement("app-runtime-state");
         writer.addElement("app-id", "" + state.getAppId());
         writer.addElement("retire-timeout-secs", "" + state.getRetireTimeoutSeconds());
         writer.addElement("retire-time-millis", "" + state.getRetireTimeMillis());
         if (state.getModules() != null) {
            writer.addElement("module-state", state.getModules().toString());
         }

         if (state.getAppTargetState() != null) {
            writer.addElement("app-target-state", state.getAppTargetState().toString());
         }

         if (state.getDeploymentVersion() != null) {
            writer.addElement("deployment-version", state.getDeploymentVersion().toString());
         }

         writer.closeElement();
         writer.flush();
      }

   }
}
