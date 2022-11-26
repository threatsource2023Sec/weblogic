package weblogic.application.compiler;

import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.compiler.flow.ManifestFlow;
import weblogic.application.compiler.flow.SingleModuleCompileFlow;
import weblogic.application.compiler.flow.ToolsExtensionCompileFlow;

final class SingleModuleCompiler extends BaseCompiler {
   SingleModuleCompiler(CompilerCtx ctx) {
      super(new CompilerFlow[]{new SingleModuleCompileFlow(ctx), new ToolsExtensionCompileFlow(ctx), new ManifestFlow(ctx)});
   }
}
