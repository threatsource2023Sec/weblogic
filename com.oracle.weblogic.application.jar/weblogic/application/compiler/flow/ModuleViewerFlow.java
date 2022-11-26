package weblogic.application.compiler.flow;

import java.io.IOException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;
import weblogic.descriptor.DescriptorBean;
import weblogic.utils.compiler.ToolFailureException;

public class ModuleViewerFlow extends CompilerFlow {
   public ModuleViewerFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      EditableDeployableObjectFactory objectFactory = this.ctx.getObjectFactory();
      if (objectFactory != null) {
         ToolsModule[] modules = this.ctx.getModules();
         if (modules.length != 1) {
            throw new AssertionError("ModuleViewerFlow can be invoked for standalone modules only");
         }

         try {
            EditableDeployableObject deployableObject = this.ctx.getModuleState(modules[0]).buildDeploymentView(objectFactory);
            modules[0].enhanceDeploymentView(deployableObject);
            this.ctx.setDeployableApplication(deployableObject);
         } catch (IOException var4) {
            throw new ToolFailureException("Unable to create deployable object", var4);
         }
      }

   }

   protected void dump(ApplicationDescriptor appDesc) {
      try {
         DescriptorBean app = (DescriptorBean)appDesc.getApplicationDescriptor();
         app.getDescriptor().toXML(System.out);
         DescriptorBean wlapp = (DescriptorBean)appDesc.getWeblogicApplicationDescriptor();
         wlapp.getDescriptor().toXML(System.out);
         DescriptorBean wlext = (DescriptorBean)appDesc.getWeblogicExtensionDescriptor();
         wlext.getDescriptor().toXML(System.out);
      } catch (Exception var5) {
      }

   }

   public void cleanup() {
   }
}
