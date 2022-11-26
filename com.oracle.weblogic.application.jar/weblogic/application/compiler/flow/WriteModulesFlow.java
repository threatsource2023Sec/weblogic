package weblogic.application.compiler.flow;

import java.util.Iterator;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleExtension;
import weblogic.utils.compiler.ToolFailureException;

public class WriteModulesFlow extends CompilerFlow {
   public WriteModulesFlow(CompilerCtx ctx) {
      super(ctx);
   }

   private void writeModules(ToolsModule[] modules) throws ToolFailureException {
      for(int count = 0; count < modules.length; ++count) {
         modules[count].write();
         Iterator var3 = this.ctx.getModuleState(modules[count]).getExtensions().iterator();

         while(var3.hasNext()) {
            ToolsModuleExtension modExtension = (ToolsModuleExtension)var3.next();
            modExtension.write();
         }
      }

   }

   public void compile() throws ToolFailureException {
      this.writeModules(this.ctx.getModules());
      ToolsModule[] customModules = this.ctx.getCustomModules();
      if (customModules != null) {
         this.writeModules(customModules);
      }

   }

   public void cleanup() throws ToolFailureException {
   }
}
