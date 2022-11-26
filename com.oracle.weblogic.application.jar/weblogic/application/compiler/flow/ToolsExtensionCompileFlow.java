package weblogic.application.compiler.flow;

import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsExtension;
import weblogic.utils.compiler.ToolFailureException;

public class ToolsExtensionCompileFlow extends CompilerFlow {
   public ToolsExtensionCompileFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      ToolsExtension[] var1 = this.ctx.getToolsExtensions();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ToolsExtension extn = var1[var3];
         extn.compile();
      }

   }

   public void cleanup() throws ToolFailureException {
   }
}
