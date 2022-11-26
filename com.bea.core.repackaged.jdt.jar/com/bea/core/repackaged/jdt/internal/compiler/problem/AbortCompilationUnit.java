package com.bea.core.repackaged.jdt.internal.compiler.problem;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import java.io.IOException;

public class AbortCompilationUnit extends AbortCompilation {
   private static final long serialVersionUID = -4253893529982226734L;
   public String encoding;

   public AbortCompilationUnit(CompilationResult compilationResult, CategorizedProblem problem) {
      super(compilationResult, problem);
   }

   public AbortCompilationUnit(CompilationResult compilationResult, IOException exception, String encoding) {
      super(compilationResult, (Throwable)exception);
      this.encoding = encoding;
   }
}
