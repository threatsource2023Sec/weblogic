package weblogic.application.compiler.flow;

import weblogic.application.compiler.CompilerCtx;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.utils.compiler.ToolFailureException;

public final class CheckLibraryReferenceFlow extends CompilerFlow {
   private final boolean isError;

   public CheckLibraryReferenceFlow(CompilerCtx ctx) {
      this(ctx, true);
   }

   public CheckLibraryReferenceFlow(CompilerCtx ctx, boolean isError) {
      super(ctx);
      this.isError = isError;
   }

   public void compile() throws ToolFailureException {
      try {
         LibraryLoggingUtils.verifyLibraryReferences(this.ctx.getLibraryManagerAggregate(), this.isError);
      } catch (LoggableLibraryProcessingException var2) {
         if (this.isError) {
            throw new ToolFailureException(var2.getLoggable().getMessage(), var2);
         }

         var2.getLoggable().log();
      }

   }

   public void cleanup() {
   }
}
