package weblogic.application.compiler.flow;

import java.io.File;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.Compiler;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.FactoryException;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.application.WarDetector;
import weblogic.utils.compiler.ToolFailureException;

public final class AppCompilerFlow extends CompilerFlow {
   private File sourceFile;

   public AppCompilerFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.sourceFile = this.ctx.getSourceFile();
      this.compileInput(this.ctx);
      if (this.ctx.getTargetArchive() != null) {
         AppcUtils.createOutputArchive(this.ctx.getTargetArchive(), this.ctx.getOutputDir());
      }

      if (this.ctx.isVerbose()) {
         J2EELogger.logCompilationComplete();
      }

   }

   public void cleanup() {
   }

   private Compiler createCompiler(CompilerCtx ctx) throws ToolFailureException {
      FactoryException creationException = null;

      try {
         return ToolsFactoryManager.createCompiler(ctx);
      } catch (FactoryException var5) {
         Loggable l = null;
         if (this.sourceFile.isDirectory()) {
            l = J2EELogger.logAppcNoValidModuleFoundInDirectoryLoggable(this.sourceFile.getAbsolutePath());
         } else {
            String lowerCaseSourceName = this.sourceFile.getName().toLowerCase();
            if (lowerCaseSourceName.endsWith(".jar")) {
               l = J2EELogger.logAppcJarNotValidLoggable(this.sourceFile.getAbsolutePath());
            } else if (WarDetector.instance.suffixed(lowerCaseSourceName)) {
               l = J2EELogger.logAppcWarNotValidLoggable(this.sourceFile.getAbsolutePath());
            } else if (lowerCaseSourceName.endsWith(".rar")) {
               l = J2EELogger.logAppcRarNotValidLoggable(this.sourceFile.getAbsolutePath());
            } else {
               l = J2EELogger.logAppcEarNotValidLoggable(this.sourceFile.getAbsolutePath());
            }
         }

         l.log();
         throw new ToolFailureException(l.getMessage(), var5);
      }
   }

   private void compileInput(CompilerCtx ctx) throws ToolFailureException {
      Compiler comp = this.createCompiler(ctx);

      try {
         comp.compile();
      } catch (ToolFailureException var5) {
         throw var5;
      } catch (Throwable var6) {
         Loggable l = J2EELogger.logAppcErrorProcessingFileLoggable(this.sourceFile.getAbsolutePath(), StackTraceUtils.throwable2StackTrace(var6));
         throw new ToolFailureException(l.getMessage(), var6);
      }
   }
}
