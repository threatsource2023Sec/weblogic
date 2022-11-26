package com.bea.core.repackaged.jdt.internal.compiler.problem;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;

public class AbortType extends AbortCompilationUnit {
   private static final long serialVersionUID = -5882417089349134385L;

   public AbortType(CompilationResult compilationResult, CategorizedProblem problem) {
      super(compilationResult, problem);
   }
}
