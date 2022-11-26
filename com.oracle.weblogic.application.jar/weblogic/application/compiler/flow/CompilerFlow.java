package weblogic.application.compiler.flow;

import weblogic.application.compiler.CompilerCtx;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.compiler.ToolFailureException;

public abstract class CompilerFlow {
   protected final CompilerCtx ctx;
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainerTools");

   public CompilerFlow(CompilerCtx ctx) {
      this.ctx = ctx;
   }

   public abstract void compile() throws ToolFailureException;

   public abstract void cleanup() throws ToolFailureException;
}
