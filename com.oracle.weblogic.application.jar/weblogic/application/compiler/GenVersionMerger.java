package weblogic.application.compiler;

import weblogic.application.compiler.flow.CheckLibraryReferenceFlow;
import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.compiler.flow.DescriptorParsingFlow;
import weblogic.application.compiler.flow.EarClassLoaderFlow;
import weblogic.application.compiler.flow.GenVersionFlow;
import weblogic.application.compiler.flow.ImportLibrariesFlow;
import weblogic.application.compiler.flow.InitModulesFlow;
import weblogic.application.compiler.flow.MergeModuleFlow;
import weblogic.application.compiler.flow.ModuleClassLoaderFlow;
import weblogic.application.compiler.flow.PrepareModulesFlow;
import weblogic.application.compiler.flow.SingleModuleMergeFlow;

public class GenVersionMerger extends BaseMerger {
   GenVersionMerger(CompilerCtx ctx, boolean ear) {
      super(ear ? new CompilerFlow[]{new EarClassLoaderFlow(ctx), new DescriptorParsingFlow(ctx), new ImportLibrariesFlow(ctx), new CheckLibraryReferenceFlow(ctx, ctx.verifyLibraryReferences()), new InitModulesFlow(ctx), new PrepareModulesFlow(ctx), new ModuleClassLoaderFlow(ctx), new MergeModuleFlow(ctx), new GenVersionFlow(ctx)} : new CompilerFlow[]{new SingleModuleMergeFlow(ctx), new GenVersionFlow(ctx)});
   }

   GenVersionMerger(CompilerCtx ctx) {
      this(ctx, true);
   }
}
