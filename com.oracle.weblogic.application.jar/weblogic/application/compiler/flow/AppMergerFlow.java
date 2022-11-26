package weblogic.application.compiler.flow;

import java.io.File;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.FactoryException;
import weblogic.application.compiler.Merger;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.compiler.ToolFailureException;

public final class AppMergerFlow extends CompilerFlow {
   private File sourceFile;
   private Merger merger;

   public AppMergerFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.sourceFile = this.ctx.getSourceFile();
      this.mergeInput(this.ctx);
   }

   public void cleanup() throws ToolFailureException {
      this.merger.cleanup();
      if (!this.ctx.isReadOnlyInvocation() && this.ctx.getTargetArchive() != null) {
         AppcUtils.createOutputArchive(this.ctx.getTargetArchive(), this.ctx.getOutputDir());
      }

   }

   private Merger createMerger(CompilerCtx ctx) throws ToolFailureException {
      File f = ctx.getSourceFile();

      try {
         return ToolsFactoryManager.createMerger(ctx);
      } catch (FactoryException var5) {
         Loggable l = J2EELogger.logAppcNoValidModuleFoundInDirectoryLoggable(this.sourceFile.getAbsolutePath());
         l.log();
         throw new ToolFailureException(l.getMessage(), var5);
      }
   }

   private void mergeInput(CompilerCtx ctx) throws ToolFailureException {
      this.merger = this.createMerger(ctx);

      try {
         this.merger.merge();
      } catch (ToolFailureException var4) {
         throw var4;
      } catch (Throwable var5) {
         Loggable l = J2EELogger.logAppcErrorProcessingFileLoggable(this.sourceFile.getAbsolutePath(), StackTraceUtils.throwable2StackTrace(var5));
         throw new ToolFailureException(l.getMessage(), var5);
      }
   }
}
