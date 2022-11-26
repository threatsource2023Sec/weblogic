package weblogic.application.compiler.flow;

import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsModule;
import weblogic.utils.compiler.ToolFailureException;

public class CleanupModulesFlow extends CompilerFlow {
   public CleanupModulesFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
   }

   public void cleanup() throws ToolFailureException {
      ToolsModule[] modules = this.ctx.getModules();

      for(int i = 0; i < modules.length; ++i) {
         modules[i].cleanup();
         this.ctx.getModuleState(modules[i]).cleanup();
      }

   }
}
