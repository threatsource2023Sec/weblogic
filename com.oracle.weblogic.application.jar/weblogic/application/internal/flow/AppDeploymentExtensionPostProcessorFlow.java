package weblogic.application.internal.flow;

import java.util.Set;
import weblogic.application.internal.FlowContext;

public class AppDeploymentExtensionPostProcessorFlow extends AppDeploymentExtensionFlow {
   public AppDeploymentExtensionPostProcessorFlow(FlowContext appCtx) {
      super(appCtx);
   }

   protected Set getExtensions() {
      return this.appCtx.getAppDeploymentExtensions(FlowContext.ExtensionType.POST);
   }
}
