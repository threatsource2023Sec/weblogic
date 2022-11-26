package weblogic.application.compiler;

import weblogic.application.compiler.flow.CheckLibraryReferenceFlow;
import weblogic.application.compiler.flow.CleanupModulesFlow;
import weblogic.application.compiler.flow.CompileModuleFlow;
import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.compiler.flow.DescriptorParsingFlow;
import weblogic.application.compiler.flow.EarClassLoaderFlow;
import weblogic.application.compiler.flow.ExplodeModulesFlow;
import weblogic.application.compiler.flow.ImportLibrariesFlow;
import weblogic.application.compiler.flow.InitModulesFlow;
import weblogic.application.compiler.flow.LibraryDirectoryFlow;
import weblogic.application.compiler.flow.ManifestFlow;
import weblogic.application.compiler.flow.ModuleClassLoaderFlow;
import weblogic.application.compiler.flow.ParseAnnotationsFlow;
import weblogic.application.compiler.flow.PrepareModulesFlow;
import weblogic.application.compiler.flow.ToolsExtensionCompileFlow;
import weblogic.application.compiler.flow.WriteInferredDescriptorFlow;
import weblogic.utils.compiler.ToolFailureException;

public final class EARCompiler implements Compiler {
   private final CompilerFlow[] flow;

   EARCompiler(CompilerCtx ctx) {
      this.flow = new CompilerFlow[]{new EarClassLoaderFlow(ctx), new DescriptorParsingFlow(ctx), new ImportLibrariesFlow(ctx), new LibraryDirectoryFlow(ctx), new ParseAnnotationsFlow(ctx, true), new CheckLibraryReferenceFlow(ctx, false), new InitModulesFlow(ctx), new PrepareModulesFlow(ctx), new ExplodeModulesFlow(ctx), new CleanupModulesFlow(ctx), new ModuleClassLoaderFlow(ctx), new CompileModuleFlow(ctx), new WriteInferredDescriptorFlow(ctx), new ManifestFlow(ctx), new ToolsExtensionCompileFlow(ctx)};
   }

   public void compile() throws ToolFailureException {
      (new FlowDriver()).run(this.flow);
   }
}
