package weblogic.application.compiler;

import weblogic.application.compiler.flow.ApplicationViewerFlow;
import weblogic.application.compiler.flow.CleanupModulesFlow;
import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.compiler.flow.CustomModuleFlow;
import weblogic.application.compiler.flow.CustomModuleProviderFlow;
import weblogic.application.compiler.flow.DescriptorParsingFlow;
import weblogic.application.compiler.flow.EarClassLoaderFlow;
import weblogic.application.compiler.flow.InitModulesFlow;
import weblogic.application.compiler.flow.MergeModuleFlow;
import weblogic.application.compiler.flow.ModuleClassLoaderFlow;
import weblogic.application.compiler.flow.ParseAnnotationsFlow;
import weblogic.application.compiler.flow.PrepareModulesFlow;

class EarReader extends BaseMerger {
   EarReader(CompilerCtx ctx) {
      super(new CompilerFlow[]{new EarClassLoaderFlow(ctx), new ParseAnnotationsFlow(ctx, false), new DescriptorParsingFlow(ctx), new CustomModuleProviderFlow(ctx), new InitModulesFlow(ctx), new PrepareModulesFlow(ctx), new ModuleClassLoaderFlow(ctx), new CustomModuleFlow(ctx), new CleanupModulesFlow(ctx), new MergeModuleFlow(ctx), new ApplicationViewerFlow(ctx)});
   }
}
