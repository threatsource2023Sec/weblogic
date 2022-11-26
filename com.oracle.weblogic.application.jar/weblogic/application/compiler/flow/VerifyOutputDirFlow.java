package weblogic.application.compiler.flow;

import java.io.File;
import weblogic.application.compiler.CompilerCtx;
import weblogic.utils.compiler.ToolFailureException;

public final class VerifyOutputDirFlow extends CompilerFlow {
   public VerifyOutputDirFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      if (this.ctx.isSplitDir()) {
         File sourceFile = this.ctx.getSourceFile();
         File outputDir = this.ctx.getOutputDir();
         if (sourceFile.equals(outputDir)) {
            throw new ToolFailureException("Cannot merge into splitdir please set -output to a different dir/ear");
         }
      }
   }

   public void cleanup() {
   }
}
