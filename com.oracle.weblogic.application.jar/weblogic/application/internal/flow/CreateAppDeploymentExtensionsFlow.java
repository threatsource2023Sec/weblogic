package weblogic.application.internal.flow;

import java.util.Iterator;
import weblogic.application.AppDeploymentExtension;
import weblogic.application.AppDeploymentExtensionFactory;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.internal.FlowContext;
import weblogic.management.DeploymentException;

public class CreateAppDeploymentExtensionsFlow extends BaseFlow {
   public CreateAppDeploymentExtensionsFlow(FlowContext appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      Iterator iter = afm.getAppExtensionFactories();

      AppDeploymentExtension extension;
      while(iter.hasNext()) {
         AppDeploymentExtensionFactory factory = (AppDeploymentExtensionFactory)iter.next();
         extension = factory.createPreProcessorExtension(this.appCtx);
         if (extension != null) {
            this.appCtx.addAppDeploymentExtension(extension, FlowContext.ExtensionType.PRE);
         }

         extension = factory.createPostProcessorExtension(this.appCtx);
         if (extension != null) {
            this.appCtx.addAppDeploymentExtension(extension, FlowContext.ExtensionType.POST);
         }
      }

      Iterator var5 = this.appCtx.getAppDeploymentExtensions(FlowContext.ExtensionType.PRE).iterator();

      while(var5.hasNext()) {
         extension = (AppDeploymentExtension)var5.next();
         extension.init(this.appCtx);
      }

   }
}
