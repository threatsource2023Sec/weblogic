package weblogic.application.compiler.flow;

import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ModuleState;
import weblogic.application.compiler.ToolsExtension;
import weblogic.application.compiler.ToolsModule;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.compiler.ToolFailureException;

public class ModuleClassLoaderFlow extends CompilerFlow {
   public ModuleClassLoaderFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      ToolsModule[] var1 = this.ctx.getModules();
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         ToolsModule module = var1[var3];
         ModuleState state = this.ctx.getModuleState(module);
         ClassFinder finder = module.init(state, this.ctx, this.ctx.getAppClassLoader());
         state.init(this.ctx.getAppClassLoader(), finder);
      }

      ToolsExtension[] var7 = this.ctx.getToolsExtensions();
      var2 = var7.length;

      for(var3 = 0; var3 < var2; ++var3) {
         ToolsExtension extension = var7[var3];
         extension.init(this.ctx, this.ctx.getAppClassLoader());
      }

   }

   public void cleanup() throws ToolFailureException {
   }
}
