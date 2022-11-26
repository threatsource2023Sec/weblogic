package weblogic.application.compiler;

import weblogic.application.compiler.flow.ApplicationViewerFlow;
import weblogic.application.compiler.flow.CheckLibraryReferenceFlow;
import weblogic.application.compiler.flow.CleanupModulesFlow;
import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.compiler.flow.CopyLibrariesFlow;
import weblogic.application.compiler.flow.CustomModuleFlow;
import weblogic.application.compiler.flow.CustomModuleProviderFlow;
import weblogic.application.compiler.flow.DescriptorParsingFlow;
import weblogic.application.compiler.flow.EarClassLoaderFlow;
import weblogic.application.compiler.flow.ExplodeModulesFlow;
import weblogic.application.compiler.flow.ImportLibrariesFlow;
import weblogic.application.compiler.flow.InitModulesFlow;
import weblogic.application.compiler.flow.LibraryDirectoryFlow;
import weblogic.application.compiler.flow.MergeModuleFlow;
import weblogic.application.compiler.flow.ModuleClassLoaderFlow;
import weblogic.application.compiler.flow.ParseAnnotationsFlow;
import weblogic.application.compiler.flow.PrepareModulesFlow;
import weblogic.application.compiler.flow.VerifyOutputDirFlow;
import weblogic.application.compiler.flow.WriteDescriptorsFlow;
import weblogic.application.compiler.flow.WriteModulesFlow;

class EARMerger extends BaseMerger {
   EARMerger(CompilerCtx ctx) {
      super(new CompilerFlow[]{new EarClassLoaderFlow(ctx), new VerifyOutputDirFlow(ctx), new DescriptorParsingFlow(ctx), new ImportLibrariesFlow(ctx), new LibraryDirectoryFlow(ctx), new ParseAnnotationsFlow(ctx, false), new CheckLibraryReferenceFlow(ctx, ctx.verifyLibraryReferences()), new CustomModuleProviderFlow(ctx), new InitModulesFlow(ctx), new PrepareModulesFlow(ctx), new CopyLibrariesFlow(ctx), new ModuleClassLoaderFlow(ctx), new CustomModuleFlow(ctx), new ExplodeModulesFlow(ctx, true), new CleanupModulesFlow(ctx), new MergeModuleFlow(ctx), new WriteModulesFlow(ctx), new WriteDescriptorsFlow(ctx), new ApplicationViewerFlow(ctx)});
   }
}
