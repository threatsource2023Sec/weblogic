package weblogic.application.compiler.flow;

import java.util.Map;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.internal.flow.CustomModuleHelper;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.management.DeploymentException;
import weblogic.utils.compiler.ToolFailureException;

public final class CustomModuleProviderFlow extends CompilerFlow {
   public CustomModuleProviderFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      try {
         WeblogicExtensionBean extDD = this.ctx.getWLExtensionDD();
         Map factoryMap = CustomModuleHelper.initFactories(extDD, this.ctx.getAppClassLoader());
         if (factoryMap != null) {
            this.ctx.setCustomModuleFactories(factoryMap);
         }
      } catch (DeploymentException var3) {
         throw new ToolFailureException("Encountered exception in initing custom factories", var3);
      }
   }

   public void cleanup() {
   }
}
