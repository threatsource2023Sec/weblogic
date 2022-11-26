package weblogic.application.compiler;

import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.compiler.flow.ModuleViewerFlow;
import weblogic.application.compiler.flow.SingleModuleMergeFlow;

final class ReadOnlySingleModuleMerger extends BaseMerger {
   ReadOnlySingleModuleMerger(CompilerCtx ctx) {
      super(new CompilerFlow[]{new SingleModuleMergeFlow(ctx), new ModuleViewerFlow(ctx)});
   }
}
