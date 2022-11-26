package weblogic.application.internal.flow;

import java.util.Iterator;
import weblogic.application.AppDeploymentExtension;
import weblogic.application.internal.FlowContext;
import weblogic.management.DeploymentException;

public class AppDeploymentExtensionPostInitFlow extends BaseFlow {
   public AppDeploymentExtensionPostInitFlow(FlowContext appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      Iterator var1 = this.appCtx.getAppDeploymentExtensions(FlowContext.ExtensionType.POST).iterator();

      while(var1.hasNext()) {
         AppDeploymentExtension extension = (AppDeploymentExtension)var1.next();
         extension.init(this.appCtx);
      }

   }
}
