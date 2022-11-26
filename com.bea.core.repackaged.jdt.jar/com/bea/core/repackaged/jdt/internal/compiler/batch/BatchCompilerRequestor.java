package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.ICompilerRequestor;

public class BatchCompilerRequestor implements ICompilerRequestor {
   private Main compiler;
   private int lineDelta = 0;

   public BatchCompilerRequestor(Main compiler) {
      this.compiler = compiler;
   }

   public void acceptResult(CompilationResult compilationResult) {
      if (compilationResult.lineSeparatorPositions != null) {
         int unitLineCount = compilationResult.lineSeparatorPositions.length;
         this.lineDelta += unitLineCount;
         if (this.compiler.showProgress && this.lineDelta > 2000) {
            this.compiler.logger.logProgress();
            this.lineDelta = 0;
         }
      }

      this.compiler.logger.startLoggingSource(compilationResult);
      if (compilationResult.hasProblems() || compilationResult.hasTasks()) {
         this.compiler.logger.logProblems(compilationResult.getAllProblems(), compilationResult.compilationUnit.getContents(), this.compiler);
         this.reportProblems(compilationResult);
      }

      this.compiler.outputClassFiles(compilationResult);
      this.compiler.logger.endLoggingSource();
   }

   protected void reportProblems(CompilationResult result) {
   }
}
