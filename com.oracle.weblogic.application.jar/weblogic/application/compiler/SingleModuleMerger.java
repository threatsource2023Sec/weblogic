package weblogic.application.compiler;

import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.compiler.flow.ModuleViewerFlow;
import weblogic.application.compiler.flow.SingleModuleMergeFlow;
import weblogic.application.compiler.flow.WriteDescriptorsFlow;
import weblogic.application.compiler.flow.WriteModulesFlow;

final class SingleModuleMerger extends BaseMerger {
   SingleModuleMerger(CompilerCtx ctx) {
      super(new CompilerFlow[]{new SingleModuleMergeFlow(ctx), new WriteModulesFlow(ctx), new WriteDescriptorsFlow(ctx), new ModuleViewerFlow(ctx)});
   }
}
